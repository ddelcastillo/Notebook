// @formatter:off

package graph.algorithms.search;

import graph.IBasicUnweightedGraph;
import util.BasicSearch;

/**
 * Algorithm that represents a depth first search for a simple numerical graph.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 6/1/20.
 */
public class BasicDFS extends BasicSearch
{
    // Constructor

    /**
     * Creates a BasicDFS object that uses the given graph and starts the search from the given vertex.
     * @param pGraph Graph to use for the algorithm.
     * @param pOrigin Vertex from which the search starts.
     */
    public BasicDFS(IBasicUnweightedGraph pGraph, int pOrigin)
    {
        super(pGraph, pOrigin);
        basicDFS(pGraph, pOrigin);
    }

    // Methods

    /**
     * Recursive auxiliary method to find the paths from the origin vertex.
     * @param pGraph Graph to use for the algorithm.
     * @param pVertex Vertex from which the search starts.
     */
    private void basicDFS(IBasicUnweightedGraph pGraph, int pVertex)
    {
        marked[pVertex] = true;
        for(int vertex : pGraph.adjacent(pVertex))
        {
            if(!marked[vertex])
            {
                edgeTo[vertex] = pVertex;
                basicDFS(pGraph, vertex);
            }
        }
    }
}