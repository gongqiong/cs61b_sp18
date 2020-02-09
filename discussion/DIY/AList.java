/**
 * Array based list.
 *
 * @author Josh Hug
 */

public class AList {
    int[] items;
    int size;

    /**
     * Creates an empty list.
     */
    public AList() {
        items = new int[100];
        size = 0;
    }

    /**
     * Resizes the underlying array to the target capacity.
     */
    public void resize(int capacity) {
        int[] a = new int[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    /**
     * Inserts X into the back of the list.
     */
    public void addLast(int x) {
        if (size == items.length) {
            resize(size*2);
        }

        items[size] = x;
        size++;
    }

    /**
     * Returns the item from the back of the list.
     */
    public int getLast() {
        return items[size - 1];
    }

    /**
     * Gets the ith item in the list (0 is the front).
     */
    public int get(int i) {
        return items[i];
    }

    /**
     * Returns the number of items in the list.
     */
    public int size() {
        return size;
    }

    /**
     * Deletes item from back of the list and
     * returns deleted item.
     */
    public int removeLast() {
        size--;
        return items[size];
    }
} 