package Homework3;

import edu.princeton.cs.algs4.*;

public class MyLinked {
    static class Node {
        public Node() { }
        public double item;
        public Node next;
    }

    int N;
    Node first;
    
    public MyLinked () {
        first = null;
        N = 0;
        checkInvariants ();
    }

    private static void myassert (String s, boolean b) { if (!b) throw new Error ("Assertion failed: " + s); }
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

    // return Double.NEGATIVE_INFINITY if the linked list is empty
    public double max () { return max (first); }
    private static double max (Node x) {
        double max = Double.NEGATIVE_INFINITY;
        for (Node curr = x; curr != null; curr = curr.next) {
            if (max < curr.item) max = curr.item;
        }
        return max;
    }

    public double maxRecursive () { return maxRecursive (first, Double.NEGATIVE_INFINITY); }
    private static double maxRecursive (Node x, double result) {
        if (x == null) return result;
        if (result < x.item) result = x.item;
        return maxRecursive(x.next, result);
    }

    // delete the kth element
    public void delete (int k) {
        if (k < 0 || k >= N) throw new IllegalArgumentException ();
        if (first == null) return;
        if (k == 0) {
            first = first.next;
        } else {
            // get the delendum and the prior of the delendum
            Node prior = first;
            Node del = first.next;
            // if k represents the element to be deleted, i represents k's prior
            for (int i = 0; i < k-1; i++) {
                prior = prior.next;
                del = del.next;
            }
            // next of the prior => next of the delendum
            prior.next = del.next;
        }
        // remember to decrement the counter!!
        N--;
        checkInvariants ();
    }

    // reverse the list "in place"... without creating any new nodes
    public void reverse () {
        if (N < 2) return;
        Node prev = first;
        Node curr = prev.next;
        prev.next = null;
        // curr is always the head of our new chain -- at the end of each step, nothing connects to it.
        while (curr.next != null) {
            Node temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        first = curr;
        first.next = prev;
        checkInvariants ();
    }

    // remove 
    public void remove (double item) {
        if (N == 0) return;
        while (N > 0 && first.item == item) {
            first = first.next;
            N--;
        }
        if (N > 0) {
            Node prev = first;
            Node curr = first.next;
            while (curr != null) {
                if (curr.item != item) {
                    prev = curr;
                    curr = curr.next;
                    continue;
                }
                Node temp = curr.next;
                curr = temp;
                prev.next = curr;
                N--;
            }
        }
        checkInvariants ();
    }


    private static void print (String s, MyLinked b) {
        StdOut.print (s + ": ");
        for (Node x = b.first; x != null; x = x.next)
            StdOut.print (x.item + " ");
        StdOut.println ();
    }
    private static void print (String s, MyLinked b, double i) {
        StdOut.print (s + ": ");
        for (Node x = b.first; x != null; x = x.next)
            StdOut.print (x.item + " ");
        StdOut.println (": " + i);
    }
    private static void testMax () {
        MyLinked b = new MyLinked ();
        print ("empty", b, b.max());
        myassert("testMax -- empty: max == -Infinity", b.max() == Double.NEGATIVE_INFINITY);
        b.add (-1);
        print ("singleton", b, b.max());
        myassert("testMax -- singleton: max == -1", b.max() == -1);
        b.add (-2);
        b.add (-3);
        b.add (-4);
        print ("at end", b, b.max());
        myassert("testMax -- at end: max == -1", b.max() == -1);
        b.add (5);
        print ("at beginning", b, b.max());
        myassert("testMax -- at beginning: max == 5", b.max() == 5);
        b.add (3);
        b.add (2);
        b.add (4);
        print ("in the middle", b, b.max());
        myassert("testMax -- in the middle: max == 5", b.max() == 5);
    }
    private static void testMaxRecursive () {
        MyLinked b = new MyLinked ();
        print ("empty", b, b.maxRecursive());
        myassert("testMax -- empty: max == -Infinity", b.maxRecursive() == Double.NEGATIVE_INFINITY);
        b.add (-1);
        print ("singleton", b, b.maxRecursive());
        myassert("testMax -- singleton: max == -1", b.maxRecursive() == -1);
        b.add (-2);
        b.add (-3);
        b.add (-4);
        print ("at end", b, b.maxRecursive());
        myassert("testMax -- at end: max == -1", b.maxRecursive() == -1);
        b.add (5);
        print ("at beginning", b, b.maxRecursive());
        myassert("testMax -- at beginning: max == 5", b.maxRecursive() == 5);
        b.add (3);
        b.add (2);
        b.add (4);
        print ("in the middle", b, b.maxRecursive());
        myassert("testMax -- in the middle: max == 5", b.maxRecursive() == 5);
    }
    private static void testDelete () {
        // TODO: add assertions
        MyLinked b = new MyLinked ();
        b.add (1);
        print ("singleton", b);
        b.delete (0);
        print ("deleted", b);
        for (double i = 1; i < 13; i++) {
            b.add (i);
        }
        print ("bigger list", b);
        b.delete (0);
        print ("deleted at beginning", b);
        b.delete (10);
        print ("deleted at end", b);
        b.delete (4);
        print ("deleted in middle", b);
    }
    private static void testReverse () {
        // TODO: add assertions
        MyLinked b = new MyLinked ();
        b.reverse ();
        print ("reverse empty", b);
        b.add (1);
        print ("singleton", b);
        b.reverse ();
        print ("reverse singleton", b);
        b.add (2);
        print ("two", b);
        b.reverse ();
        print ("reverse two", b);
        b.reverse ();
        print ("reverse again", b);
        for (double i = 3; i < 7; i++) {
            b.add (i);
            b.add (i);
        }
        print ("bigger list", b);
        b.reverse ();
        print ("reversed", b);
    }
    private static void testRemove () {
        // TODO: add assertions
        MyLinked b = new MyLinked ();
        b.remove (4);
        print ("removed 4 from empty", b);
        b.add (1);
        b.remove (4);
        print ("removed 4 from singelton", b);
        b.remove (1);
        print ("removed 1 from singelton", b);
        for (double i = 1; i < 5; i++) {
            b.add (i);
            b.add (i);
        }
        for (double i = 1; i < 5; i++) {
            b.add (i);
            b.add (i);
            b.add (i);
            b.add (i);
            b.add (i);
        }
        print ("longer list", b);
        b.remove (9);
        print ("removed all 9s", b); // does nothing
        b.remove (3);
        print ("removed all 3s", b);
        b.remove (1);
        print ("removed all 1s", b);
        b.remove (4);
        print ("removed all 4s", b);
        b.remove (2);
        print ("removed all 2s", b); // should be empty
    }

    public static void main (String args[]) {
        // testMax ();
        // testMaxRecursive ();
        // testDelete ();
        // testReverse ();
        // testRemove ();
    }
}

































