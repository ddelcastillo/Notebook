// @formatter:off

package graph.algorithms.misc;

import graph.IBasicGraph;

/**
 * Algorithm that represents a check to see if a simple numerical graph is acyclic.
 */
public class BasicIsAcyclic
{
    // Attributes

    /**
     * Represents the marked vertexes.
     */
    private static boolean[] marked;

    /**
     * Boolean that represents if the given graph is acyclic or not.
     */
    private static boolean isAcyclic;

    // Constructors

    /**
     * Creates a BasicIsAcyclic object that uses the given graph to check if its acyclic.
     * Doesn't require the graph to be connected, i.e., will check the entire graph.
     * @param pGraph Graph to use for the algorithm.
     */
    public BasicIsAcyclic(IBasicGraph pGraph)
    {
        marked = new boolean[pGraph.V()];
        isAcyclic = true;
        for(int v = 0; v < pGraph.V(); ++v)
        {
            if(!marked[v])
            {
                basicIsAcyclic(pGraph, v, v);
                if(!isAcyclic)
                    return;
            }
        }
    }

    /**
     * Creates a BasicIsAcyclic object that uses the given graph to check if its acyclic.
     * Will only check the component of which the given vertex is part of.
     * @param pGraph Graph to use for the algorithm.
     * @param pVertex Vertex in which the acyclic search starts.
     */
    public BasicIsAcyclic(IBasicGraph pGraph, int pVertex)
    {
        marked = new boolean[pGraph.V()];
        isAcyclic = true;
        basicIsAcyclic(pGraph, pVertex, pVertex);
    }

    // Methods

    /**
     * @return {@code true} if the graph is acyclic, {@code false} if contrary.
     */
    public boolean isAcyclic()
    { return isAcyclic; }

    /**
     * Recursive auxiliary method to find if the graph is acyclic.
     * @param pGraph Graph to use for the algorithm.
     * @param pVertex1 Vertex from which the search starts.
     */
    private void basicIsAcyclic(IBasicGraph pGraph, int pVertex1, int pVertex2)
    {
        marked[pVertex1] = true;
        for(int vertex : pGraph.adjacent(pVertex1))
        {
            if(!isAcyclic)
                return;
            if(!marked[vertex])
                basicIsAcyclic(pGraph, vertex, pVertex1);
            else if(vertex != pVertex2 || vertex == pVertex1)
            {
                isAcyclic = false;
                return;
            }
        }
    }
}
