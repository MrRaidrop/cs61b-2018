import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Point {

    double x;
    double y;
    long id;
    String name = null;
    Set<Long> adjs;
    List<Long> wayIds;

    public Point(double x, double y) {
        id = 0;
        this.x = x;
        this.y = y;
        this.adjs = new LinkedHashSet<>();
        this.wayIds = new ArrayList<>();
    }

    public Point(long id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.adjs = new LinkedHashSet<>();
        this.wayIds = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public long getId() {
        return id;
    }


    /**
     * Returns the euclidean distance (L2 norm) squared between two points
     * (x1, y1) and (x2, y2). Note: This is the square of the Euclidean distance,
     * i.e. there's no square root.
     */
    private static double distance(double x1, double x2, double y1, double y2) {
        return GraphDB.distance(x1, y1, x2, y2);
    }

    /**
     * Returns the euclidean distance (L2 norm) squared between two points.
     * Note: This is the square of the Euclidean distance, i.e.
     * there's no square root.
     */
    public static double distance(Point p1, Point p2) {
        return distance(p1.getX(), p2.getX(), p1.getY(), p2.getY());
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Point otherPoint = (Point) other;
        return getX() == otherPoint.getX() && getY() == otherPoint.getY();
    }

    @Override
    public int hashCode() {
        return Double.hashCode(x) ^ Double.hashCode(y);
    }

    @Override
    public String toString() {
        return String.format("Point x: %.10f, y: %.10f", x, y);
    }
}
