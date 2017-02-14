import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
	
	private SET<Point2D> pointSet;
	
	public PointSET() {
		pointSet = new SET<Point2D>();
	}
	
	public boolean isEmpty() {
		return pointSet.isEmpty();
	}
	
	public int size() {
		return pointSet.size();
	}
	
	public void insert(Point2D p) {
		if (p == null) {
			throw new java.lang.NullPointerException();
		}
		if (!pointSet.contains(p))
			pointSet.add(p);
	}
	
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new java.lang.NullPointerException();
		}
		return pointSet.contains(p);
	}
	
	public void draw() {
		for (Point2D p : pointSet) {
			p.draw();
		}
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new java.lang.NullPointerException();
		}
		List<Point2D> ls = new ArrayList<Point2D>();
		for (Point2D p : pointSet) {
			if (rect.contains(p)) {
				ls.add(p);
			}
		}
		return ls;
	}
	
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new java.lang.NullPointerException();
		}
		if (pointSet.isEmpty()) {
			return null;
		}
		Point2D minPoint = pointSet.min();
		double minDist = p.distanceTo(minPoint);
		for (Point2D each : pointSet) {
			double dist = p.distanceTo(each);
			if (dist < minDist) {
				minPoint = each;
				minDist = dist;
			}
		}
		return minPoint;
	}
	
	public static void main(String[] args) {
		
	}
	
}
