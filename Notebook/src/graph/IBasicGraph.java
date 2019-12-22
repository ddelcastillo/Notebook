// @formatter:off

package graph;

import java.util.Collection;

/**
 * The API for a numerical graph.
 */
public interface IBasicGraph extends IGraph
{
    /**
     * Adds an edge between two vertexes. Doesn't check for duplicates and allows self-cycles.
     * If it is the case that the edge is a self-cycle, it will add it once.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    void addEdge(int pVertex1, int pVertex2);

    /**
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertexes of the given vertex.
     */
    Collection<Integer> adjacent(int pVertex);

    /**
     * @return Array of collections corresponding to the adjacent vertexes of each vertex.
     */
    Collection<Integer>[] adjacent();
}
