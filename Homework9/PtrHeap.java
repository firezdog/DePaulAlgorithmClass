package algs24;

import stdlib.StdIn;
import stdlib.StdOut;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


/**
 *  The <tt>PtrHeap</tt> class is the priorityQ class from Question 2.4.24.
 *  It represents a priority queue of generic keys.
 *  
 *  It supports the usual <em>insert</em> and <em>delete-the-maximum</em>
 *  operations, along with methods for peeking at the maximum key,
 *  testing if the priority queue is empty, and iterating through
 *  the keys.
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */

public class PtrHeap<K extends Comparable<? super K>> {

	static class Node<K> {
		K value;
		Node<K> parent;
		Node<K> lchild;
		Node<K> rchild;
	}

	private boolean lessThan(Node a, Node b) {
		K aVal = (K) a.value;
		K bVal = (K) b.value;
		if (aVal.compareTo(bVal) < 0) return true;
		return false;
	}

	private int size;
	private Node<K> root;

	boolean isRoot(Node<K> n) {
		return n == root;
	}

	Node<K> find(int n) {
		if (n < 1 || n > size) throw new NoSuchElementException();
		if (n == 1) return root;
		String directions = Integer.toBinaryString(n);
		Node walker = root;
		for (int i = 1; i < directions.length(); i++) {
			if (directions.charAt(i) == '0') walker = walker.lchild;
			else walker = walker.rchild;
		}
		return walker;
	}

	void exch(Node<K> n1, Node<K> n2) {
		K temp = n1.value;
		n1.value = n2.value;
		n2.value = temp;
	}

	/**
	 * Create an empty priority queue
	 */
	public PtrHeap() {
		size = 0;
		root = null;
	}

	/**
	 * Is the priority queue empty?
	 */
	public boolean isEmpty() {
		return size == 0;
	}


	/**
	 * Return the number of items on the priority queue.
	 */
	public int size() {
		return size;
	}

	/**
	 * Return the largest key on the priority queue.
	 * Throw an exception if the priority queue is empty.
	 */
	public K max() {
		if (this.isEmpty()) throw new NoSuchElementException();
		return root.value;
	}

	private void swim(Node<K> n) {
		Node current = n;
		while (current != root && lessThan(current.parent, current)) {
			exch(current, current.parent);
			current = current.parent;
		}
	}

	/**
	 * Add a new key to the priority queue.
	 */
	public void insert(K x) {
		// add node to end.
		Node child = new Node();
		child.value = x;
		size++;
		if (size == 1) {
			root = child;
		} else {
			// go the parent of the node to be inserted -- even => left, odd => right
			Node parent = find(Math.max(size / 2, 1));
			if (size % 2 == 0) {
				parent.lchild = child;
			} else {
				parent.rchild = child;
			}
			child.parent = parent;
		}
		swim(child);
	}

	/**
	 * Delete and return the largest key on the priority queue.
	 * Throw an exception if the priority queue is empty.
	 */
	public K delMax() {
		if (isEmpty()) throw new NoSuchElementException();
		if (size == 1) {
			K value = root.value;
			root = null;
			size--;
			return value;
		}
		K oldMax = max();
		Node last = find(size);
		exch(root, last);
		Node parent = last.parent;
		last.parent = null;
		if (parent.lchild == last) parent.lchild = null;
		else parent.rchild = null;
		sink(root);
		size--;
		return oldMax;
	}

	private void sink(Node n) {
		Node current = n;
		boolean hasLChild = current.lchild != null;
		boolean hasRChild = current.rchild != null;
		while (hasLChild) {
			Node bigKid = current.lchild;
			if (hasRChild && lessThan(bigKid, current.rchild)) bigKid = current.rchild;
			if (!lessThan(current, bigKid)) break;
			exch(current, bigKid);
			current = bigKid;
			hasLChild = current.lchild != null;
			hasRChild = current.rchild != null;
		}
	}

	private void showHeap() {
		StdOut.println(showHeapTraversal());
	}

	private String showHeapTraversal() {
		if (this.isEmpty()) return "[]\n";
		int levels = (int) (Math.log(size) / Math.log(2));
		int currentLevel = 1;
		Node[] children = new Node[]{root};
		String s = "";
		while (currentLevel <= levels + 1) {
			Node[] grandChildren = new Node[(int) Math.pow(2, currentLevel)];
			int position = 0;
			for (int i = 0; i < children.length; i++) {
				Node currentParent = children[i];
				if (currentParent == null) break;
				s += currentParent.value + " ";
				grandChildren[position++] = currentParent.lchild;
				grandChildren[position++] = currentParent.rchild;
			}
			s += "\n";
			children = grandChildren;
			currentLevel++;
		}
		return s;
	}

	// Recursive + preorder
	private String showHeapPreOrder() {
		return "[ " + showNode(root) + "]";
	}

	// Preorder
	private String showNode(Node n) {
		if (n == null) return "";
		String s = n.value + " ";
		s += showNode(n.lchild) + showNode(n.rchild);
		return s;
	}

	public String toString() {
		// return showHeapPreOrder();
		return showHeapTraversal();
	}

	static void randomSortTest(int size) throws TestException {
		PtrHeap<Integer> pq = new PtrHeap<>();
		Integer[] sample_array = new Integer[size];
		for (int i = 0; i < size; i++) { sample_array[i] = i + 1; }
		List<Integer> sample_list = Arrays.asList(sample_array);
		Collections.shuffle(sample_list);
		for (int i : sample_list) {
			pq.insert(i);
		}
		for (int i = 0; i < size; i++) {
			sample_array[i] = pq.delMax();
		}
		Integer[] result = new Integer[size];
		for (int i = 0; i < size; i++) {
			result[i] = size - i;
		}
		if (!Arrays.equals(sample_array, result))
			throw new TestException("Result of successive de-queues is incorrect: " + Arrays.deepToString(sample_array));
	}

	static void test() throws TestException {
		// a queue is empty on construction and of size 0
		PtrHeap<Integer> pq = new PtrHeap<>();
		StdOut.println("New priority queue created.");
		if (pq.size() != 0) throw new TestException("Expected size: 0 -- actual size: " + pq.size());
		if (!pq.isEmpty()) throw new TestException("Priority queue should be empty.");
		// De-queueing empty queue causes exception.
		StdOut.println("Trying to dequeue from empty queue");
		try { pq.delMax(); } catch(Exception t) {
			if (!(t instanceof NoSuchElementException)) throw new TestException("Correct exception was not thrown when deleting from empty queue");
		}
		// after N enqueues, queue is not empty and size is N.
		StdOut.println("Enqueueing 10 numbers from 1 to 10");
		for (int i = 0; i < 10; i++) {
			pq.insert(i);
		}
		if (pq.isEmpty()) throw new TestException("Priority queue should not be empty.");
		if (pq.size() != 10) throw new TestException("Expected size: 10 -- actual size: " + pq.size());
		// Randomly enqueueing 10 items from 1 to 10 and then deleting max 10 times should yield numbers 10 to 1.
		int sampleSize = 250;
		int trialSize = 100;
		StdOut.println("Randomly enqueueing and de-queueing numbers from 1 to " + sampleSize + ", " + trialSize + " times");
		for (int i = 0; i < 100; i++) {
			randomSortTest(sampleSize);
		}
	}

	private static class TestException extends Exception {
		public TestException(String message) {
			super(message);
		}
	}

	static void sampleOutput() {
		PtrHeap<String> pq = new PtrHeap<>();
		// StdIn.fromString("1 2 3 4 5 6 7 8 - - - - - - - - 8 7 6 5 4 3 2 1 - - - - - - - -");
		StdIn.fromString("10 20 30 40 50 - - - 05 25 35 - - - 70 80 05 - - - - ");
		while (!StdIn.isEmpty()) {
			StdOut.println ("pq:  "); pq.showHeap();
			String item = StdIn.readString();
			StdOut.println("Old size: " + pq.size());
			if (item.equals("-")) StdOut.println("deleting max: " + pq.delMax());
			else {
				pq.insert(item);
				StdOut.println("inserted " + item);
			}
			StdOut.println("New size: " + pq.size());
		}
		StdOut.println("(" + pq.size() + " left on pq)");
	}

	public static void main(String[] args) {
		StdOut.println("================== TESTING ===================");
		try { test(); } catch (TestException t) { StdOut.println(t); }
		StdOut.println("=================SAMPLE OUTPUT================");
		sampleOutput();
	}

}

