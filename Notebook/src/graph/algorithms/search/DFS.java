// @formatter:off

package graph.algorithms.search;

import graph.IExtendedUnweightedGraph;
import util.Search;
import java.util.ArrayList;

/**
 * Algorithm that represents a depth first search for a graph.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 6/1/20.
 */
public class DFS<T> extends Search<T>
{
    // Constructor

    /**
     * Creates a BasicDFS object that uses the given graph and starts the search from the given vertex.
     * @param pGraph Graph to use for the algorithm.
     * @param pOrigin Vertex from which the search starts.
     */
    public DFS(IExtendedUnweightedGraph<T> pGraph, T pOrigin)
    {
        super(pGraph, pOrigin);
        basicDFS(pGraph, originNumber);
    }

    // Methods

    /**
     * Recursive auxiliary method to find the paths from the origin vertex.
     * @param pGraph Graph to use for the algorithm.
     * @param pVertex Vertex from which the search starts.
     */
    private void basicDFS(IExtendedUnweightedGraph<T> pGraph, int pVertex)
    {
        marked[pVertex] = true;
        ArrayList<Integer> adjacentNumber = pGraph.adjacentNumber().get(pVertex);
        for(Integer vertex : adjacentNumber)
        {
            if(!marked[vertex])
            {
                edgeTo[vertex] = pVertex;
                basicDFS(pGraph, vertex);
            }
        }
    }
}