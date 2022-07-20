package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;

/** created by Greyson Yu
 * using for test the time of a few rows of growing base using for the method call.
 */

public class timeTest {
    /** method using for data structure time test
    * rowCount is how many rows this test want to print, and every time row "\n" the base should double.
    * requireOP is a List that stores rowCount number of integers,
    * which are the number of obj added to the test data structure. */
    /**
    public static void timeTestDataStructure(int rowCount, int base, List requireOP, Method method, Object obs, Object ob) throws Exception{

        Stopwatch sw = new Stopwatch();
        List<Integer> dataSize = new ArrayList<>(rowCount);
        List<Double> times = new ArrayList<>(rowCount);


        // a list of size
        for (int i = 0; i < rowCount; i++) {
            dataSize.add(base * (int) Math.pow(2, i));
        }

        for (int i = 0; i < dataSize.size(); i++) {
            method.invoke(dataSize.get(i), obs, ob);
            //use Java reflection to get testNaive or testKd method.
            times.add(sw.elapsedTime());
        }
        printTimingTable(dataSize, times, requireOP);
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
    }*/
}
