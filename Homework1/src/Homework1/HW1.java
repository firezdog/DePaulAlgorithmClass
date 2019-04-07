package Homework1;

public class HW1 {
    public static double getMinimum(double[] list) {
        double minimum = list[0];
        for (int i = 0; i < list.length; i++) {
            if (list[i] < minimum) { minimum = list[i]; }
        }
        return minimum;
    }

    public static int getMinimumsIndex(double[] list) {
        int minimumIndex = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] < list[minimumIndex]) { minimumIndex = i; }
        }
        return minimumIndex;
    }

    public static int getDistanceBetweenMinimumsAndMaximumsPosition(double[] list) {
        int minimumsIndex = 0;
        int maximumsIndex = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] < list[minimumsIndex]) { minimumsIndex = i; }
            if (list[i] > list[maximumsIndex]) { maximumsIndex = i; }
        }
        return maximumsIndex - minimumsIndex > 0 ?
                maximumsIndex - minimumsIndex :
                minimumsIndex - maximumsIndex;
    }

    // assume list is sorted
    public static int countUniqueValues(double[] list) {
        if (list.length == 0) { return 0; }
        int uniqueCount = 1;
        for (int i = 1; i < list.length; i++) {
            if (list[i] != list[i-1]) { uniqueCount++; }
        }
        return uniqueCount;
    }

    // assume list is sorted.
    public static double[] getSet(double[] list) {
        if (list.length == 0) { return new double[] {}; }
        int setSize = countUniqueValues(list);
        double[] set = new double[setSize];
        set[0] = list[0];
        int setIndex = 1;
        int listIndex = 1;
        while (setIndex < set.length) {
            if (list[listIndex] != list[listIndex - 1]) {
                set[setIndex] = list[listIndex];
                setIndex++;
            }
            listIndex++;
        }
        return set;
    }

    public static boolean equals(double[] a, double[] b) {
        if (a.length != b.length) { return false; }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) { return false; }
        }
        return true;
    }

    private static void testGetSet() {
        double[] emptyList = new double[] {};
        double[] unity = new double[] {1};
        double[] legion = new double[] {1, 1, 1, 1, 1};
        double[] oddOneAtEnd = new double[] {1, 1, 1, 1, 1, 2};
        double[] oddOneInBeginning = new double[] {1, 2, 2, 2, 2};
        double[] oddOneInMiddle = new double[] {1, 1, 1, 2, 3, 3, 3, 3};
        double[] orderedList = new double[] {1, 2, 3, 4, 5, 6, 7};
        double[] repetitiveOrderedList = new double[] {1, 1, 2, 2, 2, 3, 3, 4, 4, 4, 4, 5, 6, 6, 6, 7, 7};
        assert equals(getSet(emptyList), new double[] {});
        assert equals(getSet(unity), new double[] {1});
        assert equals(getSet(legion), new double[] {1});
        assert equals(getSet(oddOneAtEnd), new double[] {1, 2});
        assert equals(getSet(oddOneInBeginning), new double[] {1, 2});
        assert equals(getSet(oddOneInMiddle), new double[] {1, 2, 3});
        assert equals(getSet(orderedList), orderedList);
        assert equals(getSet(repetitiveOrderedList), orderedList);
    }

    private static void testUniqueCount() {
        double[] emptyList = new double[] {};
        double[] unity = new double[] {1};
        double[] legion = new double[] {1, 1, 1, 1, 1};
        double[] oddOneAtEnd = new double[] {1, 1, 1, 1, 1, 2};
        double[] oddOneInBeginning = new double[] {1, 2, 2, 2, 2};
        double[] oddOneInMiddle = new double[] {1, 1, 1, 2, 3, 3, 3, 3};
        double[] orderedList = new double[] {1, 2, 3, 4, 5, 6, 7};
        assert countUniqueValues(emptyList) == 0;
        assert countUniqueValues(unity) == 1;
        assert countUniqueValues(legion) == 1;
        assert countUniqueValues(oddOneAtEnd) == 2;
        assert countUniqueValues(oddOneInBeginning) == 2;
        assert countUniqueValues(oddOneInMiddle) == 3;
        assert countUniqueValues(orderedList) == 7;
    }

    private static void testGetMinimum() {
        assert getMinimum(new double[] {-7}) == -7;
        assert getMinimum(new double[] {1, -4, -7, 7, 8, 11}) == -7;
        assert getMinimum(new double[] {-13, -4, -7, 7, 8, 11}) == -13;
        assert getMinimum(new double[] {1, -4, -7, 7, 8, 11, -9}) == -9;
    }

    private static void testGetMinimumsIndex() {
        assert 0 == getMinimumsIndex(new double[] { -7 });
        assert 2 == getMinimumsIndex(new double[] { 1, -4, -7, 7, 8, 11 });
        assert 0 == getMinimumsIndex(new double[] {-13, -4, -7, 7, 8, 11});
        assert 6 == getMinimumsIndex(new double[] {1, -4, -7, 7, 8, 11, -9});
    }

    private static void testGetDistanceBetweenMinimumsAndMaximumsPosition() {
        assert 0 == getDistanceBetweenMinimumsAndMaximumsPosition(new double[] {-7});
        assert 3 == getDistanceBetweenMinimumsAndMaximumsPosition(new double[] {1, -4, -7, 7, 8, 11});
        assert 5 == getDistanceBetweenMinimumsAndMaximumsPosition(new double[] {-13, -4, -7, 7, 8, 11});
        assert 1 == getDistanceBetweenMinimumsAndMaximumsPosition(new double[] {1, -4, -7, 7, 8, 11, -9});
    }

    public static void main(String[] args) {
        testGetMinimum();
        testGetMinimumsIndex();
        testGetDistanceBetweenMinimumsAndMaximumsPosition();
        testUniqueCount();
        testGetSet();
    }
}