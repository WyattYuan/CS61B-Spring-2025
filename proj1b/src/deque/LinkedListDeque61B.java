package deque;

import java.util.ArrayList;
import java.util.Iterator;
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
    int size;

    public LinkedListDeque61B() {
        this.sentinel = new Node<>();
        this.sentinel.next = sentinel;
        this.sentinel.prev = sentinel;
        this.size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node<T> newFirstNode = new Node<>(x, this.sentinel, this.sentinel.next);
        this.sentinel.next.prev = newFirstNode;
        this.sentinel.next = newFirstNode;
        this.size += 1;
    }

    @Override
    public void addLast(T x) {
        Node<T> newLastNode = new Node<>(x, this.sentinel.prev, this.sentinel);
        this.sentinel.prev.next = newLastNode;
        this.sentinel.prev = newLastNode;
        this.size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node<T> p = sentinel.next;
        while (p != sentinel) {
            returnList.addLast(p.item);
            p = p.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return this.sentinel.next == this.sentinel && this.sentinel.prev == this.sentinel;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T removeItem = sentinel.next.item;
        Node<T> newFirst = sentinel.next.next;
        sentinel.next = newFirst;
        newFirst.prev = sentinel;
        return removeItem;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T removeItem = sentinel.prev.item;
        Node<T> newLast = sentinel.prev.prev;
        sentinel.prev = newLast;
        newLast.next = sentinel;
        return removeItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node<T> p;
        if (2 * index > size) {
            p = sentinel.prev;
            for (int i = 0; i < size - index - 1; i++) {
                p = p.prev;
            }
        } else {
            p = sentinel.next;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
        }
        return p.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, Node<T> first) {
        if (index == 0) {
            return first.item;
        }
        return getRecursiveHelper(index - 1, first.next);
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node<T> wizPos;
        private int count;

        public LinkedListDequeIterator() {
            this.wizPos = sentinel.next;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            T returnItem = wizPos.item;
            wizPos = wizPos.next;
            count++;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }
}
