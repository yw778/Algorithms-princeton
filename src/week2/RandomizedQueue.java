import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] array;
	private int size;
	
	public RandomizedQueue() {
		array = (Item[]) new Object[1];
		size = 0;
	}
	
	public boolean isEmpty()  {
		return size==0;
	}
	
	public int size() {
		return size;
	}
		
	private void resize(int N) {
		Item[] newArray = (Item[]) new Object[N];
		for (int i = 0; i < size; i++){
			newArray[i] = array[i];
		}
		array = newArray;
	}
	
	public void enqueue(Item item) {
		if (item == null){
			throw new java.lang.NullPointerException();
		}
		if (size == array.length) 
			resize(2 * array.length);
		
		array[size ++] = item;
	}
	
	public Item dequeue() {
		if (size == 0) {
			throw new java.util.NoSuchElementException();
		}
		int randomIndex = StdRandom.uniform(size);
		Item value = array[randomIndex];
		array[randomIndex] = array[--size]; 
		array[size] = null; //prevent loitering
		if (size == array.length / 4 && size > 0) resize(array.length / 2);
		return value;
	}
	
	public Item sample()  {
		if (size == 0) {
			throw new java.util.NoSuchElementException();
		}
		int randomIndex = StdRandom.uniform(size);
		Item value = array[randomIndex];
		return value;
	}
	
	public Iterator<Item> iterator()  {
		return new RandomIterator();
	}
	
	private class RandomIterator<Item> implements Iterator<Item> {
		
		Item[] copyArray;
		int copySize;

		public RandomIterator() {
			copyArray = (Item[]) new Object[size];
			for (int i = 0; i < size; i++) {
				copyArray[i] = (Item) array[i];
			}
			copySize = size;
		}
		
		@Override
		public boolean hasNext() {			
			return copySize > 0;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			int randomIndex = StdRandom.uniform(copySize);
			Item value = copyArray[randomIndex];
			copyArray[randomIndex] = copyArray[--copySize]; 
			copyArray[copySize] = null; //prevent loitering
			return value;
		}
		
		public void remove (Item item) {
			throw new java.lang.UnsupportedOperationException();
		}
		
	}  
	
	public static void main(String[] args) {
		
	}

}
