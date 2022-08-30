package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.*;

public class AstarSolver {

    ArrayHeapMinPQ<position> pq;
    HashMap<position, position> edgeTo;
    HashSet<position> marked;
    HashMap<position, Double> distTo;
    position pos;
    List<position> solution;
    position goal;

    public AstarSolver(TETile[][] world, position pos, position goalThisTime, int width, int height) {
        this.pos = pos;

        goal = goalThisTime;

        solution = autoFindPath(world, width, height);
    }
    List<position> getSolution() {
        return solution;
    }


    List<position> autoFindPath(TETile[][] world, int width, int height) {
        pq = new ArrayHeapMinPQ<>();
        edgeTo = new HashMap<>();
        marked = new HashSet<>();
        distTo = new HashMap<>();
        distTo.put(pos, 0.0);
        List<position> solution = new ArrayList<>();
        Stack<position> solutionStack = new Stack<>();
        pq.add(pos, pos.awayfrom(goal));
        position cur;

        while (pq.size() > 0) {
            cur = pq.removeSmallest();
            marked.add(cur);
            if (cur.equals(goal)) {
                solutionStack.push(cur);
                while (!cur.equals(pos)) {
                    cur = edgeTo.get(cur);
                    solutionStack.push(cur);
                }
                // reverse the order of solution.
                while (!solutionStack.empty()) {
                    solution.add(solutionStack.pop());
                }
                return solution;
            }
            for (position.Edge e : cur.getNeighbourEdge(world, width, height)) {
                if (!marked.contains(e.to)) {
                    relax(e);
                }

            }
        }
        return solution;
    }


    void relax(position.Edge e) {
        // p is cur pos, q is next possible pos.
        position p = e.from;
        position q = e.to;
        double w = e.weight;
        if (!distTo.containsKey(q) || distTo.get(p) + w < distTo.get(q)) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            if (pq.contains(q)) {
                pq.changePriority(q, distTo.get(q) + q.awayfrom(goal));
            } else {
                pq.add(q, distTo.get(q) + q.awayfrom(goal));
            }
        }
    }

}
