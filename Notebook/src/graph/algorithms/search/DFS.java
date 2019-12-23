// @formatter:off

package graph.algorithms.search;

import graph.IExtendedGraph;import java.util.ArrayList;import java.util.Stack;

/**
 * Represents a depth first search for a simple numerical undirected graph.
 */
public class DFS<T>
{
    // Attributes

    /**
     * Represents the marked vertexes.
     */
    private boolean[] marked;

    /**
     * Array that stores the parent of each vertex.
     */
    private int[] edgeTo;

    /**
     * The origin vertex from which the search starts.
     */
    private T origin;

    /**
     * The numerical value of the origin vertex from which the search starts.
     */
    private int originNumber;

    private IExtendedGraph<T> graph;

    // Constructor

    /**
     * Creates a BasicDFS object that uses the given graph and starts from the given vertex.
     * @param pGraph Graph to use for the algorithm.
     * @param pOrigin Vertex from which the search starts.
     */
    public DFS(IExtendedGraph<T> pGraph, T pOrigin)
    {
        marked = new boolean[pGraph.V()];
        edgeTo = new int[pGraph.V()];
        origin = pOrigin;
        graph = pGraph;
        originNumber = pGraph.keyToNumber().get(pOrigin);
        basicDFS(pGraph, originNumber);
    }

    // Methods

    /**
     * Recursive auxiliary method to find the paths from the origin vertex.
     * @param pGraph Graph to use for the algorithm.
     * @param pVertex Vertex from which the search starts.
     */
    private void basicDFS(IExtendedGraph<T> pGraph, int pVertex)
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

    /**
     * @param pVertex Vertex to check if it has a path from the origin vertex.
     * @return True if there's a path to the vertex from the origin, false if contrary.
     */
    public boolean hasPathTo(T pVertex)
    { return marked[graph.keyToNumber().get(pVertex)]; }

    /**
     * @param pVertex Vertex whose path from the origin is desired.
     * @return The path from the origin to the given vertex, null if there's no path.
     */
    public Iterable<T> pathTo(T pVertex)
    {
        if(!hasPathTo(pVertex))
            return null;
        Stack<T> path = new Stack<>();
        for(int v = graph.keyToNumber().get(pVertex); v != originNumber; v = edgeTo[v])
            path.push(graph.numberToKey().get(v));
        path.push(origin);
        return path;
    }
}