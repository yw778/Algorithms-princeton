import java.util.ArrayList;

public class BruteCollinearPoints {
	
	ArrayList<LineSegment> lss = new ArrayList<LineSegment>();
	
	public BruteCollinearPoints(Point[] points) {
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
		
		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {
				for (int m = j + 1; m < points.length; m++) {
					for (int n = m + 1; n < points.length; n++) {
						Point pointa = points[i];
						Point pointb = points[j];
						Point pointc = points[m];
						Point pointd = points[n];
						
						if (pointa.slopeTo(pointb) == pointb.slopeTo(pointc) && 
								pointb.slopeTo(pointc) == pointc.slopeTo(pointd) &&
								pointa.compareTo(pointb) < 1 && 
								pointb.compareTo(pointc) < 1 &&
								pointc.compareTo(pointd) < 1) {
							
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
