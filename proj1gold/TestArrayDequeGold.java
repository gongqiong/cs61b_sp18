import static org.junit.Assert.*;

import org.junit.Test;

public class TestArrayDequeGold {
    
    /*randomly call StudentArrayDeque and ArrayDequeSolution methods
    until they disagree on an output.*/
    @Test
    public void testArrayDeque() {
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        String operation = "";
    
    
        for (int i = 0; i < 100; i++) {
            int random = StdRandom.uniform(4);
            if (random == 0) {
                student.addFirst(i);
                solution.addFirst(i);
                operation += "addFirst(" + i + ")\n";
            }
            if (random == 1) {
                student.addLast(i);
                solution.addLast(i);
                operation += "addLast(" + i + ")\n";
            }
            if (random == 2) {
                if (student == null || solution == null) {
                    continue;
                }
                Integer s = student.removeFirst();
                Integer exp = solution.removeFirst();
                operation += "removeFirst()\n";
                assertEquals(operation, s, exp);
            }
            if (random == 3) {
                if (student == null || solution == null) {
                    continue;
                }
                Integer s = student.removeLast();
                Integer exp = solution.removeLast();
                operation += "removeLast()\n";
                assertEquals(operation, exp, s);
            }
        }
    }
}
