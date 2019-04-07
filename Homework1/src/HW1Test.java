import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Homework1.HW1;

class HW1Test {

    // fixtures for min, max, and difference functions
    private final double[] singleton = new double[] {-7};
    private final double[] fixtureOne = new double[] {1, -4, -7, 7, 8, 11};
    private final double[] fixtureTwo = new double[] {-13, -4, -7, 7, 8, 11};
    private final double[] fixtureThree = new double[] {1, -4, -7, 7, 8, 11, -9};

    // fixtures for set functions
    private final double[] emptyList = new double[] {};
    private final double[] unity = new double[] {1};
    private final double[] legion = new double[] {1, 1, 1, 1, 1};
    private final double[] oddOneAtEnd = new double[] {1, 1, 1, 1, 1, 2};
    private final double[] oddOneInBeginning = new double[] {1, 2, 2, 2, 2};
    private final double[] oddOneInMiddle = new double[] {1, 1, 1, 2, 3, 3, 3, 3};
    private final double[] orderedList = new double[] {1, 2, 3, 4, 5, 6, 7};
    private final double[] repetitiveOrderedList = new double[] {1, 1, 2, 2, 2, 3, 3, 4, 4, 4, 4, 5, 6, 6, 6, 7};

    @Test
    void getMinimum() {
        assertEquals(HW1.getMinimum(singleton), -7);
        assertEquals(HW1.getMinimum(fixtureOne), -7);
        assertEquals(HW1.getMinimum(fixtureTwo), -13);
        assertEquals(HW1.getMinimum(fixtureThree), -9);
    }

    @Test
    void getMinimumsIndex() {
        assertEquals(HW1.getMinimumsIndex(singleton),0);
        assertEquals(HW1.getMinimumsIndex(fixtureOne), 2);
        assertEquals(HW1.getMinimumsIndex(fixtureTwo), 0);
        assertEquals(HW1.getMinimumsIndex(fixtureThree),6);
    }

    @Test
    void getDistanceBetweenMinimumsAndMaximumsPosition() {
        assertEquals(HW1.getDistanceBetweenMinimumsAndMaximumsPosition(singleton),0);
        assertEquals(HW1.getDistanceBetweenMinimumsAndMaximumsPosition(fixtureOne),3);
        assertEquals(HW1.getDistanceBetweenMinimumsAndMaximumsPosition(fixtureTwo),5);
        assertEquals(HW1.getDistanceBetweenMinimumsAndMaximumsPosition(fixtureThree),1);
    }

    @Test
    void countUniqueValues() {
        assertEquals(HW1.countUniqueValues(emptyList),0);
        assertEquals(HW1.countUniqueValues(unity),1);
        assertEquals(HW1.countUniqueValues(legion),1);
        assertEquals(HW1.countUniqueValues(oddOneAtEnd),2);
        assertEquals(HW1.countUniqueValues(oddOneInBeginning),2);
        assertEquals(HW1.countUniqueValues(oddOneInMiddle),3);
        assertEquals(HW1.countUniqueValues(orderedList),7);
    }

    @Test
    void getSet() {
        assertArrayEquals(HW1.getSet(emptyList), new double[] {});
        assertArrayEquals(HW1.getSet(unity), new double[] {1});
        assertArrayEquals(HW1.getSet(legion), new double[] {1});
        assertArrayEquals(HW1.getSet(oddOneAtEnd), new double[] {1,2});
        assertArrayEquals(HW1.getSet(oddOneInBeginning), new double[] {1,2});
        assertArrayEquals(HW1.getSet(oddOneInMiddle), new double[] {1,2,3});
        assertArrayEquals(HW1.getSet(orderedList), orderedList);
        assertArrayEquals(HW1.getSet(repetitiveOrderedList), orderedList);
    }

    // used to mimic Arrays.equals for assertions
    @Test
    void equals() {
        assertTrue(HW1.equals(new double[] {}, new double[] {}));
        assertTrue(HW1.equals(new double[] {1, 2, 3}, new double[] {1, 2, 3}));
        assertFalse(HW1.equals(new double[] {1, 2, 3, 4}, new double[] {1, 2, 3}));
        assertFalse(HW1.equals(new double[] {1, 2, 3, 5}, new double[] {1, 2, 3, 4}));
    }
}