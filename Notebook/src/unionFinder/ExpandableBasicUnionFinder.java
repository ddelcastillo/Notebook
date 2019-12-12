// @formatter:off
package unionFinder;

import util.Checked;import java.util.Collection;import java.util.Hashtable;
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
     * The table of values for each box.
     */
    private Hashtable<Integer, Integer> par;

    // Constructors

    /**
     * Creates an ExpandableUnionFinder object.
     */
    public ExpandableBasicUnionFinder()
    {
        numBoxes = 0;
        par = new Hashtable<>();
    }

    /**
     * Creates an ExpandableUnionFinder object with N boxes labeled from 0 to N-1.
     * @param N The number of boxes to add to the union finder.
     */
    public ExpandableBasicUnionFinder(int N)
    {
        numBoxes = N;
        par = new Hashtable<>(N);
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
        par = new Hashtable<>(pBoxes.length);
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
        par = new Hashtable<>(pBoxes.size());
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
        this.par = new Hashtable<>(pUnionFinder.numBoxes);
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
     * @param x The box.
     * @return The root of the box.
     */
    public int root(int x)
    {
        int y = par.get(x);
        if(y < 0)
            return x;
        else
        {
            y = root(par.get(x));
            par.put(x, y);
            return y;
        }
    }

    /**
     * Finds the root of the given box. Checks that x is a valid box.
     * @param x The box.
     * @return The root of the box or null if the box doesn't exist.
     */
    public Integer rootChecked(int x)
    { return par.containsKey(x) ? root(x) : null; }

    /**
     * Doesn't check if x and y are valid boxes. For this, use mergeChecked.
     * Merges the two boxes such that the box with fewer items is placed in the other box.
     * The box with fewer items will then contain the index to it's root.
     * @param x The first box.
     * @param y The second box.
     */
    public void merge(int x, int y)
    {
        x = root(x); y = root(y);
        if(x == y) return;
        if(par.get(y) < par.get(x))
        {
            x += y;
            y = x - y;
            x -= y;
        }
        par.put(x, par.get(x) + par.get(y));
        par.put(y, x);
        // Updates the number of boxes.
        --numBoxes;
    }

    /**
     * Merges the two boxes such that the box with fewer items is placed in the other box.
     * The box with fewer items will then contain the index to it's root. Checks that both boxes are valid.
     * @param x The first box.
     * @param y The second box.
     */
    public void mergeChecked(int x, int y)
    {
        if(par.containsKey(x) && par.containsKey(y))
            merge(x, y);
    }

    // Extra methods

    /**
     * Doesn't check if the box already exists. For this, use addChecked.
     * Adds a box.
     * @param x The number of the box.
     */
    public void add(int x)
    {
        par.put(x, -1);
        ++numBoxes;
    }

    /**
     * Adds a box if it doesn't already exist.
     * @param x The number of the box.
     */
    public void addChecked(int x)
    {
        if(!par.containsKey(x))
            add(x);
    }

    /**
     * Doesn't check if the box exists. For this, use sizeChecked.
     * Returns either the size of the box if it's not connected or the size of the union if it is.
     * @param x The box.
     * @return The size of the box.
     */
    public int size(int x)
    { return -par.get(root(x)); }

    /**
     * Returns either the size of the box if it's not connected, the size of the union if it is,
     * or null if the box doesn't exist. Check if the box exists.
     * @param x The box.
     * @return The size of the box or null if the box doesn't exist.
     */
    public Integer sizeChecked(int x)
    { return par.containsKey(x) ? size(x) : null; }

    /**
     * @return The hashtable of parents.
     */
    public int totalRoots()
    { return numBoxes; }

    /**
     * @return The Hashtable with the parents. 0 if its itself, -x for the parent x.
     */
    public Hashtable<Integer, Integer> parents()
    {
        Hashtable<Integer, Integer> parents = new Hashtable<>(par.size());
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