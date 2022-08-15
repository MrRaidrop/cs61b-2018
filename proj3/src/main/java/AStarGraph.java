import java.util.ArrayList;

/**
 * Represents a graph of vertices.
 * Created by hug.
 */
public interface AStarGraph<Vertex> {
    ArrayList<WeightedEdge<Vertex>> neighbors(Vertex v);
    double estimatedDistanceToGoal(Vertex s, Vertex goal);
}
