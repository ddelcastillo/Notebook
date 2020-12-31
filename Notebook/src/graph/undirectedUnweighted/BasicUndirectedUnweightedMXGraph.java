// @formatter:off

package graph.undirectedUnweighted;

import graph.IBasicGraph;
import util.Checked;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a simple numerical undirected and unweighted graph for N vertices labeled from 0 to N-1.
 * The graph has a mix of both an adjacency matrix and an adjacency list implementation.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 31/12/20.
 */
@Checked
(note = "Methods with the 'Checked' signature enforce additional checks to avoid errors and to\n" +
" ensure the structure's correctness in exchange of efficiency. For fastest results, use\n" +
" the non 'Checked' methods, however, these are liable to: ArrayIndexOutOfBounds exceptions.")
public class BasicUndirectedUnweightedMXGraph implements IBasicGraph
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
     * The adjacency matrix that represents edges between vertices.
     */
    protected boolean[][] adjacentMatrix;

    /**
     * The array of adjacent lists for each vertex.
     */
    protected ArrayList<Integer>[] adjacent;

    // Constructor

    /**
     * Creates a BasicUndirectedUnweightedGraph object with N vertices labeled from 0 to N-1.
     * @param N The number of vertices to add to the graph.
     */
    public BasicUndirectedUnweightedMXGraph(int N)
    {
        V = N;
        E = 0;
        adjacentMatrix = new boolean[N][N];
        adjacent = (ArrayList<Integer>[]) new ArrayList[N];
        for(int v = 0; v < V; ++v)
            adjacent[v] = new ArrayList<>();
    }

    /**
     * Creates a BasicUndirectedUnweightedGraph object copy of the given graph.
     * @param pGraph The graph to copy.
     */
    public BasicUndirectedUnweightedMXGraph(BasicUndirectedUnweightedMXGraph pGraph)
    {
        this.V = pGraph.V;
        this.E = pGraph.E;
        this.adjacentMatrix = new boolean[pGraph.V][pGraph.V];
        for(int i = 0; i < V; ++i)
            System.arraycopy(pGraph.adjacentMatrix[i], 0, this.adjacentMatrix[i], 0, V);
        this.adjacent = (ArrayList<Integer>[]) new ArrayList[pGraph.V];
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
     */
    public void addEdge(int pVertex1, int pVertex2)
    {
        adjacentMatrix[pVertex1][pVertex2] = true;
        adjacentMatrix[pVertex2][pVertex1] = true;
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
     * Doesn't allow self-cycles and checks if the vertices are valid and if the edge already exists.
     * Adds an edge between two vertices if the vertices are valid, not equal and the edge doesn't already exist.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    public void addEdgeChecked(int pVertex1, int pVertex2)
    {
        if(pVertex1 != pVertex2 && pVertex1 >= 0 && pVertex1 < V && pVertex2 >= 0 && pVertex2 < V && !adjacentMatrix[pVertex1][pVertex2])
                addEdge(pVertex1, pVertex2);
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

    /**
     * Doesn't check if the vertices are valid. For this, use hasEdgeChecked.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     * @return True if there's an edge between the two vertices, false if contrary.
     */
    public boolean hasEdge(int pVertex1, int pVertex2)
    { return adjacentMatrix[pVertex1][pVertex2]; }

    /**
     * Checks if the vertices are valid.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     * @return True if there's an edge between the two vertices, false if contrary or {@code null}
     * if one or two of the vertices are invalid.
     */
    public Boolean hasEdgeChecked(int pVertex1, int pVertex2)
    {
        if(pVertex1 >= 0 && pVertex2 >= 0 && pVertex1 < V && pVertex2 < V)
            return hasEdge(pVertex1, pVertex2);
        else
            return null;
    }
}