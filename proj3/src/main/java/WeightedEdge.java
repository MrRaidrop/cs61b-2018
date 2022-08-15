public interface WeightedEdge<Vertex> {


    Vertex from();
    Vertex to();
    double weight();

    String getName();

    long other(long x);
}
