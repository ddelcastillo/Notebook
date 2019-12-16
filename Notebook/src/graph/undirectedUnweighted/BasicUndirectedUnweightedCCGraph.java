// @formatter:off

package graph.undirectedUnweighted;

import unionFinder.BasicUnionFinder;
import util.Checked;

/**
 * Represents a simple numerical graph for N vertexes labeled from 0 to N-1.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 16/12/19.
 */
@Checked
(note = "Methods with the 'Checked' signature enforce additional checks to avoid errors and to\n" +
" ensure the structure's correctness in exchange of efficiency. For fastest results, use\n" +
" the non 'Checked' methods, however, these are liable to: IndexOutOfBounds exceptions.")
public class BasicUndirectedUnweightedCCGraph extends BasicUndirectedUnweightedGraph
{
    // Attributes

    /**
     * The graph's union finder.
     */
    private BasicUnionFinder unionFinder;

    // Constructor

    /**
     * Creates a BasicUndirectedUnweightedGraph object with N vertexes labeled from 0 to N.
     * @param N The number of vertexes to add to the graph.
     */
    public BasicUndirectedUnweightedCCGraph(int N)
    {
        super(N);
        unionFinder = new BasicUnionFinder(N);
    }

    /**
     * Creates a BasicUndirectedUnweightedGraph object copy of the given graph.
     * @param pGraph The graph to copy.
     */
    public BasicUndirectedUnweightedCCGraph(BasicUndirectedUnweightedCCGraph pGraph)
    {
        super(pGraph);
        this.unionFinder = new BasicUnionFinder(pGraph.unionFinder);
    }

    // Methods

    /**
     * Allows self-cycles and doesn't check if the vertexes are valid or if the edge already exists. For this, use addEdgeChecked.
     * Adds an edge between two vertexes. If it is the case that the edge is a self-cycle, it will add it once.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    public void addEdge(int pVertex1, int pVertex2)
    {
        if(pVertex1 == pVertex2)
            adjacent[pVertex1].add(pVertex2);
        else
        {
            adjacent[pVertex1].add(pVertex2);
            adjacent[pVertex2].add(pVertex1);
        }
        unionFinder.merge(pVertex1, pVertex2);
        ++E;
    }

    /**
     * Doesn't allow self-cycles and checks if the vertexes are valid and if the edge exists.
     * Adds an edge between two vertexes if the vertexes are valid, not equal and the edge doesn't already exist.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    public void addEdgeChecked(int pVertex1, int pVertex2)
    {
        if(pVertex1 != pVertex2 && pVertex1 >= 0 && pVertex1 < V && pVertex2 >= 0 && pVertex2 < V)
        {
            if (adjacent[pVertex1].size() > adjacent[pVertex2].size())
            {
                if(!adjacent[pVertex2].contains(pVertex1))
                {
                    adjacent[pVertex1].add(pVertex2);
                    adjacent[pVertex2].add(pVertex1);
                    unionFinder.merge(pVertex1, pVertex2);
                    ++E;
                }
            }
            else
            {
                if(!adjacent[pVertex1].contains(pVertex2))
                {
                    adjacent[pVertex1].add(pVertex2);
                    adjacent[pVertex2].add(pVertex1);
                    unionFinder.merge(pVertex1, pVertex2);
                    ++E;
                }
            }
        }
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
    public int sizeOfComponentChecked(int pVertex)
    { return unionFinder.sizeChecked(pVertex); }
}