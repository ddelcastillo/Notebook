// @formatter:off

package graph.algorithms.search;

import graph.IBasicUnweightedGraph;
import util.BasicSearch;
import java.util.ArrayDeque;

/**
 * Algorithm that represents a breath first search for a simple numerical graph.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 6/1/20.
 */
public class BasicBFS extends BasicSearch
{
    // Constructor

    /**
     * Creates a BasicDFS object that uses the given graph and starts the search from the given vertex.
     * @param pGraph Graph to use for the algorithm.
     * @param pOrigin Vertex from which the search starts.
     */
    public BasicBFS(IBasicUnweightedGraph pGraph, int pOrigin)
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
    private void basicBFS(IBasicUnweightedGraph pGraph, int pVertex)
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