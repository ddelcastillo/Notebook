// @formatter:off

package Graph;

import UnionFinder.BasicUnionFinder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

/**
 * Represents a simple numerical graph for N vertexes labeled from 0 to N-1.
 */
public class BasicUndirectedUnweightedCCGraph
{
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
    private ArrayList<Integer>[] adjacent;

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
        V = N;
        E = 0;
        adjacent = (ArrayList<Integer>[]) new ArrayList[N];
        unionFinder = new BasicUnionFinder(N);
        for(int v = 0; v < V; ++v)
            adjacent[v] = new ArrayList<>();
    }

    /**
     * Creates a BasicUndirectedUnweightedGraph object copy of the given graph.
     * @param pGraph The graph to copy.
     */
    public BasicUndirectedUnweightedCCGraph(BasicUndirectedUnweightedCCGraph pGraph)
    {
        this.V = pGraph.V;
        this.E = pGraph.E;
        this.adjacent = (ArrayList<Integer>[]) new ArrayList[pGraph.V];
        this.unionFinder = new BasicUnionFinder(pGraph.unionFinder);
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
        unionFinder.merge(pVertex1, pVertex2);
        ++E;
    }

    /**
     * Adds an edge between two vertexes. Checks for duplicates and doesn't allow self-cycles.
     * @param pVertex1 The first vertex.
     * @param pVertex2 The second vertex.
     */
    public void addEdgeChecked(int pVertex1, int pVertex2)
    {
        if(pVertex1 == pVertex2 || adjacent[pVertex1].contains(pVertex2))
            return;
        adjacent[pVertex1].add(pVertex2);
        adjacent[pVertex2].add(pVertex1);
        unionFinder.merge(pVertex1, pVertex2);
        ++E;
    }

    /**
     * @param pVertex The vertex whose adjacent collection is desired.
     * @return Collection corresponding to the adjacent vertexes of the given vertex.
     */
    public Collection<Integer> adjacent(int pVertex)
    { return adjacent[pVertex]; }

    // Connected components methods

    /**
     * @return The number of components in the graph.
     */
    public int numberOfComponents()
    { return unionFinder.totalRoots(); }

    /**
     * @param pVertex The vertex whose component size is desired.
     * @return The size of the component that the given vertex is a part of.
     */
    public int sizeOfComponent(int pVertex)
    { return unionFinder.size(pVertex); }

    // Search

    public static class Search
    {
        // Attributes

        /**
         * Represents the marked vertexes.
         */
        protected boolean[] marked;

        /**
         * Array that stores the parent of each vertex.
         */
        protected int[] edgeTo;

        /**
         * The origin vertex from which the search starts.
         */
        protected int origin;

        // Constructor

        /**
         * Creates a Search object that uses the given graph.
         * @param pGraph Graph to use for the algorithm.
         */
        public Search(BasicUndirectedUnweightedCCGraph pGraph, int pOrigin)
        {
            marked = new boolean[pGraph.V];
            edgeTo = new int[pGraph.V];
            origin = pOrigin;
        }

        // Methods

        /**
         * @param pVertex Vertex to check if it has a path from the origin vertex.
         * @return True if there's a path to the vertex from the origin, false if contrary.
         */
        public boolean hasPathTo(int pVertex)
        { return marked[pVertex]; }

        /**
         * @param pVertex Vertex whose path from the origin is desired.
         * @return The path from the origin to the given vertex, null if there's no path.
         */
        public Iterable<Integer> pathTo(int pVertex)
        {
            if(!hasPathTo(pVertex))
                return null;
            Stack<Integer> path = new Stack<>();
            for(int v = pVertex; v != origin; v = edgeTo[v])
                path.push(v);
            path.push(origin);
            return path;
        }
    }

    // DFS

    /**
     * Represents a depth first search for a simple numerical undirected unweighted graph.
     */
    public static class BasicDFS extends Search
    {
        // Constructor

        /**
         * Creates a BasicDFS object that uses the given graph and starts from the given vertex.
         * @param pGraph Graph to use for the algorithm.
         * @param pOrigin Vertex from which the search starts.
         */
        public BasicDFS(BasicUndirectedUnweightedCCGraph pGraph, int pOrigin)
        {
            super(pGraph, pOrigin);
            basicDFS(pGraph, pOrigin);
        }

        // Methods

        /**
         * Recursive auxiliary method to find the paths from the origin vertex.
         * @param pGraph Graph to use for the algorithm.
         * @param pVertex Vertex from which the search starts.
         */
        private void basicDFS(BasicUndirectedUnweightedCCGraph pGraph, int pVertex)
        {
            marked[pVertex] = true;
            for(int vertex : pGraph.adjacent(pVertex))
            {
                if(!marked[vertex])
                {
                    edgeTo[vertex] = pVertex;
                    basicDFS(pGraph, vertex);
                }
            }
        }
    }

    // BFS

    /**
     * Represents a breath first search for a simple numerical undirected unweighted graph.
     */
    public static class BasicBFS extends Search
    {
        // Constructor

        /**
         * Creates a BasicDFS object that uses the given graph and starts from the given vertex.
         * @param pGraph Graph to use for the algorithm.
         * @param pOrigin Vertex from which the search starts.
         */
        public BasicBFS(BasicUndirectedUnweightedCCGraph pGraph, int pOrigin)
        {
            super(pGraph, pOrigin);
            basicBFS(pGraph, pOrigin);
        }

        // Methods

        /**
         * Auxiliary method to find the paths from the origin vertex.
         * @param pGraph Graph to use for the algorithm.
         * @param pVertex Vertex from which the search starts.
         */
        private void basicBFS(BasicUndirectedUnweightedCCGraph pGraph, int pVertex)
        {
            ArrayDeque<Integer> queue = new ArrayDeque<>();
            marked[pVertex] = true;
            queue.add(pVertex);
            int vertex;
            while(!queue.isEmpty())
            {
                vertex = queue.remove();
                for(int adjacent : pGraph.adjacent(vertex))
                {
                    if(!marked[adjacent])
                    {
                        edgeTo[adjacent] = vertex;
                        marked[adjacent] = true;
                        queue.add(adjacent);
                    }
                }
            }
        }
    }
}
