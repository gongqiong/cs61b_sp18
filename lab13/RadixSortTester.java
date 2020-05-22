import static org.junit.Assert.*;
import org.junit.Test;

public class RadixSortTester {
    public static String[] toBeSorted = {"eat", "dinner", "happy", "fear","sure"};
    public static String[] correct = {"dinner","eat","fear", "happy", "sure"};
    
    @Test
    public void testRadixSort(){
        String[] sorted = RadixSort.sort(toBeSorted);
        for (int i = 0; i<sorted.length;i+=1) {
            assertTrue(sorted[i].equals(correct[i]));
        }
    }
 }
