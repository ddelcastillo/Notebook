package graph.undirectedUnweighted;

import unionFinder.BasicUnionFinder;
import util.Checked;

/**
 * Represents a simple numerical undirected and unweighted graph for N vertices labeled from 0 to N-1 that
 * also tracks connected components by means of an union finder.
 * The graph has an adjacency matrix implementation.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 6/1/20.
 */
@Checked
(note = "Methods with the 'Checked' signature enforce additional checks to avoid errors and to\n" +
" ensure the structure's correctness in exchange of efficiency. For fastest results, use\n" +
" the non 'Checked' methods, however, these are liable to: ArrayIndexOutOfBounds exceptions.")
public class BasicUndirectedUnweightedAMCCGraph extends BasicUndirectedUnweightedAMGraph
{
    // Attributes

    /**
     * The graph's union finder.
     */
    private BasicUnionFinder unionFinder;

    // Constructor

    /**
     * Creates a BasicUndirectedUnweightedAMCCGraph object with N vertices labeled from 0 to N.
     * @param N The number of vertices to add to the graph.
     */
    public BasicUndirectedUnweightedAMCCGraph(int N)
    {
        super(N);
        unionFinder = new BasicUnionFinder(N);
    }

    /**
     * Creates a BasicUndirectedUnweightedAMCCGraph object copy of the given graph.
     * @param pGraph The graph to copy.
     */
    public BasicUndirectedUnweightedAMCCGraph(BasicUndirectedUnweightedAMCCGraph pGraph)
    {
        super(pGraph);
        this.unionFinder = new BasicUnionFinder(pGraph.unionFinder);
    }

    /**
     * Allows self-cycles and doesn't check if the vertices are valid or if the edge already exists. For this, use addEdgeChecked.
     * Adds an edge between two vertices. If it is the case that the edge is a self-cycle, it will add it once.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    public void addEdge(int pVertex1, int pVertex2)
    {
        adjacent[pVertex1][pVertex2] = true;
        adjacent[pVertex2][pVertex1] = true;
        unionFinder.merge(pVertex1, pVertex2);
        ++E;
    }

    // Connected components methods

    /**
     * @return The number of connected components in the graph.
     */
    public int numberOfComponents()
    { return unionFinder.totalRoots(); }

    /**
     * Doesn't check if pVertex is a valid vertex. For that, use sizeOfComponentChecked.
     * @param pVertex The vertex whose component size is desired.
     * @return The size of the component that the given vertex is a part of.
     */
    public int sizeOfComponent(int pVertex)
    { return unionFinder.size(pVertex); }

    /**
     * Checks that pVertex is a valid vertex.
     * @param pVertex The vertex whose component size is desired.
     * @return The size of the component that the given vertex is a part of or {@code null} if the vertex is invalid.
     */
    public Integer sizeOfComponentChecked(int pVertex)
    { return unionFinder.sizeChecked(pVertex); }
}
