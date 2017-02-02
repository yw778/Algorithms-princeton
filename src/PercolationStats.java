import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private double mean;
	private double std;
	private double loConfi;
	private double upConfi;
	
	public PercolationStats(int n, int trials){
		if (n <= 0 || trials <= 0){
			throw new java.lang.IllegalArgumentException();
		}
		
		double[] threshold = new double[trials];

		for (int i = 0; i < trials; i++){
			Percolation pcTrial= new Percolation(n);
			int j = 0;
			while(j <= (n * n - 1)){
				int pn = (int)Math.random() * n * n;
				int row = pn / n + 1;
				int col = pn % n + 1;
				if (pcTrial.isOpen(row, col)){
					continue;
				}else{
					pcTrial.open(row, col);
					j++;
					if (pcTrial.percolates()){
						threshold[i] = ((double) trials) / (n * n);
						break;
					}
				}
				
			}
				
		}
		
		for (int k = 0; k < trials; k++){
			mean += threshold[k];
		}
		mean /= trials;
		for (int k = 0; k < trials; k++){
			std +=  Math.pow(threshold[k] - mean, 2);
		}
		std /= trials - 1;
		std = Math.sqrt(std);
		
		loConfi = mean - 1.96 * std / Math.sqrt(trials);
		upConfi = mean + 1.96 * std / Math.sqrt(trials);
		
	}
	
	public double mean(){
		return mean;
	}
	public double stddev() {
		return std;
	}
	public double confidenceLo(){
		return loConfi;
	}
	public double confidenceHi(){
		return upConfi;
	}
	public static void main(String[] args){
		
		int n = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		
		PercolationStats ps = new PercolationStats(n, T);
		System.out.println("mean                    = " + ps.mean());
		System.out.println("stddev                  = " + ps.stddev());
		System.out.println("95% confidence interval = " + ps.confidenceLo()
				+ ", " + ps.confidenceHi());
		
	} 

}
