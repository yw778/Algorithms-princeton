import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	public static void main(String[] args){
		int N = Integer.parseInt(args[0]);
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()){
			String value = StdIn.readString();
			rq.enqueue(value);
		}
		for (int i = 0; i < N; i++) {
			StdOut.println(rq.dequeue());
		}
	}
}
