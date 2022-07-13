package lab7;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K ,V> {
    private Node root;

    @Override
    public void clear() {
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("not null");
        }
        return get(key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return get(root, key);
    }

    /* Returns the value to which the specified key is stored in the corresponding Node,
     * if key is bigger than x's Node, get its right, vice versa,
     * or null if this map contains no mapping for the key.
     */
    private V get(Node x, K key) {
        if (key == null) {
            throw new IllegalArgumentException("not null");
        } else if (x == null) {
            return null;
        }
        int cpRes = key.compareTo(x.key);
        if (cpRes < 0) {
            return get(x.Left, key);
        } else if (cpRes > 0) {
            return get(x.Right, key);
        }
        return x.val;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size(root);
    }
    /* Returns the number of subNodes counting itself. */
    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("not null");
        }
        if (value == null) {
            remove(key);
        }
        root = put(root, key, value);
    }

    /* Puts the key and the value in the Node x, if the key is bigger than x's
     key, put it right, vice versa. */
    private Node put(Node x, K key, V val) {
        if (x == null) {
            return new Node(key, val, 1);
        }
        int cpRes = key.compareTo(x.key);
        if (cpRes < 0) {
            x.Left = put(x.Left, key, val);
        } else if (cpRes > 0) {
            x.Right = put(x.Right, key, val);
        } else {
            x.val = val;
        }
        x.size = 1 + size(x.Left) + size(x.Right);
        return x;
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Next time haha.");
    }
    /* min and max method return the min and max key in the map. */
    private K min() {
        if (root == null) {
            throw new NoSuchElementException("Nah thing in this map");
        }
        return min(root).key;
    }
    private Node min(Node x) {
        if (x.Left == null) {
            return x;
        }
        return min(x.Left);
    }
    private K max() {
        if (root == null) {
            throw new NoSuchElementException("Nah thing in this map");
        }
        return max(root).key;
    }
    private Node max(Node x) {
        if (x.Right == null) {
            return x;
        }
        return max(x.Right);
    }
    /**private Set<K> keys() {
        if (root == null) {
            return new TreeSet<>();
        }
        return keys(min(), max());
    }
    private Set<K> keys(K low, K high) {
        if (low == null) {
            throw new IllegalArgumentException();
        } else if (high == null) {
            throw new IllegalArgumentException();
        }
        Set<K> set = new TreeSet<>();
        keys(root, set, low, high);
        return set;
    }
    private void keys(Node x, Set<K> set, K low, K high) {
        if  (x == null) {
            return;
        }
        int cpLow = low.compareTo(x.key);
        int cpHigh = high.compareTo(x.key);
        if (cpLow < 0) {
            keys(x.Left, set, low, high);
        } else if (cpLow <= 0 && cpHigh >= 0) {
            set.add(x.key);
        } else if (cpHigh > 0) {
            keys(x.Right, set, low, high);
        }
    }*/

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("No such ele!");
        }
        V res = get(key);
        root = remove(root, key);
        return res;
    }
    private Node remove(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cpRes = key.compareTo(x.key);
        if (cpRes < 0) {
            x.Left = remove(x.Left, key);
        } else if (cpRes > 0) {
            x.Right = remove(x.Right, key);
        } else {
            if (x.Left == null) {
                return x.Right;
            } else if (x.Right == null) {
                return x.Left;
            }
            Node cur = x;
            x = min(cur.Right);
            x.Right = removeMin(cur.Right);
            x.Left = cur.Left;
        }
        x.size = size(x.Left) +size(x.Right) + 1;
        return x;
    }
    private Node removeMin(Node x) {
        if (x.Left == null) {
            return x.Right;
        }
        x.Left = removeMin(x.Left);
        x.size = size(x.Left) +size(x.Right) + 1;
        return x;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Do not want to do that yet.");
    }

    public void printInOrder() {
        System.out.println();
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
    public class BSTKeyIterator<K> implements Iterator<K>{
        @Override
        public K next() {
            return null;
        }

        @Override
        public boolean hasNext() {
            return false;
        }
    }

    private class Node {
        private K key;
        private V val;
        private Node Left, Right;
        private int size;

        public Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }
}
