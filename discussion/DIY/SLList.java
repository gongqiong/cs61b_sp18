public class SLList {
    private static class IntNode {
        public int item;  // not important if the class is private
        public IntNode next;

        public IntNode(int first0, IntNode rest0) {
            item = first0;
            next = rest0;
        }
    }

    public IntNode first;

    public SLList(){
        first = null;
    }

    public SLList(int x){
        first = new IntNode(x, null);
    }

    /**
     * add x to the front of the list.
     */
    public void addFirst(int x){
        first = new IntNode(x, first);
    }

    /* disc03 1.1*/

    /**
     * takes in an integer x and inserts it at the given position.
     * If the position is after the end of the list, insert the new node at the end.
     * @param item
     * @param position
     */
    public void insert(int item, int position){
        if (position == 0 || first == null){
            addFirst(item);
        }
        IntNode p = first;
        while (p.next !=null || position > 1){
            p = p.next;
            position--;
        }
        p.next = new IntNode(item, p.next);
    }

    /* disc03 1.2*/
    /**
     * reverses the elements
     * should not use new
     */
    /*iterative*/
    public IntNode reverse(){
        IntNode nextNodeToAdd = first;
        IntNode nextNode = null;
        while (nextNodeToAdd != null){
            IntNode remainToReverse = nextNodeToAdd.next;
            nextNodeToAdd.next = nextNode;
            nextNode = nextNodeToAdd;
            nextNodeToAdd = remainToReverse;
        }
        return nextNode;
    }

    /* disc03 1.3*/
    /* recursive*/
    /* helper method*/
    public IntNode reverseHelper(IntNode front){
        if (front == null || front.next == null){
            return front;
        }else {
            IntNode reversed = reverseHelper(front.next);
            front.next.next = front;
            front.next=null;
            return reversed;
        }
    }
    public void reverseRecursive(){
        if (first.next == null){
            return;
        }

    }
    public static void main(String[] args) {
        SLList L = new SLList(9);
    }
}
