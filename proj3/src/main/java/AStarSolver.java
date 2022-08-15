import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;
import java.util.ArrayList;


public class AStarSolver<Vertex>{

    private ArrayHeapMinPQ<Vertex> pq;
    AStarGraph<Vertex> input;
    private List<Vertex> solution;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private Set<Vertex> marked;
    private double timeSpent;
    private int countDe;
    private double solutionWeight;
    private Vertex goal;



    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end) {
        pq = new ArrayHeapMinPQ<>();
        edgeTo = new HashMap<>();
        this.goal = end;
        this.input = input;
        marked = new HashSet<>();
        distTo = new HashMap<>();
        distTo.put(start, 0.0);
        solution = new ArrayList<>();
        Stack<Vertex> solutionStack = new Stack<>();
        pq.add(start, input.estimatedDistanceToGoal(start, end));
        solutionWeight = 0.0;
        Vertex cur;

        while (pq.size() > 0) {
            cur = pq.removeSmallest();
            marked.add(cur);
            if (cur.equals(goal)) {
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
                if (!marked.contains(e)) {
                    relax(e);
                }
            }
        }

    }
    public List<Vertex> getSolution() {
        return solution;
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


}