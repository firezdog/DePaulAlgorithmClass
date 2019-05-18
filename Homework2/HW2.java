// isn't this an unused import?
import algs11.*;
import stdlib.StdOut;
// added to prevent overflow
import java.math.BigInteger;
import java.util.Arrays;

class HW2 {

    /**
     * As a model, here is a minValue function, both iteratively and recursively
     */
    /** iterative version
    public static double minValueI (double[] list) {
        double minSoFar = list[0];
        int i = 1;
        while (i < list.length) {
            if (list[i] < minSoFar) minSoFar = list[i];
            i++;
        }
        return minSoFar;
    } */

    /** recursive version
    public static double minValue (double[] list) {
        return minValueHelper (list, 1, list[0]);
    }
     static double minValueHelper (double[] list, int i, double minSoFar) {
        if (i < list.length) {
            if (list[i] < minSoFar) {
                return minValueHelper (list, i + 1, list[i]);
            } else {
                return minValueHelper (list, i + 1, minSoFar);
            }
        } else {
            return minSoFar;
        }
    }*/

    /**
     * PROBLEM 1: Translate the following sum function from iterative to
     * recursive
    public static double sum (double[] a) {
        double result = 0.0;
        int i = 0;
        while (i < a.length) {
            result = result + a[i];
            i = i + 1;
        }
        return result;
    }*/

    public static double sum (double[] a) {
    	return sumHelper(a, 0, 0);
    }
    public static double sumHelper (double[] arr, double tally, int i) {
        if (i < arr.length) {
            return sumHelper(arr, tally + arr[i], i + 1);
        }
        return tally;
    }

    /** PROBLEM 2: Do the same translation for this in-place reverse function
    public static void reverseI (double[] a) {
        int N = a.length;
        int i = 0;
        while (i < N / 2) {
            double lo = a[i];
            double hi = a[N - 1 - i];
            a[i] = hi;
            a[N - i - 1] = lo;
            i = i + 1;
        }
    }*/

    public static void reverse (double[] a) {
    	reverseHelper(a, a.length, 0);
    }
    public static void reverseHelper (double[] a, int N, int i) {
    	if (i < N / 2) {
            double swap = a[N - 1 - i];
            a[N - 1 - i] = a[i];
            a[i] = swap;
            reverseHelper(a, N, i + 1);
        }
    }



    /**
     * PROBLEM 3: Run runTerrible for one hour. You can stop the program using
     * the red "stop" square in eclipse. Fill in the OUTPUT line below with the
     * numbers you saw LAST --- edit the line, replacing the two ... with what
     * you saw:
     *
     * (edited) OUTPUT: terribleFibonacci(56)=225851433717
     *
     * Comment: the code uses "long" variables, which are like "int", but
     * bigger. It's because fibonacci numbers get really big really fast.
     *
     * Comment on comment: in fact, too big for long?
     */
    public static void runTerrible () {
        for (int N = 0; N < 100; N++)
            StdOut.printf ("terribleFibonacci(%2d)=%d\n", N, terribleFibonacci (N));
    }
    public static long terribleFibonacci (int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return terribleFibonacci (n - 1) + terribleFibonacci (n - 2);
    }

    /**
     * PROBLEM 4: The implementation of terribleFibonacci is TERRIBLE! Write a
     * more efficient version of fibonacci which computes each fibonacci number
     * between 0 and n at most once.
     *
     * Comment: You will want to use a local variable of type "long" rather than
     * type "int", for the reasons discussed above.
     */

    /**
        My understanding is that this Fibonacci calculator is 0-indexed, i.e.
        my expected output is f(0) = 1, f(1) = 1, f(2) = 2, f(3) = 3, f(4) = 5, etc.
    */
    public static void runFibonacci () {
        for (int N = 0; N < 100; N++)
            StdOut.printf ("fibonacci(%2d)=%d\n", N, fibonacci (N));
    }

    // interesting problem -- this doesn't cache the result of previous function calls
    public static BigInteger fibonacci (int n) {
        if (0 <= n && n <= 1) return BigInteger.ONE;
        // hard-coded but doesn't need to be (allow weird fib variants)
        int storeLength = 2;
        int lastIndex = storeLength - 1;
        BigInteger[] store = new BigInteger[storeLength];
        // initialize store
        for (int i = 0; i < storeLength; i++) {
            store[i] = BigInteger.ONE;
        }
        // get next number and store it
        for (int i = 1; i < n; i++) {
            BigInteger sum = BigInteger.ZERO;
            for (int j = 0; j <storeLength; j++) {
                sum = sum.add(store[j]);
                // move everything but the last to the left
                if (j < lastIndex) { store[j] = store[j + 1]; }
            }
            store[lastIndex] = sum;
        }
        return store[lastIndex];
    }

    public static void main (String[] args) {
        /* added assertions */
        // recursive sum
        double total = sum(new double[] {1,2,3,4,5});
        StdOut.printf("total (15): %f\n", total);
        assert total == 15;

        // recursive reversing (reversion?)
    	double[] rev = new double[] {1,2,3,3,2,4,5};
    	reverse(rev);
    	System.out.println(Arrays.toString(rev));
    	assert Arrays.equals(rev, new double[] {5,4,2,3,3,2,1});

    	// runTerrible
        // runTerrible(); // (DON'T)

    	// fibonacci
        runFibonacci();
        assert fibonacci(0).equals(BigInteger.valueOf(1));
        assert fibonacci(1).equals(BigInteger.valueOf(1));
        assert fibonacci(2).equals(BigInteger.valueOf(2));
        assert fibonacci(99).equals(new BigInteger("354224848179261915075"));

    }

}
