package Pattern_Recognition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static edu.princeton.cs.algs4.StdOut.println;

/**
 * examines 4 points at a time
 * and checks whether they all
 * lie on the same line segment,
 * returning all such line segments.
 */
public class FastCollinearPoints {

    private int mNumberOfSegments;
    private List<LineSegment> mSegments;

    /**
     * finds all line segments containing 4 points
     *
     * @param points input points
     */
    public FastCollinearPoints(Point[] points) {
        precondition(points);

        int numberOfPoints = points.length;
        mSegments = new ArrayList<LineSegment>(numberOfPoints);

        for (Point point : points) {
            Point origin = point;

            Arrays.sort(points, origin.slopeOrder());
            int start = 1;
            int end = start + 2;

            while (end < points.length) {
                if(ThreePointsInOneLine(points, start)) {
                    Point[] pointsInOneLine = new Point[4];
                    pointsInOneLine[0] = origin;
                    for(int i = 1; i < pointsInOneLine.length; i++) {
                        pointsInOneLine[i] = points[start + i - 1];
                    }
                    Arrays.sort(pointsInOneLine);
                    mSegments.add(new LineSegment(pointsInOneLine[0],
                            pointsInOneLine[3]));
                    mNumberOfSegments++;
                }
                start++;
                end++;
            }
        }
    }

    private boolean ThreePointsInOneLine(Point[] points, int start) {

        Point current = points[start];
        Point next = points[start + 1];
        Point nextnext = points[start + 2];
        if(current.slopeTo(next) == next.slopeTo(nextnext)) {
            return true;
        }
        return false;
    }

    /**
     * finds the number of line segments
     * containing more than 3 points
     *
     * @return number of line segments
     */
    public int numberOfSegments() {
        return mNumberOfSegments;
    }

    /**
     * returns line segments
     * of input points
     *
     * @return the line segments
     */
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[mSegments.size()];
        segments = mSegments.toArray(segments);
        return segments;
    }

    private void precondition(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == points[j]) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    /**
     * Unit-test like function
     *
     * @param args
     */
    public static void main(String[] args) {
        Point[] p = new Point[15];

        p[0] = new Point(10, 0);
        p[1] = new Point(8, 2);
        p[2] = new Point(2, 8);
        p[3] = new Point(0, 10);

        p[4] = new Point(20, 0);
        p[5] = new Point(18, 2);
        p[6] = new Point(2, 18);

        p[7] = new Point(10, 20);
        p[8] = new Point(30, 0);
        p[9] = new Point(0, 30);
        p[10] = new Point(20, 10);

        p[11] = new Point(13, 0);
        p[12] = new Point(11, 3);
        p[13] = new Point(5, 12);
        p[14] = new Point(9, 6);

        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(p);
        println(fastCollinearPoints.numberOfSegments());

        for (LineSegment lineSegment : fastCollinearPoints.segments()) {
            println(lineSegment);
        }
    }

}
