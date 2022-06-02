import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the AList class.
 *  @author Josh Hug
 */

public class AListTest {
    @Test
    public void testEmptySize() {
        AList L = new AList();
        assertEquals(0, L.size());
    }

    @Test
    public void testinsert() {
        AList L = new AList();
        L.addLast(99);
        L.addLast(77);
        L.insert(88,1);
        assertEquals(88, L.get(1));
        assertEquals(3, L.size());
        L.insert(66,55);
        assertEquals(66, L.get(3));
    }

    @Test
    public void testreverse() {
        AList L = new AList();
        L.addLast(99);
        L.addLast(88);
        L.addLast(77);
        L.addLast(66);
        L.addLast(55);
        L.addLast(44);
        L.addLast(33);
        L.reverse();
        assertEquals(44,L.get(1));



    }

    @Test
    public void testAddAndSize() {
        AList L = new AList();
        L.addLast(99);
        L.addLast(99);
        assertEquals(2, L.size());
    }


    @Test
    public void testAddAndGetLast() {
        AList L = new AList();
        L.addLast(99);
        assertEquals(99, L.getLast());
        L.addLast(36);
        assertEquals(36, L.getLast());
    }


    @Test
    public void testGet() {
        AList L = new AList();
        L.addLast(99);
        assertEquals(99, L.get(0));
        L.addLast(36);
        assertEquals(99, L.get(0));
        assertEquals(36, L.get(1));
    }


    @Test
    public void testRemove() {
        AList L = new AList();
        L.addLast(99);
        assertEquals(99, L.get(0));
        L.addLast(36);
        assertEquals(99, L.get(0));
        L.removeLast();
        assertEquals(99, L.getLast());
        L.addLast(100);
        assertEquals(100, L.getLast());
        assertEquals(2, L.size());
    }

    /** Tests insertion of a large number of items.*/
    @Test
    public void testMegaInsert() {
        AList L = new AList();
        int N = 1000000;
        for (int i = 0; i < N; i += 1) {
            L.addLast(i);
        }

        for (int i = 0; i < N; i += 1) {
            L.addLast(L.get(i));
        }
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests("all", AListTest.class);
    }
} 