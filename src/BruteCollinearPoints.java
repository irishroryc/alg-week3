import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/*************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints
 *  Dependencies: Point.java LineSegment.java 
 *
 *  A brute force algorithm for finding 4 point line segments.
 *
 *************************************************************************/

public class BruteCollinearPoints {
  private int lineSegmentCount;
  private final int arrayLength;
  private Point[] inputArray;
  private final ArrayList<LineSegment> lineSegments = new ArrayList<>();
  
  // finds all line segments containing 4 points 
  public BruteCollinearPoints(Point[] points) {
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
       inputArray = new Point[arrayLength];
       for (int i = 0; i < arrayLength; i++) {
         inputArray[i] = points[i];
       }
       validateLineSegments();
     }
  }
  
  private boolean checkDupes(Point[] pointArray) {
    boolean dupes = false;
    for (int i = 0; i < arrayLength; i++) {
      for (int k = i + 1; k < arrayLength; k++) {
        if (pointArray[i].compareTo(pointArray[k]) == 0) {
          // StdOut.println("DEBUG: Found duplicate point " + pointArray[i] + " !!");
          dupes = true;
        }
      }
    }
    return dupes;
  }
  
  private void validateLineSegments() {
    Point[] valPts = new Point[4];
    for (int i = 0; i < arrayLength; i++) {
      valPts[0] = inputArray[i];
      for (int j = i+1; j < arrayLength; j++) {
        valPts[1] = inputArray[j];
        for (int k = j+1; k < arrayLength; k++) {
          valPts[2] = inputArray[k];
          for (int m = k+1; m < arrayLength; m++) {
            valPts[3] = inputArray[m];
            if (valPts[0].slopeTo(valPts[1]) == valPts[0].slopeTo(valPts[2])) {
              if (valPts[0].slopeTo(valPts[1]) == valPts[0].slopeTo(valPts[3])) {
                // StdOut.println("Found collinear points! Will add to lineSegments[" + lineSegmentCount + "]");
                Arrays.sort(valPts);
                StdOut.println("DEBUG: Collinear points are (" + valPts[0] + ", " + valPts[1] + ", " + valPts[2] + ", " + valPts[3] + ")");
                addSegment(valPts[0], valPts[3]);
              }
            }
          }
        }
      }
    }
  }
  
  private void addSegment(Point p1, Point p2) {
    LineSegment foundSegment = new LineSegment(p1, p2);
    StdOut.println("New line segment is " + foundSegment);
    for (LineSegment s : lineSegments) {
      StdOut.println("Checking if " + s + " = " + foundSegment);
      if (s.equals(foundSegment)) {
        StdOut.println("Found existing line segment " + s);
        return;
      }
    }
    if (lineSegments.contains(foundSegment)) {
      StdOut.println("Found existing line segment " + foundSegment);
      return;
    }
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
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println("Segment = " + segment);
        segment.draw();
    }
    StdDraw.show();
  }
 }