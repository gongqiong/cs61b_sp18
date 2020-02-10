public class SLList {
    private static class IntNode {
        public int item;  // not important if the class is private
        public IntNode next;

        public IntNode(int first0, IntNode rest0) {
            item = first0;
            next = rest0;
        }
    }

    public IntNode sentinal;
    public int size;

    public SLList(){
        sentinal = new IntNode(0,null);
        size = 0;
    }

    public SLList(int x){
        sentinal = new IntNode(0, null);
        sentinal.next = new IntNode(x,null);
        size = 1;
    }

    /**
     * add x to the front of the list.
     */
    public void addFirst(int x){
        sentinal.next = new IntNode(x, sentinal.next);
        size++;
    }

    /**
     * get the first item in the list.
     */
    public int getFirst(){
        return sentinal.next.item;
    }

    /**
     * add x to the end of the list.
     */
    public void addLast(int x){
        size++;
        IntNode p = sentinal;
        while(p.next != null){
            p = p.next;
        }
        p.next = new IntNode(x,null);
    }

    /**
     * A method with recursion return the number of items in SLList.
     */
    /* A helper method returns the size of the list that starts at IntNode p. */
    public static int size(IntNode p){
        if (p.next == null){
            return 1;
        }else{
            return 1 + size(p.next);
        }
    }
    public int size(){
        return size(sentinal.next);
    }

    public static void main(String[] args) {
        SLList L = new SLList(9);
        L.addLast(5);
        System.out.println(L.size());
    }
}
