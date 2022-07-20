package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class KDTreeTest {

    private static final long seed = 1239100241; // Whatever long seed
    private final Random r = new Random(seed);

    //method for create random list of points.
    private List<Point> createRandomListPoints(int ops) {
        List<Point> res= new ArrayList<>(ops);
        for (int i = 0; i < ops; i++) {
            Point p = createRandomPoint();
            res.add(p);
        }
        return res;
    }
    private Point createRandomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        int mulNumber = r.nextInt(1000);
        return new Point(x * mulNumber, y * mulNumber);
    }

    @Test
    //test for basic function
    public void testBasic() {
        Point a = new Point(2, 3);
        Point z = new Point(4, 2);
        Point b = new Point(4, 2);
        Point c = new Point(4, 5);
        Point d = new Point(3, 3);
        Point e = new Point(1, 5);
        Point f = new Point(4, 4);
        List<Point> pts = new ArrayList<>();
        pts.add(a);
        pts.add(z);
        pts.add(b);
        pts.add(c);
        pts.add(d);
        pts.add(e);
        pts.add(f);
        KDTree test = new KDTree(pts);
        test.nearest(0, 7);
    }


    @Test
    public void test() {
        int row = 4; // print 4 rows of test Naive
        int rowKD = 8; // print 4 rows of test KD
        int base = 25; // the min number of test naive is base, and mul 2 every row.
        int baseKD = 3125; // the min number of test KD is baseKD, and mul 2 every row.
        List<Integer> requirementOP = new ArrayList<>(row); // for Naive test
        int ops = 1000000; // the number of point the set will have.
        List<Point> points = createRandomListPoints(ops);
        // a random points created for the test
        NaivePointSet ps = new NaivePointSet(points);
        KDTree kt = new KDTree(points);
        Point p = createRandomPoint();
        Point NaiveResult = ps.nearest(p.getX(), p.getY());
        Point KDResult = kt.nearest(p.getX(), p.getY());
        System.out.println(Point.distance(NaiveResult, KDResult));
        // create a random point to test if the two implementation get the same result.
        assertEquals(true, Point.distance(NaiveResult, KDResult) < 0.0001);
        // using the same list of points to test

        for (int i = 0; i < rowKD; i++) {
            requirementOP.add(ops);
        }
        List<Integer> dataSize = new ArrayList<>(row);
        // a list of size that will be tested for Naive approach
        List<Integer> dataSizeKD = new ArrayList<>(rowKD);
        // a list of size that will be tested for KDTree
        List<Double> times = new ArrayList<>(row);
        List<Double> timesKD = new ArrayList<>(rowKD);

        for (int i = 0; i < row; i++) {
            dataSize.add(base * (int) Math.pow(2, i));
        }
        for (int i = 0; i < rowKD; i++) {
            dataSizeKD.add(baseKD * (int) Math.pow(2, i));
        }

        //test Naive times
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < row; i++) {
            testNaive(dataSize.get(i), ps, p);
            times.add(sw.elapsedTime());
        }
        System.out.println("Test for NaivePointSet");
        printTimingTable(dataSize, times, requirementOP);
        //test KDTree times
        Stopwatch swKD = new Stopwatch();
        for (int i = 0; i < rowKD; i++) {
            testKD(dataSizeKD.get(i), kt, p);
            timesKD.add(swKD.elapsedTime());
        }
        System.out.println("Test for KDTree");
        printTimingTable(dataSizeKD, timesKD, requirementOP);
    }

    public void testNaive(int dataSize, NaivePointSet ps, Point p) {
        for (int i = 0; i < dataSize; i++) {
            ps.nearest(p.getX(), p.getY());
        }
    }
    public void testKD(int dataSize, KDTree kt, Point p) {
        for (int i = 0; i < dataSize; i++) {
            kt.nearest(p.getX(), p.getY());
        }
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
