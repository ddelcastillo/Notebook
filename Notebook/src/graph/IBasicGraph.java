// @formatter:off

package graph;

import java.util.Collection;

/**
 * The API for a numerical unweighted graph.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 6/1/20.
 */
public interface IBasicGraph extends IGraph
{
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