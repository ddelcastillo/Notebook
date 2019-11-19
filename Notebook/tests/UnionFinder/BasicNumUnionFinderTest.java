package UnionFinder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests that the BasicNumUnionFinder class works properly.
 */
public class BasicNumUnionFinderTest
{
    // Attributes

    /**
     * The union finder.
     */
    private BasicNumUnionFinder unionFinder;

    // Setups

    /**
     * Initializes the union finder with a capacity of 10 boxes labeled from 0 to 9.
     */
    @Before
    public void setup()
    { unionFinder = new BasicNumUnionFinder(10); }

    // Tests

    /**
     * Tests that the union finder is correctly initialized.
     */
    @Test
    public void initializationTest1()
    {
        // The union shouldn't be null and should have 10 parents (10 boxes).
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The number of parents should be 10.", 10, unionFinder.parents().length);
        assertEquals("The number of boxes should be 10.",10, unionFinder.totalRoots());
        // At the beginning, every box should be its own parent.
        int[] par = unionFinder.parents();
        for(int act : par)
            assertEquals("The parent should be itself.", act, par[act]);
    }

    /**
     * Tests that the union finder is correctly initialized.
     */
    @Test
    public void initializationTest2()
    {
        BasicNumUnionFinder newUnionFinder = new BasicNumUnionFinder(unionFinder);
        // The new union finder shouldn't be null and should have 10 parents (10 boxes).
        assertNotNull("The union finder shouldn't be null.", newUnionFinder);
        assertEquals("The number of parents should be 10.", 10, newUnionFinder.parents().length);
        assertEquals("The number of boxes should be 10.", 10, newUnionFinder.totalRoots());
        // At the beginning, every box should be its own parent.
        int[] par = newUnionFinder.parents();
        for(int act : par)
            assertEquals("The parent should be itself.", act, par[act]);
    }

    /**
     * Tests that a union finder given stored information is correctly initialized.
     */
    @Test
    public void initializationTest3()
    {
        // Boxes 1 and 2, and 8 and 9 will be merged.
        unionFinder.merge(1, 2);
        unionFinder.merge(8, 9);
        // A new union finder is created (with the stored information of the merges).
        BasicNumUnionFinder newUnionFinder = new BasicNumUnionFinder(unionFinder);
        // Since boxes 1 and 2, and 8 and 9 are merged, there should be 8 boxes but par of size 10.
        assertNotNull("The union finder shouldn't be null.", newUnionFinder);
        assertEquals("The number of parents should be 10.", 10, newUnionFinder.parents().length);
        assertEquals("The number of boxes should be 8.", 8, newUnionFinder.totalRoots());
        // The roots should be stored properly stored.
        assertEquals("The root should be itself.", 1, newUnionFinder.root(1));
        assertEquals("The root should be itself.", 8, newUnionFinder.root(8));
        assertEquals("The root should be 1.", 1, newUnionFinder.root(2));
        assertEquals("The root should be 8.", 8, newUnionFinder.root(9));
    }

    /**
     * Tests that the union finder finds and assigns roots properly.
     */
    @Test
    public void testRoot1()
    {
        // At the beginning, the root of all boxes should be themselves.
        for(int box = 0; box < unionFinder.parents().length; ++box)
            assertEquals("The root should be itself.", box, unionFinder.root(box));
        // Boxes 8 and 9 are merged. Everything should stay the same except for 9.
        unionFinder.merge(8, 9);
        for(int box = 0; box < unionFinder.parents().length-1; ++box)
            assertEquals("The root should be itself.", box, unionFinder.root(box));
        // The root of 9 should be 8.
        assertEquals("The root should be 8.", 8, unionFinder.root(9));
        // Boxes 6 and 5 are merged. Nothing should change other than 5 and 9.
        unionFinder.merge(6, 5);
        // To make things easier, all boxes except 5 and 9 are initialized in an array.
        int[] boxes = {0, 1, 2, 3, 4, 6, 7, 8};
        for(int box : boxes)
            assertEquals("The root should be itself.", box, unionFinder.root(box));
        // The root of 9 should be 8, and the root of 5 should be 6.
        assertEquals("The root should be 8.", 8, unionFinder.root(9));
        assertEquals("The root should be 6.", 6, unionFinder.root(5));
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
        // At the beginning, the root of all boxes should be themselves.
        for(int box = 0; box < unionFinder.parents().length; ++box)
            assertEquals("The root should be itself.", box, unionFinder.root(box));
        // Boxes 8 and 9 are merged. Everything should stay the same except for 9.
        unionFinder.merge(8, 9);
        for(int box = 0; box < unionFinder.parents().length-1; ++box)
            assertEquals("The root should be itself.", box, unionFinder.root(box));
        // The root of 9 should be 8.
        assertEquals("The root should be 8.", 8, unionFinder.root(9));
        // Boxes 6 and 5 are merged. Nothing should change other than 5 and 9.
        unionFinder.merge(6, 5);
        // To make things easier, all boxes except 5 and 9 are initialized in an array.
        int[] boxes = {0, 1, 2, 3, 4, 6, 7, 8};
        for(int box : boxes)
            assertEquals("The root should be itself.", box, unionFinder.root(box));
        // The root of 9 should be 8, and the root of 5 should be 6.
        assertEquals("The root should be 8.", 8, unionFinder.root(9));
        assertEquals("The root should be 6.", 6, unionFinder.root(5));
        // Now, the root of a non existent box should return null.
        assertNull("The root should be null.", unionFinder.rootChecked(100));
        assertNull("The root should be null.", unionFinder.rootChecked(-20));
        assertNull("The root should be null.", unionFinder.rootChecked(10));
        assertNull("The root should be null.", unionFinder.rootChecked(-1));
    }

    /**
     * Tests that the merge function works properly.
     */
    @Test
    public void mergeTest1()
    {
        // Boxes 0 and 1 are merged. Since they have the same size, box 1 is merged into box 0.
        unionFinder.merge(0, 1);
        // 0 should still be its own parent, the parent of 1 should be 0.
        assertEquals("The parent should be itself.", 0, unionFinder.parents()[0]);
        assertEquals("The parent should be 0.", 0, unionFinder.parents()[1]);
        // Checks that the root of 0 is 0, and the root of 1 is 0 (since it merged).
        assertEquals("The root should be 0.", 0, unionFinder.root(0));
        assertEquals("The root should be 0.", 0, unionFinder.root(1));
        // Checks that the root of the remaining numbers are unchanged.
        for(int box = 2; box < unionFinder.parents().length; ++box)
            assertEquals("The root should be " + box + ".", box, unionFinder.root(box));
        // Boxes 0 and 9 are merged. Since 9 is smaller, it's merged into box 0.
        unionFinder.merge(0, 9);
        // 0 should still be its own parent, the parent of 9 should be 0.
        assertEquals("The parent should be itself.", 0, unionFinder.parents()[0]);
        assertEquals("The parent should be 0.", 0, unionFinder.parents()[1]);
        assertEquals("The parent should be 0.", 0, unionFinder.parents()[9]);
        //Checks that the root of 0 is 0, and the root of 9 is 0 (since it merged).
        assertEquals("The root should be 0.", 0, unionFinder.root(0));
        assertEquals("The root should be 0.", 0, unionFinder.root(1));
        assertEquals("The root should be 0.", 0, unionFinder.root(9));
        //Checks that the root of the remaining numbers are unchanged.
        for(int box = 2; box < unionFinder.parents().length-1; ++box)
            assertEquals("The root should be " + box + ".", box, unionFinder.root(box));
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
        // Boxes 0 and 1 are merged. Since they have the same size, box 1 is merged into box 0.
        unionFinder.merge(0, 1);
        // 0 should still be its own parent, the parent of 1 should be 0.
        assertEquals("The parent should be itself.", 0, unionFinder.parents()[0]);
        assertEquals("The parent should be 0.", 0, unionFinder.parents()[1]);
        // Checks that the root of 0 is 0, and the root of 1 is 0 (since it merged).
        assertEquals("The root should be 0.", 0, unionFinder.root(0));
        assertEquals("The root should be 0.", 0, unionFinder.root(1));
        // Checks that the root of the remaining numbers are unchanged.
        for(int box = 2; box < unionFinder.parents().length; ++box)
            assertEquals("The root should be " + box + ".", box, unionFinder.root(box));
        // Boxes 0 and 9 are merged. Since 9 is smaller, it's merged into box 0.
        unionFinder.merge(0, 9);
        // Non existent boxes are merged. Nothing should happen.
        unionFinder.mergeChecked(10, 11);
        unionFinder.mergeChecked(-10, 200);
        unionFinder.mergeChecked(10, -200);
        unionFinder.mergeChecked(-10, -20);
        // 0 should still be its own parent, the parent of 9 should be 0.
        assertEquals("The parent should be itself.", 0, unionFinder.parents()[0]);
        assertEquals("The parent should be 0.", 0, unionFinder.parents()[1]);
        assertEquals("The parent should be 0.", 0, unionFinder.parents()[9]);
        //Checks that the root of 0 is 0, and the root of 9 is 0 (since it merged).
        assertEquals("The root should be 0.", 0, unionFinder.root(0));
        assertEquals("The root should be 0.", 0, unionFinder.root(1));
        assertEquals("The root should be 0.", 0, unionFinder.root(9));
        //Checks that the root of the remaining numbers are unchanged.
        for(int box = 2; box < unionFinder.parents().length-1; ++box)
            assertEquals("The root should be " + box + ".", box, unionFinder.root(box));
    }

    /**
     * Tests that the union finder keeps track of box sizes properly.
     */
    @Test
    public void sizeTest1()
    {
        // At the beginning, the size of all boxes should be 1.
        for(int box = 0; box < unionFinder.parents().length; ++box)
            assertEquals("The value should be 1.", 1, unionFinder.size(box));
        // Boxes 2 and 5 are merged.
        unionFinder.mergeChecked(2, 5);
        // To make things easier, all boxes except 2 and 5 are initialized in an array.
        int[] boxes = {1, 3, 4, 6, 7, 8, 9};
        // The size of all other boxes other than 2 and 5 should be 5.
        for(int box : boxes)
            assertEquals("The value should be 1.", 1, unionFinder.size(box));
        // The size of super box 2-5 is 2.
        assertEquals("The size should be 2.", 2, unionFinder.size(2));
        assertEquals("The size should be 2.", 2, unionFinder.size(5));
        // All boxes other than 2, 5 and 7 are initialized in an array.
        boxes = new int[] {1, 3, 4, 6, 8, 9};
        unionFinder.merge(2, 7);
        for(int key : boxes)
            assertEquals("The value should be 1.", 1, unionFinder.size(key));
        // The size of super box 2-5-7 should be 3.
        assertEquals("The size should be 3.", 3, unionFinder.size(2));
        assertEquals("The size should be 3.", 3, unionFinder.size(5));
        assertEquals("The size should be 3.", 3, unionFinder.size(7));
        // Boxes 3 and 7 are merged.
        unionFinder.merge(3, 7);
        // All boxes other than 2, 3, 5 and 7 are initialized in an array.
        boxes = new int[] {1, 4, 6, 8, 9};
        for(int key : boxes)
            assertEquals("The value should be 1.", (int) unionFinder.sizeChecked(key), 1);
        // The size of super box 2-3-5-7 should be 4.
        assertEquals("The size should be 4.", 4, unionFinder.size(2));
        assertEquals("The size should be 4.", 4, unionFinder.size(3));
        assertEquals("The size should be 4.", 4, unionFinder.size(5));
        assertEquals("The size should be 4.", 4, unionFinder.size(7));
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
        // At the beginning, the size of all boxes should be 1.
        for(int box = 0; box < unionFinder.parents().length; ++box)
            assertEquals("The value should be 1.", 1, (int) unionFinder.sizeChecked(box));
        // Boxes 2 and 5 are merged.
        unionFinder.mergeChecked(2, 5);
        // To make things easier, all boxes except 2 and 5 are initialized in an array.
        int[] boxes = {1, 3, 4, 6, 7, 8, 9};
        // The size of all other boxes other than 2 and 5 should be 5.
        for(int box : boxes)
            assertEquals("The value should be 1.", 1, (int) unionFinder.sizeChecked(box));
        // The size of super box 2-5 is 2.
        assertEquals("The size should be 2.", 2, (int) unionFinder.sizeChecked(2));
        assertEquals("The size should be 2.", 2, (int) unionFinder.sizeChecked(5));
        // All boxes other than 2, 5 and 7 are initialized in an array.
        boxes = new int[] {1, 3, 4, 6, 8, 9};
        unionFinder.merge(2, 7);
        for(int key : boxes)
            assertEquals("The value should be 1.", 1, unionFinder.size(key));
        // The size of super box 2-5-7 should be 3.
        assertEquals("The size should be 3.", 3, (int) unionFinder.sizeChecked(2));
        assertEquals("The size should be 3.", 3, (int) unionFinder.sizeChecked(5));
        assertEquals("The size should be 3.", 3, (int) unionFinder.sizeChecked(7));
        // Sizes of non-existent boxes should  be null. Nothing else should change.
        assertNull("The size should be null.", unionFinder.sizeChecked(100));
        assertNull("The size should be null.", unionFinder.sizeChecked(200));
        assertNull("The size should be null.", unionFinder.sizeChecked(-10));
        // Boxes 3 and 7 are merged.
        unionFinder.merge(3, 7);
        // All boxes other than 2, 3, 5 and 7 are initialized in an array.
        boxes = new int[] {1, 4, 6, 8, 9};
        for(int key : boxes)
            assertEquals("The value should be 1.", (int) unionFinder.sizeChecked(key), 1);
        // The size of super box 2-3-5-7 should be 4.
        assertEquals("The size should be 4.", 4, (int) unionFinder.sizeChecked(2));
        assertEquals("The size should be 4.", 4, (int) unionFinder.sizeChecked(3));
        assertEquals("The size should be 4.", 4, (int) unionFinder.sizeChecked(5));
        assertEquals("The size should be 4.", 4, (int) unionFinder.sizeChecked(7));
        // Sizes of non-existent boxes should  be null. Nothing else should change.
        assertNull("The size should be null.", unionFinder.sizeChecked(100));
        assertNull("The size should be null.", unionFinder.sizeChecked(200));
        assertNull("The size should be null.", unionFinder.sizeChecked(-10));
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