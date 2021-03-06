// @formatter:off
package unionFinder;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Tests that the UnionFinder class works properly.
 * Test completely tested, corrected, commented and revised as of 14/12/19.
 */
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
        // At the beginning, the union finder shouldn't be null and shouldn't have boxes.
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The number of parents should be 0.", 0, unionFinder.parents().size());
        assertEquals("The number of boxes should be 0.", 0, unionFinder.totalRoots());
    }

    /**
     * Tests that the union finder with boxes labeled from "a" to "d" is correctly initialized.
     */
    @Test
    public void initializationTest2()
    {
        setup2();
        // At the beginning, the union finder shouldn't be null, and there should be 4 boxes and parents.
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The number of parents should be 4.", 4, unionFinder.parents().size());
        assertEquals("The number of boxes should be 4.", 4, unionFinder.totalRoots());
        HashMap<String, String> parents = unionFinder.parents();
        // Every box should be it's own parent.
        String[] boxes = {"a", "b", "c", "d"};
        for(String box : boxes)
            assertEquals("The parent should be itself.", box, parents.get(box));
    }

    /**
     * Tests that the union finder with boxes labeled from "a" to "h" is correctly initialized.
     */
    @Test
    public void initializationTest3()
    {
        setup3();
        // At the beginning, the union finder shouldn't be null, and there should be 8 boxes and parents.
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The number of parents should be 8.", 8, unionFinder.parents().size());
        assertEquals("The number of boxes should be 8.", 8, unionFinder.totalRoots());
        HashMap<String, String> par = unionFinder.parents();
        String[] boxes = {"a", "b", "c", "d", "e", "f", "g", "h"};
        for(String box : boxes)
            assertEquals("The value should be itself.", box, par.get(box));
    }

    /**
     * Tests that the union finder is correctly initialized as a copy of a given union finder.
     */
    @Test
    public void initializationTest4()
    {
        setup3();
        UnionFinder<String> newUnionFinder = new UnionFinder<String>(unionFinder);
        // At the beginning, the union finder shouldn't be null, and there should be 8 boxes and parents.
        assertNotNull("The union finder shouldn't be null.", newUnionFinder);
        assertEquals("The number of parents should be 8.", 8, newUnionFinder.parents().size());
        assertEquals("The number of boxes should be 8.", 8, newUnionFinder.totalRoots());
        HashMap<String, String> par = newUnionFinder.parents();
        String[] boxes = {"a", "b", "c", "d", "e", "f", "g", "h"};
        for(String box : boxes)
            assertEquals("The value should be itself.", box, par.get(box));
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
        assertEquals("The number of parents should be 8.", 8, newUnionFinder.parents().size());
        assertEquals("The number of boxes should be 6.", 6, newUnionFinder.totalRoots());
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
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 3.", 3, unionFinder.totalRoots());
        HashMap<String, String> parents = unionFinder.parents();
        Iterator<String> iterator = parents.keySet().iterator();
        String box;
        while(iterator.hasNext())
        {
            box = iterator.next();
            // Same check in two different ways.
            assertEquals("The parent should be itself.", box, parents.get(box));
            assertEquals("The root of the key should be itself.", box, unionFinder.root(box));
        }
        // Tests that repeated boxes are added. Now box a's size is restarted to 1 (unchanged, for this test)
        // and box counter's are now broken (it marks 4 boxes when actually, there's still 3).
        unionFinder.add("a");
        assertEquals("The size should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 4.", 4, unionFinder.totalRoots());
        parents = unionFinder.parents();
        iterator = parents.keySet().iterator();
        while(iterator.hasNext())
        {
            box = iterator.next();
            assertEquals("The parent should be itself.", box, parents.get(box));
            assertEquals("The root of the key should be itself.", box, unionFinder.root(box));
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
        // Box a will be added.
        unionFinder.add("a");
        assertEquals("The number of parents should be 1.", 1, unionFinder.parents().size());
        assertEquals("The number of boxes should be 1.", 1, unionFinder.totalRoots());
        // Box b will be added.
        unionFinder.add("b");
        assertEquals("The number of parents should be 2.", 2, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        // Box c will be added.
        unionFinder.add("c");
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 3.", 3, unionFinder.totalRoots());
        // Boxes a and b will be merged. Now, there should be 2 boxes.
        unionFinder.merge("a", "b");
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", "a", unionFinder.root("a"));
        assertEquals("The root of the box should be a.", "a", unionFinder.root("b"));
        assertEquals("The root of the box should be itself.", "c", unionFinder.root("c"));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size("a"));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size("b"));
        assertEquals("The size of the union should be 1.", 1, unionFinder.size("c"));
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
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 3.", 3, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", "a", unionFinder.root("a"));
        assertEquals("The root of the box should be a.", "a", unionFinder.root("b"));
        assertEquals("The root of the box should be itself.", "c", unionFinder.root("c"));
        // Original box a is unreachable, thus, size("a") is the size of the wrong a, which is alone (1).
        assertEquals("The size of the union should be 1.", 1, unionFinder.size("a"));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size("b"));
        assertEquals("The size of the union should be 1.", 1, unionFinder.size("c"));
        // Now, boxes b and c will be merged. This will mean there's a box of size 3 (right a)-b-c and the box wrong a,
        // so the number of boxes is 2. The root of a is still itself since the keyToNumber is still (a, 3).
        unionFinder.merge("b", "c");
        assertEquals("The size should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", "a", unionFinder.root("a"));
        // The root of b is still a (the right a, with root 0). The root of c is also the correct a.
        assertEquals("The root of the box should be a.", "a", unionFinder.root("b"));
        assertEquals("The root of the box should be a.", "a", unionFinder.root("c"));
        // Now, the sizes of the boxes should be: box wrong a is 1, and super box (right a)-b-c is 3.
        assertEquals("The size of the union should be 2.", 1, unionFinder.size("a"));
        assertEquals("The size of the union should be 3.", 3, unionFinder.size("b"));
        assertEquals("The size of the union should be 3.", 3, unionFinder.size("c"));
        // Now, a call to merge("a", "c") will end up in merging the box wrong a with box c, since the boxToNumber
        // table still has the tuple (a, 3). This means that now there is a single super box: (right a)-b-c-(wrong a).
        // However, there are still 3 boxes (a, b and c), so the number of parents is still 3.
        unionFinder.merge("a", "c");
        assertEquals("The size should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 1.", 1, unionFinder.totalRoots());
        // Oddly enough the root of wrong a is now correct a, but since they're the same, equality holds.
        assertEquals("The root of the box should be itself.", "a", unionFinder.root("a"));
        // The root of b and c is still right a.
        assertEquals("The root of the box should be a.", "a", unionFinder.root("b"));
        assertEquals("The root of the box should be a.", "a", unionFinder.root("c"));
        // Now, since they're all in the same super box, the size of the unions should be 4.
        assertEquals("The size of the union should be 4.", 4, unionFinder.size("a"));
        assertEquals("The size of the union should be 4.", 4, unionFinder.size("b"));
        assertEquals("The size of the union should be 4.", 4, unionFinder.size("c"));
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
        // Box a will be added.
        unionFinder.addChecked("a");
        assertEquals("The number of parents should be 1.", 1, unionFinder.parents().size());
        assertEquals("The number of boxes should be 1.", 1, unionFinder.totalRoots());
        // Box b will be added.
        unionFinder.addChecked("b");
        assertEquals("The number of parents should be 2.", 2, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        // Box c will be added.
        unionFinder.addChecked("c");
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 3.", 3, unionFinder.totalRoots());
        // Boxes a and b will be merged. Now, there should be 2 boxes.
        unionFinder.merge("a", "b");
        assertEquals("The size should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", "a", unionFinder.root("a"));
        assertEquals("The root of the box should be a.", "a", unionFinder.root("b"));
        assertEquals("The root of the box should be itself.", "c", unionFinder.root("c"));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size("a"));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size("b"));
        assertEquals("The size of the union should be 1.", 1, unionFinder.size("c"));
        // Since box a already exists, nothing should change.
        unionFinder.addChecked("a");
        assertEquals("The size should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", "a", unionFinder.root("a"));
        assertEquals("The root of the box should be a.", "a", unionFinder.root("b"));
        assertEquals("The root of the box should be itself.", "c", unionFinder.root("c"));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size("a"));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size("b"));
        assertEquals("The size of the union should be 1.", 1, unionFinder.size("c"));
        // Now, boxes b and c will be merged. This means that now there is one super box a-b-c.
        unionFinder.merge("b", "c");
        assertEquals("The size should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 1.", 1, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", "a", unionFinder.root("a"));
        assertEquals("The root of the box should be a.", "a", unionFinder.root("b"));
        assertEquals("The root of the box should be a.", "a", unionFinder.root("c"));
        assertEquals("The size of the union should be 3.", 3, unionFinder.size("a"));
        assertEquals("The size of the union should be 3.", 3, unionFinder.size("b"));
        assertEquals("The size of the union should be 3.", 3, unionFinder.size("c"));
        // Boxes a and c are already merged, so nothing should change.
        unionFinder.merge("a", "c");
        assertEquals("The size should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 1.", 1, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", "a", unionFinder.root("a"));
        assertEquals("The root of the box should be a.", "a", unionFinder.root("b"));
        assertEquals("The root of the box should be a.", "a", unionFinder.root("c"));
        assertEquals("The size of the union should be 3.", 3, unionFinder.size("a"));
        assertEquals("The size of the union should be 3.", 3, unionFinder.size("b"));
        assertEquals("The size of the union should be 3.", 3, unionFinder.size("c"));
        // Now, since they're all in the same super box, the size of the unions should be 3.
        assertEquals("The size of the union should be 3.", 3, unionFinder.size("a"));
        assertEquals("The size of the union should be 3.", 3, unionFinder.size("b"));
        assertEquals("The size of the union should be 3.", 3, unionFinder.size("c"));
    }

    /**
     * Tests that the union finder finds and assigns roots properly.
     */
    @Test
    public void rootTest1()
    {
        setup2();
        HashMap<String, String> parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            assertEquals("The value should be itself.", box, parents.get(box));
            assertEquals("The root of the key should be itself.", box, unionFinder.root(box));
        }
        // At the beginning, there are only 4 single boxes: a, b c and d. Boxes a and b will be merged.
        unionFinder.merge("a", "b");
        assertEquals("The root should be itself.", "a", unionFinder.root("a"));
        assertEquals("The root should be a.", "a", unionFinder.root("b"));
        assertEquals("The root should be itself.", "c", unionFinder.root("c"));
        assertEquals("The root should be itself.", "d", unionFinder.root("d"));
        // Boxes c and d will be merged.
        unionFinder.merge("c", "d");
        assertEquals("The root should be itself.", "a", unionFinder.root("a"));
        assertEquals("The root should be a.", "a", unionFinder.root("b"));
        assertEquals("The root should be itself.", "c", unionFinder.root("c"));
        assertEquals("The root should be c.", "c", unionFinder.root("d"));
        // Finally, boxes c and d are merged.
        unionFinder.merge("c", "b");
        assertEquals("The root should be c.", "c", unionFinder.root("a"));
        assertEquals("The root should be c.", "c", unionFinder.root("b"));
        assertEquals("The root should be itself.", "c", unionFinder.root("c"));
        assertEquals("The root should be c.", "c", unionFinder.root("d"));
    }

    /**
     * Tests that the root of a non-existent box ends up with a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void testRoot2()
    {
        setup3();
        unionFinder.root("z");
    }

    /**
     * Tests that the union finder checks and finds roots properly.
     */
    @Test
    public void rootCheckedTest()
    {
        setup2();
        HashMap<String, String> parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            assertEquals("The value should be itself.", box, parents.get(box));
            assertEquals("The root of the key should be itself.", box, unionFinder.rootChecked(box));
        }
        // At the beginning, there are only 4 single boxes: a, b c and d. Boxes a and b will be merged.
        unionFinder.merge("a", "b");
        assertEquals("The root should be itself.", "a", unionFinder.rootChecked("a"));
        assertEquals("The root should be a.", "a", unionFinder.rootChecked("b"));
        assertEquals("The root should be itself.", "c", unionFinder.rootChecked("c"));
        assertEquals("The root should be itself.", "d", unionFinder.rootChecked("d"));
        // Boxes c and d will be merged.
        unionFinder.merge("c", "d");
        assertEquals("The root should be itself.", "a", unionFinder.rootChecked("a"));
        assertEquals("The root should be a.", "a", unionFinder.rootChecked("b"));
        assertEquals("The root should be itself.", "c", unionFinder.rootChecked("c"));
        assertEquals("The root should be c.", "c", unionFinder.rootChecked("d"));
        // Finally, boxes c and d are merged.
        unionFinder.merge("c", "b");
        assertEquals("The root should be c.", "c", unionFinder.rootChecked("a"));
        assertEquals("The root should be c.", "c", unionFinder.rootChecked("b"));
        assertEquals("The root should be itself.", "c", unionFinder.rootChecked("c"));
        assertEquals("The root should be c.", "c", unionFinder.rootChecked("d"));
        // Access to non-existent boxes should return null.
        assertNull("The root should be null since it doesn't exist.", unionFinder.rootChecked("z"));
        assertNull("The root should be null since it doesn't exist.", unionFinder.rootChecked("x"));
        assertNull("The root should be null since it doesn't exist.", unionFinder.rootChecked("hi"));
    }

    /**
     * Tests that the merge function works properly.
     */
    @Test
    public void mergeTest1()
    {
        setup3();
        // Boxes y and x will be added.
        unionFinder.add("y"); unionFinder.add("x");
        assertEquals("The number of boxes should be 10.", 10, unionFinder.totalRoots());
        assertEquals("The number of boxes should be 10.", 10, unionFinder.parents().size());
        HashMap<String, String> parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            assertEquals("The value should be itself.", box, parents.get(box));
            assertEquals("The root should be itself.", box, unionFinder.root(box));
            assertEquals("The size should be 1.", 1, unionFinder.size(box));
        }
        // Now, boxes a and b are merged, c and d, and x and y.
        unionFinder.merge("a", "b"); unionFinder.merge("c", "d"); unionFinder.merge("x", "y");
        assertEquals("The number of boxes should be 7.", 7, unionFinder.totalRoots());
        assertEquals("The number of boxes should be 10.", 10, unionFinder.parents().size());
        // The root of b should be a, the root of d should be c and the root of y should be x.
        assertEquals("The root should be a.", "a", unionFinder.root("b"));
        assertEquals("The root should be c.", "c", unionFinder.root("d"));
        assertEquals("The root should be x.", "x", unionFinder.root("y"));
        // The other boxes should be themselves as root. The size of super-boxes a-b, c-d and x-y should be 2. The rest, 1.
        parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            switch (box)
            {
                case "b":
                case "d":
                case "y":
                    assertEquals("The size should be 2.", 2, unionFinder.size(box));
                    break;
                case "a":
                case "c":
                case "x":
                    assertEquals("The size should be 2.", 2, unionFinder.size(box));
                    assertEquals("The root should be itself.", box, unionFinder.root(box));
                    assertEquals("The value should be itself.", box, parents.get(box));
                    break;
                default:
                    assertEquals("The root should be itself.", box, unionFinder.root(box));
                    assertEquals("The value should be itself.", box, parents.get(box));
                    assertEquals("The size should be 1.", 1, unionFinder.size(box));
            }
        }
        // Now, super-boxes c-d and x-y will be merged.
        unionFinder.merge("x", "d");
        assertEquals("The number of boxes should be 6.", 6, unionFinder.totalRoots());
        assertEquals("The number of boxes should be 10.", 10, unionFinder.parents().size());
        // The root of c, d and y should be x.
        assertEquals("The root should be x.", "x", unionFinder.root("c"));
        assertEquals("The root should be x.", "x", unionFinder.root("d"));
        assertEquals("The root should be x.", "x", unionFinder.root("y"));
        // The root of b should still be a.
        assertEquals("The root should be a.", "a", unionFinder.root("b"));
        // The other boxes should be themselves as root. The size of super-boxes c-d-x-y and a-b is 4 and 2, respectively. The rest, 1.
        parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            switch (box)
            {
                case "b":
                    assertEquals("The size should be 2.", 2, unionFinder.size(box));
                    break;
                case "a":
                    assertEquals("The size should be 2.", 2, unionFinder.size(box));
                    assertEquals("The root should be itself.", box, unionFinder.root(box));
                    assertEquals("The value should be itself.", box, parents.get(box));
                    break;
                case "c":
                case "d":
                case "y":
                    assertEquals("The size should be 4.", 4, unionFinder.size(box));
                    break;
                case "x":
                    assertEquals("The size should be 4.", 4, unionFinder.size(box));
                    assertEquals("The root should be itself.", box, unionFinder.root(box));
                    assertEquals("The value should be itself.", box, parents.get(box));
                    break;
                default:
                    assertEquals("The root should be itself.", box, unionFinder.root(box));
                    assertEquals("The value should be itself.", box, parents.get(box));
                    assertEquals("The size should be 1.", 1, unionFinder.size(box));
            }
        }
    }

    /**
     * Tests that the merge of two non-existent boxes ends up with a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void mergeTest2()
    {
        setup2();
        unionFinder.merge("z", "y");
    }

    /**
     * Tests that the union finder checks and merges boxes properly.
     */
    @Test
    public void mergeCheckedTest()
    {
        setup3();
        // Boxes y and x will be added.
        unionFinder.add("y"); unionFinder.add("x");
        assertEquals("The number of boxes should be 10.", 10, unionFinder.totalRoots());
        assertEquals("The number of boxes should be 10.", 10, unionFinder.parents().size());
        HashMap<String, String> parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            assertEquals("The root should be itself.", box, unionFinder.root(box));
            assertEquals("The value should be itself.", box, parents.get(box));
            assertEquals("The size should be 1.", 1, unionFinder.size(box));
        }
        // Now, boxes a and b are merged, c and d, and x and y.
        unionFinder.mergeChecked("a", "b"); unionFinder.mergeChecked("c", "d"); unionFinder.mergeChecked("x", "y");
        assertEquals("The number of boxes should be 7.", 7, unionFinder.totalRoots());
        assertEquals("The number of boxes should be 10.", 10, unionFinder.parents().size());
        // The root of b should be a, the root of d should be c and the root of y should be x.
        assertEquals("The root should be a.", "a", unionFinder.root("b"));
        assertEquals("The root should be c.", "c", unionFinder.root("d"));
        assertEquals("The root should be x.", "x", unionFinder.root("y"));
        // The other boxes should be themselves as root. The size of super-boxes a-b, c-d and x-y should be 2. The rest, 1.
        parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            switch (box)
            {
                case "b":
                case "d":
                case "y":
                    assertEquals("The size should be 2.", 2, unionFinder.size(box));
                    break;
                case "a":
                case "c":
                case "x":
                    assertEquals("The size should be 2.", 2, unionFinder.size(box));
                    assertEquals("The root should be itself.", box, unionFinder.root(box));
                    assertEquals("The value should be itself.", box, parents.get(box));
                    break;
                default:
                    assertEquals("The root should be itself.", box, unionFinder.root(box));
                    assertEquals("The value should be itself.", box, parents.get(box));
                    assertEquals("The size should be 1.", 1, unionFinder.size(box));
            }
        }
        // Now, super-boxes c-d and x-y will be merged.
        unionFinder.mergeChecked("x", "d");
        // Non-existent boxes s and t, u and w will be merged. Nothing should change.
        unionFinder.mergeChecked("s", "t"); unionFinder.mergeChecked("t", "u");
        assertEquals("The number of boxes should be 6.", 6, unionFinder.totalRoots());
        assertEquals("The number of boxes should be 10.", 10, unionFinder.parents().size());
        // The root of c, d and y should be x.
        assertEquals("The root should be x.", "x", unionFinder.root("c"));
        assertEquals("The root should be x.", "x", unionFinder.root("d"));
        assertEquals("The root should be x.", "x", unionFinder.root("y"));
        // The root of b should still be a.
        assertEquals("The root should be a.", "a", unionFinder.root("b"));
        // The other boxes should be themselves as root. The size of super-boxes c-d-x-y and a-b is 4 and 2, respectively. The rest, 1.
        parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            switch (box)
            {
                case "b":
                    assertEquals("The size should be 2.", 2, unionFinder.size(box));
                    break;
                case "a":
                    assertEquals("The size should be 2.", 2, unionFinder.size(box));
                    assertEquals("The root should be itself.", box, unionFinder.root(box));
                    assertEquals("The value should be itself.", box, parents.get(box));
                    break;
                case "c":
                case "d":
                case "y":
                    assertEquals("The size should be 4.", 4, unionFinder.size(box));
                    break;
                case "x":
                    assertEquals("The size should be 4.", 4, unionFinder.size(box));
                    assertEquals("The root should be itself.", box, unionFinder.root(box));
                    assertEquals("The value should be itself.", box, parents.get(box));
                    break;
                default:
                    assertEquals("The root should be itself.", box, unionFinder.root(box));
                    assertEquals("The value should be itself.", box, parents.get(box));
                    assertEquals("The size should be 1.", 1, unionFinder.size(box));
            }
        }
    }

    /**
     * Tests that the union finder keeps track of box sizes properly.
     */
    @Test
    public void sizeTest1()
    {
        setup2();
        // Boxes x, y and z will be added.
        unionFinder.add("x"); unionFinder.add("y"); unionFinder.add("z");
        // There should be 7 boxes: a, b, c, d, x, y and z.
        assertEquals("The number of boxes should be 7.", 7, unionFinder.totalRoots());
        // The size of all boxes should be 1.
        HashMap<String, String> parents = unionFinder.parents();
        for(String box : parents.keySet())
        { assertEquals("The size should be 1.", 1, unionFinder.size(box)); }
        // Now boxes a, b and c will me merged.
        unionFinder.merge("a", "b"); unionFinder.merge("b", "c");
        assertEquals("The number of boxes should be 5.", 5, unionFinder.totalRoots());
        // The size of super-box a-b-c should be 3. The rest, 1.
        parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            switch(box)
            {
                case "a":
                case "b":
                case "c":
                    assertEquals("The size should be 3.", 3, unionFinder.size(box));
                    break;
                default:
                    assertEquals("The size should be 1.", 1, unionFinder.size(box));
            }
        }
        // Boxes y and x will me merged.
        unionFinder.merge("y", "x");
        assertEquals("The number of boxes should be 4.", 4, unionFinder.totalRoots());
        // The size of super-boxes a-b-c and y-x should be 3 and 2, respectively. The rest, 1.
        parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            switch(box)
            {
                case "a":
                case "b":
                case "c":
                    assertEquals("The size should be 3.", 3, unionFinder.size(box));
                    break;
                case "x":
                case "y":
                    assertEquals("The size should be 2.", 2, unionFinder.size(box));
                    break;
                default:
                    assertEquals("The size should be 1.", 1, unionFinder.size(box));
            }
        }
        // Super boxes a-b-c and y-x will be merged.
        unionFinder.merge("b", "y");
        assertEquals("The number of boxes should be 3.", 3, unionFinder.totalRoots());
        // The size of super box a-b-c-y-x should be 5. The rest, 1.
        parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            switch(box)
            {
                case "a":
                case "b":
                case "c":
                case "x":
                case "y":
                    assertEquals("The size should be 5.", 5, unionFinder.size(box));
                    break;
                default:
                    assertEquals("The size should be 1.", 1, unionFinder.size(box));
            }
        }
        // Last boxes will be merged: z and d, with super box a-b-c-d-y-x.
        unionFinder.merge("z", "d"); unionFinder.merge("z", "c");
        assertEquals("The number of boxes should be 1.", 1, unionFinder.totalRoots());
    }

    /**
     * Tests that the size of a non-existent box ends up with a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void sizeTest2()
    {
        setup2();
        unionFinder.size("z");
    }

    /**
     * Tests that the union finder checks and returns sizes properly.
     */
    @Test
    public void sizeCheckedTest()
    {
        setup2();
        // Boxes x, y and z will be added.
        unionFinder.add("x"); unionFinder.add("y"); unionFinder.add("z");
        // There should be 7 boxes: a, b, c, d, x, y and z.
        assertEquals("The number of boxes should be 7.", 7, unionFinder.totalRoots());
        // The size of all boxes should be 1.
        HashMap<String, String> parents = unionFinder.parents();
        for(String box : parents.keySet())
        { assertEquals("The size should be 1.", (Integer) 1, unionFinder.sizeChecked(box)); }
        // Now boxes a, b and c will me merged.
        unionFinder.merge("a", "b"); unionFinder.merge("b", "c");
        assertEquals("The number of boxes should be 5.", 5, unionFinder.totalRoots());
        // The size of super-box a-b-c should be 3. The rest, 1.
        parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            switch(box)
            {
                case "a":
                case "b":
                case "c":
                    assertEquals("The size should be 3.", (Integer) 3, unionFinder.sizeChecked(box));
                    break;
                default:
                    assertEquals("The size should be 1.", (Integer) 1, unionFinder.sizeChecked(box));
            }
        }
        // Boxes y and x will me merged.
        unionFinder.merge("y", "x");
        assertEquals("The number of boxes should be 4.", 4, unionFinder.totalRoots());
        // The size of super-boxes a-b-c and y-x should be 3 and 2, respectively. The rest, 1.
        parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            switch(box)
            {
                case "a":
                case "b":
                case "c":
                    assertEquals("The size should be 3.", (Integer) 3, unionFinder.sizeChecked(box));
                    break;
                case "x":
                case "y":
                    assertEquals("The size should be 2.", (Integer) 2, unionFinder.sizeChecked(box));
                    break;
                default:
                    assertEquals("The size should be 1.", (Integer) 1, unionFinder.sizeChecked(box));
            }
        }
        // Super boxes a-b-c and y-x will be merged.
        unionFinder.merge("b", "y");
        assertEquals("The number of boxes should be 3.", 3, unionFinder.totalRoots());
        // The size of super box a-b-c-y-x should be 5. The rest, 1.
        parents = unionFinder.parents();
        for(String box : parents.keySet())
        {
            switch(box)
            {
                case "a":
                case "b":
                case "c":
                case "x":
                case "y":
                    assertEquals("The size should be 5.", (Integer) 5, unionFinder.sizeChecked(box));
                    break;
                default:
                    assertEquals("The size should be 1.", (Integer) 1, unionFinder.sizeChecked(box));
            }
        }
        // Last boxes will be merged: z and d, with super box a-b-c-d-y-x.
        unionFinder.merge("z", "d"); unionFinder.merge("z", "c");
        assertEquals("The number of boxes should be 1.", 1, unionFinder.totalRoots());
        // Size of non-existent boxes should return null.
        assertNull("The result should be null.", unionFinder.sizeChecked("hello"));
        assertNull("The result should be null.", unionFinder.sizeChecked("m"));
        assertNull("The result should be null.", unionFinder.sizeChecked("123"));
    }
}