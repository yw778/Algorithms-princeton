import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	
	ArrayList<LineSegment> lss = new ArrayList<LineSegment>();
	
	public FastCollinearPoints(Point[] points) {
				
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
		for (Point p : points) {
			Point[] arraySlopeOrder = points.clone();
			// after the sort, p is always in the first place.
			Arrays.sort(arraySlopeOrder, p.slopeOrder());
			ArrayList<Point> ps = new ArrayList<Point>();
			ps.add(p);
			for (int i = 1; i < arraySlopeOrder.length - 1; i++) {
				Point slopePoint = arraySlopeOrder[i];
				Point nextSlopePoint = arraySlopeOrder[i + 1];
				
				double slope = p.slopeTo(slopePoint);
				double nextslope = p.slopeTo(nextSlopePoint);
				
				if (slope == nextslope) {
					if (ps.get(ps.size() - 1) != slopePoint)
						ps.add(slopePoint);
					ps.add(nextSlopePoint);
				} else {
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
