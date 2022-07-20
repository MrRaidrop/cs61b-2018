package bearmaps;

import java.util.Comparator;
import java.util.List;

public class KDTree implements PointSet{
    private Node root;
    private List<Point> points;

    public KDTree(List<Point> points) {
        this.points = points;
        root = new Node(points.get(0).getX(), points.get(0).getY(), 0);
        for (int i = 1; i < points.size(); i++) {
            put(points.get(i));
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearest(root, goal, root).point;
    }
    private Node nearest(Node N, Point goal, Node bestSoFar) {
        if  (N == null) {
            return bestSoFar;
        }
        if (Point.distance(N.point, goal) < Point.distance(bestSoFar.point, goal)) {
            bestSoFar = N;
        }
        double x = goal.getX();
        double y = goal.getY();
        Node goodSide;
        Node badSide; // initialize good side and bad side.
        double diff = Diff(goal, N.point, N);
        if (diff <= 0) {
            goodSide = N.Left;
            badSide = N.Right;
        } else {
            goodSide = N.Right;
            badSide = N.Left;
        }
        // judge the node is good or bad side by Node depth % 2
        bestSoFar = nearest(goodSide, goal, bestSoFar);
        if (diff * diff < Point.distance(goal, bestSoFar.point)) {
            // there are still some gold in the trash
            bestSoFar = nearest(badSide, goal, bestSoFar);
        }
        return bestSoFar;
    }
    private double Diff(Point p1, Point p2, Node N) {
        if (N.depth % 2 == 0) {
            return p1.getX() - p2.getX();
        } else {
            return p1.getY() - p2.getY();
        }
    }

    private class Node {
        private double x;
        private double y;
        private Point point;
        private int depth;
        private Node Left, Right;

        public Node(double x, double y, int depth) {
            this.x = x;
            this.y = y;
            this.depth = depth;
            this.point = new Point(x, y);
        }
    }

    public boolean contains(Point p) {
        return get(p) != null;
    }

    public Point get(Point p) {
        return get(root, p.getX(), p.getY());
    }
    private Point get(Node N, double x, double y) {
        if (N == null) {
            return null;
        }
        int count = N.depth;
        // Node that depth % 2 == 0, such as root or depth 2 Node should be compared their x.
        if (count % 2 == 0) {
            if (x < N.x) {
                return get(N.Left, x, y);
            }
            return get(N.Right, x, y);
        }
        if (count % 2 == 1) {
            if (y < N.y) {
                return get(N.Left, x, y);
            }
            return get(N.Right, x, y);
        }
        return null; // if the x,y point doesn't exist, return null.
    }

    public void put(Point p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        put(root, p.getX(), p.getY());
    }
    private void put(Node N, double x, double y) {
        if (N == null) {
            throw new IllegalArgumentException();
        }
        if (N.x == x && N.y == y) {
            return;
        }
        int count = N.depth;
        // judge the node go left or right by tree depth % 2 and
        // if the node is empty, put it there
        if (count % 2 == 0) {
            if (x < N.x & N.Left != null) {
                put(N.Left, x, y);
            } else if (x < N.x & N.Left == null) {
                Node cur = new Node(x, y, N.depth + 1);
                N.Left = cur;
            }
            if (x >= N.x & N.Right != null) {
                put(N.Right, x, y);
            } else if (x >= N.x & N.Right == null) {
                Node cur = new Node(x, y, N.depth + 1);
                N.Right = cur;
            }
        }
        if (count % 2 == 1) {
            if (y < N.y & N.Left != null) {
                put(N.Left, x, y);
            } else if (y < N.y & N.Left == null) {
                Node cur = new Node(x, y, N.depth + 1);
                N.Left = cur;
            }
            if (y >= N.y & N.Right != null) {
                put(N.Right, x, y);
            } else if (y >= N.y & N.Right == null) {
                Node cur = new Node(x, y, N.depth + 1);
                N.Right = cur;
            }
        }
    }

}
