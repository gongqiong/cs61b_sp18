/**
 * RULES:
 * 1. add and remove must take constant time, except during resizing operations.
 * 2. get and size must take constant time.
 * 3. The starting size of your array should be 8.
 * 4. The amount of memory that your program uses at any given time must be proportional to the number of items.
 * 5. For arrays of length 16 or more, your usage factor should always be at least 25%.
 * For smaller arrays, your usage factor can be arbitrarily low.
 */
public class ArrayDeque<T> {

    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    /**
     * Creates an empty array deque.
     */
    public ArrayDeque() {
        nextFirst = 0;
        nextLast = 1;
        items = (T[]) new Object[8];
        size = 0;
    }

    public int plusOne(int index) {
        return Math.floorMod(index + 1, items.length);
    }

    public int plusOne(int index, int length) {
        return Math.floorMod(index + 1, length);
    }

    public int minusOne(int index) {
        return Math.floorMod(index - 1, items.length);
    }

    /**
     * Resizes the underlying array to the target capacity.
     */
    public void resize() {
        if (size == items.length) {
            expand();
        }
        if (size < items.length / 4 && items.length >= 16) {  //RULE 5
            reduce();  //dont worry about the float/int problem because items.length = 16*2...
        }
    }

    public void expand() {
        resizeHelper(items.length * 2);
    }

    public void reduce() {
        resizeHelper(items.length / 2);
    }

    public void resizeHelper(int capacity) {
        int begin = plusOne(nextFirst);
        int end = minusOne(nextLast);
        T[] temp = items;
        items = (T[]) new Object[capacity];
        nextFirst = 0;
        nextLast = 1;
        for (int i = begin; i != end; i = plusOne(i, temp.length)) {
            items[nextLast] = temp[i];
            nextLast = plusOne(nextLast);
        }
        items[nextLast] = temp[end];
        nextLast = plusOne(nextLast);
    }

    /**
     * Adds an item of type T to the front of the deque.
     */
    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size++;
        resize();
    }

    /**
     * Adds an item of type T to the back of the deque.
     */
    public void addLast(T item) {
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size++;
        resize();
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
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
        if (size == 0) {
            return;
        }
        for (int i = plusOne(nextFirst); i != nextLast; i = plusOne(i)) {
            System.out.print(items[i]);
            System.out.print(' ');
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size--;
        T res;
        res = items[plusOne(nextFirst)];
        nextFirst = plusOne(nextFirst);
        items[nextFirst] = null;
        resize();
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
        T res;
        res = items[minusOne(nextLast)];
        nextLast = minusOne(nextLast);
        items[nextLast] = null;
        resize();
        return res;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index) {
        if (size == 0 || index >= size) {
            return null;
        }
        int curr = 0;
        index = Math.floorMod(plusOne(nextFirst)+index, items.length);
        return items[index];
    }

    /**
     * for testing
     *
     * @param args
     */
    public static void main(String[] args) {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        for (int i = 0; i<88;i++){
            L.addLast(i);
        }
        for (int i = 0; i<80; i++){
            L.removeLast();
        }
        L.printDeque();
    }
}
