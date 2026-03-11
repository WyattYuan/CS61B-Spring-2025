import com.github.javaparser.quality.NotNull;
import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.*;

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
        for (K key : this) {
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
        return get(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> returnSet = new LinkedHashSet<>();
        for (K key : this) {
            returnSet.add(key);
        }
        return returnSet;
    }

    @Override
    public V remove(K key) {
        V r = get(key);
        if (r != null) { // 只有在获取的值不为 null 时，才会执行大括号内的逻辑
            root = remove(root, key); // 必须接收返回值以更新树的物理入口
            size -= 1; // 仅在确认找到并删除后，全局减少 1 次
        }
        return r;
    }

    /**
     * 从以 x 为根的子树中递归删除指定的 key。
     *
     * @param x   当前子树的根节点
     * @param key 要删除的键
     * @return 删除操作完成后，该子树的新根节点引用
     */
    private BSTNode remove(BSTNode x, K key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = remove(x.left, key);
        } else if (cmp > 0) {
            x.right = remove(x.right, key);
        } else {
            // 场景一 & 二：只有一个孩子或没有孩子
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;

            // 场景三：有两个孩子
            BSTNode t = x;
            // x 的替代是右子树的最小孩子，因为最后一步要return x，所以x应该更新为右子树的最小孩子
            x = getMinNode(t.right);
            // x 的右孩子删除了最小节点的原右子树
            x.right = deleteMin(t.right);
            // x 的左孩子不变
            x.left = t.left;
        }
        return x;
    }

    @NonNull
    private BSTNode getMinNode(BSTNode root) {
        if (root.left == null) return root;
        return getMinNode(root.left);
    }

    /**
     * 删除以 x 为根的子树中的最小节点
     *
     * @param x 当前子树根节点
     * @return 删除后的新子树根节点
     */
    private BSTNode deleteMin(BSTNode x) {
        // 如果左边没了，说明我就是最小的
        if (x.left == null) {
            return x.right; // 返回我的右孩子，让我的父亲指向它，我就被“跳过”了
        }

        // 递归向左找，并将返回的新子树重新连接到左边
        x.left = deleteMin(x.left);

        return x;
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
