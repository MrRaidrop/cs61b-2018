import org.junit.Test;
        import static org.junit.Assert.*;

/** Tests the AList class.
 *  @author Josh Hug
 */

public class ArrayDequeTest {
    @Test
    public void testEmptySize() {
        ArrayDeque L = new ArrayDeque();
        assertEquals(0, L.size());
    }

    /**@Test
    public void testinsert() {
        ArrayDeque L = new ArrayDeque();
        L.addLast(99);
        L.addLast(77);
        L.insert(88,1);
        assertEquals(88, L.get(1));
        assertEquals(3, L.size());
        L.insert(66,55);
        assertEquals(66, L.get(3));
    }*/

    /**@Test
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
    }*/

    @Test
    public void testAddAndSize() {
        ArrayDeque L = new ArrayDeque();
        L.addLast(88);
        L.addLast(99);
        assertEquals(99, L.get(2));
        assertEquals(2, L.size());
    }

    @Test
    public void testAddFirst() {
        ArrayDeque L = new ArrayDeque();
        L.addFirst(99);
        L.addFirst(88);
        L.addFirst(77);
        assertEquals(77, L.get(1));
        assertEquals(99, L.get(3));
        assertEquals(3, L.size());
    }


    @Test
    public void testAddAndGetLast() {
        ArrayDeque L = new ArrayDeque();
        L.addLast(99);
        assertEquals(99, L.get(1));
        L.addLast(36);
        assertEquals(36, L.get(2));
        L.addLast(0);
        assertEquals(0, L.get(3));
        assertEquals(3, L.size());
    }

    @Test
    public void testprintArray() {
        ArrayDeque L = new ArrayDeque();
        L.printDeque();
        L.addLast(11);
        L.addLast(22);
        L.addLast(33);
        L.printDeque();
    }


    @Test
    public void testRemoveLastandFirst() {
        ArrayDeque L = new ArrayDeque();
        for (int i = 1; i < 18; i++) {
            L.addLast(i);
        }
        assertEquals(1, L.removeFirst());
        assertEquals(17, L.removeLast());
    }

    /** Tests insertion of a large number of items.*/
    @Test
    public void testMegaInsert() {
        ArrayDeque L = new ArrayDeque();
        int N = 1000000;
        for (int i = 0; i < N; i += 1) {
            L.addLast(i);
        }

        for (int i = 0; i < N; i += 1) {
            L.addLast(L.get(i));
        }
    }

    @Test
    public void testArrayother() {
        ArrayDeque L = new ArrayDeque();
        L.addLast(1);
        L.addLast(2);
        L.addLast(3);
        L.addLast(4);
        assertEquals(2, L.get(2));
        //ArrayDeque S = new ArrayDeque(L);
        //assertEquals(2, S.get(2));
        //assertEquals(1, S.get(1));
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests("all", ArrayDequeTest.class);
    }

}


