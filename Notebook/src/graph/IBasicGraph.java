// @formatter:off

package graph;

import java.util.Collection;

/**
 * The API for a numerical graph.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 6/1/20.
 */
public interface IBasicGraph extends IGraph
{
    /**
     * Adds an edge between two vertices. Doesn't check for duplicates and allows self-cycles.
     * If it is the case that the edge is a self-cycle, it will add it once.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    void addEdge(int pVertex1, int pVertex2);

    /**
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertices of the given vertex.
     */
    Collection<Integer> adjacent(int pVertex);

    /**
     * @return Array of collections corresponding to the adjacent vertices of each vertex.
     */
    Collection<Integer>[] adjacent();
}