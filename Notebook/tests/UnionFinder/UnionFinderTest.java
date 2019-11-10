package UnionFinder;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;

import static org.junit.Assert.*;

public class UnionFinderTest
{
    // Attributes

    /**
     * The union finder.
     */
    private UnionFinder<String> unionFinder;

    // Setups

    /**
     * Creates an empty union finder.
     */
    public void setup1()
    { unionFinder = new UnionFinder<String>(); }

    /**
     * Creates an union finder with boxes labeled from "a" to "d".
     */
    public void setup2()
    {
        String[] boxes = {"a", "b", "c", "d"};
        unionFinder = new UnionFinder<>(boxes);
    }

    /**
     * Creates an union finder with boxes labeled from "a" to "h".
     */
    public void setup3()
    {
        ArrayList<String> boxes = new ArrayList<>();
        boxes.add("a"); boxes.add("b"); boxes.add("c"); boxes.add("d");
        boxes.add("e"); boxes.add("f"); boxes.add("g"); boxes.add("h");
        unionFinder = new UnionFinder<>(boxes);
    }

    // Tests

    /**
     * Tests that the empty union finder is correctly initialized.
     */
    @Test
    public void initializationTest1()
    {
        setup1();
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The size should be 0.", 0, unionFinder.parents().size());
        assertEquals("The number of boxes should be 0.", 0, unionFinder.totalRoots());
    }

    /**
     * Tests that the union finder with boxes labeled from "a" to "d" is correctly initialized.
     */
    @Test
    public void initializationTest2()
    {
        setup2();
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The size should be 4.", 4, unionFinder.parents().size());
        assertEquals("The number of boxes should be 4.", 4, unionFinder.totalRoots());
        Hashtable<String, String> par = unionFinder.parents();
        String[] boxes = {"a", "b", "c", "d"};
        for(String box : boxes)
        {
            assertTrue("The table should contain the key.", par.containsKey(box));
            assertEquals("The value should be itself.", box, par.get(box));
        }
    }

    /**
     * Tests that the union finder with boxes labeled from "a" to "h" is correctly initialized.
     */
    @Test
    public void initializationTest3()
    {
        setup3();
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The size should be 8.", 8, unionFinder.parents().size());
        assertEquals("The number of boxes should be 8.", 8, unionFinder.totalRoots());
        Hashtable<String, String> par = unionFinder.parents();
        String[] boxes = {"a", "b", "c", "d", "e", "f", "g", "h"};
        for(String box : boxes)
        {
            assertTrue("The table should contain the key.", par.containsKey(box));
            assertEquals("The value should be itself.", box, par.get(box));
        }
    }

    /**
     * Tests that the union finder is correctly initialized as a copy of a given union finder.
     */
    @Test
    public void initializationTest4()
    {
        setup3();
        UnionFinder<String> newUnionFinder = new UnionFinder<String>(unionFinder);
        assertNotNull("The union finder shouldn't be null.", newUnionFinder);
        assertEquals("The size should be 8.", 8, newUnionFinder.parents().size());
        assertEquals("The number of boxes should be 8.", 8, newUnionFinder.totalRoots());
        Hashtable<String, String> par = newUnionFinder.parents();
        String[] boxes = {"a", "b", "c", "d", "e", "f", "g", "h"};
        for(String box : boxes)
        {
            assertTrue("The table should contain the key.", par.containsKey(box));
            assertEquals("The value should be itself.", box, par.get(box));
        }
    }

    /**
     * Tests that an union finder given stored information is correctly initialized.
     */
    @Test
    public void initializationTest5()
    {
        setup3();
        unionFinder.merge("a", "b"); unionFinder.merge("c", "d");
        UnionFinder<String> newUnionFinder = new UnionFinder<String>(unionFinder);
        // Since boxes a and b, and c and d are merged, there should be 6 boxes but par of size 8.
        assertNotNull("The union finder shouldn't be null.", newUnionFinder);
        assertEquals("The size should be 8.", 8, newUnionFinder.parents().size());
        assertEquals("The number of boxes should be 6.", 6, newUnionFinder.totalRoots());
        Hashtable<String, String> par = newUnionFinder.parents();
        String[] boxes = {"a", "b", "c", "d", "e", "f", "g", "h"};
        for(String box : boxes)
        { assertTrue("The table should contain the key.", par.containsKey(box)); }
        assertEquals("The root should be itself.", "a", newUnionFinder.root("a"));
        assertEquals("The root should be a.", "a", newUnionFinder.root("b"));
        assertEquals("The root should be itself.", "c", newUnionFinder.root("c"));
        assertEquals("The root should be c.", "c", newUnionFinder.root("d"));
        assertEquals("The root should be itself.", "e", newUnionFinder.root("e"));
        assertEquals("The root should be itself.", "f", newUnionFinder.root("f"));
        assertEquals("The root should be itself.", "g", newUnionFinder.root("g"));
        assertEquals("The root should be itself.", "h", newUnionFinder.root("h"));
    }
}