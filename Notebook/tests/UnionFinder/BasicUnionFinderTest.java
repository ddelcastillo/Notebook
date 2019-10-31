package UnionFinder;

import org.junit.Before;
import org.junit.Test;
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
     * Initializes the union finder with a capacity of 10 boxes.
     */
    @Before
    public void setup()
    { unionFinder = new BasicUnionFinder(10); }

    // Tests

    /**
     * Tests that the union finder is correctly initialized.
     */
    @Test
    public void testInitialization1()
    {
        // At the beginning, everything starts as -1.
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The maximum size should be 10.", unionFinder.parents().length, 10);
        assertEquals("The number of boxes should be 10.", unionFinder.totalRoots(), 10);
        int[] par = unionFinder.parents();
        for(int act : par)
            assertEquals("The value should be -1.", act, -1);
    }

    /**
     * Tests that the union finder is correctly initialized.
     */
    @Test
    public void testInitialization2()
    {
        BasicUnionFinder newUnionFinder = new BasicUnionFinder(unionFinder);
        // At the beginning, everything starts as -1.
        assertNotNull("The union finder shouldn't be null.", newUnionFinder);
        assertEquals("The maximum size should be 10.", newUnionFinder.parents().length, 10);
        assertEquals("The number of boxes should be 10.", newUnionFinder.totalRoots(), 10);
        int[] par = newUnionFinder.parents();
        for(int act : par)
            assertEquals("The value should be -1.", act, -1);
    }

    /**
     * Tests that the merge function works properly.
     */
    @Test
    public void testMerge()
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
     * Tests that the union finder works properly with a simple 10 node case.
     */
    @Test
    public void testUnionFinder()
    {
        for(int i = 0; i < unionFinder.parents().length; ++i)
            assertEquals("The size of each root-node should be 1.", unionFinder.size(i), 1);
        assertEquals("The number of connected components should be 10.", unionFinder.totalRoots(), 10);
        // Lets assume we have 10 nodes on a graph. The first edges are 0-2, 2-3 and 0-1. First connected component.
        unionFinder.merge(0, 2); unionFinder.merge(2, 3); unionFinder.merge(0, 1);
        assertEquals("The number of connected components should be 7.", unionFinder.totalRoots(), 7);
        assertEquals("The size of the first connected component should be 4.", unionFinder.size(0), 4);
        // The root of all these nodes 0, 1, 2 and 3 should be equal (since they're joined).
        // System.out.println(unionFinder.toString());
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
        // System.out.println(unionFinder.toString());
        test = unionFinder.root(4) == unionFinder.root(5) && unionFinder.root(5) == unionFinder.root(6) &&
                unionFinder.root(6) == unionFinder.root(7) && unionFinder.root(7) == unionFinder.root(8) &&
                unionFinder.root(8) == unionFinder.root(9);
        assertTrue("All nodes should have the same root.", test);
        // Now that there's only two connected components, we join them through the edge 3-4.
        unionFinder.merge(3, 4);
        assertEquals("The number of connected components should be 1.", unionFinder.totalRoots(), 1);
        assertEquals("The size of the only connected component should be 10.", unionFinder.size(0), 10);
        // System.out.println(unionFinder.toString());
        //Now the root of all the nodes should be equal.
        test = unionFinder.root(1) == unionFinder.root(2) && unionFinder.root(2) == unionFinder.root(3) &&
                unionFinder.root(3) == unionFinder.root(4) && unionFinder.root(4) == unionFinder.root(5) &&
                unionFinder.root(5) == unionFinder.root(6) && unionFinder.root(6) == unionFinder.root(7) &&
                unionFinder.root(7) == unionFinder.root(8) && unionFinder.root(8) == unionFinder.root(9);
        assertTrue("All nodes should have the same root.", test);
        // System.out.println(unionFinder.toString());
    }
}