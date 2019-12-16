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
public class BasicUnionFinder implements IBasicUnionFinder
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
    public BasicUnionFinder(int N)
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
    public BasicUnionFinder(BasicUnionFinder pUnionFinder)
    {
        this.numBoxes = pUnionFinder.numBoxes;
        this.par = new int[pUnionFinder.par.length];
        System.arraycopy(pUnionFinder.par, 0, this.par, 0, pUnionFinder.par.length);
    }

    // Methods

    /**
     * Doesn't check if pBox is a valid box. For this, use rootChecked.
     * Finds the root of the given box.
     * @param pBox The box.
     * @return The root of the box.
     */
    public int root(int pBox)
    { return (par[pBox] < 0 ? pBox : (par[pBox] = root(par[pBox]))); }

    /**
     * Checks that pBox is a valid box.
     * Finds the root of the given box if it exists.
     * @param pBox The box.
     * @return The root of the box or {@code null} if x is an invalid box.
     */
    public Integer rootChecked(int pBox)
    { return (pBox >= 0 && pBox < par.length) ? root(pBox) : null; }

    /**
     * Doesn't check if pBox1 and pBox2 are valid boxes. For this, use mergeChecked.
     * Merges the two boxes.
     * @param pBox1 The first box.
     * @param pBox2 The second box.
     */
    public void merge(int pBox1, int pBox2)
    {
        pBox1 = root(pBox1);
        pBox2 = root(pBox2);
        if (pBox1 == pBox2) return;
        if (par[pBox2] < par[pBox1])
        {
            pBox1 += pBox2;
            pBox2 = pBox1 - pBox2;
            pBox1 -= pBox2;
        }
        par[pBox1] += par[pBox2];
        par[pBox2] = pBox1;
        // Updates the number of boxes.
        --numBoxes;
    }

    /**
     * Checks that both boxes pBox1 and pBox2 are valid.
     * Merges the two boxes if they are both valid.
     * @param pBox1 The first box.
     * @param pBox2 The second box.
     */
    public void mergeChecked(int pBox1, int pBox2)
    {
        if(pBox1 >= 0 && pBox2 >= 0 && pBox1 < par.length && pBox2 < par.length)
            merge(pBox1, pBox2);
    }

    // Extra methods

    /**
     * Doesn't check that pBox is a valid box. For this, use sizeChecked.
     * Returns either the size of the box if it's not connected or the size of the union if it is.
     * @param pBox The box.
     * @return The size of the box or the union it belongs to.
     */
    public int size(int pBox)
    { return -par[root(pBox)]; }

    /**
     * Checks if pBox is a valid box.
     * Returns either the size of the box if it's not connected, the size of the union if it is,
     * or {@code null} if the box is invalid.
     * @param pBox The box.
     * @return TThe size of the box or the union it belongs to, or {@code null} if the box is invalid.
     */
    public Integer sizeChecked(int pBox)
    { return (pBox >= 0 && pBox < par.length) ? size(pBox) : null; }

    /**
     * @return The number of boxes that are not in union and super-boxes.
     */
    public int totalRoots()
    { return numBoxes; }

    /**
     * @return The array with the parents of each box.
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
