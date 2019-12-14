// @formatter:off

package graph.undirectedUnweighted;

import graph.IBasicGraph;
import util.Checked;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a simple numerical graph for N vertexes labeled from 0 to N-1.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 16/12/19.
 */
@Checked
(note = "Methods with the 'Checked' signature enforce additional checks to avoid errors and to\n" +
" ensure the structure's correctness in exchange of efficiency. For fastest results, use\n" +
" the non 'Checked' methods, however, these are liable to: IndexOutOfBounds exceptions.")
public class BasicUndirectedUnweightedGraph implements IBasicGraph
{
    // Attributes

    /**
     * The number of vertexes.
     */
    protected int V;

    /**
     * The number of edges.
     */
    protected int E;

    /**
     * The array of adjacent list for each vertex.
     */
    protected ArrayList<Integer>[] adjacent;

    // Constructor

    /**
     * Creates a BasicUndirectedUnweightedGraph object with N vertexes labeled from 0 to N-1.
     * @param N The number of vertexes to add to the graph.
     */
    public BasicUndirectedUnweightedGraph(int N)
    {
        V = N;
        E = 0;
        adjacent = (ArrayList<Integer>[]) new ArrayList[N];
        for(int v = 0; v < V; ++v)
            adjacent[v] = new ArrayList<>();
    }

    /**
     * Creates a BasicUndirectedUnweightedGraph object copy of the given graph.
     * @param pGraph The graph to copy.
     */
    public BasicUndirectedUnweightedGraph(BasicUndirectedUnweightedGraph pGraph)
    {
        this.V = pGraph.V;
        this.E = pGraph.E;
        this.adjacent = (ArrayList<Integer>[]) new ArrayList[pGraph.V];
        System.arraycopy(pGraph.adjacent, 0, this.adjacent, 0, pGraph.V);
    }

    // Methods

    /**
     * @return The number of vertexes.
     */
    public int V()
    { return V; }

    /**
     * @return The number of edges.
     */
    public int E()
    { return E; }

    /**
     * Adds an edge between two vertexes. Doesn't check for duplicates and allows self-cycles.
     * If it is the case that the edge is a self-cycle, it will add it once.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    public void addEdge(int pVertex1, int pVertex2)
    {
        // So that it doesn't add it twice.
        if(pVertex1 == pVertex2)
            adjacent[pVertex1].add(pVertex2);
        else
        {
            adjacent[pVertex1].add(pVertex2);
            adjacent[pVertex2].add(pVertex1);
        }
        ++E;
    }

    /**
     * Adds an edge between two vertexes. Checks for duplicates and doesn't allow self-cycles.
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
                    ++E;
                }
            }
            else
            {
                if(!adjacent[pVertex1].contains(pVertex2))
                {
                    adjacent[pVertex1].add(pVertex2);
                    adjacent[pVertex2].add(pVertex1);
                    ++E;
                }
            }
        }
    }

    /**
     * Doesn't check if pVertex is a valid vertex. For this, use adjacentChecked.
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertexes of the given vertex.
     */
    public Collection<Integer> adjacent(int pVertex)
    { return adjacent[pVertex]; }

    /**
     * Checks that pVertex is a valid vertex.
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertexes of the given vertex.
     */
    public Collection<Integer> adjacentChecked(int pVertex)
    { return pVertex >= 0 && pVertex < V ? adjacent[pVertex] : null; }

    /**
     * @return Array of collections corresponding to the adjacent vertexes of each vertex.
     */
    public Collection<Integer>[] adjacent()
    { return adjacent; }
}