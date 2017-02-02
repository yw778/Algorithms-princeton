import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[] sites;
	private int count;
	private WeightedQuickUnionUF wquuf;
	private final int[] mx = { -1, 0, 1, 0 };
	private final int[] my = { 0, -1, 0, 1 };
	
	public Percolation(int n){
		if (n <= 0){
			throw new java.lang.IllegalArgumentException();
		}
		count = n;
		sites = new boolean[n*n];
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				sites[i*n+j]=false;
			}
		}
		wquuf = new WeightedQuickUnionUF(n * n);
	}
	
	public void open(int row, int col){
		if (row < 1 || row > count || col < 1 || col > count){
			throw new java.lang.IndexOutOfBoundsException();
		}
		int virtualRow = row - 1;
		int virtualColumn = col - 1;
		if (!sites[virtualRow * count + virtualColumn]){
			sites[virtualRow * count + virtualColumn] = true;
		}
		
		for (int m = 0; m < mx.length; m++){
			int mi = virtualRow + mx[m];
			int mj = virtualColumn + my[m];
			if (mi >= 0 && mi < count && mj >= 0 && mj < count){
				wquuf.union(virtualRow * count + virtualColumn, mi * count + mj);
			}
		}
	}	
	
	public boolean isOpen(int row, int col){
		if (row < 1 || row > count || col < 1 || col > count){
			throw new java.lang.IndexOutOfBoundsException();
		}
		int virtualRow = row - 1;
		int virtualColumn = col - 1;
		return sites[virtualRow * count + virtualColumn];
	}
	
	public boolean isFull(int row, int col){
		if (row < 1 || row > count || col < 1 || col > count){
			throw new java.lang.IndexOutOfBoundsException();
		}
		int virtualRow = row - 1;
		int virtualColumn = col - 1;
		for (int j = 0; j < count; j++){
			if (wquuf.connected(virtualRow * count + virtualColumn, j))
				return true;
		}
		return false;
	}
	
	public int numberOfOpenSites(){
		int number = 0;
		for (int i = 0; i<count; i++){
			for (int j = 0; j < count; j++){
				if (sites[i * count + j]){
					number++;
				}
			}
		}
		return number;
	}
	
	public boolean percolates(){
		for (int i = 1; i <= count; i++){
			if (isFull(count, i))
				return true;
		}
		return false;
	}
	
	public static void main(String[] args){
		
	}
	
}
