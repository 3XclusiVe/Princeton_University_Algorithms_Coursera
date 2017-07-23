package KdTrees;

import edu.princeton.cs.algs4.*;

import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> mPointSet;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        mPointSet = new TreeSet<>();
    }

    /**
     * @return is the set empty?
     */
    public boolean isEmpty() {
        return mPointSet.isEmpty();
    }

    /**
     * @return number of points in the set
     */
    public int size() {
        return mPointSet.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     *
     * @param pointAdding point to add
     */
    public void insert(Point2D pointAdding) {
        precondition(pointAdding);
        mPointSet.add(pointAdding);
    }

    /**
     * @param pointChecking point to check
     * @return does the set contain point p?
     */
    public boolean contains(Point2D pointChecking) {
        precondition(pointChecking);
        return mPointSet.contains(pointChecking);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        for (Point2D point : mPointSet) {
            StdDraw.point(point.x(), point.y());
        }
    }

    /**
     * all points that are inside the rectangle
     *
     * @param rectangle input rectangle
     * @return all points that are inside the input rectangle
     */
    public Iterable<Point2D> range(RectHV rectangle) {

        precondition(rectangle);
        Stack<Point2D> pointsInsideRectangle = new Stack<>();

        for (Point2D point : mPointSet) {
            if (rectangle.contains(point)) {
                pointsInsideRectangle.push(point);
            }
        }

        return pointsInsideRectangle;
    }

    /**
     * a nearest neighbor in the set to inputPoint p; null if the set is empty
     *
     * @param inputPoint
     * @return
     */
    public Point2D nearest(Point2D inputPoint) {

        precondition(inputPoint);
        if (mPointSet.isEmpty()) return null;

        Point2D nearestpoint = mPointSet.first();
        double minSquaredDistance = inputPoint.distanceSquaredTo(nearestpoint);

        for (Point2D point : mPointSet) {
            double currentSquaredDistance = inputPoint.distanceSquaredTo(point);

            if (currentSquaredDistance < minSquaredDistance) {
                minSquaredDistance = currentSquaredDistance;
                nearestpoint = point;
            }
        }

        return nearestpoint;
    }

    private void precondition(Object object) {
        if (object == null) throw new IllegalArgumentException();
    }

    /**
     * unit-testing-like of the methods (optional)
     *
     * @param args
     */
    public static void main(String[] args) {

        PointSET pointSet = new PointSET();
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

}