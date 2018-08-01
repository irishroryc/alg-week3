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
  private LineSegment[] lineSegments;
  
  // finds all line segments containing 4 points 
  public BruteCollinearPoints(Point[] points) {
     lineSegmentCount = 0;
  }
  
// the number of line segments
  public int numberOfSegments() {
    return lineSegmentCount;
  }
  
// the line segments
  public LineSegment[] segments() {
    return lineSegments;
  }
}