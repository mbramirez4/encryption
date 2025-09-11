package encryption.Model;

import java.util.ArrayDeque;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import encryption.Interface.EncryptedContainer;
import encryption.Util.PerformanceMonitor;

public final class Message {
    private static final Logger timesLogger = LogManager.getLogger("times");

    private String originalMessage;
    
    private String encryptedMessage;
    private Queue<EncryptedContainer<Integer>> encryptedWords;

    public Message() {
        this("Hello world!");
    }

    public Message(String message) {
        this.originalMessage = message.trim();

        encryptedWords = new ArrayDeque<>();
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }

    public void encryptMessage() {
        // The original message is passed as the description for the
        // process in order to identify if certain messages are more
        // resource intensive than others.
        PerformanceMonitor monitor = new PerformanceMonitor("Message encryption", originalMessage);
        monitor.init();

        long initialTime = System.currentTimeMillis();
        
        encryptedMessage = "";
        EncryptedContainer<Integer> encryptedWord;

        String[] words = originalMessage.split(" ");
        for (String word : words) {
            encryptedWord = Encryptor.encrypt(word);
            encryptedWords.offer(encryptedWord);

            encryptedMessage += encryptedWord.dataAsString() + " ";
        }

        encryptedMessage = encryptedMessage.trim();

        long finalTime = System.currentTimeMillis();
        timesLogger.info("Encryption of a message with " + words.length + " words took " + (finalTime - initialTime) + " ms");

        monitor.end();
    }

    public String decryptMessage() {
        PerformanceMonitor monitor = new PerformanceMonitor("Message decryption", encryptedMessage);
        monitor.init();
        
        long initialTime = System.currentTimeMillis();
        
        String decryptedMessage = "";
        int numberWords = encryptedWords.size();
        EncryptedContainer<Integer> encryptedWord;

        while (!encryptedWords.isEmpty()) {
            encryptedWord = encryptedWords.poll();
            decryptedMessage += Encryptor.decrypt(encryptedWord) + " ";
        }

        decryptedMessage = decryptedMessage.trim();

        long finalTime = System.currentTimeMillis();
        timesLogger.info("Decryption of a message with " + numberWords + " words took " + (finalTime - initialTime) + " ms");

        monitor.end();

        return decryptedMessage;
    }

    public boolean checkDecryptedMessage(String message) {
        return originalMessage.equals(message);
    }

    @Override
    public String toString() {
        return "Message{ encryptedMessage='" + encryptedMessage + "' }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;
        return (
            encryptedMessage.equals(message.encryptedMessage)
            && originalMessage.equals(message.originalMessage)
        );
    }
}
