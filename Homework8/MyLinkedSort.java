package algs13;


import stdlib.*;

// PROBLEM 2.2.17
public class MyLinkedSort {
    static class Node {
        public Node() { }
        public double item;
        public Node next;

        // Adding toString for debugging
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append("{");
            Node walker = this;
            while (walker != null) {
                s.append(walker.item);
                if (walker.next != null) s.append(", ");
                walker = walker.next;
            }
            s.append("}");
            return s.toString();
        }
    }

    int N;
    Node first;
    
    public MyLinkedSort () {
        first = null;
        N = 0;
        checkInvariants ();
    }

    private void myassert (String s, boolean b) { if (!b) throw new Error ("Assertion failed: " + s); }
    private void checkInvariants() {
        myassert("Empty <==> first==null", (N == 0) == (first == null));
        Node x = first;
        for (int i = 0; i < N; i++) {
            if (x==null) {
                throw new Error ("List too short!");
            }
            x = x.next;
        }
        myassert("EndOfList == null", x == null);
    }

    public boolean isEmpty () { return first == null; }
    public int size () { return N; }
    public void add (double item) {
        Node newfirst = new Node ();
        newfirst.item = item;
        newfirst.next = first;
        first = newfirst;
        N++;
    }

    private static void print (String s, Node b) {
        StdOut.print (s + ": ");
        for (Node x = b; x != null; x = x.next)
            StdOut.print (x.item + " ");
        StdOut.println ();
    }
    private static void print (String s, Node b, double i) {
        StdOut.print (s + ": ");
        for (Node x = b; x != null; x = x.next)
            StdOut.print (x.item + " ");
        StdOut.println (": " + i);
    }

    static public Node sort(Node l){ //
        if (l.next == null) {
            return l;
        }
        Node[] halves = split(l);
        halves[0] = sort(halves[0]);
        halves[1] = sort(halves[1]);
        Node n = merge(halves[0], halves[1]);
        return n;
	   // base case: list is of size 1. reurn
	   // otherwise use split to create two lists
	   // recursively sort each of them
	   // use merge to combine them, and return the result
	}

	/*static public Node[] split(Node l) {
        Node r = l.next;
        l.next = null;
        return new Node[] {l, r};
    }*/
		 
	static public Node[] split(Node l){
        // parameter is a List
        // it returns an array of size 2
        // the 0th element is the left list
        // the first element is the right list
        // IDEA -- build each up as you go? -- but maybe it doesn't matter.
        // Merge seems right but this isn't necessarily working.  get to half and half.
        Node r = l.next;
        l.next = null;
        Node c = r.next;
        r.next = null;
        while (c != null) {
            Node t = c.next;
            c.next = l;
            Node t2 = r;
            l = c;
            r = l;
            l = t2;
            c = t;
        }
        return new Node[] {l, r};
	  }
	
	static public Node merge(Node lt, Node rt){
	    // merge creates a new LinkedList
	    // whose elements come from the lt and rt MyLinkedLists
		Node n;
        if (lt.item < rt.item) {
            n = lt;
            lt = lt.next;
        }
        else {
            n = rt;
            rt = rt.next;
        }
        Node c = n;
        while (true) {
            if (rt == null) {
                c.next = lt;
                return n;
            }
            if (lt == null) {
                c.next = rt;
                return n;
            }
            if (lt.item < rt.item) {
                c.next = lt;
                lt = lt.next;
            } else {
                c.next = rt;
                rt = rt.next;
            }
            c = c.next;
        }
	}

	private static void sampleSort() {
        StdOut.println("=============SAMPLE SORT==================");
	    int[] al = new int[20];
        for (int i = 0; i < al.length; i++)
            al[i] = i;
        StdRandom.shuffle (al);
        MyLinkedSort b1 = new MyLinkedSort ();
        for (int i:al) b1.add(i);
        MyLinkedSort.print("before sort", b1.first);
        Node res1 = MyLinkedSort.sort(b1.first);
        MyLinkedSort.print("after sort", res1);
    }

	private static void doubleTest() {
        double time = 0;
        double prevTime;
        int N = 256;
        double avg = 0;
        StdOut.println("=============DOUBLING TEST================");
        StdOut.printf("%10s %10s %10s\n", "N", "time", "ratio");
        StdOut.println("==========================================");
        for (int t = 1; t <= 12; t++) {
            prevTime = time;
            N *= 2;
            int[] a1 = new int[N];
            for (int i = 0; i < a1.length; i++)
                a1[i] = i;
            StdRandom.shuffle (a1);
            MyLinkedSort b1 = new MyLinkedSort ();
            for (int i:a1) b1.add(i);
            Stopwatch sw = new Stopwatch();
            Node res1 = MyLinkedSort.sort(b1.first);
            time = sw.elapsedTime();
            double ratio = time / prevTime;
            // otherwise ratio is very close to 0?
            if (ratio >= 0 && prevTime != 0) avg += ratio;
            StdOut.printf ("%10d %10.3f %10.3f\n", N, time, time / prevTime);
        }
        avg = avg / 12;
        StdOut.printf("Average doubling ratio: %5.3f\n", avg);
    }

    public static void main (String args[]) {
	    sampleSort();
	    doubleTest();
        // Follow the above pattern to write a doubling test
        // to calculate ratios.  Starting from size 200 and going
        // to size 200 x 4096
     
         
    }
}



