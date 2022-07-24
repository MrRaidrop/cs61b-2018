package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private MinPQ<Integer> pq;
    private HashMap<Integer, Stack<Integer>> pqMap;
    private int tx;
    private int ty;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        pq = new MinPQ<>();
        pqMap = new HashMap<>();
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        tx = targetX;
        ty = targetY;
        distTo[s] = 0;
        edgeTo[s] = s;
    }


    /** Estimate of the distance from v to the target. */
    private int DToT(int i1) {
        int x1 = DToX(i1);
        int y1 = DToY(i1);
        return Math.abs(tx - x1) +Math.abs(ty - y1);
    }

    //return the vertex's Xpos and Ypos.
    private int DToX(int v) {
        return v % maze.N();
    }
    private int DToY(int v) {
        return v / maze.N();
    }
    /** Performs an A star search from vertex s. */
    private void astar() {
        pq.insert(0);
        Stack<Integer> start = new Stack<>();
        start.push(s);
        pqMap.put(0, start);
        // use minPQ to store the direct distance To Target.
        // use a map to store the distance as a key, and the Stack of vertexs as the values.
        int cur, curD;
        Stack<Integer> curStark;
        while (!pq.isEmpty()) {
            curD = pq.delMin();
            curStark = pqMap.get(curD);
            cur = curStark.pop();
            if (curStark.isEmpty()) {
                pqMap.remove(curD, curStark);
            }
            // pop out the most possible approach.
            // when the stack is empty of course
            marked[cur] = true;
            announce();
            if (cur == t) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }
            for (int w : maze.adj(cur)) {
                if (!marked[w]) {
                    edgeTo[w] = cur;
                    announce();
                    distTo[w] = distTo[cur] + 1;
                    curD = distTo[w] + DToT(w);
                    if (pqMap.containsKey(curD)) {
                        curStark = pqMap.get(curD);
                    } else {
                        curStark = new Stack<>();
                    }
                    curStark.push(w);
                    pqMap.put(curD, curStark);
                    pq.insert(curD);
                    if (targetFound) {
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        astar();
    }

}

