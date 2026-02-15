import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private static class Node<T> {
        Node<T> prev;
        Node<T> next;
        T item;

        public Node() {
            this.prev = null;
            this.next = null;
            this.item = null;
        }

        public Node(T x, Node<T> prev, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.item = x;
        }
    }

    Node<T> sentinel;

    public LinkedListDeque61B() {
        this.sentinel = new Node<T>();
        this.sentinel.next = sentinel;
        this.sentinel.prev = sentinel;
    }

    @Override
    public void addFirst(T x) {
        Node<T> newFirstNode = new Node<T>(x, this.sentinel, this.sentinel.next);
        this.sentinel.next.prev = newFirstNode;
        this.sentinel.next = newFirstNode;
    }

    @Override
    public void addLast(T x) {
        Node<T> newLastNode = new Node<T>(x, this.sentinel.prev, this.sentinel);
        this.sentinel.prev.next = newLastNode;
        this.sentinel.prev = newLastNode;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node<T> p = sentinel.next;
        while (p != sentinel){
            returnList.addLast(p.item);
            p = p.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
