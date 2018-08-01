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
  private int arrayLength;
  private Point[] inputArray;
  private LineSegment[] lineSegments;
  
  // finds all line segments containing 4 points 
  public BruteCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }
    
    arrayLength = points.length;
    lineSegmentCount = 0;
    StdOut.println("Array length is " + arrayLength);
    
    if (checkDupes(points)) {
      throw new IllegalArgumentException();
    }
    
    for (Point p : points) {
      StdOut.println(" * " + p);
      if (p == null) {
        throw new IllegalArgumentException();
      }
    }
    
    if (arrayLength > 3) {
       lineSegments = new LineSegment[2];
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
                StdOut.println("Found collinear points! Will add to lineSegments[" + lineSegmentCount + "]");
                Arrays.sort(valPts);
                StdOut.println("DEBUG: Collinear points are (" + valPts[0] + ", " + valPts[1] + ", " + valPts[2] + ", " + valPts[3] + ")");
                LineSegment foundSegment = new LineSegment(valPts[0], valPts[3]);
                StdOut.println("New line segment is " + foundSegment);
                addSegment(foundSegment);
                lineSegmentCount++;
              }
            }
          }
        }
      }
    }
  }
  
  private void addSegment(LineSegment ls) {
    if (lineSegmentCount > lineSegments.length-1) {
      StdOut.println("Expanding array since segCount " + lineSegmentCount + " is greater than " + (lineSegments.length -1));
      StdOut.println("Old segment was:");
      for (int i = 0; i < lineSegmentCount; i++) {
        StdOut.println(" --> " + lineSegments[i]);
      }
      LineSegment[] tempArray = new LineSegment[lineSegmentCount * 2];
      for (int i = 0; i < lineSegmentCount; i++) {
        tempArray[i] = lineSegments[i];
      }
      lineSegments = tempArray;
    }
    StdOut.println("Adding line segment " + ls + " in position " + lineSegmentCount);
    lineSegments[lineSegmentCount] = ls;
  }
  
// the number of line segments
  public int numberOfSegments() {
    return lineSegmentCount;
  }
  
// the line segments
  public LineSegment[] segments() {
    StdOut.println("*** running segments() for Brute with count = " + lineSegmentCount + " and length " + lineSegments.length);
    if (lineSegments.length > lineSegmentCount) {
      LineSegment[] tempArray = new LineSegment[lineSegmentCount];
      for (int i = 0; i < lineSegmentCount; i++) {
        StdOut.println("Adding LineSegment " + lineSegments[i]);
        tempArray[i] = lineSegments[i];
      }
      lineSegments = tempArray;
    }
    return lineSegments;
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