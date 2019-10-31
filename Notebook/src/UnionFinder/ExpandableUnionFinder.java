// @formatter:off
package UnionFinder;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * Class that represents an expandable numerical union finder that isn't
 * restrained by the box's number label.
 */
public class ExpandableUnionFinder implements IUnionFinder
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

    // Constructor

    /**
     * Creates an ExpandableUnionFinder object.
     */
    public ExpandableUnionFinder()
    {
        numBoxes = 0;
        par = new Hashtable<>();
    }

    /**
     * Creates an ExpandableUnionFinder object with N boxes labeled from 0 to N-1.
     * @param N The number of boxes to add to the union finder.
     */
    public ExpandableUnionFinder(int N)
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
    public ExpandableUnionFinder(int[] pBoxes)
    {
        numBoxes = pBoxes.length;
        par = new Hashtable<>(pBoxes.length);
        for(int i : pBoxes)
            par.put(i, -1);
    }

    /**
     * Creates an ExpandableUnionFinder object copy of the given union finder.
     * @param pUnionFinder The union finder to copy.
     */
    public ExpandableUnionFinder(ExpandableUnionFinder pUnionFinder)
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
     * Merges the two boxes such that the box with fewer items is placed in the other box.
     * The box with the fewer items will then contain the index to it's root.
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

    // Extra methods

    /**
     * Adds a box if it doesn't exist.
     * @param x The number of the box.
     */
    public void add(int x)
    {
        if(!par.containsKey(x))
        {
            par.put(x, -1);
            ++numBoxes;
        }
    }

    /**
     * Returns either the size of the box if it's not connected or the size of the union if it is.
     * @param x The box.
     * @return The size of the box.
     */
    public int size(int x)
    { return -par.get(root(x)); }

    /**
     * @return The number of boxes that are not in union (including super-boxes).
     */
    public int totalRoots()
    { return numBoxes; }

    /**
     * @return The Hashtable with the parents.
     */
    public Hashtable<Integer, Integer> parents()
    { return par; }

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
