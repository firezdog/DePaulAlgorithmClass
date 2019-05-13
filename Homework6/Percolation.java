package algs15.perc;

import stdlib.*;
import algs15.*;

import java.util.NoSuchElementException;

// Uncomment the import statements above.

// You can test this using InteractivePercolationVisualizer and PercolationVisualizer
// All methods should make at most a constant number of calls to the UF data structure,
// except percolates(), which may make up to N calls to the UF data structure.
public class Percolation {
	int N;
	boolean[] open;
	// TODO: more fields to add here -- add UF
	CompressionUF UF;

	public Percolation(int N) {
		// First row goes from 1 to N, second from, N+1 to 2N; 0 is top virtual; N*N + 1 is bottom virtual.
		this.N = N;
		this.open = new boolean[N * N + 1];
		// TODO: more to do here.  -- initialize UF, connect top to 0, connect bottom to N+1
		UF = new CompressionUF(N * N + 2);
		for (int i = 0; i < N; i++) {
			// connect 1 to N (first row) to top virtual
			UF.union(0, 1 + i);
			// connect bottom row (N + 1 to N * N to bottom virtual
			// UF.union(N * N + 1, N * N - i);
			// + efficiency but leads to "backwash"
		}
	}

	// open site (row i, column j) if it is not already
	public void open(int i, int j) {
		checkBounds(i,j);
		int site = convertCell(i, j);
		open[site] = true;
		// TODO: more to do here. -- connect to open neighbors
		/* Not counting diagonals, each cell has four neighbors:

			LEFT (i, j - 1)
			RIGHT (i, j + 1)
			ABOVE(i - 1, j)
			BELOW (i + 1, j)

		For some value sof i and j, there are fewer neighbors：

			i == 1 -- top row, no ABOVE
			i == 3 -- bottom row, no BELOW
			j == 1 -- left row, no LEFT
			j == 3 -- right row, no RIGHT

			Remember we don't need to throw exceptions below -- if a neighbor doesn't exist, we just don't open it.
		*/
		if (i > 0 && isOpen(i - 1, j)) {
			int top = convertCell(i - 1, j);
			UF.union(site, top);
		}
		if (j < N - 1 && isOpen(i , j + 1)) {
			int right = convertCell(i, j + 1);
			UF.union(site, right);
		}
		if (i < N - 1 && isOpen(i + 1, j)) {
			int bottom = convertCell(i + 1, j);
			UF.union(site, bottom);
		}
		if (j > 0 && isOpen(i, j - 1)) {
			int left = convertCell(i, j - 1);
			UF.union(site, left);
		}
	}

	private void checkBounds(int i, int j) {
		if (0 > i || N - 1 < i) throw new NoSuchElementException();
		if (0 > j || N - 1 < j) throw new NoSuchElementException();
	}

	// From cell at row i and column j derive UF node identifier
	private int convertCell(int i, int j) {
		checkBounds(i, j);
		return i * N + j + 1;
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		int entry = convertCell(i,j);
		return open[entry];
	}
	// is site (row i, column j) full? -- i.e. is it connected to virtual top?
	public boolean isFull(int i, int j) {
		// TODO
		int site = convertCell(i,j);
		return UF.connected(0, site) && open[site];
	}
	// does the system percolate? -- is the virtual top connected to the virtual bottom?
	public boolean percolates() {
		// this is less efficient, but it is one way of removing "backwash".
		for (int i = N * N; i > N * (N-1); i --) {
			if (UF.connected(0, i)) return true;
		}
		return false;
	}

	public static void main(String[] args) {
		// SETUP
		int N = 3;
		Percolation p = new Percolation(N);
		StdOut.printf("Top row connected to virtual top: %b\n", p.UF.connected(0, N)); // expect true
		StdOut.printf("Virtual top connected to non-top row: %b\n", p.UF.connected(0, N + 1)); // expect false
		StdOut.printf("Bottom row connected to virtual bottom: %b\n", p.UF.connected(N*N + 1, N * N - N + 1)); // expect true
		StdOut.printf("Virtual bottom connected to non-bottom row: %b\n", p.UF.connected(N*N + 1, N * N - N)); // expect false
		StdOut.printf("Percolates: %b\n", p.percolates()); // expect false
		// EXCEPTIONS -- OPENING OUT OF BOUNDS
		try { p.open(0, 0); }
		catch (NoSuchElementException e) { StdOut.println("Cannot open (0,0)"); }
		try { p.open(3, 3); }
		catch (NoSuchElementException e) { StdOut.println("Cannot open (3,3)"); }
		try { p.open(0, -1); }
		catch (NoSuchElementException e) { StdOut.println("Cannot open (0,-1)"); }
		try { p.open(-1, 0); }
		catch (NoSuchElementException e) { StdOut.println("Cannot open (-1,0)"); }
		try { p.open(0, 3); }
		catch (NoSuchElementException e) { StdOut.println("Cannot open (0,3)"); }
		try { p.open(3, 0); }
		catch (NoSuchElementException e) { StdOut.println("Cannot open (3,0)"); }
		// TEST CONNECTIONS -- ABOVE, BELOW, LEFT, RIGHT
		StdOut.printf("Percolates: %b\n", p.percolates());
		StdOut.println("Open (1,1)");
		StdOut.printf("Percolates: %b\n", p.percolates());
		p.open(1,1);
		StdOut.printf("Site is full: %b\n", p.isFull(1,1));
		p.open(1, 0);
		StdOut.println("Open (1,0)");
		StdOut.printf("Percolates: %b\n", p.percolates());
		// site will be full because it is connected to top
		StdOut.printf("Site is full: %b\n", p.isFull(1,1));
		p.open(1, 2);
		StdOut.println("Open (1,2)");
		StdOut.printf("Percolates: %b\n", p.percolates());
		p.open(0, 1);
		StdOut.println("Open (0,1)");
		StdOut.printf("Percolates: %b\n", p.percolates());
		p.open(2, 1);
		StdOut.println("Open (2,1)");
		StdOut.printf("Percolates: %b\n", p.percolates());
	}
}