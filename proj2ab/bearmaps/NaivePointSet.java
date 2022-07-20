package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet{

    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        double tempDistance = 99999.99;
        Point curPoint = new Point(0, 0);
        Point thisPoint = new Point(x, y);
        for (Point p : points) {
            if (Point.distance(thisPoint, p) < tempDistance) {
                tempDistance = Point.distance(thisPoint, p);
                curPoint = p;
            }
        }
        return curPoint;
    }
}
