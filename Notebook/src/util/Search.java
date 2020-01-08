// @formatter:off

package util;

import graph.IExtendedGraph;
import java.util.Stack;

/**
 * Class that represents a search over a fixed number of vertices labeled from 0 to N-1.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 6/1/20.
 */
public class Search<T>
{
    // Attributes

    /**
     * Represents the marked vertices.
     */
    protected boolean[] marked;

    /**
     * Array that stores the parent of each vertex.
     */
    protected int[] edgeTo;

    /**
     * The origin vertex from which the search starts.
     */
    protected T originVertex;

    /**
     * The numerical value of the origin vertex from which the search starts.
     */
    protected int originNumber;

    /**
     * The corresponding graph used for the algorithm.
     */
    protected IExtendedGraph<T> graph;

    // Constructor

    /**
     * Creates a Search object that uses the given graph.
     * @param pGraph Graph to use for the algorithm.
     * @param pOrigin Assigned vertex as the origin of the search.
     */
    public Search(IExtendedGraph<T> pGraph, T pOrigin)
    {
        marked = new boolean[pGraph.V()];
        edgeTo = new int[pGraph.V()];
        originVertex = pOrigin;
        graph = pGraph;
        originNumber = pGraph.keyToNumber().get(pOrigin);
    }

    // Methods

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
        path.push(originVertex);
        return path;
    }
}