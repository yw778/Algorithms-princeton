import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	
	private ArrayList<LineSegment> lss = new ArrayList<LineSegment>();
	
	public BruteCollinearPoints(Point[] inputPoints) {
		//immutable
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
		
		Arrays.sort(points);
		
		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {
				for (int m = j + 1; m < points.length; m++) {
					for (int n = m + 1; n < points.length; n++) {
						Point pointa = points[i];
						Point pointb = points[j];
						Point pointc = points[m];
						Point pointd = points[n];
						
						if (pointa.slopeTo(pointb) == pointb.slopeTo(pointc) && 
								pointb.slopeTo(pointc) == pointc.slopeTo(pointd)) {
							
							lss.add(new LineSegment(pointa, pointd));
							
						}
					}
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
