package UnionFinder;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.*;

/**
 * Tests that the BasicUnionFinder class works properly.
 */
public class BasicUnionFinderTest
{
    // Attributes

    /**
     * The union finder.
     */
    private BasicUnionFinder unionFinder;

    // Setup

    /**
     * Initializes the union finder with a capacity of 10 boxes labeled from 0 to 9.
     */
    @Before
    public void setup()
    { unionFinder = new BasicUnionFinder(10); }

    // Tests

    /**
     * Tests that the union finder is correctly initialized.
     */
    @Test
    public void initializationTest1()
    {
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The maximum size should be 10.", unionFinder.parents().length, 10);
        assertEquals("The number of boxes should be 10.", unionFinder.totalRoots(), 10);
        // At the beginning, everything starts as -1.
        int[] par = unionFinder.parents();
        for(int act : par)
            assertEquals("The value should be -1.", act, -1);
    }

    /**
     * Tests that the union finder is correctly initialized.
     */
    @Test
    public void initializationTest2()
    {
        BasicUnionFinder newUnionFinder = new BasicUnionFinder(unionFinder);
        assertNotNull("The union finder shouldn't be null.", newUnionFinder);
        assertEquals("The maximum size should be 10.", newUnionFinder.parents().length, 10);
        assertEquals("The number of boxes should be 10.", newUnionFinder.totalRoots(), 10);
        // At the beginning, everything starts as -1.
        int[] par = newUnionFinder.parents();
        for(int act : par)
            assertEquals("The value should be -1.", act, -1);
    }

    /**
     * Tests that a union finder given stored information is correctly initialized.
     */
    @Test
    public void initializationTest3()
    {
        unionFinder.merge(1, 2);
        unionFinder.merge(8, 9);
        BasicUnionFinder newUnionFinder = new BasicUnionFinder(unionFinder);
        // Since boxes 1 and 2, and 8 and 9 are merged, there should be 8 boxes but par of size 10.
        assertNotNull("The union finder shouldn't be null.", newUnionFinder);
        assertEquals("The maximum size should be 10.", newUnionFinder.parents().length, 10);
        assertEquals("The number of boxes should be 8.", newUnionFinder.totalRoots(), 8);
        // The roots should  be stored.
        assertEquals("The root should be itself.", newUnionFinder.root(1), 1);
        assertEquals("The root should be 1.", newUnionFinder.root(2), 1);
        assertEquals("The root should be itself.", newUnionFinder.root(8), 8);
        assertEquals("The root should be 8.", newUnionFinder.root(9), 8);
        assertEquals("The size should be 2.", newUnionFinder.size(2), 2);
        assertEquals("The size should be 2.", newUnionFinder.size(8), 2);
    }

    /**
     * Tests that the union finder finds and assigns roots properly.
     */
    @Test
    public void testRoot1()
    {
        for(int i = 0; i < unionFinder.parents().length; ++i)
            assertEquals("The root should be itself.", unionFinder.root(i), i);
        // Boxes 8 and 9 are merged. Everything should stay the same except for 9:
        unionFinder.merge(8, 9);
        for(int i = 0; i < unionFinder.parents().length-1; ++i)
            assertEquals("The root should be itself.", unionFinder.root(i), i);
        assertEquals("The root should be 8.", unionFinder.root(9), 8);
        // Boxes 6 and 5 are merged. Nothing should change other than 5:
        unionFinder.merge(6, 5);
        int[] keys = {0, 1, 2, 3, 4, 6, 7, 8};
        for(int i : keys)
            assertEquals("The root should be itself.", unionFinder.root(i), i);
        assertEquals("The root should be 8.", unionFinder.root(9), 8);
        assertEquals("The root should be 6.", unionFinder.root(5), 6);
    }

    /**
     * Tests that the root of a non-existent box ends up with a ArrayIndexOutOfBoundsException.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testRoot2()
    {
        unionFinder.root(100);
    }

    /**
     * Tests that the union finder checks and finds roots properly.
     */
    @Test
    public void testRootChecked()
    {
        for(int i = 0; i < unionFinder.parents().length; ++i)
            assertEquals("The root should be itself.", (int) unionFinder.rootChecked(i), i);
        // Boxes 8 and 9 are merged. Everything should stay the same except for 9:
        unionFinder.merge(8, 9);
        for(int i = 0; i < unionFinder.parents().length-1; ++i)
            assertEquals("The root should be itself.", (int) unionFinder.rootChecked(i), i);
        assertEquals("The root should be 8.", (int) unionFinder.rootChecked(9), 8);
        // Boxes 6 and 5 are merged. Nothing should change other than 5:
        unionFinder.merge(6, 5);
        int[] keys = {0, 1, 2, 3, 4, 6, 7, 8};
        for(int i : keys)
            assertEquals("The root should be itself.", (int) unionFinder.rootChecked(i), i);
        assertEquals("The root should be 8.", (int) unionFinder.rootChecked(9), 8);
        assertEquals("The root should be 6.", (int) unionFinder.rootChecked(5), 6);
        // Now, a root of a non-existent box is retrieved:
        assertNull("The root should be null.", unionFinder.rootChecked(100));
        assertNull("The root should be null.", unionFinder.rootChecked(10));
        assertNull("The root should be null.", unionFinder.rootChecked(-20));
    }

    /**
     * Tests that the merge function works properly.
     */
    @Test
    public void mergeTest1()
    {
        // Merges boxes 0 and 1. Since they have the same size, box 1 is merged into box 0.
        unionFinder.merge(0, 1);
        assertEquals("The value should be -2.", unionFinder.parents()[0], -2);
        assertEquals("The value should be 0", unionFinder.parents()[1], 0);
        // Checks that the root of 0 is 0, and the root of 1 is 0 (since it merged):
        assertEquals("The root should be 0.", unionFinder.root(0), 0);
        assertEquals("The root should be 0.", unionFinder.root(1), 0);
        // Checks that the root of the remaining numbers are unchanged.
        for(int i = 2; i < unionFinder.parents().length; ++i)
            assertEquals("The root should be " + i + ".", unionFinder.root(i), i);
        // Merges boxes 0 and 9. Since 9 is smaller, it's merged into box 0.
        unionFinder.merge(0, 9);
        assertEquals("The value should be -3.", unionFinder.parents()[0], -3);
        assertEquals("The value should be 0.", unionFinder.parents()[9], 0);
        //Checks that the root of 0 is 0, and the root of 9 is 0 (since it merged):
        assertEquals("The root should be 0.", unionFinder.root(0), 0);
        assertEquals("The root should be 0.", unionFinder.root(9), 0);
        //Checks that the root of the remaining numbers are unchanged.
        assertEquals("The root should be 0.", unionFinder.root(1), 0);
        for(int i = 2; i < unionFinder.parents().length-1; ++i)
            assertEquals("The root should be " + i + ".", unionFinder.root(i), i);
    }

    /**
     * Tests that the merge of two non-existent boxes ends up with an IndexOutOfBoundsException.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void mergeTest2()
    {
        unionFinder.merge(10, 11);
    }

    /**
     * Tests that the union finder checks and merges boxes properly.
     */
    @Test
    public void mergeCheckedTest()
    {
        // Merges boxes 0 and 1. Since they have the same size, box 1 is merged into box 0.
        unionFinder.mergeChecked(0, 1);
        assertEquals("The value should be -2.", unionFinder.parents()[0], -2);
        assertEquals("The value should be 0", unionFinder.parents()[1], 0);
        // Checks that the root of 0 is 0, and the root of 1 is 0 (since it merged):
        assertEquals("The root should be 0.", unionFinder.root(0), 0);
        assertEquals("The root should be 0.", unionFinder.root(1), 0);
        // Checks that the root of the remaining numbers are unchanged.
        for(int i = 2; i < unionFinder.parents().length; ++i)
            assertEquals("The root should be " + i + ".", unionFinder.root(i), i);
        // Merges boxes 0 and 9. Since 9 is smaller, it's merged into box 0.
        unionFinder.mergeChecked(0, 9);
        assertEquals("The value should be -3.", unionFinder.parents()[0], -3);
        assertEquals("The value should be 0.", unionFinder.parents()[9], 0);
        //Checks that the root of 0 is 0, and the root of 9 is 0 (since it merged):
        assertEquals("The root should be 0.", unionFinder.root(0), 0);
        assertEquals("The root should be 0.", unionFinder.root(9), 0);
        //Checks that the root of the remaining numbers are unchanged.
        assertEquals("The root should be 0.", unionFinder.root(1), 0);
        for(int i = 2; i < unionFinder.parents().length-1; ++i)
            assertEquals("The root should be " + i + ".", unionFinder.root(i), i);
        // Merging non-existent boxes shouldn't fail or change anything:
        unionFinder.mergeChecked(10, 11);
        unionFinder.mergeChecked(-10, 200);
        unionFinder.mergeChecked(10, -200);
        unionFinder.mergeChecked(-10, -20);
        assertEquals("The value should be -3.", unionFinder.parents()[0], -3);
        assertEquals("The value should be 0.", unionFinder.parents()[9], 0);
        //Checks that the root of 0 is 0, and the root of 9 is 0:
        assertEquals("The root should be 0.", unionFinder.root(0), 0);
        assertEquals("The root should be 0.", unionFinder.root(9), 0);
        //Checks that the root of the remaining numbers are unchanged.
        assertEquals("The root should be 0.", unionFinder.root(1), 0);
        for(int i = 2; i < unionFinder.parents().length-1; ++i)
            assertEquals("The root should be " + i + ".", unionFinder.root(i), i);
    }

    /**
     * Tests that the union finder keeps track of box sizes properly.
     */
    @Test
    public void sizeTest1()
    {
        for(int i = 0; i < unionFinder.parents().length; ++i)
            assertEquals("The value should be 1.", unionFinder.size(i), 1);
        int[] keys = {1, 4, 6, 8, 9};
        unionFinder.mergeChecked(2, 5);
        for(int key : keys)
            assertEquals("The value should be 1.", (int) unionFinder.sizeChecked(key), 1);
        assertEquals("The size should be 2.", unionFinder.size(2), 2);
        assertEquals("The size should be 1.", unionFinder.size(3), 1);
        assertEquals("The size should be 2.", unionFinder.size(5), 2);
        assertEquals("The size should be 1.", unionFinder.size(7), 1);
        unionFinder.mergeChecked(2, 7);
        for(int key : keys)
            assertEquals("The value should be 1.", (int) unionFinder.sizeChecked(key), 1);
        assertEquals("The size should be 3.", unionFinder.size(2), 3);
        assertEquals("The size should be 1.", unionFinder.size(3), 1);
        assertEquals("The size should be 3.", unionFinder.size(5), 3);
        assertEquals("The size should be 3.", unionFinder.size(7), 3);
        unionFinder.mergeChecked(3, 7);
        for(int key : keys)
            assertEquals("The value should be 1.", (int) unionFinder.sizeChecked(key), 1);
        assertEquals("The size should be 4.", unionFinder.size(2), 4);
        assertEquals("The size should be 4.", unionFinder.size(3), 4);
        assertEquals("The size should be 4.", unionFinder.size(5), 4);
        assertEquals("The size should be 4.", unionFinder.size(7), 4);
    }

    /**
     * Tests that the size of a non-existent box ends up with an IndexOutOfBoundsException.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void sizeTest2()
    {
        unionFinder.size(100);
    }

    /**
     * Tests that the union finder checks and returns sizes properly.
     */
    @Test
    public void sizeCheckedTest()
    {
        for(int i = 0; i < unionFinder.parents().length; ++i)
            assertEquals("The value should be 1.", (int) unionFinder.sizeChecked(i), 1);
        unionFinder.mergeChecked(2, 5);
        assertEquals("The size should be 2.", (int) unionFinder.sizeChecked(2), 2);
        assertEquals("The size should be 1.", (int) unionFinder.sizeChecked(3), 1);
        assertEquals("The size should be 2.", (int) unionFinder.sizeChecked(5), 2);
        assertEquals("The size should be 1.", (int) unionFinder.sizeChecked(7), 1);
        unionFinder.mergeChecked(2, 7);
        assertEquals("The size should be 3.", (int) unionFinder.sizeChecked(2), 3);
        assertEquals("The size should be 1.", (int) unionFinder.sizeChecked(3), 1);
        assertEquals("The size should be 3.", (int) unionFinder.sizeChecked(5), 3);
        assertEquals("The size should be 3.", (int) unionFinder.sizeChecked(7), 3);
        unionFinder.mergeChecked(3, 7);
        assertEquals("The size should be 4.", (int) unionFinder.sizeChecked(2), 4);
        assertEquals("The size should be 4.", (int) unionFinder.sizeChecked(3), 4);
        assertEquals("The size should be 4.", (int) unionFinder.sizeChecked(5), 4);
        assertEquals("The size should be 4.", (int) unionFinder.sizeChecked(7), 4);
        // Sizes of non-existent boxes should  be null:
        assertNull("The size should be null.", unionFinder.sizeChecked(100));
        assertNull("The size should be null.", unionFinder.sizeChecked(200));
        assertNull("The size should be null.", unionFinder.sizeChecked(-10));
        int[] keys = {1, 4, 6, 8, 9};
        for(int key : keys)
            assertEquals("The value should be 1.", (int) unionFinder.sizeChecked(key), 1);
        assertEquals("The size should be 4.", (int) unionFinder.sizeChecked(2), 4);
        assertEquals("The size should be 4.", (int) unionFinder.sizeChecked(3), 4);
        assertEquals("The size should be 4.", (int) unionFinder.sizeChecked(5), 4);
        assertEquals("The size should be 4.", (int) unionFinder.sizeChecked(7), 4);
    }

    /**
     * Tests that the union finder works properly with a simple 10 node case.
     */
    @Test
    public void caseTest1()
    {
        for(int i = 0; i < unionFinder.parents().length; ++i)
            assertEquals("The size of each root-node should be 1.", unionFinder.size(i), 1);
        assertEquals("The number of connected components should be 10.", unionFinder.totalRoots(), 10);
        // Lets assume we have 10 nodes on a graph. The first edges are 0-2, 2-3 and 0-1. First connected component.
        unionFinder.merge(0, 2); unionFinder.merge(2, 3); unionFinder.merge(0, 1);
        assertEquals("The number of connected components should be 7.", unionFinder.totalRoots(), 7);
        assertEquals("The size of the first connected component should be 4.", unionFinder.size(0), 4);
        // The root of all these nodes 0, 1, 2 and 3 should be equal (since they're joined).
        boolean test = unionFinder.root(0) == unionFinder.root(1) &&
                unionFinder.root(1) == unionFinder.root(2) && unionFinder.root(2) == unionFinder.root(3);
        assertTrue("All nodes should have the same root.", test);
        // Now the second connected component has the edges: 4-5, 6-8, 5-7, 7-9, 8-9, 4-6.
        unionFinder.merge(4, 5); unionFinder.merge(6, 8); unionFinder.merge(5, 7);
        unionFinder.merge(7, 9); unionFinder.merge(8, 9); unionFinder.merge(4, 6);
        assertEquals("The number of connected components should be 2.", unionFinder.totalRoots(), 2);
        assertEquals("The size of the first connected component should be 4.", unionFinder.size(0), 4);
        assertEquals("The size of the first connected component should be 6.", unionFinder.size(6), 6);
        // The root of all these nodes 4, 5, 6, 7, 8 and 9 should be equal.
        test = unionFinder.root(4) == unionFinder.root(5) && unionFinder.root(5) == unionFinder.root(6) &&
                unionFinder.root(6) == unionFinder.root(7) && unionFinder.root(7) == unionFinder.root(8) &&
                unionFinder.root(8) == unionFinder.root(9);
        assertTrue("All nodes should have the same root.", test);
        // Now that there's only two connected components, we join them through the edge 3-4.
        unionFinder.merge(3, 4);
        assertEquals("The number of connected components should be 1.", unionFinder.totalRoots(), 1);
        assertEquals("The size of the only connected component should be 10.", unionFinder.size(0), 10);
        //Now the root of all the nodes should be equal.
        test = unionFinder.root(1) == unionFinder.root(2) && unionFinder.root(2) == unionFinder.root(3) &&
                unionFinder.root(3) == unionFinder.root(4) && unionFinder.root(4) == unionFinder.root(5) &&
                unionFinder.root(5) == unionFinder.root(6) && unionFinder.root(6) == unionFinder.root(7) &&
                unionFinder.root(7) == unionFinder.root(8) && unionFinder.root(8) == unionFinder.root(9);
        assertTrue("All nodes should have the same root.", test);
    }
}