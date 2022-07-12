package hw2;


public class Percolation {
    private int n;
    private  int NN;
    private int count;
    private int[] parent;
    private boolean[][] openCondition;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("");
        }
        n = N;
        NN = N * N;
        parent = new int[N * N];
        openCondition = new boolean[N][N];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                openCondition[i][j] = false;
            }
        }
        parent[0] = -1 * N;
        for (int i = 1; i < NN; i++) {
            parent[i] = -1;
        }
        for (int i = n; i < NN; i += n) {
            parent[i] = 0;
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        indexCheck(row, col);
        if (isOpen(row, col)) {
            return;
        }
        openCondition[row][col] = true;
        if (n != 1) {
            connectAround(row, col, row + 1, col);
            connectAround(row, col, row - 1, col);
            connectAround(row, col, row, col + 1);
            connectAround(row, col, row, col - 1);
        }
        count++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        indexCheck(row, col);
        return openCondition[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        indexCheck(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        return isConnected(n * col + row, 0);
    }


    // number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 1; i <= n; i++) {
            if (openCondition[n - 1][i - 1]) {
                if (find(i * n - 1) == 0) {
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
        /**while (v1 != root) {
            int cur = parent[v1];
            parent[v1] = root;
            v1 = cur;
        } //saw no difference with or without path compression. why??????
         */
        return root;
    }
    private boolean isConnected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    //when open the site, connect it with its around sites.
    private void connectAround(int row, int col, int newRow, int newCol) {
        if (newRow < 0 || newRow >= n || newCol < 0 || newCol >= n) {
            return;
        }
        if (openCondition[newRow][newCol]) {
            connect(n * col + row, n * newCol + newRow);
        }
    }

    private void indexCheck(int col, int row) {
        if (row < 0 | col < 0 | row > n - 1 | col > n - 1) {
            throw new IndexOutOfBoundsException("Index out of Bound.");
        }
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {
    }

}
