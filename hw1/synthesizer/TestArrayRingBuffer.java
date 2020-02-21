package synthesizer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the ArrayRingBuffer class.
 *
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void enqueueTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        arb.enqueue(1);
        assertEquals(arb.fillCount, 1);
    }
    
    @Test
    public void isEmptyTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        assertTrue(arb.isEmpty());
    }
    
    @Test
    public void iteratorTest() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i += 1) {
            arb.enqueue(Math.random());
        }
        for (double i : arb) {
            System.out.println(i);
        }
    }
    
    /**
     * Calls tests for ArrayRingBuffer.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
