import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
  private List<LineSegment> lineSegments = new ArrayList<>();
  private HashMap<Double, List<Point>> slopeSegments = new HashMap<>();  
  
  // finds all line segments containing 4 points 
  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }

    // Check for duplicate entries in the input array
    checkDupes(points);

    // Check for null entries in input array of Point objects
    for (Point p : points) {
      if (p == null) {
        throw new IllegalArgumentException("Input Point is null!");
      }
    }

    // Make a local copy of the input array of Point objects
    Point[] inputArray = Arrays.copyOf(points, points.length);

    
    for (Point basePoint : inputArray) {
      Arrays.sort(inputArray, basePoint.slopeOrder());

      double slope = 0;
      double lastSlope = Double.NEGATIVE_INFINITY;
      List<Point> linePoints = new ArrayList<>();
      
      for (int i = 1; i < inputArray.length; i++) {
        slope = basePoint.slopeTo(inputArray[i]);
        StdOut.println("Calculate slope as: " + slope);
        
        if (slope == lastSlope) {
          StdOut.println("Found matching slope!");
          linePoints.add(inputArray[i]);
        } else {
          if (linePoints.size() >= 3) {
            linePoints.add(basePoint);
            StdOut.println("Running addNewSegment for slope = " + lastSlope);
            addNewSegment(linePoints, lastSlope);
          } 
          linePoints.clear();
          linePoints.add(inputArray[i]);
        }
        lastSlope = slope;
      }
      
      if (linePoints.size() >= 3) {
        linePoints.add(basePoint);
        StdOut.println("Running addNewSegment for slope = " + lastSlope);
        addNewSegment(linePoints, lastSlope);
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
  

  private void addNewSegment(List<Point> linePoints, double slope) {
    Collections.sort(linePoints);
    Point beginPoint = linePoints.get(0);
    Point endPoint = linePoints.get(linePoints.size() - 1);
    
    List<Point> existingPoints = slopeSegments.get(slope);
    
    if (existingPoints == null) {
      StdOut.println("existingPoints came back as null!");
      existingPoints = new ArrayList<>();
      existingPoints.add(beginPoint);
      existingPoints.add(endPoint);
      slopeSegments.put(slope,  existingPoints);
      lineSegments.add(new LineSegment(beginPoint, endPoint));
    } else {
      // Check if points already exist along slope
      for (Point checkPoint : existingPoints) {
        if (checkPoint.compareTo(endPoint) == 0) {
          StdOut.println("DEBUG: Duplicate line segment being skipped!");
          return;
        }
      }
      existingPoints.add(endPoint);
      existingPoints.add(beginPoint);
      lineSegments.add(new LineSegment(beginPoint, endPoint));
    }
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