// @formatter:off
package unionFinder;

import util.Checked;

/**
 * Class that represents a simple numerical union finder for N boxes labeled from 0 to N-1.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 14/12/19.
 */
@Checked
(note = "Methods with the 'Checked' signature enforce additional checks to avoid errors and to\n" +
" ensure the structure's correctness in exchange of efficiency. For fastest results, use\n" +
" the non 'Checked' methods, however, these are liable to: IndexOutOfBounds exceptions.")
public class BasicNumUnionFinder implements INumUnionFinder
{
    // Attributes

    /**
     * The number of boxes.
     */
    private int numBoxes;

    /**
     * The array of values for each box.
     */
    private int[] par;

    // Constructors

    /**
     * Creates a BasicUnionFinder object with N boxes labeled from 0 to N-1.
     * @param N The number of boxes to add to the union finder.
     */
    public BasicNumUnionFinder(int N)
    {
        numBoxes = N;
        par = new int[N];
        for (int i = 0; i < N; ++i)
            par[i] = -1;
    }

    /**
     * Creates a BasicUnionFinder object copy of the given union finder.
     * @param pUnionFinder The union finder to copy.
     */
    public BasicNumUnionFinder(BasicNumUnionFinder pUnionFinder)
    {
        this.numBoxes = pUnionFinder.numBoxes;
        this.par = new int[pUnionFinder.par.length];
        System.arraycopy(pUnionFinder.par, 0, this.par, 0, pUnionFinder.par.length);
    }

    // Methods

    /**
     * Doesn't check if x is a valid box. For this, use rootChecked.
     * Finds the root of the given box.
     * @param x The box.
     * @return The root of the box.
     */
    public int root(int x)
    { return (par[x] < 0 ? x : (par[x] = root(par[x]))); }

    /**
     * Checks that x is a valid box.
     * Finds the root of the given box.
     * @param x The box.
     * @return The root of the box.
     */
    public Integer rootChecked(int x)
    { return (x >= 0 && x < par.length) ? root(x) : null; }

    /**
     * Doesn't check if x and y are valid boxes. For this, use mergeChecked.
     * Merges the two boxes such that the box with fewer items is placed in the other box.
     * The box with fewer items will then contain the index to it's root.
     * @param x The first box.
     * @param y The second box.
     */
    public void merge(int x, int y)
    {
        x = root(x);
        y = root(y);
        if (x == y) return;
        if (par[y] < par[x])
        {
            x += y;
            y = x - y;
            x -= y;
        }
        par[x] += par[y];
        par[y] = x;
        // Updates the number of boxes.
        --numBoxes;
    }

    /**
     * Checks that both boxes are valid.
     * Merges the two boxes such that the box with fewer items is placed in the other box.
     * The box with fewer items will then contain the index to it's root.
     * @param x The first box.
     * @param y The second box.
     */
    public void mergeChecked(int x, int y)
    {
        if(x >= 0 && y >= 0 && x < par.length && y < par.length)
            merge(x, y);
    }

    // Extra methods

    /**
     * Doesn't check that x is a valid box. For this, use sizeChecked.
     * Returns either the size of the box if it's not connected or the size of the union if it is.
     * @param x The box.
     * @return The size of the box.
     */
    public int size(int x)
    { return -par[root(x)]; }

    /**
     * Checks that x is a valid box.
     * Returns either the size of the box if it's not connected or the size of the union if it is.
     * @param x The box.
     * @return The size of the box.
     */
    public Integer sizeChecked(int x)
    { return (x >= 0 && x < par.length) ? size(x) : null; }

    /**
     * @return The number of boxes that are not in union (including super-boxes).
     */
    public int totalRoots()
    { return numBoxes; }

    /**
     * @return The array of parents.
     */
    public int[] parents()
    {
        int[] parents = new int[par.length];
        for(int i = 0; i < par.length; ++i)
        { parents[i] = root(i); }
        return parents;
    }

    /**
     * Transcripts the union finder's contents into a String.
     * @return The String with the union finder's contents.
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Size: " + par.length + "\n");
        for(int i = 0; i < par.length; ++i)
            sb.append(i).append(": ").append(par[i]).append("\n");
        return sb.toString();
    }
}