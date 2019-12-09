// @formatter:off

package graph.algorithms;

import graph.IBasicGraph;
import util.Search;

/**
 * Represents a depth first search for a simple numerical undirected graph.
 */
public class BasicDFS extends Search
{
    // Constructor

    /**
     * Creates a BasicDFS object that uses the given graph and starts from the given vertex.
     * @param pGraph Graph to use for the algorithm.
     * @param pOrigin Vertex from which the search starts.
     */
    public BasicDFS(IBasicGraph pGraph, int pOrigin)
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
    private void basicDFS(IBasicGraph pGraph, int pVertex)
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