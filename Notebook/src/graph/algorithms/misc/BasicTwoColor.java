// @formatter:off

package graph.algorithms.misc;

import graph.IBasicGraph;

/**
 * Algorithm that represents a coloring of a simple numerical graph such that
 * only two colors are used, this, if the graph is two-colorable.
 */
public class BasicTwoColor
{
    // Attributes

    /**
     * Represents the marked vertexes.
     */
    private boolean[] marked;

    /**
     * Represents the color of the vertexes.
     */
    private boolean[] color;

    /**
     * Boolean that represents if the given graph is two-colorable or not.
     */
    private static boolean isTwoColorable;

    // Constructors

    /**
     * Creates a BasicTwoColor object that uses the given graph to two color it.
     * Doesn't require the graph to be connected, i.e., will check the entire graph.
     * @param pGraph Graph to use for the algorithm.
     */
    public BasicTwoColor(IBasicGraph pGraph)
    {
        marked = new boolean[pGraph.V()];
        color = new boolean[pGraph.V()];
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
     * Creates a BasicTwoColor object that uses the given graph to two-color it.
     * Will only check the component of which the given vertex is part of.
     * @param pGraph Graph to use for the algorithm.
     * @param pVertex Vertex in which the coloring starts.
     */
    public BasicTwoColor(IBasicGraph pGraph, int pVertex)
    {
        marked = new boolean[pGraph.V()];
        color = new boolean[pGraph.V()];
        isTwoColorable = true;
        basicTwoColor(pGraph, pVertex);
    }

    /**
     * @return {@code true} if the graph is two colorable, {@code false} if contrary.
     */
    public boolean isTwoColorable()
    { return isTwoColorable; }

    /**
     * @return The color of each vertex if the graph is colorable, null if contrary.
     */
    public boolean[] getColor()
    { return isTwoColorable() ? color : null; }

    /**
     * Recursive auxiliary method to color the graph.
     * @param pGraph Graph to use for the algorithm.
     * @param pVertex Vertex from which the coloring starts.
     */
    private void basicTwoColor(IBasicGraph pGraph, int pVertex)
    {
        marked[pVertex] = true;
        for(int vertex : pGraph.adjacent(pVertex))
        {
            if(!isTwoColorable)
                return;
            if(!marked[vertex])
            {
                color[vertex] = !color[pVertex];
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
