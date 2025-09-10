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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Node<?> node = (Node<?>) o;
        return (
            this.data.equals(node.data)
            && this.next == node.next // Check if the next reference is the same to avoid infinite loop
            );
    }
}
