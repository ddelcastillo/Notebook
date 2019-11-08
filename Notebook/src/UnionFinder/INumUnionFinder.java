// @formatter:off
package UnionFinder;

/**
 * The API for the numerical union finder.
 */
public interface INumUnionFinder
{
    // Basic required methods
        /**
     * Finds the root of the given box.
     * @param x The box.
     * @return The root of the box.
     */
    int root(int x);

        /**
     * Merges the two boxes such that the box with fewer items is placed in the other box.
     * The box with the fewer items will then contain the index to it's root.
     * @param x The first box.
     * @param y The second box.
     */
    void merge(int x, int y);
}
