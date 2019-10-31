package Util;

/**
 * Class that represents a simple generic Pair than contains two items.
 * @param <T> The first item's class.
 * @param <E> The second item's class.
 */
public class Pair<T, E>
{
    // Attributes

    /**
     * The pair's first item.
     */
    public T first;

    /**
     * The pair's second item.
     */
    public E second;

    // Constructor

    /**
     * Creates a Pair object with the given items.
     * @param pFirst The pair's first item.
     * @param pSecond The pair's second item.
     */
    public Pair(T pFirst, E pSecond)
    { first = pFirst; second = pSecond; }
}
