package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {

    private int[][] tiles;
    private static final int BLANK = 0;
    /** Constructs a board from an N-by-N array of tiles where
     tiles[i][j] = tile at row i, column j. */
    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++){
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }
    /** Returns value of tile at row i, column j (or 0 if blank) */
    public int tileAt(int i, int j) {
        if (i < 0 || i >= tiles.length || j < 0 || j >= tiles.length) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }
    /** Returns the board size N.*/
    public int size(){
        return tiles.length;
    }
    /** Returns the neighbors of the current board.
     * @source http://joshh.ug/neighbors.html */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /** The number of tiles in the wrong position. */
    public int hamming() {
        int x = 0;
        int res = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != x + 1) {
                    res++;
                }
                x++;
            }
        }
        return res;
    }
    /**
    public static void main(String[] args) {
        int x = 0;
        int[][] cur = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cur[i][j] = 6;
            }
        }
        Board test = new Board(cur);
        test.manhattan();
    }*/
    /** The sum of the Manhattan distances
     * (sum of the vertical and horizontal distance)
     * from the tiles to their goal positions.. */
    public int manhattan() {
        int res = 0;
        int x = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == BLANK) {
                    continue;
                }
                if (tiles[i][j] != x) {
                    res += deviationXY(i, j, tiles[i][j]);
                }
            }
        }
        return res;
    }
    private int deviationXY(int x, int y, int cur) {
        if (cur - x * tiles.length - y - 1 == 0) {
            return 0;
        }
        int res = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (cur - i * tiles.length - j - 1 == 0) {
                    res = Math.abs(x - i) + Math.abs(y - j);
                    break;
                }
            }
        }
        return res;
    }
    /**Estimated distance to goal. This method should
     simply return the results of manhattan() when submitted to
     Gradescope. */
    @Override
    public int estimatedDistanceToGoal(){
        return manhattan();
    }
    /**Returns true if this board's tile values are the same
     position as y's. */
    public boolean equals(Object y){
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board cur = (Board) y;
        if (tiles.length != cur.tiles.length) {
            return false;
        }
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++){
                if (tiles[i][j] != cur.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
