package util;

import graph.IGraph;

import java.util.Stack;

/**
 * Class that represents a search over a fixed number of vertices labeled from 0 to N-1.
 */
public class Search
{
    // Attributes

    /**
     * Represents the marked vertexes.
     */
    protected boolean[] marked;

    /**
     * Array that stores the parent of each vertex.
     */
    protected int[] edgeTo;

    /**
     * The origin vertex from which the search starts.
     */
    protected int origin;

    // Constructor

    /**
     * Creates a Search object that uses the given graph.
     * @param pGraph Graph to use for the algorithm.
     */
    public Search(IGraph pGraph, int pOrigin)
    {
        marked = new boolean[pGraph.V()];
        edgeTo = new int[pGraph.V()];
        origin = pOrigin;
    }

    // Methods

    /**
     * @param pVertex Vertex to check if it has a path from the origin vertex.
     * @return True if there's a path to the vertex from the origin, false if contrary.
     */
    public boolean hasPathTo(int pVertex)
    { return marked[pVertex]; }

    /**
     * @param pVertex Vertex whose path from the origin is desired.
     * @return The path from the origin to the given vertex, null if there's no path.
     */
    public Iterable<Integer> pathTo(int pVertex)
    {
        if(!hasPathTo(pVertex))
            return null;
        Stack<Integer> path = new Stack<>();
        for(int v = pVertex; v != origin; v = edgeTo[v])
            path.push(v);
        path.push(origin);
        return path;
    }
}