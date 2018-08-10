import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

/*************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: Point.java LineSegment.java 
 *
 *  A Fast force algorithm for finding 4 point line segments.
 *
 *************************************************************************/

public class FastCollinearPoints {
  final private ArrayList<LineSegment> lineSegments = new ArrayList<>();
  final private ArrayList<Point> beginPoints = new ArrayList<>();
  final private ArrayList<Point> endPoints = new ArrayList<>();
  
  // finds all line segments containing 4 points 
  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }
    
    for (Point p : points) {
      // StdOut.println(" * " + p);
      if (p == null) {
        throw new IllegalArgumentException();
      }
    }

    checkDupes(points);

    Point[] inputArray = Arrays.copyOf(points, points.length);

    for (Point basePoint : points) {
      Arrays.sort(inputArray, basePoint.slopeOrder());

      double slope = 0;
      double lastSlope = Double.NEGATIVE_INFINITY;
      ArrayList<Point> linePoints = new ArrayList<>();
      
      for (int i = 1; i < inputArray.length; i++) {
        slope = basePoint.slopeTo(inputArray[i]);
        // StdOut.println("Calculate slope as: " + slope);
        
        if (slope == lastSlope) {
          // StdOut.println("Found matching slope!");
          linePoints.add(inputArray[i]);
        } else {
          if (linePoints.size() >= 3) {
            linePoints.add(basePoint);
            // StdOut.println("Running addNewSegment for slope = " + lastSlope);
            addSegment(linePoints);
          } 
          linePoints.clear();
          linePoints.add(inputArray[i]);
        }
        lastSlope = slope;
      }
      
      if (linePoints.size() >= 3) {
        linePoints.add(basePoint);
        // StdOut.println("Running addNewSegment for slope = " + lastSlope);
        addSegment(linePoints);
      }
    }
  }
  
  private void checkDupes(Point[] pointArray) {
    for (int i = 0; i < pointArray.length; i++) {
      for (int k = i + 1; k < pointArray.length; k++) {
        if (pointArray[i].compareTo(pointArray[k]) == 0) {
          throw new IllegalArgumentException("Duplicate points found in input!");
        }
      }
    }
  }
    
  private void addSegment(ArrayList<Point> linePoints) {
    Collections.sort(linePoints);
    Point newBeginPoint = linePoints.get(0);
    Point newEndPoint = linePoints.get(linePoints.size() - 1);

    for (Point p : beginPoints) {
      if (newBeginPoint.compareTo(p) == 0) {
        for (Point s : endPoints) {
          if (newEndPoint.compareTo(s) == 0) {
            // StdOut.println("Duplicate line segment found, skipping!");
            return;
          }
        }
      }
    }
    
    beginPoints.add(newBeginPoint);
    endPoints.add(newEndPoint);
    lineSegments.add(new LineSegment(newBeginPoint, newEndPoint));
  }
  
// the number of line segments
  public int numberOfSegments() {
    return lineSegments.size();
  }
  
// the line segments
  public LineSegment[] segments() {
    return lineSegments.toArray(new LineSegment[lineSegments.size()]);
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
        // StdOut.println("Segment = " + segment);
        segment.draw();
    }
    StdDraw.show();
  }
 }