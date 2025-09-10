package encryption.Util;

public class Node<T> {
    public Node<T> next;
    private T data;

    public Node(T data){
        this.data = data;
        this.next = null;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString(){
        return "Node{ data='" + data + "'}";
    }
}
