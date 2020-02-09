public class LinkedListDeque<T> {
    private class Node {
        public Node prev;
        public T item;
        public Node next;

        public Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    /**
     * Creates an empty linked list deque
     */
    public LinkedListDeque() {
        sentinel = new Node(null, (T) "0", null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**
     * add x to the front of the list. No loop or recursion
     */
    public void addFirst(T x) {
        sentinel.next = new Node(sentinel, x, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size++;
    }

    /**
     * add x to the end of the list.
     * No loop or recursion.
     * A single such operation must take “constant time”.
     */
    public void addLast(T x) {
        size++;
        sentinel.prev = new Node(sentinel.prev, x, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        Node p = sentinel;
        while (p.next != sentinel) {
            p = p.next;
            System.out.print(p.item);
            System.out.print(" ");
        }
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size--;
        T res = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return res;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size--;
        T res = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return res;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    /* iteration */
    public T get(int index) {
        if (size == 0 || index >= size) {
            return null;
        }
        Node p = sentinel;
        for (int i = 0; i <= index; i++) {
            p = p.next;
        }
        return p.item;
    }

    /* recursion*/
    /* a helper method*/
    private T getRecursiveHelper(int index, Node p) {
        if (size == 0 || index >= size) {
            return null;
        } else if (index == 0) {
            return p.next.item;
        } else {
            return getRecursiveHelper(index - 1, p.next);
        }
    }

    public T getRecursive(int index) {
        return getRecursiveHelper(index, sentinel);
    }

    /*
    public static void main(String[] args) {
        LinkedListDeque<Integer> L = new LinkedListDeque<>(9);
        L.addLast(3);
        L.addLast(6);
        System.out.println(L.getRecursive(0));
    }
     */
}
