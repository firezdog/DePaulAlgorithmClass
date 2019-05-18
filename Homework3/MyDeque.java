// Exercise 1.3.33
package algs13;

import stdlib.StdOut;

import java.util.NoSuchElementException;

/**
 * This is a skeleton file for your homework.
 * Edit the sections marked TODO.
 * You may also edit the function "main" to test your code.
 *
 * You should not need to add any new functions or fields.
 * 
 * You must not add static variables.
 * As always, you must not change the declaration of any method.
 * Do not change any of the methods I have provided, such as "toString" and "check".
 */
public class MyDeque_copy {

    Node first = null;
    Node last = null;
    int N = 0;

    static class Node {
        public Node() { }
        public int item;
        public Node next;
        public Node prev;
    }

    public boolean isEmpty () {
        return N == 0;
    }

    public int size () {
        return N;
    }

    private void addFirst(int item) {
        first = new Node();
        first.item = item;
        last = first;
    }

    public void pushLeft (int item) {
        if (N > 0) {
            Node newFirst = new Node();
            newFirst.item = item;
            newFirst.next = first;
            first.prev = newFirst;
            first = first.prev;
        }
        else addFirst(item);
        N++;
    }

    public void pushRight (int item) {
        if (N > 0) {
            Node newLast = new Node();
            newLast.item = item;
            last.next = newLast;
            newLast.prev = last;
            last = last.next;
        }
        else addFirst(item);
        N++;
    }

    public int popLeft () {
        int item;
        try {
            item = first.item;
        } catch (NullPointerException e) {
            throw new NoSuchElementException();
        }
        if (N > 1) {
            first = first.next;
            first.prev = null;
        }
        else if (N == 1) annihilate(this);
        N--;
        return item;
    }

    public int popRight () {
        int item;
        try {
            item = last.item;
        } catch (NullPointerException e) {
            throw new NoSuchElementException();
        }
        if (N > 1) {
            last = last.prev;
            last.next = null;
        }
        else if (N == 1) annihilate(this);
        N--;
        return item;
    }

    private void annihilate(MyDeque_copy d) {
        d.first = d.last = null;
    }

    // exercise 1.3.47 
    //
    // The concat method should take the Nodes from "that"
    // after execution, "that" should be empty.
    // See the tests in the main program.
    //
    // This method should create no new Nodes;
    // therefore it should not call pushLeft or pushRight.
    // Do not use a loop or a recursive definition.
    //
    public void concat (MyDeque_copy that) {
        if (that.first == null) return;
        if (this.first == null && that.first == null) return;
        if (this.first == null) {
            this.first = that.first;
            this.last = that.last;
            annihilate(that);
        }
        else {
            this.last.next = that.first;
            that.first.prev = this.last;
            // don't forget to move the last of this to that
            this.last = that.last;
            annihilate(that);
        }
        this.N += that.N;
        that.N = 0;
    }

    
    // Delete should delete and return the kth element from the left.
    // See the tests in the main program.
    //
    // This method should create no new Nodes;
    // therefore it should not call pushLeft or pushRight.
    // You can use a loop or a recursive definition here.
    //
    public int delete (int k) {
        int item;
        // this also covers the case where N is 0
        if (k < 0 || k > N - 1) throw new IndexOutOfBoundsException();
        // special case: delete only
        if (N == 1) {
            item = first.item;
            annihilate(this);
        }
        else if (k == 0) {
            item = first.item;
            first = first.next;
            first.prev = null;
        }
        else if (k == N - 1) {
            item = last.item;
            last = last.prev;
            last.next = null;
        }
        else {
            Node curr = first;
            for (int i = 0; i < k; i++) {
                curr = curr.next;
            }
            item = curr.item;
            Node prev = curr.prev;
            Node next = curr.next;
            prev.next = curr.next;
            next.prev = curr.prev;
            curr = null;
        }
        N--;
        return item;
    }

    public String toString () {
        if (first == null) return "[]";
        StringBuilder sb = new StringBuilder ("[");
        sb.append (first.item);
        for (Node cursor = first.next; cursor != null; cursor = cursor.next) {
            sb.append (" ");
            sb.append (cursor.item);
        }
        sb.append ("]");
        return sb.toString ();
    }
    private void checkInvariants () {
        if (N < 0) throw new Error ();
        if (N == 0) {
            if (first != null || last != null) throw new Error ();
        } else {
            if (first == null || last == null) throw new Error ();
        }
        if (N > 0) {
            Node prev = null;
            Node current = first;
            for (int i = 0; i < N; i++) {
                //StdOut.println ("checking " + current.item);
                if (current.prev != prev) throw new Error ();
                prev = current;
                current = current.next;
            }
            if (current != null) throw new Error ();
            Node next = null;
            current = last;
            for (int i = 0; i < N; i++) {
                //StdOut.println ("checking " + current.item);
                if (current.next != next) throw new Error ();
                next = current;
                current = current.prev;
            }
            if (current != null) throw new Error ();
        }
    }
    private void check (String expected) {
        checkInvariants ();
        if (expected != null) {
            if (!expected.equals (this.toString ())) throw new Error ("Expected \"" + expected + "\", got \"" + this + "\"");
        }
        StdOut.println (this);
    }
    private void check (int iExpected, int iActual, String expected) {
        if (iExpected != iActual) throw new Error ("Expected \"" + iExpected + "\", got \"" + iActual + "\"");
        check (expected);
    }

    public static void main (String args[]) {
        // Here are some tests to get you started.
        // You can edit this all you like.
        MyDeque_copy d1, d2, d3;
        Integer k;
        
        ////////////////////////////////////////////////////////////////////
        // push/pop tests
        ////////////////////////////////////////////////////////////////////
        d1 = new MyDeque_copy();
        d1.pushLeft (11);
        d1.check ("[11]");
        d1.pushLeft (12);
        d1.check ("[12 11]");
        d1.pushLeft (13);
        d1.check ("[13 12 11]");
        k = d1.popLeft ();
        d1.check (13, k, "[12 11]");
        k = d1.popLeft ();
        d1.check (12, k, "[11]");
        k = d1.popLeft ();
        d1.check (11, k, "[]");

        d1 = new MyDeque_copy();
        d1.pushRight (11);
        d1.check ("[11]");
        d1.pushRight (12);
        d1.check ("[11 12]");
        d1.pushRight (13);
        d1.check ("[11 12 13]");
        k = d1.popRight ();
        d1.check (13, k, "[11 12]");
        k = d1.popRight ();
        d1.check (12, k, "[11]");
        k = d1.popRight ();
        d1.check (11, k, "[]");
        
        ////////////////////////////////////////////////////////////////////
        //  test exceptions
        ////////////////////////////////////////////////////////////////////
        try {
            d1.popLeft ();
            throw new Error ("Expected exception");
        } catch (NoSuchElementException e) {}
        try {
            d1.popRight ();
            throw new Error ("Expected exception");
        } catch (NoSuchElementException e) {}
        
        ////////////////////////////////////////////////////////////////////
        // concat tests (and more push/pop tests)
        ////////////////////////////////////////////////////////////////////
        d1 = new MyDeque_copy();
        d1.concat (new MyDeque_copy());
        d1.check ("[]");
        d1.pushLeft (11);
        d1.concat (new MyDeque_copy());
        d1.check ("[11]");

        d1 = new MyDeque_copy();
        d2 = new MyDeque_copy();
        d2.pushLeft (11);
        d1.concat (d2);
        d1.check ("[11]");

        d1 = new MyDeque_copy();
        for (int i = 10; i < 15; i++) { d1.pushLeft (i); d1.checkInvariants (); }
        for (int i = 20; i < 25; i++) { d1.pushRight (i); d1.checkInvariants (); }
        d1.check ("[14 13 12 11 10 20 21 22 23 24]");
        d2 = new MyDeque_copy();
        d1.concat (d2);
        d1.check ("[14 13 12 11 10 20 21 22 23 24]");
        d2.check ("[]");

        for (int i = 30; i < 35; i++) { d2.pushLeft (i); d2.checkInvariants (); }
        for (int i = 40; i < 45; i++) { d2.pushRight (i); d2.checkInvariants (); }
        d2.check ("[34 33 32 31 30 40 41 42 43 44]");

        d3 = new MyDeque_copy();
        d2.concat (d3);
        d2.check ("[34 33 32 31 30 40 41 42 43 44]");
        d3.check ("[]");

        d1.concat (d2);
        d1.check ("[14 13 12 11 10 20 21 22 23 24 34 33 32 31 30 40 41 42 43 44]");
        d2.check ("[]");
        for (int i = 0; i < 20; i++) { d1.popLeft (); d1.checkInvariants (); }
        
        ////////////////////////////////////////////////////////////////////
        // delete tests
        ////////////////////////////////////////////////////////////////////
        d1 = new MyDeque_copy();
        d1.pushLeft (11);
        d1.delete (0);
        d1.check ("[]");
        for (int i = 10; i < 20; i++) { d1.pushRight (i); d1.checkInvariants (); }
        d1.delete (0);
        d1.check ("[11 12 13 14 15 16 17 18 19]");
        d1.delete (8);
        d1.check ("[11 12 13 14 15 16 17 18]");
        d1.delete (4);
        d1.check ("[11 12 13 14 16 17 18]");
        d1.delete (0);
        d1.check ("[12 13 14 16 17 18]");
        d1.delete (0);
        d1.check ("[13 14 16 17 18]");
        d1.delete (0);
        d1.check ("[14 16 17 18]");
        d1.delete (0);
        d1.check ("[16 17 18]");
        d1.delete (0);
        d1.check ("[17 18]");
        d1.delete (0);
        d1.check ("[18]");
        d1.delete (0);
    }
}
