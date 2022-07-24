package lab11.graphs;

import edu.princeton.cs.algs4.Queue;


/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int start;
    private int target;
    private boolean targetFound = false;
    private Maze maze;
    private Queue<Integer> queue;



    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        queue = new Queue<>();
        start = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        distTo[start] = 0;
        edgeTo[start] = start;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        queue.enqueue(start);
        int cur;
        while (!queue.isEmpty()) {
            cur = queue.dequeue();
            marked[cur] = true;
            announce();
            if (cur == target) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }
            for (int w : maze.adj(cur)) {
                if (!marked[w]) {
                    queue.enqueue(w);
                    edgeTo[w] = cur;
                    announce();
                    distTo[w] = distTo[cur] + 1;
                    if (targetFound) {
                        return;
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

