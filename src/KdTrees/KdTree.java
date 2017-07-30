package KdTrees;

import edu.princeton.cs.algs4.*;


/**
 * Created by user on 17.07.17.
 */
public class KdTree {

    private int mSize;
    private KdTreeNode mRoot;
    private Stack<Point2D> pointsInsideRectangle = new Stack<Point2D>();
    private Point2D nearestPoint;
    private double nearestPointSquaredDistance;

    /**
     * construct an empty set of points
     */
    public KdTree() {
        mSize = 0;
        mRoot = null;
    }

    public static void main(String[] args) {

        KdTree pointSet = new KdTree();
        String fileName = args[0];
        In in = new In(fileName);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D point = new Point2D(x, y);
            pointSet.insert(point);
            //StdOut.println("point:" + point);
            //StdOut.println("nearest: " + pointSet.nearest(point));
        }
        RectHV recangle = new RectHV(0.1, 0.2, 0.4, 0.5);
        StdDraw.setPenColor(StdDraw.RED);
        recangle.draw();
        Iterable<Point2D> pointsInsideRectangle = pointSet.range(recangle);
        PointSET pointsInsideRectangleSet = new PointSET();
        for (Point2D point : pointsInsideRectangle) {
            pointsInsideRectangleSet.insert(point);
        }
        pointsInsideRectangleSet.draw();

    }

    /**
     * @return is the set empty?
     */
    public boolean isEmpty() {
        return mSize == 0;
    }

    /**
     * @return number of points in the set
     */
    public int size() {
        return mSize;
    }

    /**
     * add the point to the set (if it is not already in the set)
     *
     * @param pointAdding point to add
     */
    public void insert(Point2D pointAdding) {
        precondition(pointAdding);

        if (isEmpty()) {
            makeRootNode(pointAdding);
            return;
        }


        KdTreeNode currentNode = mRoot;
        KdTreeNode upperToCurrentNode = null;
        KdTreeNode tempNodeToAdd = createTempNode(pointAdding);

        while (currentNode != null) {
            if (currentNode.compareTo(tempNodeToAdd) >= 1) {
                upperToCurrentNode = currentNode;
                currentNode = currentNode.left;
                if (currentNode == null) {
                    addNodeToLeft(upperToCurrentNode, pointAdding);
                }
            } else if (currentNode.compareTo(tempNodeToAdd) <= -1) {
                upperToCurrentNode = currentNode;
                currentNode = currentNode.right;
                if (currentNode == null) {
                    addNodeToRight(upperToCurrentNode, pointAdding);
                }

            } else {
                return;
            }
        }
    }

    private void addNodeToRight(KdTreeNode upperToCurrentNode, Point2D pointAdding) {
        RectHV nodeArea = splitUpperNodeAreaAndChooseRightPart
                (upperToCurrentNode);
        boolean orientation = !upperToCurrentNode.isVertical;
        upperToCurrentNode.right = new KdTreeNode(pointAdding, nodeArea,
                orientation);
        mSize++;
    }

    private RectHV splitUpperNodeAreaAndChooseRightPart(KdTreeNode
                                                                upperToCurrentNode) {
        if (upperToCurrentNode.isVertical) {
            return new RectHV(upperToCurrentNode.point.x(), upperToCurrentNode
                    .nodeArea.ymin(),
                    upperToCurrentNode.nodeArea.xmax(), upperToCurrentNode
                    .nodeArea.ymax());
        } else {
            return new RectHV(upperToCurrentNode.nodeArea.xmin(),
                    upperToCurrentNode.point
                            .y(), upperToCurrentNode.nodeArea.xmax(),
                    upperToCurrentNode.nodeArea.ymax());
        }
    }

    private void addNodeToLeft(KdTreeNode upperToCurrentNode, Point2D
            pointAdding) {
        RectHV nodeArea = splitUpperNodeAreaAndChooseLeftPart
                (upperToCurrentNode);
        boolean orientation = !upperToCurrentNode.isVertical;
        upperToCurrentNode.left = new KdTreeNode(pointAdding, nodeArea,
                orientation);
        mSize++;
    }

    private RectHV splitUpperNodeAreaAndChooseLeftPart(KdTreeNode upperToCurrentNode) {
        if (upperToCurrentNode.isVertical) {
            return new RectHV(upperToCurrentNode.nodeArea.xmin(), upperToCurrentNode
                    .nodeArea.ymin(),
                    upperToCurrentNode.point.x(), upperToCurrentNode
                    .nodeArea.ymax());
        } else {
            return new RectHV(upperToCurrentNode.nodeArea.xmin(),
                    upperToCurrentNode.nodeArea.ymin(),
                    upperToCurrentNode.nodeArea.xmax(),
                    upperToCurrentNode.point.y());
        }
    }


    private KdTreeNode createTempNode(Point2D pointAdding) {
        RectHV fullAreaRectangle = new RectHV(0, 0, 1, 1);
        boolean vertical = true;
        boolean rootOrientation = vertical;
        return new KdTreeNode(pointAdding, fullAreaRectangle,
                rootOrientation);
    }

    private void makeRootNode(Point2D pointAdding) {
        RectHV fullAreaRectangle = new RectHV(0, 0, 1, 1);
        boolean vertical = true;
        boolean rootOrientation = vertical;
        mRoot = new KdTreeNode(pointAdding, fullAreaRectangle,
                rootOrientation);

        mSize++;
    }

    /**
     * @param pointChecking point to check
     * @return does the set contain point p?
     */
    public boolean contains(Point2D pointChecking) {
        precondition(pointChecking);
        KdTreeNode currentNode = mRoot;
        KdTreeNode tempNode = createTempNode(pointChecking);

        while (currentNode != null) {
            if (currentNode.compareTo(tempNode) == 0) {
                return true;
            } else if (currentNode.compareTo(tempNode) >= 1) {
                currentNode = currentNode.left;
            } else if (currentNode.compareTo(tempNode) <= -1) {
                currentNode = currentNode.right;
            }
        }

        return false;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {

        draw(mRoot);

    }

    private void draw(KdTreeNode currentRoot) {
        if (currentRoot == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.05);
        currentRoot.point.draw();
        if (currentRoot.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(currentRoot.point.x(), currentRoot.nodeArea.ymin(),
                    currentRoot.point.x(), currentRoot.nodeArea.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(currentRoot.nodeArea.xmin(), currentRoot.point.y(),
                    currentRoot.nodeArea.xmax(), currentRoot.point.y());
        }
        draw(currentRoot.left);
        draw(currentRoot.right);
    }

    /**
     * all points that are inside the rectangle
     *
     * @param rectangle input rectangle
     * @return all points that are inside the input rectangle
     */
    public Iterable<Point2D> range(RectHV rectangle) {

        precondition(rectangle);

        pointsInsideRectangle = new Stack<Point2D>();

        range(mRoot, rectangle);

        return pointsInsideRectangle;

    }

    private void range(KdTreeNode currentNode, RectHV rectangle) {
        if (currentNode == null) return;

        if (currentNode.nodeArea.intersects(rectangle)) {
            if (rectangle.contains(currentNode.point)) {
                pointsInsideRectangle.push(currentNode.point);
            }
            range(currentNode.left, rectangle);
            range(currentNode.right, rectangle);
        }
    }

    /**
     * a nearest neighbor in the set to inputPoint p; null if the set is empty
     *
     * @param inputPoint
     * @return
     */
    public Point2D nearest(Point2D inputPoint) {

        precondition(inputPoint);
        nearestPoint = mRoot.point;
        nearestPointSquaredDistance = mRoot.point.distanceSquaredTo(inputPoint);
        nearest(mRoot, inputPoint);

        return nearestPoint;
    }

    private void nearest(KdTreeNode currentNode, Point2D inputPoint) {

        if (currentNode == null) return;

        if (currentNode.nodeArea.contains(inputPoint)) {
            if (currentNode.point.distanceSquaredTo(inputPoint) <
                    nearestPointSquaredDistance) {
                nearestPoint = currentNode.point;
                nearestPointSquaredDistance = inputPoint.distanceSquaredTo(nearestPoint);
            }

            nearest(currentNode.left, inputPoint);
            nearest(currentNode.right, inputPoint);
        }

    }

    private void precondition(Object object) {
        if (object == null) throw new IllegalArgumentException();
    }

    private static class KdTreeNode implements Comparable<KdTreeNode> {
        private Point2D point;
        private RectHV nodeArea;
        private KdTreeNode left;
        private KdTreeNode right;
        private boolean isVertical;

        private KdTreeNode(Point2D point, RectHV nodeArea, boolean isVertical) {
            this.point = point;
            this.nodeArea = nodeArea;
            this.isVertical = isVertical;
            this.left = null;
            this.right = null;
        }

        @Override
        public int compareTo(KdTreeNode that) {
            if (this.point.equals(that.point)) {
                return 0;
            }
            if (this.isVertical) {
                if (this.point.x() >= that.point.x()) return 1;
                if (this.point.x() < that.point.x()) return -1;
            }

            if (!this.isVertical) {
                if (this.point.y() >= that.point.y()) return 1;
                if (this.point.y() < that.point.y()) return -1;
            }
            return 0;
        }
    }

}
