package deque;

import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    int size;
    int capacity = 8;
    T[] backArray;
    int nextFirst;
    int nextLast;

    @SuppressWarnings("unchecked")
    public ArrayDeque61B() {
        this.backArray = (T[]) new Object[capacity];
        this.size = 0;
        this.nextFirst = capacity - 1;
        this.nextLast = 0;
    }

    @SafeVarargs
    public static <Q> ArrayDeque61B<Q> of(Q... stuff) {
        ArrayDeque61B<Q> returnAD = new ArrayDeque61B<>();
        for (Q x : stuff) {
            returnAD.addLast(x);
        }
        return returnAD;
    }

    private boolean isFull() {
        return this.nextFirst == this.nextLast;
    }

    @SuppressWarnings("unchecked")
    public void resize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        System.arraycopy(this.backArray, nextFirst + 1, newArray, 0, capacity - nextFirst - 1);
        System.arraycopy(this.backArray, 0, newArray, capacity - nextFirst - 1, nextLast);
        this.backArray = newArray;
        this.capacity = newSize;
        this.nextFirst = newSize - 1;
        this.nextLast = this.size;
    }

    @Override
    public void addFirst(T x) {
        if (isFull()) {
            resize(this.capacity * 2);
        }
        this.backArray[nextFirst] = x;
        this.nextFirst -= 1;
        this.size += 1;
    }

    @Override
    public void addLast(T x) {
        if (isFull()) {
            resize(this.capacity * 2);
        }
        this.backArray[nextLast] = x;
        this.nextLast += 1;
        this.size += 1;
    }

    @Override
    public List<T> toList() {
        if (capacity == 0) {
            return List.of();
        }
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(this.backArray[Math.floorMod(i + nextFirst + 1, this.capacity)]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
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
        T returnItem = this.backArray[Math.floorMod(nextFirst + 1, capacity)];
        this.nextFirst = Math.floorMod(nextFirst + 1, capacity);
        this.size -= 1;
        return returnItem;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T returnItem = this.backArray[Math.floorMod(nextLast - 1, capacity)];
        this.nextLast = Math.floorMod(nextLast - 1, capacity);
        this.size -= 1;
        return returnItem;
    }

    @Override
    public T get(int index) {
        if (this.size == 0 || index < 0 || index >= this.size) {
            return null;
        }
        return this.backArray[Math.floorMod(this.nextFirst + index + 1, this.capacity)];
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
