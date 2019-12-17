// @formatter:off

package graph.undirectedUnweighted;

import graph.IGraph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

// TODO finish the implementation and do the tests of the graph. Maybe implement more constructors.
// TODO the structure remains unfinished and untested.

/**
 * Represents a generic undirected unweighted graph.
 */
public class UndirectedUnweightedGraph<T> implements IGraph
{
    // Constants

    /**
     * Represents the initial capacity of the graph's tables (nodes and edges).
     */
    private static final int INITIAL_CAPACITY = 7;

    /**
     * Represents the initial capacity of a node's adjacent list.
     */
    private static int LIST_CAPACITY = 5;

    // Attributes

    /**
     * The number of vertexes.
     */
    private int V;

    /**
     * The number of edges.
     */
    private int E;

    /**
     * The array of adjacent list of vertexes for each vertex as numbers.
     */
    private ArrayList<ArrayList<Integer>> adjacentNumber;

    // TODO complete integrate the adjacent of just keys.
    /**
     * The array of adjacent list of vertexes for each vertex as keys.
     */
    private ArrayList<ArrayList<T>> adjacentKey;

    /**
     * The map that accesses the given number to a certain key.
     */
    private HashMap<T, Integer> keyToNumber;

    /**
     * The map that accesses the assigned key of a given number.
     */
    private HashMap<Integer, T> numberToKey;

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
            adjacentNumber.add(new ArrayList<>(LIST_CAPACITY));
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
            adjacentNumber.add(new ArrayList<>(pCapacityLists));
            keyToNumber.put(pKeys[i], i);
            numberToKey.put(i, pKeys[i]);
        }
    }

    /**
     * Creates an UndirectedUnweightedGraph object copy of the given graph.
     * @param pGraph The graph to copy.
     */
    public UndirectedUnweightedGraph(UndirectedUnweightedGraph pGraph)
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
     * Adds a vertex to the graph. Doesn't check if the vertex already exists.
     * @param pVertex Vertex to add to the graph.
     */
    public void addVertex(T pVertex)
    { addVertex(pVertex, LIST_CAPACITY); }

    /**
     * Adds a vertex to the graph. Doesn't check if the vertex already exists.
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
     * Adds a vertex to the graph. Checks if the vertex already exists. If it does,
     * the vertex isn't added.
     * @param pVertex Vertex to add to the graph.
     */
    public void addVertexChecked(T pVertex)
    { addVertexChecked(pVertex, LIST_CAPACITY); }

    /**
     * Adds a vertex to the graph. Checks if the vertex already exists. If it does,
     * the vertex isn't added.
     * @param pVertex Vertex to add to the graph.
     * @param pCapacityList The node's adjacency list capacity.
     */
    public void addVertexChecked(T pVertex, int pCapacityList)
    {
        if(keyToNumber.containsKey(pVertex))
            return;
        addVertex(pVertex, pCapacityList);
    }

    /**
     * Allows self-cycles and doesn't check if the vertexes are {@code null} or if the edge already exists. For this, use addEdgeChecked.
     * Adds an edge between two vertexes. If it is the case that the edge is a self-cycle, it will add it once.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    public void addEdge(T pVertex1, T pVertex2)
    {
        int num, num1, num2;
        if(pVertex1.equals(pVertex2))
        {
            num = keyToNumber.get(pVertex1);
            adjacentNumber.get(num).add(num);
            adjacentKey.get(num).add(pVertex1);
        }
        else
        {
            num1 = keyToNumber.get(pVertex1);
            num2 = keyToNumber.get(pVertex2);
            adjacentNumber.get(num1).add(num2);
            adjacentNumber.get(num2).add(num1);
            adjacentKey.get(num1).add(pVertex2);
            adjacentKey.get(num2).add(pVertex1);
        }
        ++E;
    }

    /**
     * Doesn't allow self-cycles and checks if the vertexes are not {@code null} and if the edge exists.
     * Adds an edge between two vertexes if the vertexes are not {@code null}, not equal and the edge doesn't already exist.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    public void addEdgeChecked(T pVertex1, T pVertex2)
    {
        if(pVertex1 == null || pVertex2 == null)
            return;
        if(pVertex1.equals(pVertex2))
            return;
        Integer num1 = keyToNumber.get(pVertex1);
        Integer num2 = keyToNumber.get(pVertex2);
        if(num1 == null || num2 == null)
            return;
        if(adjacentKey.get(num1).size() > adjacentKey.get(num2).size())
            if(adjacentNumber.get(num2).contains(num1))
                return;
        else
            if(adjacentNumber.get(num1).contains(num2))
                return;
        adjacentNumber.get(num1).add(num2);
        adjacentNumber.get(num2).add(num1);
        adjacentKey.get(num1).add(pVertex2);
        adjacentKey.get(num2).add(pVertex1);
        ++E;
    }

    // TODO finish implementation of adjacent method.

    /**
     * Doesn't check if pVertex is not {@code null} or exists. For this, use adjacentChecked.
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertexes of the given vertex.
     */
    public Collection<T> adjacent(T pVertex)
    {
        Integer num = keyToNumber.get(pVertex);
        return adjacentKey.get(num);
    }

    /**
     * Checks that pVertex is not {@code null} and exists.
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertexes of the given vertex or {@code null}
     * if pVertex is {@code null} or doesn't exist.
     */
    public Collection<T> adjacentChecked(T pVertex)
    {
        Integer num = keyToNumber.get(pVertex);
        return adjacentKey.get(num);
    }

    /**
     * @return Collection of collections corresponding to the adjacent vertexes of each vertex.
     */
    public Collection<Collection<T>> adjacent()
    { return Collections.unmodifiableList(adjacentKey); }
}