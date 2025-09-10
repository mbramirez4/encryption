package encryption.Interface;

/*
Inheritance of Iterable interface enables using for-each
loops in the implementations of the EncryptedContainer
interface. This way we can hide the implementation
details of the underlying data structure from the user.
*/
public interface EncryptedContainer<T> extends Iterable<T>{
    int size();
    void addFirst(T data);
    void addLast(T data);
    void swapAdjacentData();
    String dataAsString();
}
