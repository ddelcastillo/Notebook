package UnionFinder;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

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
    private void setup1()
    { unionFinder = new UnionFinder<String>(); }

    /**
     * Creates an union finder with boxes labeled from "a" to "d".
     */
    private void setup2()
    {
        String[] boxes = {"a", "b", "c", "d"};
        unionFinder = new UnionFinder<>(boxes);
    }

    /**
     * Creates an union finder with boxes labeled from "a" to "h".
     */
    private void setup3()
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

    /**
     * Tests that the union finder adds boxes properly.
     */
    @Test
    public void addTest1()
    {
        setup1();
        // Three boxes will be added: "a", "b" and "c".
        unionFinder.add("a"); unionFinder.add("b"); unionFinder.add("c");
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 3.", unionFinder.totalRoots(), 3);
        Hashtable<String, String> par = unionFinder.parents();
        Iterator<String> iterator = par.keySet().iterator();
        String key;
        while(iterator.hasNext())
        {
            key = iterator.next();
            assertEquals("The value should be itself.", par.get(key), key);
            assertEquals("The root of the key should be itself.", unionFinder.root(key), key);
        }
        // Tests that repeated boxes are added. Now box a's size is restarted to -1 (unchanged, for this test)
        // and box counter's are now broken (it marks 4 boxes when actually, there's still 3).
        unionFinder.add("a");
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 4.", unionFinder.totalRoots(), 4);
        par = unionFinder.parents();
        iterator = par.keySet().iterator();
        while(iterator.hasNext())
        {
            key = iterator.next();
            assertEquals("The value should be itself.", par.get(key), key);
            assertEquals("The root of the key should be itself.", unionFinder.root(key), key);
        }
    }

    /**
     * Tests that the union finder responds accordingly to logic when adding an already existing box.
     * That is, while sizes will not be correct, the logic remains intact.
     */
    @Test
    public void addTest2()
    {
        setup1();
        // A box 10 will be added:
        unionFinder.add("a");
        assertEquals("The size should be 1.", unionFinder.parents().size(), 1);
        assertEquals("The number of boxes should be 1.", unionFinder.totalRoots(), 1);
        // Another box 20 will be added:
        unionFinder.add("b");
        assertEquals("The size should be 2.", unionFinder.parents().size(), 2);
        assertEquals("The number of boxes should be 2.", unionFinder.totalRoots(), 2);
        // Another box 30 will be added:
        unionFinder.add("c");
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 3.", unionFinder.totalRoots(), 3);
        // Boxes 10 and 20 will be merged. Now, there should be 2 boxes:
        unionFinder.merge("a", "b");
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 2.", unionFinder.totalRoots(), 2);
        assertEquals("The root of the box should be itself.", unionFinder.root("a"), "a");
        assertEquals("The root of the box should be a.", unionFinder.root("b"), "a");
        assertEquals("The root of the box should be itself.", unionFinder.root("c"), "c");
        assertEquals("The size of the union should be 2.", unionFinder.size("a"), 2);
        assertEquals("The size of the union should be 2.", unionFinder.size("b"), 2);
        assertEquals("The size of the union should be 1.", unionFinder.size("c"), 1);
        // Now box a will be added again, which is a mistake. With the current implementation, the problem is:
        // a new key will be added to the numberToBox but not to the boxToNumber table (since a is already a key).
        // The union finder will generate a number (in this case, 3) meaning N = 3 and the number of boxes is 3.
        // So, new pair in numberToBox (3, a) and now in boxToNumber the pair (a, 0) is changed to (a, 3).
        unionFinder.add("a");
        // Since there are actually 3 boxes (a, b and c), thus the size of parents is 3. a-b were merged,
        // meaning that the introduction of a new box a is seen by the numerical union finder as a new box 3,
        // thus, there should 3 boxes (0-1 [a-b], 2[c], 3[wrong-a]). It is important to note that now original box
        // a is unreachable since its boxToNumber value was updated from 0 to 3 (so root("a") returns the 'wrong' a).
        // The only way to reach it is from the root of b, who is still in the numerical union finder, 0 (right a).
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 3.", unionFinder.totalRoots(), 3);
        assertEquals("The root of the box should be itself.", unionFinder.root("a"), "a");
        assertEquals("The root of the box should be a.", unionFinder.root("b"), "a");
        assertEquals("The root of the box should be itself.", unionFinder.root("c"), "c");
        // Original box a is unreachable, thus, size("a") is the size of the wrong a, which is alone.
        assertEquals("The size of the union should be 1.", unionFinder.size("a"), 1);
        assertEquals("The size of the union should be 2.", unionFinder.size("b"), 2);
        assertEquals("The size of the union should be 1.", unionFinder.size("c"), 1);
        // Now, boxes b and c will be merged. This will mean there's a box of size 3 (right a)-b-c and the box wrong a,
        // so the number of boxes is 2. The root of a is still itself since the keyToNumber is still (a, 3).
        unionFinder.merge("b", "c");
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 2.", unionFinder.totalRoots(), 2);
        assertEquals("The root of the box should be itself.", unionFinder.root("a"), "a");
        // The root of b is still a (the right a, with root 0). The root of c is also the correct a.
        assertEquals("The root of the box should be a.", unionFinder.root("b"), "a");
        assertEquals("The root of the box should be a.", unionFinder.root("c"), "a");
        // Now, the sizes of the boxes should be: box wrong a is 1, and super box (right a)-b-c is 3.
        assertEquals("The size of the union should be 2.", unionFinder.size("a"), 1);
        assertEquals("The size of the union should be 3.", unionFinder.size("b"), 3);
        assertEquals("The size of the union should be 3.", unionFinder.size("c"), 3);
        // Now, a call to merge("a", "c") will end up in merging the box wrong a with box c, since the boxToNumber
        // table still has the couple (a, 3). This means that now there is a single super box: (right a)-b-c-(wrong a).
        // However, there are still 3 boxes (a, b and c), so the number of parents is still 3.
        unionFinder.merge("a", "c");
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 1.", unionFinder.totalRoots(), 1);
        // Oddly enough the root of wrong a is now correct a, but since they're the same, equality holds.
        assertEquals("The root of the box should be itself.", unionFinder.root("a"), "a");
        // The root of b and c is still right a.
        assertEquals("The root of the box should be a.", unionFinder.root("b"), "a");
        assertEquals("The root of the box should be a.", unionFinder.root("c"), "a");
        // Now, since they're all in the same super box, the size of the unions should be 4.
        assertEquals("The size of the union should be 4.", unionFinder.size("a"), 4);
        assertEquals("The size of the union should be 4.", unionFinder.size("b"), 4);
        assertEquals("The size of the union should be 4.", unionFinder.size("c"), 4);
    }

    /**
     * Tests that the root of a non-existent box ends up with a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void addTest3()
    {
        // Searching for the key should result in null, root(null) in numerical union finder.
        setup1(); unionFinder.root("c");
    }

    /**
     * Tests that the union finder adds and checks boxes properly.
     */
    @Test
    public void addCheckedTest()
    {
        setup1();
        // A box 10 will be added:
        unionFinder.addChecked("a");
        assertEquals("The size should be 1.", unionFinder.parents().size(), 1);
        assertEquals("The number of boxes should be 1.", unionFinder.totalRoots(), 1);
        // Another box 20 will be added:
        unionFinder.addChecked("b");
        assertEquals("The size should be 2.", unionFinder.parents().size(), 2);
        assertEquals("The number of boxes should be 2.", unionFinder.totalRoots(), 2);
        // Another box 30 will be added:
        unionFinder.addChecked("c");
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 3.", unionFinder.totalRoots(), 3);
        // Boxes 10 and 20 will be merged. Now, there should be 2 boxes:
        unionFinder.merge("a", "b");
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 2.", unionFinder.totalRoots(), 2);
        assertEquals("The root of the box should be itself.", unionFinder.root("a"), "a");
        assertEquals("The root of the box should be a.", unionFinder.root("b"), "a");
        assertEquals("The root of the box should be itself.", unionFinder.root("c"), "c");
        assertEquals("The size of the union should be 2.", unionFinder.size("a"), 2);
        assertEquals("The size of the union should be 2.", unionFinder.size("b"), 2);
        assertEquals("The size of the union should be 1.", unionFinder.size("c"), 1);
        // Since box a already exists, nothing should change.
        unionFinder.addChecked("a");
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 2.", unionFinder.totalRoots(), 2);
        assertEquals("The root of the box should be itself.", unionFinder.root("a"), "a");
        assertEquals("The root of the box should be a.", unionFinder.root("b"), "a");
        assertEquals("The root of the box should be itself.", unionFinder.root("c"), "c");
        assertEquals("The size of the union should be 2.", unionFinder.size("a"), 2);
        assertEquals("The size of the union should be 2.", unionFinder.size("b"), 2);
        assertEquals("The size of the union should be 1.", unionFinder.size("c"), 1);
        // Now, boxes b and c will be merged. This means that now there is one super box a-b-c.
        unionFinder.merge("b", "c");
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 1.", unionFinder.totalRoots(), 1);
        assertEquals("The root of the box should be itself.", unionFinder.root("a"), "a");
        assertEquals("The root of the box should be a.", unionFinder.root("b"), "a");
        assertEquals("The root of the box should be a.", unionFinder.root("c"), "a");
        assertEquals("The size of the union should be 3.", unionFinder.size("a"), 3);
        assertEquals("The size of the union should be 3.", unionFinder.size("b"), 3);
        assertEquals("The size of the union should be 3.", unionFinder.size("c"), 3);
        // Boxes a and c are already merged, so nothing should change.
        unionFinder.merge("a", "c");
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 1.", unionFinder.totalRoots(), 1);
        assertEquals("The root of the box should be itself.", unionFinder.root("a"), "a");
        assertEquals("The root of the box should be a.", unionFinder.root("b"), "a");
        assertEquals("The root of the box should be a.", unionFinder.root("c"), "a");
        // Now, since they're all in the same super box, the size of the unions should be 3.
        assertEquals("The size of the union should be 3.", unionFinder.size("a"), 3);
        assertEquals("The size of the union should be 3.", unionFinder.size("b"), 3);
        assertEquals("The size of the union should be 3.", unionFinder.size("c"), 3);
    }
}