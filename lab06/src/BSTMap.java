import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        private BSTNode(K key, V value, BSTNode left, BSTNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }


    private BSTNode root;

    private int compareRoots(BSTNode node) {
        return 0;
    }

    public void printInOrder() {
        return;
    }


    @Override
    public void put(K key, V value) {
        if (compareRoots(root) > 0) {
            // root.right = put();
        }
    }

    private BSTNode put(K key, V value, BSTNode root) {
        if (root == null) {
            return new BSTNode(key, value, null, null);
        }
        return root;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
