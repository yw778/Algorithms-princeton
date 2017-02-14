import java.awt.Color;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	
	private Node root;	
	private int size;
	
	private static class Node {
		
	    private Point2D p;      // the point
	    private RectHV rect;    // the axis-aligned rectangle corresponding to this node
	    private Node lb;        // the left/bottom subtree
	    private Node rt;        // the right/top subtree
	    
	    public Node(Point2D p, RectHV rect) {
		   this.p = p;
		   this.rect = rect;
		   this.lb = null;
		   this.rt = null;
	    }	    
	}
	// construct an empty set of points
	public KdTree() {
		root = null;
		size = 0;
	}
	// is the set empty? 
	public boolean isEmpty() {
		return root == null;
	}
	// number of points in the set 
	public int size() {
		return size;
	}
	
	private enum Orientation {
		LEFT,
		RIGHT,
		TOP,
		BOTTOM
	}
	
	private RectHV splitRect (RectHV rect, Point2D point, Orientation orientation) {
		RectHV newRect;
		switch (orientation) {
			case LEFT:
				newRect = new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax());
				break;
			case RIGHT:
				newRect = new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax());
				break;
			case TOP:
				newRect = new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax());
				break;
			case BOTTOM:
				newRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y());
				break;
			default:
				throw new IllegalArgumentException();		
		}
		return newRect;
	}
	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null) {
			throw new java.lang.NullPointerException();
		}
		if (root == null) {
			root = new Node(p, new RectHV(0, 0, 1, 1));
			size += 1;
		}
		Node node = root;
		boolean compareX = true;
		while (true) {
			if (node.p.equals(p)) return;
			if (compareX && (p.x() < node.p.x())) {
				if (node.lb != null) {
					node = node.lb; 
					compareX = !compareX;
					continue;
				} else {
					RectHV rect = splitRect(node.rect, p, Orientation.LEFT);
					node.lb = new Node(p, rect);
					size += 1;
					return;
				}
			}
			if (compareX && (p.x() > node.p.x())) {
				if (node.rt != null) {
					node = node.rt;
					compareX = !compareX;
					continue;
				} else {
					RectHV rect = splitRect(node.rect, p, Orientation.RIGHT);
					node.rt = new Node(p, rect);
					size += 1;
					return;
				}			
			}
			if (!compareX && (p.y() < node.p.y())) {
				if (node.lb != null) {
					node = node.lb;
					compareX = !compareX;
					continue;
				} else {
					RectHV rect = splitRect(node.rect, p, Orientation.BOTTOM);
					node.rt = new Node(p, rect);
					size += 1;
					return;
				}	
			}
			if (!compareX && (p.y() > node.p.y())) {
				if (node.rt != null) {
					node = node.rt;
					compareX = !compareX;
					continue;
				} else {
					RectHV rect = splitRect(node.rect, p, Orientation.TOP);
					node.rt = new Node(p, rect);
					size += 1;
					return;
				}
			}
		}		
	}
	// does the set contain point p? 
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new java.lang.NullPointerException();
		}
		Node node = root;
		boolean compareX = true;		
		while (true) {
			if (node == null) return false;
			if (node.p.equals(p)) return true;
			
			if ((compareX && (p.x() < node.p.x())) 
				|| (!compareX) && (p.y() < node.p.y())) {
				node = node.lb;
			} else {
				node = node.rt;
			}
			compareX = !compareX;
		}
	}
	
	private void draw(Node node, boolean compareX) {
		if (node == null) return;
		StdDraw.setPenColor(Color.black);
		StdDraw.setPenRadius(0.01);
		node.p.draw();
		if (compareX) {
			StdDraw.setPenColor(Color.red);
			StdDraw.setPenRadius();
			StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
		} else {
			StdDraw.setPenColor(Color.blue);
			StdDraw.setPenRadius();
			StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
		}
		draw(node.lb, !compareX);
		draw(node.rt, !compareX);
	}
	// draw all points to standard draw 
	public void draw() {
		draw(root, true);
	}
	
	private Stack<Point2D> range(RectHV rect, Node node, Stack<Point2D> points) {
		if (node == null) return points;
		if (rect.contains(node.p))
			points.push(node.p);
		if (rect.intersects(node.rect)){
			range(rect, node.lb, points);
			range(rect, node.rt, points);
		}
		return points;
	}
	// all points that are inside the rectangle 
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new java.lang.NullPointerException();
		}
		Stack<Point2D> points = new Stack<Point2D>();
		Node node = root;
		if (node != null) {
			range(rect, node, points);
		}
		return points;
	}
	
	private Point2D nearest(Node node, Point2D p, Point2D champion, boolean compareX) {
		if (node == null) return champion;
		if (champion.distanceTo(p) > node.p.distanceTo(p)) 
			champion = node.p;
		if (champion.distanceTo(p) > node.rect.distanceTo(p)){
			if ((compareX && (p.x() < node.p.x())) 
					|| (!compareX) && (p.y() < node.p.y())) {
				nearest(node.lb, p, champion, !compareX);
				nearest(node.rt, p, champion, !compareX);
			} else {
				nearest(node.rt, p, champion, !compareX);
				nearest(node.lb, p, champion, !compareX);
			}
		}
		return champion;	
	}
	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new java.lang.NullPointerException();
		}
		if (root == null) return null;
		else return nearest(root, p, root.p, true);
	}
	// unit testing of the methods (optional) 
	public static void main(String[] args) {
		
	}
}
