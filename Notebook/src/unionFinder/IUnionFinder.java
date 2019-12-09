package unionFinder;

/**
 * The API for the generic union finder.
 */
public interface IUnionFinder<T>
{
    /**
     * Finds the root of the given box.
     * @param pBox The box.
     * @return The root of the box.
     */
    T root(T pBox);

    /**
     * Merges the two boxes such that the box with fewer items is placed in the other box.
     * The box with the fewer items will then contain the index to it's root.
     * @param pBox1 The first box.
     * @param pBox2 The second box.
     */
    void merge(T pBox1, T pBox2);
}
