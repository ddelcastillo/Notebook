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
public interface IExtendedUnweightedGraph<T> extends IGraph
{
    /**
     * Adds a vertex to the graph. Doesn't check if the vertex is {@code null} or already exists.
     * @param pVertex Vertex to add to the graph.
     */
    void addVertex(T pVertex);

    /**
     * Adds a vertex to the graph. Doesn't check if the vertex is {@code null} or already exists.
     * @param pVertex Vertex to add to the graph.
     * @param pCapacityList The node's adjacency list capacity.
     */
    void addVertex(T pVertex, int pCapacityList);

    /**
     * Allows self-cycles and doesn't check if the vertices are {@code null} or if the edge already exists.
     * Adds an edge between two vertices. If it is the case that the edge is a self-cycle, it will add it once.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    void addEdge(T pVertex1, T pVertex2);

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