import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	
	private ArrayList<LineSegment> lss = new ArrayList<LineSegment>();
	
	public FastCollinearPoints(Point[] inputPoints) {
		// immutable		
		Point[] points = inputPoints.clone();
		
		if (points == null) {
			throw new java.lang.NullPointerException();
		}
		for (Point p : points) {
			if (p == null) {
				throw new java.lang.NullPointerException();
			}
		}
		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {
				if (points[i].compareTo(points[j]) == 0) {
					throw new java.lang.IllegalArgumentException();
				}
			}
		}
		// sort it in natural order by comparable interface automatically
		Arrays.sort(points);		
		// iterate over each point
		for (int j = 0; j < points.length; j++) {
			Point[] arraySlopeOrder = Arrays.copyOfRange(points, j, points.length);
			Point p = points[j];
			Arrays.sort(arraySlopeOrder, p.slopeOrder());
			ArrayList<Point> ps = new ArrayList<Point>();
			ps.add(p);
			// after sort p is always in the first place
			for (int i = 1; i < arraySlopeOrder.length - 1; i++) {
				Point slopePoint = arraySlopeOrder[i];
				Point nextSlopePoint = arraySlopeOrder[i + 1];
				
				double slope = p.slopeTo(slopePoint);
				double nextslope = p.slopeTo(nextSlopePoint);
				
				if (slope == nextslope) {
					if (ps.get(ps.size() - 1) != slopePoint)
						ps.add(slopePoint);
					ps.add(nextSlopePoint);
				} 
				// handle condition when loop ends
				if (slope != nextslope || (i == (arraySlopeOrder.length - 2))){
					if (ps.size() > 3) {
						lss.add(new LineSegment(p, ps.get(ps.size() - 1)));						
					} 
					ps.clear();
					ps.add(p);
				}
			}	
		}
	}
	
	public int numberOfSegments() {
		return lss.size();
	}
	
	public LineSegment[] segments() {
		return lss.toArray(new LineSegment[lss.size()]);
	}

}