package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Gong Qiong
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    
    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;
        
        /* Children of this Node. */
        private Node left;
        private Node right;
        
        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }
    
    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */
    
    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }
    
    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }
    
    /**
     * Returns the value mapped to by KEY in the subtree rooted in P.
     * or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        } else if (key.compareTo(p.key) == 0) {
            return p.value;
        } else if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }
    
    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }
    
    /**
     * Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            p = new Node(key, value);
            size += 1;
        } else if (key.compareTo(p.key) == 0) {
            p.value = value;
        } else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }
    
    /**
     * Inserts the key KEY
     * If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }
    
    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }
    
    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////
    
    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> resSet = new HashSet<>();
        addToSet(resSet, root);
        return resSet;
    }
    
    private void addToSet(Set s, Node p) {
        if (p == null) {
            return;
        }
        s.add(p.key);
        addToSet(s, p.left);
        addToSet(s, p.right);
    }
    
    /**
     * Removes KEY from the tree if present
     * returns VALUE removed,
     * null on failed removal.
     */
    @Override
    public V remove(K key) {
        V value = get(key);
        if (value != null) {
            root = removeHelper(key, root);
            size -= 1;
        }
        return value;
    }
    
    private Node removeHelper(K key, Node p) {
        if (p == null) {
            return null;
        } else if (key.compareTo(p.key) == 0) {
            if (p.left == null) {
                return p.right;
            } else if (p.right == null) {
                return p.left;
            } else {
                Node q = p;
                p = min(q.right);
                p.right = removeMin(q.right);
                p.left = q.left;
            }
        } else if (key.compareTo(p.key) < 0) {
            p.left = removeHelper(key, p.left);
        } else {
            p.right = removeHelper(key, p.right);
        }
        return p;
    }
    
    private Node min(Node p) {
        if (p == null) {
            return null;
        }
        if (p.left == null) {
            return p;
        }
        return min(p.left);
    }
    
    private Node removeMin(Node p) {
        if (p == null) {
            return null;
        } else if (p.left == null) {
            p = p.right;
        } else {
            p.left = removeMin(p.left);
        }
        return p;
    }
    
    /**
     * Removes the key-value entry for the specified key only if it is
     * currently mapped to the specified value.  Returns the VALUE removed,
     * null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        size -= 1;
        V keyValue = get(key);
        if (keyValue != value) {
            return null;
        } else {
            remove(key);
        }
        return keyValue;
    }
    
    @Override
    public Iterator<K> iterator() {
        return new MapIterator();
    }
    
    private class MapIterator implements Iterator<K> {
        Node p = root;
        
        public boolean hasNext() {
            return p != null;
        }
        
        public K next() {
            K key = min(p).key;
            p = removeMin(p);
            return key;
        }
    }
}
