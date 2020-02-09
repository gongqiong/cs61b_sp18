public class ArrayDisc {
    /* Disc03 2.1*/

    /**
     * inserts item into array arr at the given position.
     * The method should return the resulting array.
     * If position is past the end of the array, insert item at the end of the array.
     */
    public static int[] insertArray(int[] arr, int item, int position) {
        int[] newarr = new int[arr.length + 1];
        position = Math.min(position, arr.length);
        for (int i = 0; i < position; i++) {
            newarr[i] = arr[i];
        }
        newarr[position] = item;
        for (int i = position; i < arr.length; i++) {
            newarr[i + 1] = arr[i];
        }
        return newarr;
    }

    /* Disc03 2.2*/

    /**
     * destructively reverses the items in arr.
     */
    public static void reverse(int[] arr) {
        int len = arr.length;
        int x = len / 2;
        for (int i = 0; i < x; i++) {
            int temp = arr[i];
            arr[i] = arr[len - i-1];
            arr[len - i-1] = temp;
        }
    }

    /* Disc03 2.3*/
    /**
     * a non-destructive method replicate(int[] arr) that replaces the
     * number at index i with arr[i] copies of itself.
     * [3, 2, 1]
     * [3, 3, 3, 2, 2, 1]
     */
    public static int[] replicate(int[] arr){
        int size = 0;
        for (int item: arr){
            size += item;
        }
        int[] res = new int[size];
        int count = 0;
        for (int item: arr){
            for (int i = 0; i<item; i++ ){
                res[count] = item;
                count++;
            }
        }
        return res;
    }
}
