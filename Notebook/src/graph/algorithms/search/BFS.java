// @formatter:off

package graph.algorithms.search;

import graph.IExtendedGraph;
import util.Search;
import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Algorithm that represents a breath first search for a graph.
 */
public class BFS<T> extends Search<T>
{
    // Constructor

    /**
     * Creates a BasicDFS object that uses the given graph and starts from the given vertex.
     * @param pGraph Graph to use for the algorithm.
     * @param pOrigin Vertex from which the search starts.
     */
    public BFS(IExtendedGraph<T> pGraph, T pOrigin)
    {
        super(pGraph, pOrigin);
        basicBFS(pGraph, originNumber);
    }

    // Methods

    /**
     * Auxiliary method to find the paths from the origin vertex.
     * @param pGraph Graph to use for the algorithm.
     * @param pVertex Vertex from which the search starts.
     */
    private void basicBFS(IExtendedGraph<T> pGraph, int pVertex)
    {
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        marked[pVertex] = true;
        queue.add(pVertex);
        int vertex;
        ArrayList<Integer> adjacentNumber;
        while(!queue.isEmpty())
        {
            vertex = queue.remove();
            adjacentNumber = pGraph.adjacentNumber().get(vertex);
            for(Integer adjacent : adjacentNumber)
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