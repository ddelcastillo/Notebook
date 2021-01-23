// @formatter:off

package graph.undirectedUnweighted;

import graph.IExtendedGraph;import util.Checked;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * Represents a generic undirected and unweighted graph.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 6/1/20.
 */
@Checked
(note = "Methods with the 'Checked' signature enforce additional checks to avoid errors and to\n" +
" ensure the structure's correctness in exchange of efficiency. For fastest results, use\n" +
" the non 'Checked' methods, however, these are liable to: NullPointer exceptions.")
public class UndirectedUnweightedGraph<T> implements IExtendedGraph<T>
{
    // Constants

    /**
     * Represents the initial capacity of the graph's tables (nodes and edges).
     */
    protected static final int INITIAL_CAPACITY = 7;

    /**
     * Represents the initial capacity of a node's adjacent list.
     */
    protected static int LIST_CAPACITY = 5;

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
     * The array of adjacent vertex lists for each node.
     */
    protected ArrayList<ArrayList<Integer>> adjacentNumber;

    /**
     * The array of adjacent list of vertices for each vertex as keys.
     */
    protected ArrayList<ArrayList<T>> adjacentKey;

    /**
     * The map that accesses the given number to a certain key.
     */
    protected HashMap<T, Integer> keyToNumber;

    /**
     * The map that accesses the assigned key of a given number.
     */
    protected HashMap<Integer, T> numberToKey;

    // Constructor

    /**
     * Creates an UndirectedUnweightedGraph object.
     */
    public UndirectedUnweightedGraph()
    {
        V = 0;
        E = 0;
        adjacentNumber = new ArrayList<>(INITIAL_CAPACITY);
        adjacentKey = new ArrayList<>(INITIAL_CAPACITY);
        keyToNumber = new HashMap<>(INITIAL_CAPACITY);
        numberToKey = new HashMap<>(INITIAL_CAPACITY);
    }

    /**
     * Creates an UndirectedUnweightedGraph object with the given initial capacity.
     * @param pInitialCapacity The initial capacity of the graph's nodes.
     */
    public UndirectedUnweightedGraph(int pInitialCapacity)
    {
        V = 0;
        E = 0;
        adjacentNumber = new ArrayList<>(pInitialCapacity);
        adjacentKey = new ArrayList<>(pInitialCapacity);
        keyToNumber = new HashMap<>(pInitialCapacity);
        numberToKey = new HashMap<>(pInitialCapacity);
    }

    /**
     * Creates an UndirectedUnweightedGraph object with the given keys (nodes).
     * @param pKeys The graph's initial keys (nodes).
     */
    public UndirectedUnweightedGraph(T[] pKeys)
    {
        V = pKeys.length;
        E = 0;
        adjacentNumber = new ArrayList<>(pKeys.length);
        adjacentKey = new ArrayList<>(pKeys.length);
        keyToNumber = new HashMap<>(pKeys.length);
        numberToKey = new HashMap<>(pKeys.length);
        for(int i = 0; i < pKeys.length; ++i)
        {
            adjacentKey.add(i, new ArrayList<>(LIST_CAPACITY));
            adjacentNumber.add(i, new ArrayList<>(LIST_CAPACITY));
            keyToNumber.put(pKeys[i], i);
            numberToKey.put(i, pKeys[i]);
        }
    }

    /**
     * Creates an UndirectedUnweightedGraph object with the given keys (nodes).
     * @param pKeys The graph's initial keys (nodes).
     * @param pCapacityLists The graph's adjacency list capacity for each node.
     */
    public UndirectedUnweightedGraph(T[] pKeys, int pCapacityLists)
    {
        V = pKeys.length;
        E = 0;
        LIST_CAPACITY = pCapacityLists;
        adjacentNumber = new ArrayList<>(pKeys.length);
        adjacentKey = new ArrayList<>(pKeys.length);
        keyToNumber = new HashMap<>(pKeys.length);
        numberToKey = new HashMap<>(pKeys.length);
        for(int i = 0; i < pKeys.length; ++i)
        {
            adjacentKey.add(i, new ArrayList<>(pCapacityLists));
            adjacentNumber.add(i, new ArrayList<>(pCapacityLists));
            keyToNumber.put(pKeys[i], i);
            numberToKey.put(i, pKeys[i]);
        }
    }

    /**
     * Creates an UndirectedUnweightedGraph object copy of the given graph.
     * @param pGraph The graph to copy.
     */
    public UndirectedUnweightedGraph(UndirectedUnweightedGraph<T> pGraph)
    {
        this.V = pGraph.V;
        this.E = pGraph.E;
        this.adjacentNumber = (ArrayList<ArrayList<Integer>>) pGraph.adjacentNumber.clone();
        this.adjacentKey = (ArrayList<ArrayList<T>>) pGraph.adjacentKey.clone();
        this.keyToNumber = (HashMap<T, Integer>) pGraph.keyToNumber.clone();
        this.numberToKey = (HashMap<Integer, T>) pGraph.numberToKey.clone();
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
     * Adds a vertex to the graph. Doesn't check if the vertex is {@code null} or already exists.
     * @param pVertex Vertex to add to the graph.
     */
    public void addVertex(T pVertex)
    { addVertex(pVertex, LIST_CAPACITY); }

    /**
     * Adds a vertex to the graph. Doesn't check if the vertex is {@code null} or already exists.
     * @param pVertex Vertex to add to the graph.
     * @param pCapacityList The node's adjacency list capacity.
     */
    public void addVertex(T pVertex, int pCapacityList)
    {
        adjacentNumber.add(V, new ArrayList<>(pCapacityList));
        adjacentKey.add(V, new ArrayList<>(pCapacityList));
        keyToNumber.put(pVertex, V);
        numberToKey.put(V, pVertex);
        ++V;
    }

    /**
     * Adds a vertex to the graph. Checks if the vertex is {@code null} or if it already exists.
     * If it does, the vertex isn't added.
     * @param pVertex Vertex to add to the graph.
     */
    public void addVertexChecked(T pVertex)
    { addVertexChecked(pVertex, LIST_CAPACITY); }

    /**
     * Adds a vertex to the graph. Checks if the vertex is {@code null} or if it already exists.
     * If it does, the vertex isn't added.
     * @param pVertex Vertex to add to the graph.
     * @param pCapacityList The node's adjacency list capacity.
     */
    public void addVertexChecked(T pVertex, int pCapacityList)
    {
        if(pVertex == null || keyToNumber.containsKey(pVertex))
            return;
        addVertex(pVertex, pCapacityList);
    }

    /**
     * Allows self-cycles and doesn't check if the vertices are {@code null} or if the edge already exists. For this, use addEdgeChecked.
     * Adds an edge between two vertices. If it is the case that the edge is a self-cycle, it will add it once.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    public void addEdge(T pVertex1, T pVertex2)
    {
        Integer num1, num2;
        if(pVertex1.equals(pVertex2))
        {
            num1 = keyToNumber.get(pVertex1);
            adjacentNumber.get(num1).add(num1);
            adjacentKey.get(num1).add(pVertex1);
            ++E;
        }
        else
        {
            num1 = keyToNumber.get(pVertex1);
            num2 = keyToNumber.get(pVertex2);
            addEdge(pVertex1, pVertex2, num1, num2);
        }
    }

    /**
     * Private procedure to add vertices in their respective lists.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     * @param pVertexNum1 The numerical key of the first vertex.
     * @param pVertexNum2 The numerical key of the second vertex.
     */
    protected void addEdge(T pVertex1, T pVertex2, Integer pVertexNum1, Integer pVertexNum2)
    {
        adjacentNumber.get(pVertexNum1).add(pVertexNum2);
        adjacentNumber.get(pVertexNum2).add(pVertexNum1);
        adjacentKey.get(pVertexNum1).add(pVertex2);
        adjacentKey.get(pVertexNum2).add(pVertex1);
        ++E;
    }

    /**
     * Doesn't allow self-cycles and checks if the vertices are not {@code null} and if the edge exists.
     * Adds an edge between two vertices if the vertices are not {@code null}, not equal and the edge doesn't already exist.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    public void addEdgeChecked(T pVertex1, T pVertex2)
    {
        if(pVertex1 == null || pVertex2 == null || pVertex1.equals(pVertex2))
            return;
        Integer num1 = keyToNumber.get(pVertex1);
        Integer num2 = keyToNumber.get(pVertex2);
        if(num1 == null || num2 == null)
            return;
        if(adjacentKey.get(num1).size() > adjacentKey.get(num2).size())
        {
            if(adjacentNumber.get(num2).contains(num1))
                return;
        }
        else
        {
            if(adjacentNumber.get(num1).contains(num2))
                return;
        }
        addEdge(pVertex1, pVertex2, num1, num2);
    }

    /**
     * Doesn't check if pVertex is not {@code null} or exists. For this, use adjacentChecked.
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertices of the given vertex.
     */
    public Collection<T> adjacent(T pVertex)
    {
        Integer num = keyToNumber.get(pVertex);
        return adjacentKey.get(num);
    }

    /**
     * Checks that pVertex is not {@code null} and exists.
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertices of the given vertex or {@code null}
     * if pVertex is {@code null} or doesn't exist.
     */
    public Collection<T> adjacentChecked(T pVertex)
    {
        if(pVertex == null || !keyToNumber.containsKey(pVertex))
            return null;
        Integer num = keyToNumber.get(pVertex);
        return adjacentKey.get(num);
    }

    /**
     * @return Collection of collections corresponding to the adjacent vertices of each vertex.
     */
    public Collection<Collection<T>> adjacent()
    { return Collections.unmodifiableList(adjacentKey); }

    // Extra methods for algorithms

    /**
     * @return The HashMap containing each vertex with its assigned numerical value from 0 to V-1
     */
    public HashMap<T, Integer> keyToNumber()
    { return keyToNumber; }

    /**
     * @return The HashMap containing the numerical value from 0 to V-1 assigned to each vertex.
     */
    public HashMap<Integer, T> numberToKey()
    { return numberToKey; }

    /**
     * Doesn't check if pVertex is not {@code null} or exists.
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertices of the given vertex.
     */
    public ArrayList<Integer> adjacentNumber(T pVertex)
    {
        Integer num = keyToNumber.get(pVertex);
        return adjacentNumber.get(num);
    }

    /**
     * @return ArrayList of ArrayList corresponding to the adjacent vertices of each vertex.
     */
    public ArrayList<ArrayList<Integer>> adjacentNumber()
    { return adjacentNumber; }
}