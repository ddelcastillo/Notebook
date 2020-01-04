package graph.undirectedUnweighted;

import unionFinder.BasicUnionFinder;

public class BasicUndirectedUnweightedAMCCGraph extends BasicUndirectedUnweightedAMGraph
{
    // Attributes

    /**
     * The graph's union finder.
     */
    private BasicUnionFinder unionFinder;

    // Constructor

    /**
     * Creates a BasicUndirectedUnweightedAMCCGraph object with N vertexes labeled from 0 to N.
     * @param N The number of vertexes to add to the graph.
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
     * Allows self-cycles and doesn't check if the vertexes are valid or if the edge already exists. For this, use addEdgeChecked.
     * Adds an edge between two vertexes. If it is the case that the edge is a self-cycle, it will add it once.
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
