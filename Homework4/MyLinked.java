package algs13;
// importing everything :(
import stdlib.*;

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

    // I changed this to static so that I could call it in main()
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

    /* I could also have used this in the methods below instead of checking N == 0 -- but on second thought
    seems like isEmpty is more for the client API?
    */
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
        // could also be if (N == 0) -- or call isEmpty :)
        if (first == null) return;
        if (k == 0) {
            first = first.next;
        } else {
            // get the delendum and the prior of the delendum
            Node prior = first;
            Node del = first.next;
            // if k represents the index of the element to be deleted, i represents k's prior
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

    public String toString() {
        String s = "";
        for (Node x = first; x != null; x = x.next) {
            s += String.format("%.2f ", x.item);
        }
        return s;
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

    // TESTS -- added assertions using myassert to confirm what is printed :)

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
        myassert("delete from singleton", b.toString().equals(""));
        for (double i = 1; i < 13; i++) {
            b.add (i);
        }
        print ("bigger list", b);
        b.delete (0);
        myassert("delete beginning", b.toString().equals("11.00 10.00 9.00 8.00 7.00 6.00 5.00 4.00 3.00 2.00 1.00 "));
        print ("deleted at beginning", b);
        b.delete (10);
        myassert("delete end", b.toString().equals("11.00 10.00 9.00 8.00 7.00 6.00 5.00 4.00 3.00 2.00 "));
        print ("deleted at end", b);
        b.delete (4);
        print ("deleted in middle", b);
        myassert("delete middle", b.toString().equals("11.00 10.00 9.00 8.00 6.00 5.00 4.00 3.00 2.00 "));
    }
    private static void testReverse () {
        MyLinked b = new MyLinked ();
        b.reverse ();
        print ("reverse empty", b);
        myassert("reverse empty", b.toString().equals(""));
        b.add (1);
        print ("singleton", b);
        b.reverse ();
        print ("reverse singleton", b);
        myassert("reverse singleton", b.toString().equals("1.00 "));
        b.add (2);
        print ("two", b);
        b.reverse ();
        print ("reverse two", b);
        myassert("reverse two", b.toString().equals("1.00 2.00 "));
        b.reverse ();
        print ("reverse again", b);
        myassert("reverse two", b.toString().equals("2.00 1.00 "));
        for (double i = 3; i < 7; i++) {
            b.add (i);
            b.add (i);
        }
        print ("bigger list", b);
        b.reverse ();
        print ("reversed", b);
        myassert("reverse big list", b.toString().equals("1.00 2.00 3.00 3.00 4.00 4.00 5.00 5.00 6.00 6.00 "));
    }
    private static void testRemove () {
        MyLinked b = new MyLinked ();
        b.remove (4);
        print ("removed 4 from empty", b);
        myassert("remove 4 from empty", b.toString().equals(""));
        b.add (1);
        b.remove (4);
        print ("removed 4 from singleton", b);
        myassert("remove 4 from singleton", b.toString().equals("1.00 "));
        b.remove (1);
        print ("removed 1 from singleton", b);
        myassert("remove 1 from singleton", b.toString().equals(""));
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
        myassert("remove 9", b.toString().equals("4.00 4.00 4.00 4.00 4.00 3.00 3.00 3.00 3.00 3.00 2.00 2.00 " +
                "2.00 2.00 2.00 1.00 1.00 1.00 1.00 1.00 4.00 4.00 3.00 3.00 2.00 2.00 1.00 1.00 "));
        b.remove (3);
        print ("removed all 3s", b);
        myassert("remove 3", b.toString().equals("4.00 4.00 4.00 4.00 4.00 2.00 2.00 " +
                "2.00 2.00 2.00 1.00 1.00 1.00 1.00 1.00 4.00 4.00 2.00 2.00 1.00 1.00 "));
        b.remove (1);
        print ("removed all 1s", b);
        myassert("remove 1", b.toString().equals("4.00 4.00 4.00 4.00 4.00 2.00 2.00 " +
                "2.00 2.00 2.00 4.00 4.00 2.00 2.00 "));
        b.remove (4);
        print ("removed all 4s", b);
        myassert("remove 4", b.toString().equals("2.00 2.00 2.00 2.00 2.00 2.00 2.00 "));
        b.remove (2);
        print ("removed all 2s", b); // should be empty
        myassert("remove 2", b.toString().equals(""));

    }

    public static void main (String args[]) {
        // Added some spacing.
        StdOut.println("");
        StdOut.println("Test max()");
        testMax ();
        StdOut.println("");

        StdOut.println("Test maxRecursive()");
        testMaxRecursive ();
        StdOut.println("");

        StdOut.println("Test delete()");
        testDelete ();
        StdOut.println("");

        StdOut.println("Test reverse()");
        testReverse ();
        StdOut.println("");

        StdOut.println("Test remove()");
        testRemove ();
    }
}

































