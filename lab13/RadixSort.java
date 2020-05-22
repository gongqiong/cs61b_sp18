import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 */
public class RadixSort {
    private static final int R = 256;
    
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        int numDigits = Integer.MIN_VALUE;
        for (int i = 0; i < asciis.length; i += 1) {
            numDigits = Math.max(numDigits, asciis[i].length());
        }
        String[] sortA = Arrays.copyOf(asciis, asciis.length);
        
        for (int i = numDigits - 1; i >= 0; i -= 1) {
            sortHelperLSD(sortA, i);
        }
        return sortA;
    }
    
    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * @param asciis Input array of Strings
     * @param index  The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        /*
        int[] counts = new int[R + 1];
        String[] sort = new String[asciis.length];
        for (String s : asciis) {
            if (index > s.length() - 1) {
                counts[0] += 1;
            } else {
                int c = s.charAt(index);
                counts[c + 1] += 1;
            }
        }
        int[] starts = new int[R + 1];
        starts[0] = 0;
        for (int i = 1; i < starts.length; i += 1) {
            starts[i] = starts[i - 1] + counts[i - 1];
        }
        
        for (String s : asciis) {
            if (index > s.length() - 1) {
                sort[starts[0]] = s;
                starts[0] += 1;
            } else {
                int c = s.charAt(index);
                sort[starts[c + 1]] = s;
                starts[c + 1] += 1;
            }
        }
        System.arraycopy(sort,0,asciis,0,sort.length);
         */
        Queue<String>[] charToString = new Queue[R+1];
        for (int i = 0; i < R+1; i++) {
            charToString[i] = new LinkedList();
        }
        for (String s : asciis) {
            if (index > s.length() - 1) {
                charToString[0].add(s);
            } else {
                int place = s.charAt(index);
                charToString[place+1].add(s);
            }
        }
        int i = 0;
        for (Queue<String> strings: charToString){
            for (String s: strings){
                asciis[i] =s;
                i+=1;
            }
        }
    }
    
    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start  int for where to start sorting in this method (includes String at start)
     * @param end    int for where to end sorting in this method (does not include String at end)
     * @param index  the index of the character the method is currently sorting on
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
    
    public static void main(String[] args) {
        int i = 'a';
        System.out.println(i);
    }
}
