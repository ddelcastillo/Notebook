// @formatter:off

package graph.algorithms.misc;

import graph.IExtendedUnweightedGraph;
import java.util.ArrayList;

/**
 * Algorithm that represents a check to see if a graph is acyclic.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 6/1/20.
 */
public class IsAcyclic<T>
{
    // Attributes

    /**
     * Represents the marked vertices.
     */
    private static boolean[] marked;

    /**
     * Boolean that represents if the given graph is acyclic or not.
     */
    private static boolean isAcyclic;

    // Constructors

    /**
     * Creates an IsAcyclic object that uses the given graph to check if its acyclic.
     * Doesn't require the graph to be connected, i.e., will check the entire graph.
     * @param pGraph Graph to use for the algorithm.
     */
    public IsAcyclic(IExtendedUnweightedGraph<T> pGraph)
    {
        marked = new boolean[pGraph.V()];
        isAcyclic = true;
        for(int v = 0; v < pGraph.V(); ++v)
        {
            if(!marked[v])
            {
                isAcyclic(pGraph, v, v);
                if(!isAcyclic)
                    return;
            }
        }
    }

    /**
     * Creates an IsAcyclic object that uses the given graph to check if its acyclic.
     * Will only check the component of which the given vertex is a part of.
     * @param pGraph Graph to use for the algorithm.
     * @param pVertex Vertex in which the acyclic search starts.
     */
    public IsAcyclic(IExtendedUnweightedGraph<T> pGraph, T pVertex)
    {
        marked = new boolean[pGraph.V()];
        isAcyclic = true;
        int vertex = pGraph.keyToNumber().get(pVertex);
        isAcyclic(pGraph, vertex, vertex);
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
    private void isAcyclic(IExtendedUnweightedGraph<T> pGraph, int pVertex1, int pVertex2)
    {
        marked[pVertex1] = true;
        ArrayList<Integer> adjacentNumber = pGraph.adjacentNumber().get(pVertex1);
        for(int vertex : adjacentNumber)
        {
            if(!isAcyclic)
                return;
            if(!marked[vertex])
                isAcyclic(pGraph, vertex, pVertex1);
            else if(vertex != pVertex2 || vertex == pVertex1)
            {
                isAcyclic = false;
                return;
            }
        }
    }
}