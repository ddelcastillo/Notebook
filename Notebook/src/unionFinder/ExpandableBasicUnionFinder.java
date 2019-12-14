// @formatter:off
package unionFinder;

import util.Checked;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class that represents an expandable numerical union finder that isn't
 * restrained by the box's number label, i.e., to any specific range.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 14/12/19.
 */
@Checked
(note = "Methods with the 'Checked' signature enforce additional checks to avoid errors and to " +
"ensure the structure's correctness in exchange of efficiency. For fastest results, use " +
"the non 'Checked' methods, however, these are liable to: NullPointer exceptions, box size " +
"mismatches and wrong tracking of the number of boxes.")
public class ExpandableBasicUnionFinder implements IBasicUnionFinder
{
    // Attributes

    /**
     * The number of boxes.
     */
    private int numBoxes;

    /**
     * The map of values for each box.
     */
    private HashMap<Integer, Integer> par;

    // Constructors

    /**
     * Creates an ExpandableUnionFinder object.
     */
    public ExpandableBasicUnionFinder()
    {
        numBoxes = 0;
        par = new HashMap<>();
    }

    /**
     * Creates an ExpandableUnionFinder object with N boxes labeled from 0 to N-1.
     * @param N The number of boxes to add to the union finder.
     */
    public ExpandableBasicUnionFinder(int N)
    {
        numBoxes = N;
        par = new HashMap<>(N);
        for(int i = 0; i < N; ++i)
            par.put(i, -1);
    }

    /**
     * Creates an ExpandableUnionFinder object with the given box indexes.
     * @param pBoxes The indexes of the boxes to add to the union finder.
     */
    public ExpandableBasicUnionFinder(int[] pBoxes)
    {
        numBoxes = pBoxes.length;
        par = new HashMap<>(pBoxes.length);
        for(int box : pBoxes)
            par.put(box, -1);
    }

    /**
     * Creates an ExpandableUnionFinder object with the given box indexes.
     * @param pBoxes The indexes of the boxes to add to the union finder.
     */
    public ExpandableBasicUnionFinder(Collection<Integer> pBoxes)
    {
        numBoxes = pBoxes.size();
        par = new HashMap<>(pBoxes.size());
        for(int box : pBoxes)
            par.put(box, -1);
    }

    /**
     * Creates an ExpandableUnionFinder object copy of the given union finder.
     * @param pUnionFinder The union finder to copy.
     */
    public ExpandableBasicUnionFinder(ExpandableBasicUnionFinder pUnionFinder)
    {
        this.numBoxes = pUnionFinder.numBoxes;
        this.par = new HashMap<>(pUnionFinder.numBoxes);
        Iterator<Integer> iterator = pUnionFinder.par.keySet().iterator();
        int key, value;
        while(iterator.hasNext())
        {
            key = iterator.next();
            value = pUnionFinder.par.get(key);
            this.par.put(key, value);
        }
    }

    // Methods

    /**
     * Doesn't check if x is a valid box. For this, use rootChecked.
     * Finds the root of the given box.
     * @param pBox The box.
     * @return The root of the box.
     */
    public int root(int pBox)
    {
        int y = par.get(pBox);
        if(y < 0)
            return pBox;
        else
        {
            y = root(par.get(pBox));
            par.put(pBox, y);
            return y;
        }
    }

    /**
     * Checks that x is a valid box.
     * Finds the root of the given box if it exists.
     * @param pBox The box.
     * @return The root of the box or {@code null} if the box doesn't exist.
     */
    public Integer rootChecked(int pBox)
    { return par.containsKey(pBox) ? root(pBox) : null; }

    /**
     * Doesn't check if boxes pBox1 and pBox2 exist. For this, use mergeChecked.
     * Merges the two boxes.
     * @param pBox1 The first box.
     * @param pBox2 The second box.
     */
    public void merge(int pBox1, int pBox2)
    {
        pBox1 = root(pBox1); pBox2 = root(pBox2);
        if(pBox1 == pBox2) return;
        if(par.get(pBox2) < par.get(pBox1))
        {
            pBox1 += pBox2;
            pBox2 = pBox1 - pBox2;
            pBox1 -= pBox2;
        }
        par.put(pBox1, par.get(pBox1) + par.get(pBox2));
        par.put(pBox2, pBox1);
        --numBoxes;
    }

    /**
     * Checks that both boxes pBox1 and pBox2 exist.
     * Merges the two boxes if they both exist.
     * @param pBox1 The first box.
     * @param pBox2 The second box.
     */
    public void mergeChecked(int pBox1, int pBox2)
    {
        if(par.containsKey(pBox1) && par.containsKey(pBox2))
            merge(pBox1, pBox2);
    }

    // Extra methods

    /**
     * Doesn't check if pBox already exists. For this, use addChecked.
     * Adds a box.
     * @param pBox The box to add.
     */
    public void add(int pBox)
    {
        par.put(pBox, -1);
        ++numBoxes;
    }

    /**
     * Checks if pBox already exists.
     * Adds a box if it doesn't exist.
     * @param pBox The box to add.
     */
    public void addChecked(int pBox)
    {
        if(!par.containsKey(pBox))
            add(pBox);
    }

    /**
     * Doesn't check if pBox exists. For this, use sizeChecked.
     * Returns either the size of the box if it's not connected or the size of the union if it is.
     * @param pBox The box.
     * @return The size of the box or the union it belongs to.
     */
    public int size(int pBox)
    { return -par.get(root(pBox)); }

    /**
     * Checks if pBox exists.
     * Returns either the size of the box if it's not connected, the size of the union if it is,
     * or {@code null} if the box doesn't exist.
     * @param pBox The box.
     * @return The size of the box or the union it belongs to, or {@code null} if the box doesn't exist.
     */
    public Integer sizeChecked(int pBox)
    { return par.containsKey(pBox) ? size(pBox) : null; }

    /**
     * @return The number of boxes that are not in union and super-boxes.
     */
    public int totalRoots()
    { return numBoxes; }

    /**
     * @return The HashMap with the parents of each box.
     */
    public HashMap<Integer, Integer> parents()
    {
        HashMap<Integer, Integer> parents = new HashMap<>(par.size());
        for(Integer box : par.keySet())
            parents.put(box, root(box));
        return parents;
    }

    /**
     * Transcripts the union finder's contents into a String.
     * @return The String with the union finder's contents.
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Size: " + par.size() + "\n");
        for(int i : par.keySet())
            sb.append(i).append(": ").append(par.get(i)).append("\n");
        return sb.toString();
    }
}
