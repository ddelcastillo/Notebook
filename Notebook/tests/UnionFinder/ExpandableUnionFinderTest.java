// @formatter:off
package UnionFinder;

import org.junit.Test;

import java.util.Hashtable;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ExpandableUnionFinderTest
{
    // Attributes

    /**
     * The union finder.
     */
    private ExpandableUnionFinder unionFinder;

    // Setup

    private void setup1()
    { unionFinder = new ExpandableUnionFinder(); }

    private void setup2()
    { unionFinder = new ExpandableUnionFinder(10); }

    private void setup3()
    {
        int[] indexes = {2, 3, 5, 7};
        unionFinder = new ExpandableUnionFinder(indexes);
    }

    // Tests

    /**
     * Tests that the union finder is correctly initialized.
     */
    @Test
    public void testInitialization1()
    {
        setup1();
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The size should be 0.", unionFinder.parents().size(), 0);
        assertEquals("The number of boxes should be 0.", unionFinder.totalRoots(), 0);
    }

    /**
     * Tests that the union finder is correctly initialized.
     */
    @Test
    public void testInitialization2()
    {
        setup2();
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The size should be 10.", unionFinder.parents().size(), 10);
        assertEquals("The number of boxes should be 10.", unionFinder.totalRoots(), 10);
        Hashtable<Integer, Integer> par = unionFinder.parents();
        for(int i = 0; i < par.size(); ++i)
        {
            assertTrue("The table should contain the key.", par.containsKey(i));
            assertEquals("The value should be -1.", (int) par.get(i), -1);
        }
    }

    /**
     * Tests that the union finder is correctly initialized.
     */
    @Test
    public void testInitialization3()
    {
        setup3();
        int[] indexes = {2, 3, 5, 7};
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The size should be 4.", unionFinder.parents().size(), 4);
        assertEquals("The number of boxes should be 4.", unionFinder.totalRoots(), 4);
        Hashtable<Integer, Integer> par = unionFinder.parents();
        for(int i : indexes)
        {
            assertTrue("The table should contain the key.", par.containsKey(i));
            assertEquals("The value should be -1.", (int) par.get(i), -1);
        }
    }

    /**
     * Tests that the union finder is correctly initialized.
     */
    @Test
    public void testInitialization4()
    {
        setup3();
        int[] indexes = {2, 3, 5, 7};
        ExpandableUnionFinder newUnionFinder = new ExpandableUnionFinder(unionFinder);
        assertNotNull("The union finder shouldn't be null.", newUnionFinder);
        assertEquals("The size should be 4.", newUnionFinder.parents().size(), 4);
        assertEquals("The number of boxes should be 4.", newUnionFinder.totalRoots(), 4);
        Hashtable<Integer, Integer> par = newUnionFinder.parents();
        for(int i : indexes)
        {
            assertTrue("The table should contain the key.", par.containsKey(i));
            assertEquals("The value should be -1.", (int) par.get(i), -1);
        }
    }

    /**
     * Tests that the union finder adds boxes properly.
     */
    @Test
    public void testAdd()
    {
        setup1();
        // Three boxes will be added: box 1, 2 and 5.
        unionFinder.add(1); unionFinder.add(2); unionFinder.add(5);
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 3.", unionFinder.totalRoots(), 3);
        Hashtable<Integer, Integer> par = unionFinder.parents();
        Iterator<Integer> iterator = par.keySet().iterator();
        int key;
        while(iterator.hasNext())
        {
            key = iterator.next();
            assertTrue("The table should contain the key.", par.containsKey(key));
            assertEquals("The value should be -1.", (int) par.get(key), -1);
            assertEquals("The root of the key should be itself.", unionFinder.root(key), key);
        }
        // Tests that repeated boxes are added. Now box 1's size is restarted and box counter's are now broken.
        unionFinder.add(1);
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 4.", unionFinder.totalRoots(), 4);
        par = unionFinder.parents();
        iterator = par.keySet().iterator();
        while(iterator.hasNext())
        {
            key = iterator.next();
            assertTrue("The table should contain the key.", par.containsKey(key));
            assertEquals("The value should be -1.", (int) par.get(key), -1);
            assertEquals("The root of the key should be itself.", unionFinder.root(key), key);
        }
    }

    /**
     * Tests that the union finder copy doesn't add items to the original union finder.
     */
    @Test
    public void testCopy()
    {
        setup1();
        unionFinder.add(2); unionFinder.add(3); unionFinder.add(5);
        ExpandableUnionFinder newUnionFinder = new ExpandableUnionFinder(unionFinder);
        newUnionFinder.add(7);
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 3.", unionFinder.totalRoots(), 3);
        assertEquals("The size should be 4.", newUnionFinder.parents().size(), 4);
        assertEquals("The number of boxes should be 4.", newUnionFinder.totalRoots(), 4);
    }

    /**
     * Tests that the merge function works properly.
     */
    @Test
    public void testMerge()
    {
        setup2();
        // Two boxes will be added: 10 and 20.
        unionFinder.add(10); unionFinder.add(20);
        assertEquals("The size should be 12.", unionFinder.parents().size(), 12);
        assertEquals("The number of boxes should be 12.", unionFinder.totalRoots(), 12);
        // The value of each box should be -1.
        Iterator<Integer> iterator = unionFinder.parents().values().iterator();
        int value;
        while(iterator.hasNext())
        {
            value = iterator.next();
            assertEquals("The value should be -1.", value, -1);
        }
        int[] keys = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20};
        for(int i : keys)
            assertEquals("The root of each box should be itself.", unionFinder.root(i), i);
        // Now, boxes merged are: 1 and 2, 3 and 4, 5 and 7, 6 and 8, 9 and 10, 9 and 20, and 0 and 20.
        unionFinder.merge(1, 2); unionFinder.merge(3, 4); unionFinder.merge(5, 7);
        unionFinder.merge(6, 8); unionFinder.merge(9, 10); unionFinder.merge(9, 20);
        unionFinder.merge(0, 20);
        // System.out.println(unionFinder.toString());
        // There should be 5 boxes, 4 of size 2 and 1 of size 4.
        assertEquals("There should be 5 boxes.", unionFinder.totalRoots(), 5);
        assertEquals("The size should be 2.", unionFinder.size(1), 2);
        assertEquals("The size should be 2.", unionFinder.size(2), 2);
        assertEquals("The size should be 2.", unionFinder.size(3), 2);
        assertEquals("The size should be 2.", unionFinder.size(4), 2);
        assertEquals("The size should be 2.", unionFinder.size(5), 2);
        assertEquals("The size should be 2.", unionFinder.size(6), 2);
        assertEquals("The size should be 2.", unionFinder.size(7), 2);
        assertEquals("The size should be 2.", unionFinder.size(8), 2);
        assertEquals("The size should be 4.", unionFinder.size(9), 4);
        assertEquals("The size should be 4.", unionFinder.size(0), 4);
        assertEquals("The size should be 4.", unionFinder.size(10), 4);
        assertEquals("The size should be 4.", unionFinder.size(20), 4);
        // System.out.println(unionFinder.toString());
        // A box will be added: 100.
        unionFinder.add(100);
        assertEquals("The size should be 13.", unionFinder.parents().size(), 13);
        assertEquals("The number of boxes should be 6.", unionFinder.totalRoots(), 6);
        // Now, boxes merged are: 100 and 10.
        unionFinder.merge(100, 10);
        assertEquals("The size should be 5.", unionFinder.size(100), 5);
        assertEquals("The number of boxes should be 5.", unionFinder.totalRoots(), 5);
        // System.out.println(unionFinder.toString());
    }
}