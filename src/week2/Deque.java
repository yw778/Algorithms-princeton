import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	
	private Node first;
	private Node last;
	private int size;
	
	private class Node<Item> {
		Item value = null;
		Node next = null;
		Node previous = null;
	}

	public Deque() {
		first = null;
		last = null;
		size = 0;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public void addFirst(Item item) {
		if (item == null) {
			throw new java.lang.NullPointerException();
		}
		Node current = new Node();
		current.value = item;
		current.previous = null;
		current.next = first;
		if (isEmpty()) {
			last = current;
		} else {
			first.previous = current;	
		}
		first = current;
		size ++;
	}
	
	public void addLast(Item item) {
		if (item == null) {
			throw new java.lang.NullPointerException();
		}
		Node current = new Node();
		current.value = item;
		current.previous = last;
		current.next = null;
		if (isEmpty()) {
			first = current;
		} else {
			last.next = current;
		}
		last = current;
		size ++;
	}
	
	public Item removeFirst() {
		if (size == 0) {
			throw new java.util.NoSuchElementException();
		}
		Item value = (Item) first.value;
		first = first.next;
		size --;
		if (isEmpty()) {
			last = null;
		} else {
			first.previous = null;
		}
		
		return value;
	}
	
	public Item removeLast() {
		if (size == 0) {
			throw new java.util.NoSuchElementException();
		}
		Item value = (Item) last.value;
		last = last.previous;
		size--;
		if (isEmpty()) {
			first = null;
		} else {
			last.next = null;
		}
		return value;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}
	
	private class DequeIterator implements Iterator<Item> {
		
		Node current = first;
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext()){
				throw new java.util.NoSuchElementException();
			}
			Item value = (Item) current.value;
			current = current.next;
			return value;
		}
		
		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		
	}

	public static void main(String[] args) {
		
	}

}

	
