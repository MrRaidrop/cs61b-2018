package hw2;

import java.util.HashMap;
import java.util.Map;

public class Percolation {
    private int n;
    private  int NN;
    private int count;
    private int[] parent;
    private Map<Integer, Boolean> grid;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("");
        }
        n = N;
        NN = N * N;
        parent = new int[N * N];
        grid = new HashMap<>();
        for (int i = 0; i < n; i++) {
            grid.put(i, true);
        }
        for (int i = n; i < NN; i++) {
            grid.put(i, false);
        }
        parent[0] = -1 * N;
        for (int i = 1; i < N; i++) {
            parent[i] = 0;
        }
        for (int i = N; i < NN; i++) {
            parent[i] = -1;
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        indexCheck(row, col);
        if (isOpen(row, col)) {
            return;
        }
        grid.put(n * col + row, true);
        connectAround(row, col);
        count++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        indexCheck(row, col);
        return grid.get(n * col + row);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        indexCheck(row, col);
        return isConnected(n * col + row, 0);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = NN - n; i < NN; i++) {
            if (grid.get(i)) {
                if (find(i) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private void connect(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);
        if (root1 == root2) {
            return;
        }
        int newSize = parent[root1] + parent[root2];
        //if connected to the top, make its parent become the top.
        if (root1 == 0) {
            parent[root2] = root1;
            parent[0] = newSize;  //may be useful when counting the grid that is full.
            return;
        }
        if (root2 == 0) {
            parent[root1] = root2;
            parent[0] = newSize;
            return;
        }
        if (parent[root1] > parent[root2]) {
            parent[root1] = root2;
            parent[root2] = newSize;
            return;
        }
        parent[root2] = root1;
        parent[root1] = newSize;
    }

    private int find(int v1) {
        int root = v1;
        while (parent[root] >= 0) {
            root = parent[root];
        }
        while (v1 != root) {
            int cur = parent[v1];
            parent[v1] = root;
            v1 = cur;
        } //saw no difference with or without path compression. why??????
        return root;
    }
    private boolean isConnected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    //when open the site, connect it with its around sites.
    private void connectAround(int row, int col) {
        if (col == 0 & row == 0) {
            connectDOWN(row, col);
            connectRIGHT(row, col);
            return;
        }
        if (col == 0 & row == n - 1) {
            connectDOWN(row, col);
            connectLEFT(row, col);
            return;
        }
        if (col == n - 1 & row == 0) {
            connectUP(row, col);
            connectRIGHT(row, col);
            return;
        }
        if (col == n - 1 & row == n - 1) {
            connectUP(row, col);
            connectLEFT(row, col);
            return;
        }
        if (col == 0) {
            connectDOWN(row, col);
            connectLEFT(row, col);
            connectRIGHT(row, col);
            return;
        }
        if (row == 0) {
            connectUP(row, col);
            connectDOWN(row, col);
            connectRIGHT(row, col);
            return;
        }
        if (col == n - 1) {
            connectUP(row, col);
            connectRIGHT(row, col);
            connectLEFT(row, col);
            return;
        }
        if (row == n - 1) {
            connectUP(row, col);
            connectDOWN(row, col);
            connectLEFT(row, col);
            return;
        }
        connectUP(row, col);
        connectRIGHT(row, col);
        connectLEFT(row, col);
        connectDOWN(row, col);
        return;
    }
    private void connectUP(int row, int col) {
        int up = n * (col - 1) + row;
        if (grid.get(up)) {
            connect(n * col + row, up);
        }
    }
    private void connectDOWN(int row, int col) {
        int down = n * (col + 1) + row;
        if (grid.get(down)) {
            connect(n * col + row, down);
        }
    }
    private void connectLEFT(int row, int col) {
        int left = n * col + row - 1;
        if (grid.get(left)) {
            connect(n * col + row, left);
        }
    }
    private void connectRIGHT(int row, int col) {
        int right = n * col + row + 1;
        if (grid.get(right)) {
            connect(n * col + row, right);
        }
    }
    private void indexCheck(int row, int col) {
        if (row < 0 | col < 0 | row > n - 1 | col > n - 1) {
            throw new IndexOutOfBoundsException("Index out of Bound.");
        }
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {
    }

}
