// @formatter:off

package graph.algorithms;

import graph.IBasicGraph;
import util.Search;
import java.util.ArrayDeque;

/**
 * Represents a breath first search for a simple numerical undirected unweighted graph.
 */
public class BasicBFS extends Search
{
    // Constructor

    /**
     * Creates a BasicDFS object that uses the given graph and starts from the given vertex.
     * @param pGraph Graph to use for the algorithm.
     * @param pOrigin Vertex from which the search starts.
     */
    public BasicBFS(IBasicGraph pGraph, int pOrigin)
    {
        super(pGraph, pOrigin);
        basicBFS(pGraph, pOrigin);
    }

    // Methods

    /**
     * Auxiliary method to find the paths from the origin vertex.
     * @param pGraph Graph to use for the algorithm.
     * @param pVertex Vertex from which the search starts.
     */
    private void basicBFS(IBasicGraph pGraph, int pVertex)
    {
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        marked[pVertex] = true;
        queue.add(pVertex);
        int vertex;
        while(!queue.isEmpty())
        {
            vertex = queue.remove();
            for(int adjacent : pGraph.adjacent(vertex))
            {
                if(!marked[adjacent])
                {
                    edgeTo[adjacent] = vertex;
                    marked[adjacent] = true;
                    queue.add(adjacent);
                }
            }
        }
    }
}