// @formatter:off

package graph.undirectedWeighted;

import graph.IBasicGraph;
import util.Checked;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a simple numerical undirected and weighted graph for N vertices labeled from 0 to N-1.
 * The graph has an adjacency list implementation.
 */
@Checked
(note = "Methods with the 'Checked' signature enforce additional checks to avoid errors and to\n" +
" ensure the structure's correctness in exchange of efficiency. For fastest results, use\n" +
" the non 'Checked' methods, however, these are liable to: ArrayIndexOutOfBounds exceptions.")
public class BasicUndirectedWeightedGraph implements IBasicGraph
{
    // Attributes

    /**
     * The number of vertices.
     */
    protected int V;

    /**
     * The number of edges.
     */
    protected int E;

    /**
     * The array of adjacent lists for each vertex.
     */
    protected ArrayList<Integer>[] adjacent;

    /**
     * The matrix that represents the weight of an edge between vertexes.
     */
    protected int[][] weights;

    // Constructor

    /**
     * Creates a BasicUndirectedWeightedGraph object with N vertices labeled from 0 to N-1.
     * @param N The number of vertices to add to the graph.
     */
    public BasicUndirectedWeightedGraph(int N)
    {
        V = N;
        E = 0;
        adjacent = (ArrayList<Integer>[]) new ArrayList[N];
        weights = new int[N][N];
        for(int v = 0; v < V; ++v)
            adjacent[v] = new ArrayList<>();
    }

    /**
     * Creates a BasicUndirectedWeightedGraph object copy of the given graph.
     * @param pGraph The graph to copy.
     */
    public BasicUndirectedWeightedGraph(BasicUndirectedWeightedGraph pGraph)
    {
        this.V = pGraph.V;
        this.E = pGraph.E;
        this.adjacent = (ArrayList<Integer>[]) new ArrayList[pGraph.V];
        this.weights = new int[V][V];
        for(int i = 0; i < V; ++i)
            System.arraycopy(pGraph.weights[i], 0, this.weights[i], 0, V);
        System.arraycopy(pGraph.adjacent, 0, this.adjacent, 0, pGraph.V);
    }

    // Methods

    /**
     * @return The number of vertices.
     */
    public int V()
    { return V; }

    /**
     * @return The number of edges.
     */
    public int E()
    { return E; }

    /**
     * Allows self-cycles and doesn't check if the vertices are valid or if the edge already exists. For this, use addEdgeChecked.
     * Adds an edge between two vertices. If it is the case that the edge is a self-cycle, it will add it once.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     * @param pWeight The weight of the edge.
     */
    public void addEdge(int pVertex1, int pVertex2, int pWeight)
    {
        if(pVertex1 == pVertex2)
            adjacent[pVertex1].add(pVertex2);
        else
        {
            adjacent[pVertex1].add(pVertex2);
            adjacent[pVertex2].add(pVertex1);
        }
        weights[pVertex1][pVertex2] = pWeight;
        weights[pVertex2][pVertex1] = pWeight;
        ++E;
    }

    /**
     * Doesn't allow self-cycles and checks if the vertices are valid and if the edge already exists.
     * Adds an edge between two vertices if the vertices are valid, not equal and the edge doesn't already exist.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     * @param pWeight The weight of the edge.
     */
    public void addEdgeChecked(int pVertex1, int pVertex2, int pWeight)
    {
        if(pVertex1 != pVertex2 && pVertex1 >= 0 && pVertex1 < V && pVertex2 >= 0 && pVertex2 < V && pWeight >= 0)
        {
            if (adjacent[pVertex1].size() > adjacent[pVertex2].size())
            {
                if(!adjacent[pVertex2].contains(pVertex1))
                {
                    adjacent[pVertex1].add(pVertex2);
                    adjacent[pVertex2].add(pVertex1);
                    weights[pVertex1][pVertex2] = pWeight;
                    weights[pVertex2][pVertex1] = pWeight;
                    ++E;
                }
            }
            else
            {
                if(!adjacent[pVertex1].contains(pVertex2))
                {
                    adjacent[pVertex1].add(pVertex2);
                    adjacent[pVertex2].add(pVertex1);
                    weights[pVertex1][pVertex2] = pWeight;
                    weights[pVertex2][pVertex1] = pWeight;
                    ++E;
                }
            }
        }
    }

    /**
     * Doesn't check if pVertex is a valid vertex. For this, use adjacentChecked.
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertices of the given vertex.
     */
    public Collection<Integer> adjacent(int pVertex)
    { return adjacent[pVertex]; }

    /**
     * Checks that pVertex is a valid vertex.
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertices of the given vertex or {@code null} if the vertex is invalid.
     */
    public Collection<Integer> adjacentChecked(int pVertex)
    { return pVertex >= 0 && pVertex < V ? adjacent[pVertex] : null; }

    /**
     * @return Array of collections corresponding to the adjacent vertices of each vertex.
     */
    public Collection<Integer>[] adjacent()
    { return adjacent; }
}