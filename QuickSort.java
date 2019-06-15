package algs23;

import stdlib.StdOut;

import java.util.Arrays;

// attempt at quicksort

public class QuickSort {

    public static void sort(int[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        // the left sub-array
        sort(a, lo, j-1);
        // the right sub-array
        sort(a, j+1, hi);
    }

    static int partition(int[] a, int lo, int hi) {
        int l = lo;
        // we have to do this -- if we tried "h--" below and set h = hi, won't work
        // this is necessary because we want to exchange the first element, not skip past it.
        int h = hi + 1;
        int pivot = a[lo];
        while (true) {
            // l=lo is <= itself, so start with the next
            while (a[++l] <= pivot) if (l == hi) break;
            while (a[--h] >= pivot) if (h == lo) break;
            // we should break when everything to the left is <= and everything to the right is >=
            // what happens when they cross?  If left and right have crossed cross...
            // that means that the first item > pivot on the left side is after the first item < pivot on the left
            // or another way: everything to the left of l is < pivot, everything to the right of r is >
            // when they cross, we don't want to exchange or we break the invariant
            if (l >= h) break;
            swap(a, l, h);
        }
        // pick a new pivot ????
        // currently a[h] should be < pivot, so when we swap, everything to the left of the pivot is less
        // and everything to right will be >=
        swap(a, lo, h);
        // return the new location of the pivot -- the array will be divided into two new "arrays" -- the subsection
        // to the left of the pivot and the subsection to the right.
        return h;
    }

    static void swap(int[] a, int x, int y) {
        int temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

    public static void main(String[] args) {
        int[] toSort = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        sort(toSort, 0, toSort.length - 1);
        StdOut.println(toSort);
    }
}
