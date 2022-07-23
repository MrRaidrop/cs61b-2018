package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {

    private MinPQ<searchNode> minPQ;
    private List<WorldState> result;
    private HashMap<WorldState, Integer> cache; // store if the worldstate is marked
    /** Solver(initial): Constructor which solves the puzzle, computing
     everything necessary for moves() and solution() to
     not have to solve the problem again. Solves the
     puzzle using the A* algorithm. Assumes a solution exists.*/
    public Solver(WorldState initial) {
        NodeComparator NodeCmp = new NodeComparator();
        minPQ = new MinPQ<>(NodeCmp);
        //stringSetCache = new HashSet<>();
        result = new ArrayList<>();
        cache = new HashMap<>();
        searchNode cur = new searchNode(initial, 0, null);
        minPQ.insert(cur);
        while (!minPQ.isEmpty()) {
            cur = minPQ.delMin();
            if (cur.worldState.isGoal()) {
                break; // find it
            }
            for (WorldState world : cur.worldState.neighbors()) {
//                if (cache.containsKey(world) && world.estimatedDistanceToGoal() == cache.get(world)) {
//                    continue; // don't put same worldstate in minpq
//                }//wrong
                if (cur.parentNode != null && world.equals(cur.parentNode.worldState)) {
                    continue; // don't put parent in the minPQ
                }
                //cache.put(world, world.estimatedDistanceToGoal());
                searchNode son = new searchNode(world, cur.moved + 1, cur);
                minPQ.insert(son);
            }
        }

        Stack<WorldState> path = new Stack<>();
        searchNode x = cur;

        // decompression time
        while (x != null) {
            path.push(x.worldState);
            x = x.parentNode;
        }
        // reverse the order
        while (!path.empty()) {
            result.add(path.pop());
        }
    }


    private class searchNode {
        private WorldState worldState;
        private int moved;
        private searchNode parentNode;

        searchNode(WorldState worldState, int moved, searchNode parentNode) {
            this.worldState = worldState;
            this.moved = moved;
            this.parentNode = parentNode;
        }
    }
    private class NodeComparator implements Comparator<searchNode> {
        @Override
        public int compare(searchNode o1, searchNode o2) {
            int o1DisToGoal = getDis(o1);
            int o2DisToGoal = getDis(o2);
            int o1Priority = o1.moved + o1DisToGoal;
            int o2Priority = o2.moved + o2DisToGoal;
            return o1Priority - o2Priority;
        }
        public int getDis(searchNode o) {
            if (!cache.containsKey(o.worldState)) {
            cache.put(o.worldState, o.worldState.estimatedDistanceToGoal());
        }
            return cache.get(o.worldState);
        }
    }


        /** moves():Returns the minimum number of moves to solve the puzzle starting
     at the initial WorldState.*/
    public int moves() {
        return result.size() - 1;
    }
    /** solution(): Returns a sequence of WorldStates from the initial WorldState
     to the solution.*/
    public Iterable<WorldState> solution() {
        return result;
    }
}
