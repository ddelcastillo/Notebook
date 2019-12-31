// @formatter:off

package graph.undirectedUnweighted;

import unionFinder.ExpandableBasicUnionFinder;
import java.util.ArrayList;

public class UndirectedUnweightedCCGraph<T> extends UndirectedUnweightedGraph<T>
{
    // Attributes

    private ExpandableBasicUnionFinder unionFinder;

    // Constructor

    /**
     * Creates an UndirectedUnweightedCCGraph object.
     */
    public UndirectedUnweightedCCGraph()
    {
        super();
        unionFinder = new ExpandableBasicUnionFinder();
    }

    /**
     * Creates an UndirectedUnweightedCCGraph object with the given initial capacity.
     * @param pInitialCapacity The initial capacity of the graph's nodes.
     */
    public UndirectedUnweightedCCGraph(int pInitialCapacity)
    {
        super(pInitialCapacity);
        unionFinder = new ExpandableBasicUnionFinder();
    }

    /**
     * Creates an UndirectedUnweightedCCGraph object with the given keys (nodes).
     * @param pKeys The graph's initial keys (nodes).
     */
    public UndirectedUnweightedCCGraph(T[] pKeys)
    {
        super(pKeys);
        unionFinder = new ExpandableBasicUnionFinder(pKeys.length);
    }

    /**
     * Creates an UndirectedUnweightedCCGraph object with the given keys (nodes).
     * @param pKeys The graph's initial keys (nodes).
     * @param pCapacityLists The graph's adjacency list capacity for each node.
     */
    public UndirectedUnweightedCCGraph(T[] pKeys, int pCapacityLists)
    {
        super(pKeys, pCapacityLists);
        unionFinder = new ExpandableBasicUnionFinder(pKeys.length);
    }

    /**
     * Creates an UndirectedUnweightedCCGraph object copy of the given graph.
     * @param pGraph The graph to copy.
     */
    public UndirectedUnweightedCCGraph(UndirectedUnweightedCCGraph<T> pGraph)
    {
        super(pGraph);
        unionFinder = new ExpandableBasicUnionFinder(pGraph.unionFinder);
    }

    // Methods

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
        unionFinder.add(V);
        ++V;
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
            unionFinder.merge(num1, num2);
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
        unionFinder.merge(num1, num2);
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
    public int sizeOfComponent(T pVertex)
    { return unionFinder.size(keyToNumber.get(pVertex)); }

    /**
     * Checks that pVertex is a valid vertex.
     * @param pVertex The vertex whose component size is desired.
     * @return The size of the component that the given vertex is a part of or {@code null} if the vertex is invalid.
     */
    public Integer sizeOfComponentChecked(T pVertex)
    {
        if(pVertex == null || !keyToNumber.containsKey(pVertex))
            return null;
        else
            return unionFinder.sizeChecked(keyToNumber.get(pVertex));
    }
}