package encryption.Util;

public class SimpleLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    
    public SimpleLinkedList() {
        head = null;
        tail = null;
    }

    public void addFirst(T data) {
        Node<T> node = new Node<>(data);
        node.next = head;

        // if the list is empty tail and head are null, then both should
        // be the same tail = head = node
        if (head == null & tail == null) {
            tail = node;
        }

        head = node;
    }

    public void addLast(T data) {
        Node<T> node = new Node<>(data);

        // if the list is empty tail and head are null, then both should
        // be the same tail = head = node
        if (head == null & tail == null) {
            head = node;
            tail = head;
            return;
        }

        tail.next = node;
        tail = node;
    }

    /*
    we have a -> b -> c -> d -> e ...
    after the fist swap we want b -> a -> c -> d -> e ...
    then we want b -> a -> d -> c -> e ...
    then we want b -> a -> d -> c -> f -> e ...

    In the minimum case
    a -> b -> c -> d -> e
    b = a.next
    c = b.next

    to get b -> a -> c
    b.next = a
    a.next = c

    then from b -> a -> c -> d -> e ...
    A = c
    B = A.next (d)
    C = B.next (e)

    to get b -> a -> d -> c -> e ...
    B.next = A (d -> c)
    A.next = C (c -> e) b -> a -> c -> e (d is missing)
    a.next = B (a -> d) b -> a -> d -> c -> e (Bring d back to the linked list)
    */
    public void swapNodes(){
        Node<T> a = head;
        // if the list is empty there is nothing to do
        if (a == null) return;
        

        Node<T> b = a.next;
        // if there is only one element in the list, there is nothing to do
        if (b == null) return;

        Node<T> c = b.next;

        // swap the first time:
        a.next = c;
        b.next = a;
        head = b;

        Node<T> prevA;
        while (c != null) {
            prevA = a;

            a = c;
            b = a.next;
            if (b == null) break;

            c = b.next;

            //Swap
            a.next = c;
            b.next = a;
            prevA.next = b;
        }

        tail = a;
    }

    @Override
    public String toString(){
        String message = "SimpleLinkedList{ ";
        Node<T> node = head;
        while (node != null){
            message += node.toString() + "->" ;
            node = node.next;
        }
        message += "null }";

        return message;
    }

}
