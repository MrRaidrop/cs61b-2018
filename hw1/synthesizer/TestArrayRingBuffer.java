package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(4);
        try {
            arb.dequeue();
        }
        catch (RuntimeException under) {
            System.out.println(under);
        }
        try {
            arb.enqueue(1);
            arb.enqueue(2);
            arb.enqueue(3);
            arb.enqueue(4);
            arb.enqueue(5);
        }
        catch (RuntimeException over) {
            System.out.println(over);
        }
        System.out.println(arb.capacity());
        System.out.println(arb.fillCount());
        System.out.println(arb.dequeue());
        System.out.println(arb.peek());
        System.out.println(arb.fillCount());

    }
}
