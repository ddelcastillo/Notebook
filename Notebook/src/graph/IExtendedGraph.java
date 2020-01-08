// @formatter:off

package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * The API for an extendable unweighted generic graph.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 6/1/20.
 */
public interface IExtendedGraph<T> extends IGraph
{
    /**
     * Doesn't check if pVertex is not {@code null} or exists.
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertices of the given vertex.
     */
    Collection<T> adjacent(T pVertex);

    /**
     * @return Collection of collections corresponding to the adjacent vertices of each vertex.
     */
    Collection<Collection<T>> adjacent();

    /**
     * @return The HashMap containing each vertex with its assigned numerical value from 0 to V-1
     */
    HashMap<T, Integer> keyToNumber();

    /**
     * @return The HashMap containing the numerical value from 0 to V-1 assigned to each vertex.
     */
    HashMap<Integer, T> numberToKey();

    /**
     * Doesn't check if pVertex is not {@code null} or exists.
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertices of the given vertex.
     */
    Collection<Integer> adjacentNumber(T pVertex);

    /**
     * @return Collection of collections corresponding to the adjacent vertices of each vertex.
     */
    ArrayList<ArrayList<Integer>> adjacentNumber();
}