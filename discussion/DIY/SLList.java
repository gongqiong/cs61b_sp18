public class SLList<T> {
    private class IntNode {
        public T item;  // not important if the class is private
        public IntNode next;
        
        public IntNode(T first0, IntNode rest0) {
            item = first0;
            next = rest0;
        }
    }
    
    public IntNode first;
    public int size;
    
    public SLList() {
        first = null;
        size = 0;
    }
    
    public SLList(T x) {
        first = new IntNode(x, null);
        size = 1;
    }
    
    /**
     * add x to the front of the list.
     */
    public void addFirst(T x) {
        first = new IntNode(x, first);
        size++;
    }
    
    /* disc03 1.1*/
    
    /**
     * takes in an integer x and inserts it at the given position.
     * If the position is after the end of the list, insert the new node at the end.
     *
     * @param item
     * @param position
     */
    public void insert(T item, int position) {
        if (position == 0 || first == null) {
            addFirst(item);
        }
        IntNode p = first;
        while (p.next != null || position > 1) {
            p = p.next;
            position--;
        }
        p.next = new IntNode(item, p.next);
        size++;
    }
    
    /* disc03 1.2*/
    
    /**
     * reverses the elements
     * should not use new
     */
    /*iterative*/
    public IntNode reverse() {
        IntNode frontOfReversed = null;
        IntNode nextNodeToAdd = first;
        while (nextNodeToAdd != null) {
            IntNode remainToReverse = nextNodeToAdd.next;
            nextNodeToAdd.next = frontOfReversed;
            frontOfReversed = nextNodeToAdd;
            nextNodeToAdd = remainToReverse;
        }
        return frontOfReversed;
    }
    
    /* disc03 1.3*/
    /* recursive*/
    /* helper method*/
    public IntNode reverseHelper(IntNode front) {
        if (front == null || front.next == null) {
            return front;
        } else {
            IntNode reversed = reverseHelper(front.next);
            front.next.next = front;
            front.next = null;
            return reversed;
        }
    }
    
    public void reverseRecursive() {
        first = reverseHelper(first);
    }
    
    public static void main(String[] args) {
        SLList<Integer> L = new SLList<>(9);
        L.addFirst(8);
        L.addFirst(7);
        L.reverseRecursive();
    }
}
