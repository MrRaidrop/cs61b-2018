public class KdTree {
    private KdNode root;
    private int size;

    private static class KdNode {
        private final Point point;
        private boolean compareX;
        private KdNode leftBottom;
        private KdNode rightTop;

        KdNode(Point p, boolean compareX) {
            this.point = p;
            this.compareX = compareX;
        }

        public boolean isRightOrTopOf(Point q) {
            return (compareX && point.getX() > q.getX() || (!compareX && point.getY() > q.getY()));
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(Point p) {
        if (root == null) {
            root = new KdNode(p, true);
            size++;
            return;
        }

        // find node position for insertion
        KdNode pre = null;
        KdNode cur = root;
        do {
            if (cur.point.id == p.id) {
                return;
            }
            pre = cur;
            cur = cur.isRightOrTopOf(p) ? cur.leftBottom : cur.rightTop;
        } while (cur != null);

        // prepare new node and insert
        if (pre.isRightOrTopOf(p)) {
            pre.leftBottom = new KdNode(p, !pre.compareX);
        } else {
            pre.rightTop = new KdNode(p, !pre.compareX);
        }
        size++;
    }

    public Point nearest(double x, double y) {
        return nearest(new Point(x, y));
    }

    public Point nearest(Point p) {
        return nearest(p, root, Double.MAX_VALUE);
    }

    private Point nearest(Point p, KdTree.KdNode node, double minDist) {
        if (p == null) {
            throw new NullPointerException();
        }
        if (node.point == null) {
            return null;
        }
        if (node.point.equals(p)) {
            return node.point;
        }
        Point bestPoint = null;
        double bestDist = minDist;
        double nodeDist = GraphDB.distance(node.point.getX(), node.point.getY(), p.getX(), p.getY());
        if (nodeDist < minDist) {
            bestPoint = node.point;
            bestDist = nodeDist;
        }
        KdNode first = node.rightTop, second = node.leftBottom;
        if ((node.compareX && p.getX() <= node.point.getX())
                || (!node.compareX && p.getY() <= node.point.getY())) {
            first = node.leftBottom;
            second = node.rightTop;
        }
        if (first != null) {
            Point firstBestPoint = nearest(p, first, bestDist);
            if (firstBestPoint != null) {
                bestPoint = firstBestPoint;
                bestDist = GraphDB.distance(bestPoint.getX(), bestPoint.getY(), p.getX(), p.getY());
            }
        }
        if (second == null) {
            return bestPoint;
        }

        if (second == node.leftBottom) {
            if (node.compareX && p.getX() - node.point.getX() >= bestDist) {
                return bestPoint;
            }
            if (!node.compareX && p.getY() - node.point.getY() >= bestDist) {
                return bestPoint;
            }
        } else if (second == node.rightTop) {
            if (node.compareX && node.point.getX() - p.getX() >= bestDist) {
                return bestPoint;
            }
            if (!node.compareX && node.point.getY() - p.getY() >= bestDist) {
                return bestPoint;
            }
        }
        Point secondBestPoint = nearest(p, second, bestDist);
        if (secondBestPoint != null) {
            bestPoint = secondBestPoint;
        }
        return bestPoint;
    }
}