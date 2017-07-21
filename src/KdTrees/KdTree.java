package KdTrees;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;


/**
 * Created by user on 17.07.17.
 */
public class KdTree {

    private int mSize;
    private KdTreeNode mRoot;


    private static class KdTreeNode implements Comparable<KdTreeNode> {
        private Point2D point;
        private RectHV nodeArea;
        private KdTreeNode left;
        private KdTreeNode right;

        @Override
        public int compareTo(KdTreeNode that) {
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

        private boolean isVertical;

        private KdTreeNode(Point2D point, RectHV nodeArea, boolean isVertical) {
            this.point = point;
            this.nodeArea = nodeArea;
            this.isVertical = isVertical;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * construct an empty set of points
     */
    public KdTree() {
        mSize = 0;
        mRoot = null;
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

        while(currentNode != null) {
            if (currentNode.compareTo(tempNodeToAdd) >= 1) {
                upperToCurrentNode = currentNode;
                currentNode = currentNode.right;
            }

            if (currentNode.compareTo(tempNodeToAdd) <= -1) {
                upperToCurrentNode = currentNode;
                currentNode = currentNode.left;
            }
        }
        //RectHV currentNodeArea = splitUpperNodeArea(upperToCurrentNode);
        //currentNode = new KdTreeNode(pointAdding, )


    }

    private RectHV splitUpperNodeArea(KdTreeNode upperToCurrentNode) {
        RectHV rootArea = upperToCurrentNode.nodeArea;

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
    }

    /**
     * @param pointChecking point to check
     * @return does the set contain point p?
     */
    public boolean contains(Point2D pointChecking) {
        precondition(pointChecking);
        return mPointsTree.contains(pointChecking);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        mPointsTree.draw();
    }

    /**
     * all points that are inside the rectangle
     *
     * @param rectangle input rectangle
     * @return all points that are inside the input rectangle
     */
    public Iterable<Point2D> range(RectHV rectangle) {

        precondition(rectangle);

        return mPointsTree.range(rectangle);

    }

    /**
     * a nearest neighbor in the set to inputPoint p; null if the set is empty
     *
     * @param inputPoint
     * @return
     */
    public Point2D nearest(Point2D inputPoint) {

        precondition(inputPoint);

        return mPointsTree.nearest(inputPoint);
    }

    private void precondition(Object object) {
        if (object == null) throw new IllegalArgumentException();
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
        StdDraw.setPenColor(Color.red);
        recangle.draw();
        Iterable<Point2D> pointsInsideRectangle = pointSet.range(recangle);
        PointSET pointsInsideRectangleSet = new PointSET();
        for (Point2D point : pointsInsideRectangle) {
            pointsInsideRectangleSet.insert(point);
        }
        pointsInsideRectangleSet.draw();

    }

}
