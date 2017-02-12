import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	
	private int ultimateMoves;
	private boolean sovable;
	private MinPQ<Node> queue;
	private MinPQ<Node> twinQueue;
	
	private class Node implements Comparable<Node> {
		
		int moves;
		Board bd;
		int priority;
		// previous with constructor
		Node previous;
		
		public Node(Node previous, Board bd, int moves) {
			this.bd = bd;
			this.moves = moves;
			this.priority = bd.manhattan() + moves;
			this.previous = previous;
		}
		// priority queue keys must be comparable 
		@Override
		public int compareTo(Node o) {
			if (this.priority < o.priority) {
				return -1;
			}else if (this.priority == o.priority) {
				return 0;
			} else {
				return 1;
			}
		}
	}
	
	public Solver(Board initial) {
		if (initial == null) {
			throw new java.lang.NullPointerException();
		}
		queue = new MinPQ<Node>();
		twinQueue = new MinPQ<Node>();
		queue.insert(new Node(null, initial, 0));
		twinQueue.insert(new Node(null, initial.twin(), 0));
		while ((!queue.min().bd.isGoal()) && (!twinQueue.min().bd.isGoal())) {
			Node min = queue.delMin();
			Node twinMin = twinQueue.delMin();
			for (Board b : min.bd.neighbors()) {
				if ((min.previous != null) && (min.previous.bd.equals(b))) {
					continue;
				}
				queue.insert(new Node(min, b, min.moves + 1));
			}
			for (Board bn : twinMin.bd.neighbors()) {
				if ((twinMin.previous != null) && (twinMin.previous.bd.equals(bn))) {
					continue;
				}
				twinQueue.insert(new Node(twinMin, bn, twinMin.moves + 1));
			}
		}
		if (queue.min().bd.isGoal()) {
			ultimateMoves = queue.min().moves;
			sovable = true;
		}
		if (twinQueue.min().bd.isGoal()) {
			ultimateMoves = -1;
			sovable = false;
		}
		
	}
	
	public boolean isSolvable() {
		return sovable;
	}
	
	public int moves() {
		return ultimateMoves;
	}
	
	public Iterable<Board> solution() {
		if (!sovable) return null;
		Stack<Board> st = new Stack<Board>();
		for (Node o = queue.min(); o != null; o = o.previous) {
			st.push(o.bd);
		}
		return st;
	}
	
	public static void main(String[] args) {
		
	}
	
}
