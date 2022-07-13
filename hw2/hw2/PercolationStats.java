package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int t;
    private double[] resultFractions;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("");
        }
        t = T;
        resultFractions = new double[T];
        int row;
        int col;
        for (int i = 0; i < T; i++) {
            Percolation simple = pf.make(N);
            while (!simple.percolates()) {
                int x, y;
                do {
                    x = StdRandom.uniform(N);
                    y = StdRandom.uniform(N);
                } while (simple.isOpen(x, y));
                simple.open(x, y);
            }
            resultFractions[i] = (double) simple.numberOfOpenSites() / N * N;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(resultFractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(resultFractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(t);
    }

}
