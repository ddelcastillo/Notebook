// @formatter:off

package graph.undirectedUnweighted;

import graph.IBasicGraph;
import util.Checked;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a simple numerical graph for N vertices labeled from 0 to N-1.
 * The graph has an adjacency matrix implementation.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 6/1/20.
 */
@Checked
(note = "Methods with the 'Checked' signature enforce additional checks to avoid errors and to\n" +
" ensure the structure's correctness in exchange of efficiency. For fastest results, use\n" +
" the non 'Checked' methods, however, these are liable to: ArrayIndexOutOfBounds exceptions.")
public class BasicUndirectedUnweightedAMGraph implements IBasicGraph
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
    protected boolean[][] adjacent;

    // Constructor

    /**
     * Creates a BasicUndirectedUnweightedGraph object with N vertices labeled from 0 to N-1.
     * @param N The number of vertices to add to the graph.
     */
    public BasicUndirectedUnweightedAMGraph(int N)
    {
        V = N;
        E = 0;
        adjacent = new boolean[N][N];
    }

    /**
     * Creates a BasicUndirectedUnweightedGraph object copy of the given graph.
     * @param pGraph The graph to copy.
     */
    public BasicUndirectedUnweightedAMGraph(BasicUndirectedUnweightedAMGraph pGraph)
    {
        this.V = pGraph.V;
        this.E = pGraph.E;
        this.adjacent = new boolean[pGraph.V][pGraph.V];
        for(int i = 0; i < V; ++i)
            System.arraycopy(pGraph.adjacent[i], 0, this.adjacent[i], 0, V);
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
        adjacent[pVertex1][pVertex2] = true;
        adjacent[pVertex2][pVertex1] = true;
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
        if(pVertex1 != pVertex2 && pVertex1 >= 0 && pVertex1 < V && pVertex2 >= 0 && pVertex2 < V && !adjacent[pVertex1][pVertex2])
            addEdge(pVertex1, pVertex2);
    }

    /**
     * Doesn't check if pVertex is a valid vertex. For this, use adjacentChecked.
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertices of the given vertex.
     */
    public Collection<Integer> adjacent(int pVertex)
    {
        ArrayList<Integer> adj = new ArrayList<>();
        for(int i = 0; i < V; ++i)
        {
            if(adjacent[pVertex][i])
                adj.add(i);
        }
        return adj;
    }

    /**
     * Checks that pVertex is a valid vertex.
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertices of the given vertex or {@code null} if the vertex is invalid.
     */
    public Collection<Integer> adjacentChecked(int pVertex)
    { return pVertex >= 0 && pVertex < V ? adjacent(pVertex) : null; }

    /**
     * @return Array of collections corresponding to the adjacent vertices of each vertex.
     */
    public Collection<Integer>[] adjacent()
    {
        ArrayList<Integer>[] adj = new ArrayList[V];
        for(int i = 0; i < V; ++i)
        {
            adj[i] = new ArrayList<>();
            for(int j = 0; j < V; ++j)
            {
                if(adjacent[i][j])
                    adj[i].add(j);
            }
        }
        return adj;
    }

    /**
     * Doesn't check if the vertices are valid. For this, use hasEdgeChecked.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     * @return True if there's an edge between the two vertices, false if contrary.
     */
    public boolean hasEdge(int pVertex1, int pVertex2)
    { return adjacent[pVertex1][pVertex2]; }

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