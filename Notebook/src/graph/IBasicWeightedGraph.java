// @formatter:off

package graph;

import java.util.Collection;

/**
 * The API for a numerical weighted graph.
 */
public interface IBasicWeightedGraph extends IGraph
{
    /**
     * Allows self-cycles and doesn't check if the vertices are valid or if the edge already exists. For this, use addEdgeChecked.
     * Adds an edge between two vertices. If it is the case that the edge is a self-cycle, it will add it once.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     * @param pWeight The weight of the edge.
     */
    void addEdge(int pVertex1, int pVertex2, int pWeight);

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