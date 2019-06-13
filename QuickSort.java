package algs23;

import stdlib.StdOut;

import java.util.Arrays;

// attempt at quicksort

public class QuickSort {

    public Integer[] sort(Integer[] a) {
        int pivot = a.length / 2;
        partition(a, 0, pivot, a.length - 1);
        return a;
    }

    void partition(Integer[] a, int lo, int pivot, int hi) {
        if (lo >= hi) return;
        int pivotElement = a[pivot];
        while (lo < hi) {
            if (lo == pivot) lo++;
            if (hi == pivot) hi--;
            while (a[lo++] < pivotElement) if (lo == a.length - 1) break;
            while (a[hi--] > pivotElement) if (hi == 0) break;
            swap(a, lo, hi);
        }
        swap(a, pivot, lo);
        int quarter = (lo + hi ) / 4;
        partition(a, lo + quarter, lo + (lo + 2 * quarter) / 2, lo + 2 * quarter);
        partition(a, hi - 2 * quarter, hi - (hi - 2 * quarter) / 2, hi);
    }

    void swap(Integer[] a, int x, int y) {
        int temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

    public static void main(String[] args) {
        StdOut.println("Hello world!");
        QuickSort q = new QuickSort();
        StdOut.println(Arrays.deepToString(q.sort(new Integer[] {4, 3, 2, 1})));
    }
}
