import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/*************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: Point.java LineSegment.java 
 *
 *  A Fast force algorithm for finding 4 point line segments.
 *
 *************************************************************************/

public class FastCollinearPoints {
  private int lineSegmentCount;
  final private int arrayLength;
  private Point[] inputArray;
  final private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();
  
  // finds all line segments containing 4 points 
  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }
    
    arrayLength = points.length;
    lineSegmentCount = 0;
    // StdOut.println("Array length is " + arrayLength);
    
    if (checkDupes(points)) {
      throw new IllegalArgumentException();
    }
    
    for (Point p : points) {
      // StdOut.println(" * " + p);
      if (p == null) {
        throw new IllegalArgumentException();
      }
    }
    
    if (arrayLength > 3) {
       // lineSegments = new LineSegment[2];
       inputArray = points;
       validateLineSegments();
     }
  }
  
  private boolean checkDupes(Point[] pointArray) {
    boolean dupes = false;
    for (int i = 0; i < arrayLength - 1; i++) {
      for (int k = i + 1; k < arrayLength; k++) {
        if (pointArray[i] == pointArray[k]) {
          dupes = true;
        }	
      }
    }
    return dupes;
  }
  
  private void validateLineSegments() {
    for (int i = 0; i < arrayLength; i++) {
      Point basePoint = inputArray[i];
      Arrays.sort(inputArray, i+1, arrayLength, basePoint.slopeOrder());
      // StdOut.println("Array sorted on " + basePoint + ":");
      for (int k = i + 1; k < arrayLength - 2; k++) {
        int equalPoints = 0;
        Point endPoint = null;
        double slope = basePoint.slopeTo(inputArray[k]);
        // StdOut.println("Slope for initial point compare is " + slope);
        for (int t = k + 1; t < arrayLength - 1; t++) {
          StdOut.println("Point " + t + ": " + inputArray[t] + ", slope = " + basePoint.slopeTo(inputArray[t]));
          if (slope == basePoint.slopeTo(inputArray[t])) {
            equalPoints++;
            endPoint = inputArray[t];
            // StdOut.println("DEBUG: Found matching slopes! equalPoints = " + equalPoints);
            if (t == arrayLength - 2) {
              // StdOut.println("Final point in array, evaluating adding segment.");
              if (equalPoints > 1) {
                addSegment(basePoint, endPoint);
              }
              equalPoints = 0;
            }
          } else {
            if (equalPoints > 1) {
              addSegment(basePoint, endPoint);
              equalPoints = 0;
              endPoint = inputArray[t];
            } else {
              equalPoints = 0;
              endPoint = inputArray[t];
            } 
          }
        }
      }
    }
  }

  private void addSegment(Point p1, Point p2) {
    LineSegment foundSegment = new LineSegment(p1, p2);
    // StdOut.println("New line segment is " + foundSegment);
    lineSegments.add(foundSegment);
    lineSegmentCount++;
  }
  
// the number of line segments
  public int numberOfSegments() {
    return lineSegmentCount;
  }
  
// the line segments
  public LineSegment[] segments() {
	LineSegment[] temp = new LineSegment[lineSegmentCount];
	lineSegments.toArray(temp);
    return temp;
  }
  
  public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println("Segment = " + segment);
        segment.draw();
    }
    StdDraw.show();
  }
 }