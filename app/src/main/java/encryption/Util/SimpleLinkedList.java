package encryption.Util;

import java.util.Iterator;

import encryption.Interface.EncryptedContainer;

public class SimpleLinkedList<T> implements EncryptedContainer<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
    public SimpleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void addFirst(T data) {
        Node<T> node = new Node<>(data);
        node.next = head;

        // if the list is empty tail and head are null, then both should
        // be the same tail = head = node
        if (head == null & tail == null) {
            tail = node;
        }

        head = node;
        size++;
    }

    @Override
    public void addLast(T data) {
        Node<T> node = new Node<>(data);

        // if the list is empty tail and head are null, then both should
        // be the same tail = head = node
        if (head == null & tail == null) {
            head = node;
            tail = head;
            size ++;
            return;
        }

        tail.next = node;
        tail = node;
        size++;
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
    @Override
    public void swapAdjacentData(){
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
    public int size() {
        return size;
    }

    @Override
    public String dataAsString() {
        String message = "";
        Node<T> node = head;

        while (node != null){
            message += node.getData() + "->";
            node = node.next;
        }
        
        message += "null }";
        message = message.replace("->null }", "");

        return message;
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleLinkedListIterator();
    }
    
    @Override
    public String toString(){
        return "SimpleLinkedList{ " + dataAsString() + ", size=" + size + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        
        SimpleLinkedList<?> that = (SimpleLinkedList<?>) o;
        Node<T> thisNode = head;
        Node<?> thatNode = that.head;
        
        boolean equals = thisNode.equals(thatNode);
        while (equals && thisNode != null) {
            thisNode = thisNode.next;
            thatNode = thatNode.next;
            equals = thisNode.equals(thatNode);
        }

        if (!equals) return false;

        equals = thisNode == null && thatNode == null;
        
        return equals;
    }

    private class SimpleLinkedListIterator implements Iterator<T> {
        private Node<T> current = head;

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public T next() {
            T data = current.getData();
            current = current.next;
            return data;
        }
    }
}
