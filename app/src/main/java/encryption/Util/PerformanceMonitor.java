package encryption.Util;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public final class PerformanceMonitor {

    private static final Logger logger = LogManager.getLogger( "performance" );
    private String processName;
    private String processDetails;
    private String logMessage;

    private long initialTime; // Tiempo de inicio del monitoreo
    private long finalTime; // Tiempo de finalización del monitoreo

    private long initialMemoryUsage; // Uso de memoria de la JVM al inicio del monitoreo
    private long finalMemoryUsage; // Uso de memoria de la JVM al final del monitoreo

    // Metricas OSHI
    private SystemInfo systemInfo; // Información del sistema usando OSHI
    private CentralProcessor processor;
    private GlobalMemory physicalMemory; // Información de la memoria física
    private OperatingSystem os; // Información del sistema operativo
    private OSProcess initialProcess;

    public PerformanceMonitor( String processName, String processDetails){
        this.processName = processName;
        this.processDetails = processDetails;
        logMessage = "";

        // Inicializar OSHI
        systemInfo = new SystemInfo();
        processor = systemInfo.getHardware().getProcessor();
        physicalMemory = systemInfo.getHardware().getMemory();
        os = systemInfo.getOperatingSystem();
    }

    public void init(){
        initialTime = System.currentTimeMillis();
        initialMemoryUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        
        long[] tmpTicks = processor.getSystemCpuLoadTicks();
        double initialCPUConsumption = processor.getSystemCpuLoadBetweenTicks(tmpTicks) * 100;

        initialProcess = os.getProcess(os.getProcessId());

        logMessage += "\n === PROCESS [ " + processName + " ] === ";
        logMessage += "\n Description : " + processDetails + "\n";
        logMessage += "\n - Initial Memory Usage : " + Math.round(bytesToMegabytes(initialMemoryUsage)) + " MB";
        logMessage += "\n - Initial Overall CPU Consumption : " + String.format( "%.2f", initialCPUConsumption ) + " %";

    }

    public void end(){
        Runtime runtime = Runtime.getRuntime();

        long deltaTime = 0;
        long deltaMemory = 0;

        int threadCount = 0; // Número de hilos activos del proceso actual
        long cpuTime = 0; // Tiempo de CPU usado por el proceso actual
        long physicalMemoryUsed = 0; // Memoria física usada por el sistema

        finalTime = System.currentTimeMillis();
        finalMemoryUsage = runtime.totalMemory() - runtime.freeMemory();

        long[] tmpTicks = processor.getSystemCpuLoadTicks();
        double finalCPUConsumption = processor.getSystemCpuLoadBetweenTicks(tmpTicks) * 100;

        // David's Method:
        OSProcess finalProcess = os.getProcess(os.getProcessId());
        double deltaCPUConsumption = finalProcess.getProcessCpuLoadBetweenTicks(initialProcess) * 100;

        // Calcular las diferencias entre las métricas iniciales y finales
        deltaTime = finalTime - initialTime;
        deltaMemory = finalMemoryUsage - initialMemoryUsage;

        logMessage += "\n - Final Memory Usage : " + Math.round(bytesToMegabytes(finalMemoryUsage)) + " MB";
        logMessage += "\n - Final Overall CPU Consumption : " + String.format( "%.2f", finalCPUConsumption ) + " %";
        logMessage += "\n - Delta Memory Usage : " + Math.round(bytesToMegabytes(deltaMemory)) + " MB";
        logMessage += "\n - Delta CPU Consumption (Current Process) : " + String.format( "%.2f", deltaCPUConsumption ) + " %";
        logMessage += "\n - Exectution Time : " + deltaTime + " ms";
        logMessage += "\n - Free Memory JVM : " + Math.round(bytesToMegabytes(runtime.freeMemory())) + " MB";
        logMessage += "\n =============================== \n";

        // Métricas adicionales
        physicalMemoryUsed = physicalMemory.getTotal() - physicalMemory.getAvailable(); // Calcular memoria física usada
        cpuTime = os.getProcess(os.getProcessId()).getKernelTime() + os.getProcess(os.getProcessId()).getUserTime(); // Calcular tiempo de CPU usado por el proceso
        threadCount = os.getProcess(os.getProcessId()).getThreadCount(); // Obtener el número de hilos activos

        // Agregar métricas adicionales al log
        logMessage += "\n - Total Physical Memory : " + Math.round(bytesToMegabytes(physicalMemory.getTotal())) + " MB";
        logMessage += "\n - Physical Memory Used : " + Math.round(bytesToMegabytes(physicalMemoryUsed)) + " MB";
        logMessage += "\n - Number of Active Threads : " + threadCount;
        logMessage += "\n - CPU Time : " + cpuTime + " ms \n\n";

        logger.info( logMessage );

    }

    private double bytesToMegabytes(long bytes) { return bytes / (1024.0 * 1024.0); }

}
