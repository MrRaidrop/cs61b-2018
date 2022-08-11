package bearmaps.proj2c;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private ArrayHeapMinPQ<Vertex> pq;
    AStarGraph<Vertex> input;
    private SolverOutcome outcome;
    private List<Vertex> solution;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private Set<Vertex> marked;
    private double timeSpent;
    private int countDe;
    private double solutionWeight;
    private Vertex goal;



    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        pq = new ArrayHeapMinPQ<>();
        edgeTo = new HashMap<>();
        this.goal = end;
        this.input = input;
        marked = new HashSet<>();
        Stopwatch sw = new Stopwatch();
        distTo = new HashMap<>();
        distTo.put(start, 0.0);
        solution = new ArrayList<>();
        Stack<Vertex> solutionStack = new Stack<>();
        pq.add(start, input.estimatedDistanceToGoal(start, end));
        countDe = 0;
        timeSpent = 0.0;
        solutionWeight = 0.0;
        Vertex cur;

        while (pq.size() > 0) {
            cur = pq.removeSmallest();
            marked.add(cur);
            countDe++;
            timeSpent = sw.elapsedTime();
            /**if (timeSpent > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                break;
            }*/
            if (cur.equals(goal)) {
                outcome = SolverOutcome.SOLVED;
                solutionWeight = distTo.get(end);
                solutionStack.push(cur);
                while (!cur.equals(start)) {
                    cur = edgeTo.get(cur);
                    solutionStack.push(cur);
                }
                // reverse the order of solution.
                while (!solutionStack.empty()) {
                    solution.add(solutionStack.pop());
                }
                return;
            }
            for (WeightedEdge<Vertex> e : input.neighbors(cur)) {
                relax(e);
            }
        }
        timeSpent = sw.elapsedTime();
        outcome = SolverOutcome.UNSOLVABLE;
    }

    private void relax(WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();
        if (!distTo.containsKey(q) || distTo.get(p) + w < distTo.get(q)) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            if (pq.contains(q)) {
                pq.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, goal));
            } else {
                pq.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, goal));
            }
        }
    }

    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        return solution;
    }
    public double solutionWeight() {
        return solutionWeight;
    }
    public int numStatesExplored() {
        return countDe;
    }
    public double explorationTime() {
        return timeSpent;
    }
}