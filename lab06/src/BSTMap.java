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

        private BSTNode() {
            this.key = null;
            this.value = null;
            this.left = null;
            this.right = null;
        }

        private BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private BSTNode root;
    private int size;

    private int compareRoots(BSTNode node) {
        return 0;
    }

    public void printInOrder() {
        for (K key : this){
            System.out.println(key);
        }
    }

    @Override
    public void put(K key, V value) {
        if (this.root == null) {
            this.root = new BSTNode(key, value);
            size += 1;
            return;
        }
        put(key, value, root);
    }

    private void put(K key, V value, BSTNode root) {
        if (key.compareTo(root.key) > 0) {
            if (root.right == null) {
                root.right = new BSTNode(key, value);
                size += 1;
            } else {
                put(key, value, root.right);
            }
        } else if (key.compareTo(root.key) < 0) {
            if (root.left == null) {
                root.left = new BSTNode(key, value);
                size += 1;
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
        } else if (root.key == null) {
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
        } else if (root.key == null) {
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
        return size;
    }

    @Override
    public void clear() {
        root = new BSTNode();
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> returnSet = new LinkedHashSet<>();
        for(K key : this){
            returnSet.add(key);
        }
        return returnSet;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator(root);
    }


    /**
     * An iterator for traversing the keys of a Binary Search Tree (BST) in ascending order.
     * <p>
     * The iterator uses an explicit stack to simulate the in-order traversal of the BST.
     * Keys are returned in the order they would appear in an in-order traversal of the tree.
     * </p>
     *
     * @param <K> the type of keys stored in the BST.
     */
    private class BSTIterator implements Iterator<K> {
        Stack<BSTNode> stack = new Stack<>();

        public BSTIterator(BSTNode root) {
            pushLeft(root);
        }

        private void pushLeft(BSTNode node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            BSTNode node = stack.pop();
            if (node.right != null) {
                pushLeft(node.right);
            }
            return node.key;
        }
    }
}
