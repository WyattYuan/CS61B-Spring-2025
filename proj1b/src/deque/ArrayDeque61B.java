package deque;

import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    int length;
    int size = 8;
    T[] backArray;
    int nextFirst;
    int nextLast;

    public ArrayDeque61B() {
        this.backArray = (T[]) new Object[size];
        this.length = 0;
        this.nextFirst = size - 1;
        this.nextLast = 0;
    }

    public ArrayDeque61B(T[] args) {
        this.backArray = (T[]) new Object[size];
        int i = 0;
        for (T x : args) {
            if (i == this.size) {
                this.size *= 2;

            }
            this.backArray[i] = x;
            i += 1;
        }
        this.length = 0;
        this.nextFirst = size - 1;
        this.nextLast = 0;
    }

    private boolean isFull() {
        return this.nextFirst == this.nextLast;
    }

    public void resize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        // System.arraycopy(this.backArray, nextFirst + 1, newArray, 0, size - nextFirst - 1);
        // System.arraycopy(this.backArray, 0, newArray, size - nextFirst, nextLast);
        List<T> oldList = toList();
        int i = 0;
        for (T x : oldList) {
            newArray[i] = x;
            i++;
        }
        this.backArray = newArray;
        this.size = newSize;
        this.nextFirst = newSize - 1;
        this.nextLast = this.length;
    }

    @Override
    public void addFirst(T x) {
        if (isFull()) {
            resize(this.size * 2);
        }
        this.backArray[nextFirst] = x;
        this.nextFirst -= 1;
        this.length += 1;
    }

    @Override
    public void addLast(T x) {
        if (isFull()) {
            resize(this.size * 2);
        }
        this.backArray[nextLast] = x;
        this.nextLast += 1;
        this.length += 1;
    }

    @Override
    public List<T> toList() {
        if (size == 0) {
            return List.of();
        }
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            returnList.add(this.backArray[Math.floorMod(i + nextFirst + 1, this.size)]);
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
