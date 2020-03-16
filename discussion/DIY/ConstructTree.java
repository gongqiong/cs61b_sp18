public class ConstructTree {
    private class IntTree {
        int item;
        IntTree left, right;
    }
    
    public IntTree constructTree(int[] preorder, int[] inorder) {
        int[] preorderLeft;
        int[] preorderRight;
        int[] inorderLeft;
        int[] inorderRight;
        if (preorder.length == 0) {
            return null;
        }
        IntTree resTree = new IntTree();
        resTree.item = preorder[0];
        int IndexOfRoot = getIndex(inorder, preorder[0]);
        if (IndexOfRoot == 0) {
            preorderLeft = new int[0];
            preorderRight = new int[preorder.length - 1];
            inorderLeft = new int[0];
            inorderRight = new int[inorder.length - 1];
            System.arraycopy(preorder, 1, preorderRight, 0, preorder.length - 1);
            System.arraycopy(inorder, 1, inorderRight, 0, inorder.length - 1);
        } else {
            int IndexOfLeft = getIndex(preorder, inorder[IndexOfRoot - 1]);
            preorderLeft = new int[IndexOfRoot];
            preorderRight = new int[preorder.length - IndexOfLeft-1];
            inorderLeft = new int[IndexOfRoot];
            inorderRight = new int[inorder.length - IndexOfLeft-1];
            System.arraycopy(preorder, 1, preorderLeft, 0, IndexOfRoot);
            System.arraycopy(preorder, IndexOfLeft + 1, preorderRight, 0, preorderRight.length);
            System.arraycopy(inorder, 0, inorderLeft, 0, IndexOfRoot);
            System.arraycopy(inorder, IndexOfRoot + 1, inorderRight, 0, inorderRight.length);
        }
        resTree.left = constructTree(preorderLeft, inorderLeft);
        resTree.right = constructTree(preorderRight, inorderRight);
        return resTree;
    }
    
    private int getIndex(int[] order, int value) {
        for (int i = 0; i < order.length; i += 1) {
            if (order[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        ConstructTree cst = new ConstructTree();
        int[] preorder = {1, 3, 9, 7, 15, 8, 12};
        int[] inorder = {9, 3, 7, 15, 1, 8, 12};
        IntTree resTree = cst.constructTree(preorder, inorder);
    }
}
