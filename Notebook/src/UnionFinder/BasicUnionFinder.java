// @formatter:off
package UnionFinder;

/**
 * Class that represents a simple numerical union finder for N boxes labeled from 0 to N-1.
 */
public class BasicUnionFinder implements IUnionFinder
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

    // Constructor

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
        this.par = new int[pUnionFinder.numBoxes];
        System.arraycopy(pUnionFinder.par, 0, this.par, 0, pUnionFinder.numBoxes);
    }

    // Methods

    /**
     * Finds the root of the given box.
     * @param x The box.
     * @return The root of the box.
     */
    public int root(int x)
    { return (par[x] < 0 ? x : (par[x] = root(par[x]))); }

    /**
     * Merges the two boxes such that the box with fewer items is placed in the other box.
     * The box with the fewer items will then contain the index to it's root.
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

    // Extra methods

    /**
     * Returns either the size of the box if it's not connected or the size of the union if it is.
     * @param x The box.
     * @return The size of the box.
     */
    public int size(int x)
    { return -par[root(x)]; }

    /**
     * @return The number of boxes that are not in union (including super-boxes).
     */
    public int totalRoots()
    { return numBoxes; }

    /**
     * @return The array of parents.
     */
    public int[] parents()
    { return par; }

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
