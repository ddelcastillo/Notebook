// @formatter:off

package Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

// TODO finish the implementation and do the tests of the graph. Maybe implement more constructors.
// TODO the structure remains unfinished and untested.

/**
 * Represents a generic undirected unweighted graph.
 */
public class UndirectedUnweightedGraph<K>
{
    // Constants

    /**
     * Represents the initial capacity of the graph's tables (nodes and edges).
     */
    private static final int INITIAL_CAPACITY = 7;

    /**
     * Represents the initial capacity of a node's adjacent list.
     */
    private static int LIST_CAPACITY = 10;

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
     * The array of adjacent list for each vertex.
     */
    private ArrayList<ArrayList<Integer>> adjacent;

    /**
     * The table that accesses the given number to a certain key.
     */
    private Hashtable<K, Integer> keyToNumber;

    /**
     * The table that accesses the assigned key of a given number.
     */
    private Hashtable<Integer, K> numberToKey;

    // Constructor

    /**
     * Creates an UndirectedUnweightedGraph object.
     */
    public UndirectedUnweightedGraph()
    {
        V = 0;
        E = 0;
        adjacent = new ArrayList<>(INITIAL_CAPACITY);
        keyToNumber = new Hashtable<>(INITIAL_CAPACITY);
        numberToKey = new Hashtable<>(INITIAL_CAPACITY);
    }

    /**
     * Creates an UndirectedUnweightedGraph object with the given initial capacity.
     * @param pInitialCapacity The initial capacity of the graph's nodes.
     */
    public UndirectedUnweightedGraph(int pInitialCapacity)
    {
        V = 0;
        E = 0;
        adjacent = new ArrayList<>(pInitialCapacity);
        keyToNumber = new Hashtable<>(pInitialCapacity);
        numberToKey = new Hashtable<>(pInitialCapacity);
    }

    /**
     * Creates an UndirectedUnweightedGraph object with the given keys (nodes).
     * @param pKeys The graph's initial keys (nodes).
     */
    public UndirectedUnweightedGraph(K[] pKeys)
    {
        V = pKeys.length;
        E = 0;
        adjacent = new ArrayList<>(pKeys.length);
        keyToNumber = new Hashtable<>(pKeys.length);
        numberToKey = new Hashtable<>(pKeys.length);
        for(int i = 0; i < pKeys.length; ++i)
        {
            adjacent.add(new ArrayList<>(LIST_CAPACITY));
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
        adjacent = new ArrayList<>(pKeys.length);
        keyToNumber = new Hashtable<>(pKeys.length);
        numberToKey = new Hashtable<>(pKeys.length);
        for(int i = 0; i < pKeys.length; ++i)
        {
            adjacent.add(new ArrayList<>(pCapacityLists));
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
        this.adjacent = (ArrayList<ArrayList<Integer>>) pGraph.adjacent.clone();
        this.keyToNumber = (Hashtable<K, Integer>) pGraph.keyToNumber.clone();
        this.numberToKey = (Hashtable<Integer, K>) pGraph.numberToKey.clone();
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
        adjacent.add(V, new ArrayList<>(pCapacityList));
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
        adjacent.add(V, new ArrayList<>(pCapacityList));
        keyToNumber.put(pVertex, V);
        numberToKey.put(V, pVertex);
        ++V;
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
        if(pVertex1.equals(pVertex2))
        {
            int num = keyToNumber.get(pVertex1);
            adjacent.get(num).add(num);
        }
        else
        {
            int num1 = keyToNumber.get(pVertex1);
            int num2 = keyToNumber.get(pVertex2);
            adjacent.get(num1).add(num2);
            adjacent.get(num2).add(num1);
            ++E;
        }
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
        int num1 = keyToNumber.get(pVertex1);
        int num2 = keyToNumber.get(pVertex2);
        if(adjacent.get(num1).contains(num2))
            return;
        adjacent.get(num1).add(num2);
        adjacent.get(num2).add(num1);
        ++E;
    }

    /**
     * <b>WARNING</b>: May cause incoherence and errors if it adds ghost edges.
     * Replaces the vertex's edges by the ones given. Updates the number of edges.
     * The method does not update the vertexes in the given edge collection to have the given
     * vertex as an adjacent vertex, in the case they don't. Doesn't check if a vertex in the collection
     * doesn't exist. Doesn't check if the given vertex exists. For all that, use the setEdgesChecked method.
     * This means that if the collection of edge Y has edge X, then edge X won't be checked to
     * ensure it exists and contains edge Y in its adjacent's list.
     * @param pVertex The vertex whose edges will be replaced.
     * @param pEdges The vertex's new collection of edges.
     */
    public void setEdges(K pVertex, Collection<K> pEdges)
    {
        int num = keyToNumber.get(pVertex);
        ArrayList<Integer> newEdges = new ArrayList<>(pEdges.size());
        for(K key : pEdges)
            newEdges.add(keyToNumber.get(key));
        adjacent.set(num, newEdges);
        E += (pEdges.size() - adjacent.get(num).size());
    }

    // TODO finish and check implementation.
    /**
     * WARNING: doesn't check for duplicates in the given edges.
* @param pVertex
* @param pEdges
     */
    public void setEdgesChecked(K pVertex, Collection<K> pEdges)
    {
        int num, numTemp, numEdges;
        num = keyToNumber.get(pVertex);
        numEdges = -adjacent.get(num).size();
        ArrayList<Integer> edges = new ArrayList<>(pEdges.size());
        for(K key : pEdges)
        {
            numTemp = keyToNumber.get(key);
            if(!adjacent.get(numTemp).contains(num))
            { adjacent.get(numTemp).add(num); ++numEdges; }
            edges.add(numTemp);
        }
    }

    public Collection<K> adjacent(K pVertex)
    {
        int num = keyToNumber.get(pVertex);
        ArrayList<Integer> adjNum = adjacent.get(num);
        ArrayList<K> adjKey = new ArrayList<>(adjNum.size());
        for(Integer i : adjNum)
            adjKey.add(numberToKey.get(i));
        return adjKey;
    }
}
