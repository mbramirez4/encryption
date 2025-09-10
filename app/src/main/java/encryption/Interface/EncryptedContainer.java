package encryption.Interface;

public interface EncryptedContainer<T> {
    void addFirst(T data);
    void addLast(T data);
    void swapAdjacentData();
    String dataAsString();
}
