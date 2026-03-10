import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    public class BSTNode {
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

    public BSTNode root;

    private int compareRoots(BSTNode node) {
        return 0;
    }

    public void printInOrder() {
        return;
    }


    @Override
    public void put(K key, V value) {
        if (this.root == null) {
            this.root = new BSTNode(key, value, null, null);
            return;
        }
        put(key, value, root);
    }

    private void put(K key, V value, BSTNode root) {
        if (key.compareTo(root.key) > 0) {
            if (root.right == null) {
                root.right = new BSTNode(key, value, null, null);
            } else {
                put(key, value, root.right);
            }
        } else if (key.compareTo(root.key) < 0) {
            if (root.left == null) {
                root.left = new BSTNode(key, value, null, null);
            } else {
                put(key, value, root.left);
            }
        } else {
            // key相同时应该更新value
            root.value = value;
        }
    }

    @Override
    public V get(K key) {
        return get(key, this.root);
    }

    private V get(K key, BSTNode root) {
        if (root == null) {
            return null;
        }
        if (key.equals(root.key)) {
            return root.value;
        } else if (key.compareTo(root.key) > 0) {
            return get(key, root.right);
        } else if (key.compareTo(root.key) < 0) {
            return get(key, root.left);
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(key, root);
    }

    private boolean containsKey(K key, BSTNode root) {
        if (root == null) {
            return false;
        }
        if (key.equals(root.key)) {
            return true;
        } else if (key.compareTo(root.key) > 0) {
            return containsKey(key, root.right);
        } else if (key.compareTo(root.key) < 0) {
            return containsKey(key, root.left);
        }
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
