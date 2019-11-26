// @formatter:off
package UnionFinder;

import org.junit.Test;

import java.awt.*;import java.util.ArrayList;import java.util.Collection;import java.util.Hashtable;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Tests that the ExpandableNumUnionFinder class works properly.
 */
public class ExpandableNumUnionFinderTest
{
    // Attributes

    /**
     * The union finder.
     */
    private ExpandableNumUnionFinder unionFinder;

    // Setups

    /**
     * Creates an empty expandable union finder.
     */
    private void setup1()
    { unionFinder = new ExpandableNumUnionFinder(); }

    /**
     * Creates an expandable union finder with boxes labeled from 0 to 9.
     */
    private void setup2()
    { unionFinder = new ExpandableNumUnionFinder(10); }

    /**
     * Creates an expandable union finder with boxes whose labels are 2, 3, 5 and 7.
     */
    private void setup3()
    {
        int[] indexes = {2, 3, 5, 7};
        unionFinder = new ExpandableNumUnionFinder(indexes);
    }

    // Tests

    // TODO Document step-bys-step tests.

    /**
     * Tests that the empty union finder is correctly initialized.
     */
    @Test
    public void initializationTest1()
    {
        setup1();
        // The union shouldn't be null and should have 0 parents and boxes.
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The number of parents should be 0.", 0, unionFinder.parents().size());
        assertEquals("The number of boxes should be 0.", 0, unionFinder.totalRoots());
    }

    /**
     * Tests that the union finder with boxes labeled from 0 to 9 is correctly initialized.
     */
    @Test
    public void initializationTest2()
    {
        setup2();
        // The union shouldn't be null and should have 10 parents (10 boxes).
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The number of parents should be 10.", 10, unionFinder.parents().size());
        assertEquals("The number of boxes should be 10.", 10, unionFinder.totalRoots());
        // At the beginning, every box should be its own parent.
        Hashtable<Integer, Integer> parents = unionFinder.parents();
        for(int box = 0; box < parents.size(); ++box)
            assertEquals("The parent should be itself.", (int) parents.get(box), box);
    }

    /**
     * Tests that the union finder with boxes whose labels are 2, 3, 5 and 7 is correctly initialized.
     */
    @Test
    public void initializationTest3()
    {
        setup3();
        // The union shouldn't be null and should have 4 parents (4 boxes).
        int[] boxes = {2, 3, 5, 7};
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The number of parents should be 4.", 4, unionFinder.parents().size());
        assertEquals("The number of boxes should be 4.", 4, unionFinder.totalRoots());
        // At the beginning, every box should be its own parent.
        Hashtable<Integer, Integer> parents = unionFinder.parents();
        for(int box : boxes)
            assertEquals("The parent should be itself.", box, (int) parents.get(box));
    }

    /**
     * Tests that the union finder with boxes whose labels are 2, 3, 5 and 7 is correctly initialized.
     */
    @Test
    public void initializationTest4()
    {
        int[] boxes = {2, 3, 5, 7};
        ArrayList<Integer> array = new ArrayList<>();
        for(int box : boxes)
            array.add(box);
        unionFinder = new ExpandableNumUnionFinder(array);
        // The union shouldn't be null and should have 4 parents (4 boxes).
        assertNotNull("The union finder shouldn't be null.", unionFinder);
        assertEquals("The number of parents should be 4.", 4, unionFinder.parents().size());
        assertEquals("The number of boxes should be 4.", 4, unionFinder.totalRoots());
        // At the beginning, every box should be its own parent.
        Hashtable<Integer, Integer> parents = unionFinder.parents();
        for(int box : boxes)
            assertEquals("The parent should be itself.", box, (int) parents.get(box));
    }

    /**
     * Tests that the union finder is correctly initialized as a copy of a given expandable union finder.
     */
    @Test
    public void initializationTest5()
    {
        setup3();
        int[] boxes = {2, 3, 5, 7};
        ExpandableNumUnionFinder newUnionFinder = new ExpandableNumUnionFinder(unionFinder);
        // The union shouldn't be null and should have 4 parents (4 boxes).
        assertNotNull("The union finder shouldn't be null.", newUnionFinder);
        assertEquals("The number of parents should be 4.", 4, newUnionFinder.parents().size());
        assertEquals("The number of boxes should be 4.", 4, newUnionFinder.totalRoots());
        // At the beginning, every box should be its own parent.
        Hashtable<Integer, Integer> parents = newUnionFinder.parents();
        for(int box : boxes)
            assertEquals("The parent should be itself.", box, (int) parents.get(box));
    }

    /**
     * Tests that an expandable union finder given stored information is correctly initialized.
     */
    @Test
    public void initializationTest6()
    {
        setup2();
        // Boxes 1 and 2, and 8 and 9 are merged.
        unionFinder.merge(1, 2);
        unionFinder.merge(8, 9);
        ExpandableNumUnionFinder newUnionFinder = new ExpandableNumUnionFinder(unionFinder);
        // Since boxes 1 and 2, and 8 and 9 are merged, there should be 8 boxes but parents of size 10.
        // The union shouldn't be null.
        assertNotNull("The union finder shouldn't be null.", newUnionFinder);
        assertEquals("The number of parents should be 10.", 10, newUnionFinder.parents().size());
        assertEquals("The number of boxes should be 8.", 8, newUnionFinder.totalRoots());
        // The roots should  be stored correctly.
        assertEquals("The root should be itself.", 1, newUnionFinder.root(1));
        assertEquals("The root should be 1.", 1, newUnionFinder.root(2));
        assertEquals("The root should be itself.", 8, newUnionFinder.root(8));
        assertEquals("The root should be 8.", 8, newUnionFinder.root(9));
        Hashtable<Integer, Integer> parents = newUnionFinder.parents();
        // The parent of 2 should be 1 and the parent of 9 should be 8. The rest, the parent should be themselves.
        for(Integer box : parents.keySet())
        {
            switch(box)
            {
                case 2:
                    assertEquals("The parent should be 1.", 1, (int) parents.get(box));
                    break;
                case 9:
                    assertEquals("The parent should be 8.", 8, (int) parents.get(box));
                    break;
                default:
                    assertEquals("The parent should be itself.", box, parents.get(box));
            }
        }
    }

    /**
     * Tests that the union finder adds boxes properly. Displays how the method might break the box counter.
     */
    @Test
    public void addTest1()
    {
        setup1();
        // Three boxes will be added: box 1, 2 and 5.
        unionFinder.add(1); unionFinder.add(2); unionFinder.add(5);
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 3.", 3, unionFinder.totalRoots());
        Hashtable<Integer, Integer> parents = unionFinder.parents();
        Iterator<Integer> iterator = parents.keySet().iterator();
        int box;
        // For every box, the parent should be themselves.
        while(iterator.hasNext())
        {
            box = iterator.next();
            assertEquals("The parent should be itself.", box, (int) parents.get(box));
            assertEquals("The root of the box should be itself.", box, unionFinder.root(box));
        }
        // Tests that repeated boxes are added. Now box 1's size is restarted to 1 (unchanged, for this test)
        // and box counter's are now broken (it marks 4 boxes when actually, there's still 3).
        unionFinder.add(1);
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 4.", 4, unionFinder.totalRoots());
        parents = unionFinder.parents();
        iterator = parents.keySet().iterator();
        // Every node should still be the same.
        while(iterator.hasNext())
        {
            box = iterator.next();
            assertEquals("The parent should be itself.", box, (int) parents.get(box));
            assertEquals("The root of the box should be itself.", unionFinder.root(box), box);
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
        // Box 10 will be added.
        unionFinder.add(10);
        assertEquals("The number of parents should be 1.", 1, unionFinder.parents().size());
        assertEquals("The number of boxes should be 1.", 1, unionFinder.totalRoots());
        // Box 20 will be added.
        unionFinder.add(20);
        assertEquals("The number of parents should be 2.", 2, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        // Box 30 will be added.
        unionFinder.add(30);
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 3.", 3, unionFinder.totalRoots());
        // Boxes 10 and 20 will be merged. Now, there should be 2 boxes.
        unionFinder.merge(10, 20);
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", 10, unionFinder.root(10));
        assertEquals("The root of the box should be 10.", 10, unionFinder.root(20));
        assertEquals("The root of the box should be itself.", 30, unionFinder.root(30));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(10));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(20));
        assertEquals("The size of the union should be 1.", 1, unionFinder.size(30));
        // Now box 10 will be added again, which is a mistake. However, the number of boxes should
        // go up by a unit, the root of box 10 should be itself and the root of 20 should still be 10.
        // The number of parents doesn't change since there isn't a new box in the union.
        unionFinder.add(10);
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 3.", 3, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", 10, unionFinder.root(10));
        assertEquals("The root of the box should be 10.", 10, unionFinder.root(20));
        assertEquals("The root of the box should be itself.", 30, unionFinder.root(30));
        // Since the root of 20 is 10, and the size of 10 is 1, then the size given by box 20 is 1.
        assertEquals("The size of the union should be 1.", 1, unionFinder.size(10));
        assertEquals("The size of the union should be 1.", 1, unionFinder.size(20));
        assertEquals("The size of the union should be 1.", 1, unionFinder.size(30));
        // Now boxes 20 and 30 will be merged. In theory, there should be a single box. However,
        // this is not the case due to the error introduced before. Therefore, the number of boxes is 2.
        unionFinder.merge(20, 30);
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", 10, unionFinder.root(10));
        // The root of 20 is still 10.
        assertEquals("The root of the box should be 10.", 10, unionFinder.root(20));
        assertEquals("The root of the box should be 10.", 10, unionFinder.root(30));
        // Since the root of 20 is 10, and the size of 10 is 1, and 20 was merged with 30 and 20
        // which pointed to box 10, then box 10 will undergo a size increment of 1.
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(10));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(20));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(30));
        // Now boxes 10 and 30 will be merged. Nothing should change however broken the numbers are.
        unionFinder.merge(10, 30);
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", 10, unionFinder.root(10));
        // The root of 20 is still 10.
        assertEquals("The root of the box should be 10.", 10, unionFinder.root(20));
        assertEquals("The root of the box should be 10.", 10, unionFinder.root(30));
        // The broken size of the union should remain unchanged.
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(10));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(20));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(30));
    }

    /**
     * Tests that the union finder adds and checks boxes properly.
     */
    @Test
    public void addCheckedTest()
    {
        setup1();
        // Box 10 will be added.
        unionFinder.add(10);
        assertEquals("The number of parents should be 1.", 1, unionFinder.parents().size());
        assertEquals("The number of boxes should be 1.", 1, unionFinder.totalRoots());
        // Box 20 will be added.
        unionFinder.add(20);
        assertEquals("The number of parents should be 2.", 2, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        // Box 30 will be added.
        unionFinder.add(30);
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 3.", 3, unionFinder.totalRoots());
        // Boxes 10 and 20 will be merged. Now, there should be 2 boxes.
        unionFinder.merge(10, 20);
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", 10, unionFinder.root(10));
        assertEquals("The root of the box should be 10.", 10, unionFinder.root(20));
        assertEquals("The root of the box should be itself.", 30, unionFinder.root(30));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(10));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(20));
        assertEquals("The size of the union should be 1.", 1, unionFinder.size(30));
        // Now box 10 will be added again. Nothing should happen, as bos 10 already exists.
        unionFinder.addChecked(10);
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", 10, unionFinder.root(10));
        assertEquals("The root of the box should be 10.", 10, unionFinder.root(20));
        assertEquals("The root of the box should be itself.", 30, unionFinder.root(30));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(10));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(20));
        assertEquals("The size of the union should be 1.", 1, unionFinder.size(30));
        // Boxes 10 and 20 will be added and boxes 10 and 20 will be merged again. Nothing should happen.
        unionFinder.addChecked(10); unionFinder.addChecked(20); unionFinder.merge(10, 20);
        assertEquals("The number of parents should be 3.", 3, unionFinder.parents().size());
        assertEquals("The number of boxes should be 2.", 2, unionFinder.totalRoots());
        assertEquals("The root of the box should be itself.", 10, unionFinder.root(10));
        assertEquals("The root of the box should be 10.", 10, unionFinder.root(20));
        assertEquals("The root of the box should be itself.", 30, unionFinder.root(30));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(10));
        assertEquals("The size of the union should be 2.", 2, unionFinder.size(20));
        assertEquals("The size of the union should be 1.", 1, unionFinder.size(30));
    }

    // TODO fix parents.

    /**
     * Tests that the union finder finds and assigns roots properly.
     */
    @Test
    public void rootTest1()
    {
        setup1();
        // Boxes 10 and 30 will be added.
        unionFinder.add(10); unionFinder.add(30);
        // The root of both boxes should be themselves.
        assertEquals("The root of the box should be itself.", 10, unionFinder.root(10));
        assertEquals("The root of the box should be itself.", 30, unionFinder.root(30));
        // Box 20 will be added.
        unionFinder.add(20);
        // The roof of the three boxes should be themselves.
        assertEquals("The root of the box should be itself.", 10, unionFinder.root(10));
        assertEquals("The root of the box should be itself.", 20, unionFinder.root(20));
        assertEquals("The root of the box should be itself.", 30, unionFinder.root(30));
        // Boxes 10 and 30 will be merged.
        unionFinder.merge(10, 30);
        // The root of boxes 10 and 20 should be themselves, but the root of 30 is now 10.
        assertEquals("The root of the box should be itself.", 10, unionFinder.root(10));
        assertEquals("The root of the box should be itself.", 20, unionFinder.root(20));
        assertEquals("The root of the box should be 10.", 10, unionFinder.root(30));
        // Boxes 30 and 20 will be merged.
        unionFinder.merge(30, 20);
        // Now, the root of 20 should be the same root as 30, i.e., 10. The root of 10 should still be itself.
        assertEquals("The root of the box should be itself.", 10, unionFinder.root(10));
        assertEquals("The root of the box should be 10.", 10, unionFinder.root(20));
        assertEquals("The root of the box should be 10.", 10, unionFinder.root(30));
    }

    /**
     * Tests that the root of a non-existent box ends up with a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void rootTest2()
    {
        setup2();
        unionFinder.root(200);
    }

    /**
     * Tests that the union finder checks and finds roots properly.
     */
    @Test
    public void rootCheckedTest()
    {
        setup2();
        Hashtable<Integer, Integer> par = unionFinder.parents();
        Iterator<Integer> iterator = par.keySet().iterator();
        int key;
        while(iterator.hasNext())
        {
            key = iterator.next();
            assertTrue("The table should contain the key.", par.containsKey(key));
            assertEquals("The value should be -1.", (int) par.get(key), -1);
            assertEquals("The root of the key should be itself.", (int) unionFinder.rootChecked(key), key);
        }
        // Access to non-existent boxes should return null:
        assertNull("The root should be null since it doesn't exist.", unionFinder.rootChecked(200));
        assertNull("The root should be null since it doesn't exist.", unionFinder.rootChecked(10));
        assertNull("The root should be null since it doesn't exist.", unionFinder.rootChecked(-200));
    }

    /**
     * Tests that the merge function works properly.
     */
    @Test
    public void mergeTest1()
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
        // A box will be added: 100.
        unionFinder.add(100);
        assertEquals("The size should be 13.", unionFinder.parents().size(), 13);
        assertEquals("The number of boxes should be 6.", unionFinder.totalRoots(), 6);
        // Now, boxes merged are: 100 and 10.
        unionFinder.merge(100, 10);
        assertEquals("The size should be 5.", unionFinder.size(100), 5);
        assertEquals("The number of boxes should be 5.", unionFinder.totalRoots(), 5);
    }

    /**
     * Tests that the merge of two non-existent boxes ends up with a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void mergeTest2()
    {
        setup1();
        unionFinder.merge(10, 20);
    }

    /**
     * Tests that the union finder checks and merges boxes properly.
     */
    @Test
    public void mergeCheckedTest()
    {
        setup1();
        // A box 10 will be added:
        unionFinder.addChecked(10);
        assertEquals("The size should be 1.", unionFinder.parents().size(), 1);
        assertEquals("The number of boxes should be 1.", unionFinder.totalRoots(), 1);
        // Another box 20 will be added:
        unionFinder.addChecked(20);
        assertEquals("The size should be 2.", unionFinder.parents().size(), 2);
        assertEquals("The number of boxes should be 2.", unionFinder.totalRoots(), 2);
        // Another box 30 will be added:
        unionFinder.addChecked(30);
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 3.", unionFinder.totalRoots(), 3);
        // Boxes 10 and 20 will be merged. Now, there should be 2 boxes:
        unionFinder.mergeChecked(10, 20);
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 2.", unionFinder.totalRoots(), 2);
        assertEquals("The root of the box should be itself.", unionFinder.root(10), 10);
        assertEquals("The root of the box should be 10.", unionFinder.root(20), 10);
        assertEquals("The root of the box should be itself.", unionFinder.root(30), 30);
        assertEquals("The size of the union should be 2.", unionFinder.size(10), 2);
        assertEquals("The size of the union should be 2.", unionFinder.size(20), 2);
        assertEquals("The size of the union should be 1.", unionFinder.size(30), 1);
        // Now box 10 will be merged with non-existent box 100. Nothing should happen.
        unionFinder.mergeChecked(10, 100);
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 2.", unionFinder.totalRoots(), 2);
        assertEquals("The root of the box should be itself.", unionFinder.root(10), 10);
        assertEquals("The root of the box should be 10.", unionFinder.root(20), 10);
        assertEquals("The root of the box should be itself.", unionFinder.root(30), 30);
        assertEquals("The size of the union should be 2.", unionFinder.size(10), 2);
        assertEquals("The size of the union should be 2.", unionFinder.size(20), 2);
        assertEquals("The size of the union should be 1.", unionFinder.size(30), 1);
        // Boxes 10 and 20 will be merged again. Nothing should happen.
        unionFinder.mergeChecked(10, 20);
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 2.", unionFinder.totalRoots(), 2);
        assertEquals("The root of the box should be itself.", unionFinder.root(10), 10);
        assertEquals("The root of the box should be 10.", unionFinder.root(20), 10);
        assertEquals("The root of the box should be itself.", unionFinder.root(30), 30);
        assertEquals("The size of the union should be 2.", unionFinder.size(10), 2);
        assertEquals("The size of the union should be 2.", unionFinder.size(20), 2);
        assertEquals("The size of the union should be 1.", unionFinder.size(30), 1);
        // Merge of two non-existent boxes should result in nothing changing.
        unionFinder.mergeChecked(100, 200);
        assertEquals("The size should be 3.", unionFinder.parents().size(), 3);
        assertEquals("The number of boxes should be 2.", unionFinder.totalRoots(), 2);
        assertEquals("The root of the box should be itself.", unionFinder.root(10), 10);
        assertEquals("The root of the box should be 10.", unionFinder.root(20), 10);
        assertEquals("The root of the box should be itself.", unionFinder.root(30), 30);
        assertEquals("The size of the union should be 2.", unionFinder.size(10), 2);
        assertEquals("The size of the union should be 2.", unionFinder.size(20), 2);
        assertEquals("The size of the union should be 1.", unionFinder.size(30), 1);
    }

    /**
     * Tests that the union finder keeps track of box sizes properly.
     */
    @Test
    public void sizeTest1()
    {
        setup3();
        Hashtable<Integer, Integer> par = unionFinder.parents();
        Iterator<Integer> iterator = par.keySet().iterator();
        int key;
        while(iterator.hasNext())
        {
            key = iterator.next();
            assertEquals("The value should be 1.", unionFinder.size(key), 1);
        }
        unionFinder.mergeChecked(2, 5);
        assertEquals("The size should be 2.", unionFinder.size(2), 2);
        assertEquals("The size should be 1.", unionFinder.size(3), 1);
        assertEquals("The size should be 2.", unionFinder.size(5), 2);
        assertEquals("The size should be 1.", unionFinder.size(7), 1);
        unionFinder.mergeChecked(2, 7);
        assertEquals("The size should be 3.", unionFinder.size(2), 3);
        assertEquals("The size should be 1.", unionFinder.size(3), 1);
        assertEquals("The size should be 3.", unionFinder.size(5), 3);
        assertEquals("The size should be 3.", unionFinder.size(7), 3);
        unionFinder.mergeChecked(3, 7);
        assertEquals("The size should be 4.", unionFinder.size(2), 4);
        assertEquals("The size should be 4.", unionFinder.size(3), 4);
        assertEquals("The size should be 4.", unionFinder.size(5), 4);
        assertEquals("The size should be 4.", unionFinder.size(7), 4);
        unionFinder.addChecked(100);
        assertEquals("The size should be 4.", unionFinder.size(2), 4);
        assertEquals("The size should be 4.", unionFinder.size(3), 4);
        assertEquals("The size should be 4.", unionFinder.size(7), 4);
        assertEquals("The size should be 4.", unionFinder.size(5), 4);
        assertEquals("The size should be 1.", unionFinder.size(100), 1);
        unionFinder.mergeChecked(2, 100);
        assertEquals("The size should be 5.", unionFinder.size(2), 5);
        assertEquals("The size should be 5.", unionFinder.size(3), 5);
        assertEquals("The size should be 5.", unionFinder.size(7), 5);
        assertEquals("The size should be 5.", unionFinder.size(5), 5);
        assertEquals("The size should be 5.", unionFinder.size(100), 5);
    }

    /**
     * Tests that the size of a non-existent box ends up with a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void testSize2()
    {
        setup2();
        unionFinder.size(20);
    }

    /**
     * Tests that the union finder checks and returns sizes properly.
     */
    @Test
    public void testSizeChecked()
    {
        setup3();
        Hashtable<Integer, Integer> par = unionFinder.parents();
        Iterator<Integer> iterator = par.keySet().iterator();
        int key;
        while(iterator.hasNext())
        {
            key = iterator.next();
            assertEquals("The value should be 1.", (int) unionFinder.sizeChecked(key), 1);
        }
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
        // The size check of a non-existent box shouldn't change anything.
        assertNull("The box size shouldn't exist.", unionFinder.sizeChecked(200));
        assertNull("The box size shouldn't exist.", unionFinder.sizeChecked(10));
        assertNull("The box size shouldn't exist.", unionFinder.sizeChecked(-100));
    }
}