package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {

    private static final long seed = 1239100241; // Whatever long seed
    private final Random r = new Random(seed);

    @Test
    public void testBasic() {
        ArrayHeapMinPQ<Double> heap = new ArrayHeapMinPQ<>(1000);
        NaiveMinPQ<Double> nm = new NaiveMinPQ<>();
        int cap = 10000;
        double[] doubles = createRandomList(cap);
        for (int i = 0; i < cap; i++) {
            nm.add(doubles[i], doubles[i]);
            heap.add(doubles[i], doubles[i]);
        }
        assertEquals(0, heap.size() - nm.size());
        assertEquals(true, heap.getSmallest() - nm.getSmallest() < 0.001);
        for (int x = 0; x < r.nextInt(500); x++) {
            nm.removeSmallest();
            heap.removeSmallest();
        }
        assertEquals(0, heap.size() - nm.size());
        assertEquals(true, heap.getSmallest() - nm.getSmallest() < 0.001);

    }

    @Test
    public void testTime() {
        ArrayHeapMinPQ<Double> heap = new ArrayHeapMinPQ<>(1000);
        NaiveMinPQ<Double> nm = new NaiveMinPQ<>();
        int row = 4;
        int baseN = 1000;
        int baseH = 1000;
        List<Integer> dataSize = new ArrayList<>(row);
        // a list of size that will be tested for Naive approach
        List<Integer> dataSizeHeap = new ArrayList<>(row);
        // a list of size that will be tested for Heap
        List<Double> times = new ArrayList<>(row);
        List<Double> timesHeap = new ArrayList<>(row);
        for (int i = 0; i < row; i++) {
            dataSize.add(baseN * (int) Math.pow(2, i));
        }
        for (int i = 0; i < row; i++) {
            dataSizeHeap.add(baseH * (int) Math.pow(2, i));
        }
        int ops = 1000000; // the number of point the set will have.
        List<Integer> requirementOP = new ArrayList<>(row); // for Naive test
        for (int i = 0; i < row; i++) {
            requirementOP.add(ops);
        }



        double[] doubles = createRandomList(ops);
        for (int i = 0; i < row; i++) {
            for (int x = 0; x < dataSize.get(i); x++) {
                nm.add(doubles[x], doubles[x]);
            }
        }
        for (int i = 0; i < row; i++) {
            for (int x = 0; x < dataSizeHeap.get(i); x++) {
                heap.add(doubles[x], doubles[x]);
            }
        }//add items for after test


        //test function time for both
        Stopwatch swHeap = new Stopwatch();
        for (int i = 0; i < row; i++) {
            for (int x = 0; x < dataSizeHeap.get(i) / 2; x++) {
                heap.removeSmallest();
            }
            timesHeap.add(swHeap.elapsedTime());
        }
        System.out.println("Test for Heap");
        printTimingTable(dataSizeHeap, timesHeap, requirementOP);

        //test Naive add
        Stopwatch swAddN = new Stopwatch();
        for (int i = 0; i < row; i++) {
            for (int x = 0; x < dataSize.get(i) / 2; x++) {
                nm.removeSmallest();
            }
            times.add(swAddN.elapsedTime());
        }
        System.out.println("Test for Naive");
        printTimingTable(dataSize, times, requirementOP);
    }

    /**
     * In summary, because of resize, heap's add is slower than naive one,
     * even the number come to a very big number,
     * but get,contains and remove are much faster.
     */

    private double[] createRandomList(int capacity) {
        double[] res = new double[capacity];
        for (int i = 0; i < capacity; i++) {
            res[i] = createRandomItem();
        }
        return res;
    }
    private double createRandomItem() {
        double y = r.nextDouble();
        int mulNumber = r.nextInt(10000);
        return mulNumber * y;
    }

    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }
}
