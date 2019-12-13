// @formatter:off

package graph.undirectedUnweighted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

// TODO finish the implementation and do the tests of the graph. Maybe implement more constructors.
// TODO the structure remains unfinished and untested.

/**
 * Represents a generic undirected unweighted graph.
 */
public class UndirectedUnweightedGraph<K extends Comparable<K>>
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
     * The array of adjacent list of vertexes for each vertex as key.
     */
    private ArrayList<ArrayList<K>> adjacentKey;

    /**
     * The map that accesses the given number to a certain key.
     */
    private HashMap<K, Integer> keyToNumber;

    /**
     * The map that accesses the assigned key of a given number.
     */
    private HashMap<Integer, K> numberToKey;

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
    public UndirectedUnweightedGraph(K[] pKeys)
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
    public UndirectedUnweightedGraph(K[] pKeys, int pCapacityLists)
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
        this.adjacentKey = (ArrayList<ArrayList<K>>) pGraph.adjacentKey.clone();
        this.keyToNumber = (HashMap<K, Integer>) pGraph.keyToNumber.clone();
        this.numberToKey = (HashMap<Integer, K>) pGraph.numberToKey.clone();
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
    public void addVertex(K pVertex)
    { addVertex(pVertex, LIST_CAPACITY); }

    /**
     * Adds a vertex to the graph. Doesn't check if the vertex already exists.
     * @param pVertex Vertex to add to the graph.
     * @param pCapacityList The node's adjacency list capacity.
     */
    public void addVertex(K pVertex, int pCapacityList)
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
    public void addVertexChecked(K pVertex)
    { addVertexChecked(pVertex, LIST_CAPACITY); }

    /**
     * Adds a vertex to the graph. Checks if the vertex already exists. If it does,
     * the vertex isn't added.
     * @param pVertex Vertex to add to the graph.
     * @param pCapacityList The node's adjacency list capacity.
     */
    public void addVertexChecked(K pVertex, int pCapacityList)
    {
        if(keyToNumber.containsKey(pVertex))
            return;
        addVertex(pVertex, pCapacityList);
    }

    /**
     * Adds an edge between two vertexes. Doesn't check for duplicates and allows self-cycles.
     * If it is the case that the edge is a self-cycle, it will add it once.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    public void addEdge(K pVertex1, K pVertex2)
    {
        // So that it doesn't add it twice.
        int num, num1, num2;
        if(pVertex1.compareTo(pVertex2) == 0)
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
     * Adds an edge between two vertexes. Checks for duplicates and doesn't allow self-cycles.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    public void addEdgeChecked(K pVertex1, K pVertex2)
    {
        if(pVertex1.equals(pVertex2))
            return;
        Integer num1 = keyToNumber.get(pVertex1);
        Integer num2 = keyToNumber.get(pVertex2);
        if(num1 == null || num2 == null)
            return;
        if(adjacentKey.get(num1).size() > adjacentKey.get(num2).size())
            if(adjacentNumber.get(num1).contains(num2))
                return;
        else
            if(adjacentNumber.get(num2).contains(num1))
                return;
        adjacentNumber.get(num1).add(num2);
        adjacentNumber.get(num2).add(num1);
        adjacentKey.get(num1).add(pVertex2);
        adjacentKey.get(num2).add(pVertex1);
        ++E;
    }

    // TODO finish implementation of adjacent method.

    /**
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertexes of the given vertex, null
     * if the vertex is invalid (less than 0 or greater than N-1) or the vertex hasn't been added.
     */
    public Collection<K> adjacent(K pVertex)
    {
        Integer num = keyToNumber.get(pVertex);
        ArrayList<Integer> adjNum = adjacentNumber.get(num);
        ArrayList<K> adjKey = new ArrayList<>(adjNum.size());
        for(Integer i : adjNum)
            adjKey.add(numberToKey.get(i));
        return adjKey;
    }
}
