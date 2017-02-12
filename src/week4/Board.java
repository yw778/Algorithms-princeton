import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;

public class Board {
	// immutable data type
	private final int[][] node;
	private final int dimension;
	
	public Board(int[][] blocks) {
		dimension = blocks.length;
		node = new int[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				node[i][j] = blocks[i][j];
			}
		}
	}
	private int[][] copy2DArray(int[][] array) {
		int[][] copyArray = new int[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				copyArray[i][j] = array[i][j];
			}
		}
		return copyArray;
	}
	
	public int dimension() {
		return dimension;
	}
	
	public int hamming() {
		int count = 0;
		for (int i = 0; i < dimension * dimension; i++) {
			int row = i / dimension;
			int col = i % dimension;
			if ((node[row][col] != 0) && (node[row][col] != i + 1)) {
				count++;
			}
		}
		return count;
	}
	
	public int manhattan() {
		int count = 0;
		for (int i = 0; i < dimension * dimension; i++) {
			int row = i / dimension;
			int col = i % dimension;
			int value = node[row][col];
			int goalRow = (value - 1) / dimension;
			int goalCol = (value - 1) % dimension;
			if (value != 0) {
				count += Math.abs(goalRow - row) + Math.abs(goalCol - col);
			}
		}
		return count;
	}
	
	public boolean isGoal() {
		return hamming() == 0;		
	}
	
	public Board twin() {
		// todo when de =1
		// simplest implementation since any
		int[][] twin = this.copy2DArray(node); 
		int row = 0;
		int col = 0;
		int exchangeRow = 0;
		int exchangecol = 1;
		if (node[row][col] == 0) {
			row = 1;
		}
		if (node[exchangeRow][exchangecol] == 0) {
			exchangeRow = 1;
		}
		int temp = twin[row][col];
		twin[row][col] = twin[exchangeRow][exchangecol];
		twin[exchangeRow][exchangecol] = temp;
		return new Board(twin);
	}
	
	public boolean equals(Object y) {
		if (this == y) return true;
		if (y == null) return false;
		if (y.getClass() != this.getClass()) return false;
		Board yBoard = (Board) y; 
		if (yBoard.dimension() != this.dimension) return false;
	    boolean equalFlag = true;
//	    Arrays.deepEquals
		for (int i = 0; i < dimension * dimension; i++) {
			int row = i / dimension;
			int col = i % dimension;
			if (node[row][col] != yBoard.node[row][col]) {
				equalFlag = false;
				break;
			}
		}
		return equalFlag;
	}
	
	public Iterable<Board> neighbors() {
		List<Board> boards = new ArrayList<Board>();
		int[] mx = {1,-1,0,0};
		int[] my = {0,0,1,-1};
		boolean breakFlag = false;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (this.node[i][j] == 0){
					for (int k = 0; k < 4; k++) {
						int x = i + mx[k];
						int y = j + my[k];
						if (x >= 0 && x < dimension && y >= 0 && y < dimension) {
							int[][] neighbor = this.copy2DArray(node);
							neighbor[i][j] = node[x][y];
//							System.out.println(node[i][j]);
							neighbor[x][y] = node[i][j];
							boards.add(new Board(neighbor));
						}
					}
					breakFlag = true;
					break;
				}
			}
			if (breakFlag)
				break;
		}
		return boards;
	}
	
	public String toString() {
		// String builder a lot faster than cantenate string
		// since string is immutable
		StringBuilder out = new StringBuilder();
		out.append(dimension+"\n");
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				out.append(node[i][j]+" ");
			}
			out.append("\n");
		}
		return out.toString();
	}
	
	public static void main(String[] args) {
		In in = new In("src/puzzle04.txt");
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);
	    // test neighbors
	    for (Board b : initial.neighbors()) {
	    	System.out.println(b);
	    }

	}
}