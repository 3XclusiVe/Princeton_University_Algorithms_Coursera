package _3_Pattern_Recognition;

/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

import static edu.princeton.cs.algs4.StdOut.println;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {

        checkCompareTo();

        checkSlopeTo();

        System.out.println(new Point(1, 1) == new Point(1, 1));

    }

    private static void checkSlopeTo() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(0, 1);
        Point p0 = new Point(0, 0);

        testCase(p1.slopeTo(p1) == Double.NEGATIVE_INFINITY);
        testCase(p1.slopeTo(p2) == Double.POSITIVE_INFINITY);
        testCase(p3.slopeTo(p1) == 0);
        testCase(p0.slopeTo(p1) == 1);
    }

    private static void checkCompareTo() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(0, 1);
        Point p0 = new Point(0, 0);


        testCase(p1.compareTo(p1) == 0);
        testCase(p2.compareTo(p2) == 0);
        testCase(p3.compareTo(p3) == 0);

        testCase(p1.compareTo(p2) == 1);
        testCase(p1.compareTo(p3) == 1);

        testCase(p2.compareTo(p3) == -1);
    }

    private static void testCase(boolean testCase) {
        if (testCase == true) {
            println("PASSED");
        } else {
            println("FAILED");
        }
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double deltaY = that.y - this.y;
        double deltaX = that.x - this.x;

        if ((deltaX == 0) && (deltaY == 0)) {
            return Double.NEGATIVE_INFINITY;
        } else if (deltaX == 0) {
            return Double.POSITIVE_INFINITY;
        } else if (deltaY == 0) {
            return 0;
        } else {
            return deltaY / deltaX;
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {

        if (this.y > that.y) {
            return 1;
        } else if (this.y < that.y) {
            return -1;
        } else if (this.x > that.x) {
            return 1;
        } else if (this.x < that.x) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    private class SlopeOrder implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            double o1Slope = slopeTo(o1);
            double o2Slope = slopeTo(o2);

            if (o1Slope > o2Slope) {
                return 1;
            } else if (o1Slope < o2Slope) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}