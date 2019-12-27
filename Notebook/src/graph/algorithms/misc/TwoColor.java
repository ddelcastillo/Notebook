// @formatter:off

package graph.algorithms.misc;

import graph.IExtendedGraph;
import java.util.ArrayList;import java.util.HashMap;

/**
 * Algorithm that represents a coloring of a graph such that
 * only two colors are used, this, if the graph is two-colorable.
 */
public class TwoColor<T>
{
    // Attributes

    /**
     * Represents the marked vertexes.
     */
    private static boolean[] marked;

    /**
     * Represents the color of the vertexes.
     */
    private static boolean[] color;

    /**
     * Boolean that represents if the given graph is two-colorable or not.
     */
    private static boolean isTwoColorable;

    /**
     * HashMap that represents the color of each vertex.
     */
    private HashMap<T, Boolean> colors;

    /**
     * HashMap that represents the numerical color of each vertex.
     */
    private HashMap<T, Integer> colorsNum;

    // Constructors

    /**
     * Creates a TwoColor object that uses the given graph to two color it.
     * Doesn't require the graph to be connected, i.e., will check the entire graph.
     * @param pGraph Graph to use for the algorithm.
     */
    public TwoColor(IExtendedGraph<T> pGraph)
    {
        marked = new boolean[pGraph.V()];
        color = new boolean[pGraph.V()];
        colors = new HashMap<>(pGraph.V());
        colorsNum = new HashMap<>(pGraph.V());
        isTwoColorable = true;
        for(int v = 0; v < pGraph.V(); ++v)
        {
            if(!marked[v])
            {
                basicTwoColor(pGraph, v);
                if(!isTwoColorable)
                    return;
            }
        }
    }

    /**
     * Creates a TwoColor object that uses the given graph to two-color it.
     * Will only check the component of which the given vertex is part of.
     * @param pGraph Graph to use for the algorithm.
     * @param pVertex Vertex in which the coloring starts.
     */
    public TwoColor(IExtendedGraph<T> pGraph, T pVertex)
    {
        marked = new boolean[pGraph.V()];
        color = new boolean[pGraph.V()];
        colors = new HashMap<>(pGraph.V());
        colorsNum = new HashMap<>(pGraph.V());
        isTwoColorable = true;
        int vertex = pGraph.keyToNumber().get(pVertex);
        basicTwoColor(pGraph, vertex);
    }

    /**
     * @return {@code true} if the graph is two colorable, {@code false} if contrary.
     */
    public boolean isTwoColorable()
    { return isTwoColorable; }

    /**
     * @return The color of each vertex if the graph is two-colorable, {@code null} if contrary.
     */
    public HashMap<T, Boolean> getColor()
    { return colors; }

    /**
     * @return The color of each vertex in numerical format (color = {0, 1}) if the
     * graph is two-colorable, {@code null} if contrary.
     */
    public HashMap<T, Integer> getColorNum()
    { return colorsNum; }

    /**
     * Recursive auxiliary method to color the graph.
     * @param pGraph Graph to use for the algorithm.
     * @param pVertex Vertex from which the coloring starts.
     */
    private void basicTwoColor(IExtendedGraph<T> pGraph, int pVertex)
    {
        marked[pVertex] = true;
        colors.put(pGraph.numberToKey().get(pVertex), color[pVertex]);
        colorsNum.put(pGraph.numberToKey().get(pVertex), color[pVertex] ? 1 : 0);
        ArrayList<Integer> adjacentNumber = pGraph.adjacentNumber().get(pVertex);
        for(int vertex : adjacentNumber)
        {
            if(!isTwoColorable)
                return;
            if(!marked[vertex])
            {

                color[vertex] = !color[pVertex];
                colors.put(pGraph.numberToKey().get(vertex), color[vertex]);
                colorsNum.put(pGraph.numberToKey().get(vertex), color[vertex] ? 1 : 0);
                basicTwoColor(pGraph, vertex);
            }
            else if(color[vertex] == color[pVertex])
            {
                isTwoColorable = false;
                return;
            }
        }
    }
}