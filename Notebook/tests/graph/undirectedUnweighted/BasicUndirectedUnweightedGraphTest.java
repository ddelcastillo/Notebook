// @formatter:off

package graph.undirectedUnweighted;

import graph.algorithms.search.BasicBFS;
import graph.algorithms.search.BasicDFS;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Class that tests the UndirectedUnweightedGraph class.
 */
public class BasicUndirectedUnweightedGraphTest
{
    // Attributes

    /**
     * The graph.
     */
    private BasicUndirectedUnweightedGraph graph;

    // Setups

    /**
     * Initializes the graph with 5 nodes.
     */
    @Before
    public void setup()
    { graph = new BasicUndirectedUnweightedGraph(5); }

    // Tests

    /**
     * Tests that the graph is initialized properly.
     */
    @Test
    public void initializationTest1()
    {
        assertNotNull("The graph shouldn't be null.", graph);
        assertEquals("The number of edges should be 0.", 0, graph.E());
        assertEquals("The number of vertexes should be 5.", 5, graph.V());
        assertEquals("The number of lists should be 5.", 5, graph.adjacent().length);
        // We check that the adjacent list of each vertex isn't null and is empty.
        for(int i = 0; i < 5; ++i)
        {
            assertNotNull("The list shouldn't be null.", graph.adjacent(i));
            assertTrue("The list should be empty.", graph.adjacent(i).isEmpty());
        }
    }

    /**
     * Tests that the graph is initialized properly.
     */
    @Test
    public void initializationTest2()
    {
        BasicUndirectedUnweightedGraph newGraph = new BasicUndirectedUnweightedGraph(graph);
        assertNotNull("The graph shouldn't be null.", newGraph);
        assertEquals("The number of edges should be 0.", 0, newGraph.E());
        assertEquals("The number of vertexes should be 5.", 5, newGraph.V());
        assertEquals("The number of lists should be 5.", 5, graph.adjacent().length);
        // We check that the adjacent list of each vertex isn't null and is empty.
        for(int i = 0; i < 5; ++i)
        {
            assertNotNull("The list shouldn't be null.", newGraph.adjacent(i));
            assertTrue("The list should be empty.", newGraph.adjacent(i).isEmpty());
        }
    }

    /**
     * Tests that a graph copy stores information properly.
     */
    @Test
    public void initializationTest3()
    {
        graph.addEdge(0, 4); graph.addEdge(1, 2);
        BasicUndirectedUnweightedGraph newGraph = new BasicUndirectedUnweightedGraph(graph);
        assertNotNull("The graph shouldn't be null.", newGraph);
        assertEquals("The number of edges should be 2.", 2, newGraph.E());
        assertEquals("The number of vertexes should be 5.", 5, newGraph.V());
        assertEquals("The number of lists should be 5.", 5, newGraph.adjacent().length);
        // We check that the adjacent list of each vertex isn't null. List should have 1 or 0 vertexes.
        for(int i = 0; i < 5; ++i)
        {
            assertNotNull("The list shouldn't be null.", newGraph.adjacent(i));
            if(i == 3)
                assertEquals("The list should be empty.", 0, newGraph.adjacent(i).size());
            else
                assertEquals("The list should contain one vertex.", 1, newGraph.adjacent(i).size());
        }
    }

    /**
     * Tests that the graph adds edges properly.
     */
    @Test
    public void addEdgeTest1()
    {
        // We will add the edge 0-4.
        graph.addEdge(0, 4);
        assertEquals("The number of edges should be 1.", 1, graph.E());
        assertEquals("The number of vertexes should be 5.", 5, graph.V());
        assertEquals("The size of the list should be 1.", 1, graph.adjacent(0).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(1).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(2).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(3).size());
        assertEquals("The size of the list should be 1.", 1, graph.adjacent(4).size());
        // We check that the items on the list are correct.
        for(Integer integer: graph.adjacent(0))
            assertEquals("The only item on the list should be 4.", 4, (int) integer);
        for(Integer integer: graph.adjacent(4))
            assertEquals("The only item on the list should be 0.", 0, (int) integer);
        // Now, the method should allow to add a self-cycle.
        graph.addEdge(0, 0);
        assertEquals("The number of edges should be 2.", 2, graph.E());
        assertEquals("The size of the list should be 2.", 2, graph.adjacent(0).size());
        // Nothing else should have changed.
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(1).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(2).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(3).size());
        assertEquals("The size of the list should be 1.", 1, graph.adjacent(4).size());
        // Now, the method should allow to add the same edge twice.
        graph.addEdge(4, 0);
        assertEquals("The number of edges should be 3.", 3, graph.E());
        assertEquals("The size of the list should be 3.", 3, graph.adjacent(0).size());
        assertEquals("The size of the list should be 2.", 2, graph.adjacent(4).size());
        // Nothing else should have changed.
        assertEquals("The number of vertexes should be 5.", graph.V(), 5);
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(1).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(2).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(3).size());
    }

    /**
     * Tests that adding an edge between invalid nodes ends up in an IndexOutOfBoundsException.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void addEdgeTest2()
    {
        graph.addEdge(200, 201);
    }

    /**
     * Tests that the graph adds edges properly and checks that no self-cycles or double edges are formed.
     */
    @Test
    public void addEdgeCheckedTest()
    {
        // We will add the edge 0-4.
        graph.addEdgeChecked(0, 4);
        assertEquals("The number of edges should be 1.", graph.E(), 1);
        assertEquals("The number of vertexes should be 5.", graph.V(), 5);
        assertEquals("The size of the list should be 1.", 1, graph.adjacent(0).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(1).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(2).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(3).size());
        assertEquals("The size of the list should be 1.", 1, graph.adjacent(4).size());
        // We check that the items on the list are correct.
        for(Integer integer: graph.adjacent(0))
            assertEquals("The only item on the list should be 4.", 4, (int) integer);
        for(Integer integer: graph.adjacent(4))
            assertEquals("The only item on the list should be 0.", 0, (int) integer);
        // Now, the method shouldn't allow to add a self-cycle.
        graph.addEdgeChecked(0, 0);
        assertEquals("The number of edges should be 1.", graph.E(), 1);
        assertEquals("The size of the list should be 1.", 1, graph.adjacent(0).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(1).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(2).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(3).size());
        assertEquals("The size of the list should be 1.", 1, graph.adjacent(4).size());
        // Now, the method shouldn't allow to add the same edge twice.
        graph.addEdgeChecked(4, 0);
        assertEquals("The number of edges should be 1.", 1, graph.E());
        assertEquals("The number of vertexes should be 5.", 5, graph.V());
        assertEquals("The size of the list should be 1.", 1, graph.adjacent(0).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(1).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(2).size());
        assertEquals("The size of the list should be 0.", 0, graph.adjacent(3).size());
        assertEquals("The size of the list should be 1.", 1, graph.adjacent(4).size());
        // Adding an edge between non-existing vertexes shouldn't throw any errors.
        graph.addEdgeChecked(200, 201);
        graph.addEdgeChecked(-1, -2);
        graph.addEdgeChecked(2, 700);
    }

    // TODO: test adjacent and adjacent checked.

    /**
     * Tests that the DFS algorithm works properly.
     */
    @Test
    public void DFSTest()
    {
        BasicUndirectedUnweightedGraph newGraph = new BasicUndirectedUnweightedGraph(7);
        // The following edges are added: 0-1, 0-2, 2-3, 2-4, 1-4 and 5-6.
        newGraph.addEdge(0, 1); newGraph.addEdge(0, 2); newGraph.addEdge(2, 3);
        newGraph.addEdge(2, 4); newGraph.addEdge(1, 4); newGraph.addEdge(5, 6);
        // A DFS from the origin is created.
        BasicDFS dfs = new BasicDFS(newGraph, 0);
        // There should be a path from all vertexes except 5 and 6.
        for(int i = 0; i < 5; ++i)
            assertTrue("There should be a path to the vertex " + i + ".", dfs.hasPathTo(i));
        for(int i = 5; i < 7; ++i)
            assertFalse("There shouldn't be a path to the vertex " + i + ".", dfs.hasPathTo(i));
        // The DFS checks the vertexes in the list in added order instead of numerical order, therefore,
        // since the 0-1 edge was first added for the vertex 0, the first edge checked from 0 will be 0-1,
        // then the same for the vertex 1 and so on.
        // Now we check the paths.
        ArrayList<Integer> vertexes;
        Iterable<Integer> path;
        for(int i = 1; i < 7; ++i)
        {
            vertexes = new ArrayList<>();
            path = dfs.pathTo(i);
            switch(i)
            {
                case 1:
                    // Path from 0 to 1 should be 0, 1 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 2.", 2, vertexes.size());
                    assertEquals("The first item should be 1.", 1, (int) vertexes.get(0));
                    assertEquals("The second item should be 0.", 0, (int) vertexes.get(1));
                    break;
                case 2:
                    // Path from 0 to 2 should be 0, 1, 4, 2 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 4.", 4, vertexes.size());
                    assertEquals("The first item should be 2.", 2, (int) vertexes.get(0));
                    assertEquals("The second item should be 4.", 4, (int) vertexes.get(1));
                    assertEquals("The third item should be 1.", 1, (int) vertexes.get(2));
                    assertEquals("The fourth item should be 0.", 0, (int) vertexes.get(3));
                    break;
                case 3:
                    // Path from 0 to 3 should be 0, 1, 4, 2, 3 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 5.", 5, vertexes.size());
                    assertEquals("The first item should be 3.", 3, (int) vertexes.get(0));
                    assertEquals("The second item should be 2.", 2, (int) vertexes.get(1));
                    assertEquals("The third item should be 4.", 4, (int) vertexes.get(2));
                    assertEquals("The fourth item should be 1.", 1, (int) vertexes.get(3));
                    assertEquals("The fifth item should be 0.", 0, (int) vertexes.get(4));
                    break;
                case 4:
                    // Path from 0 to 4 should be 0, 1, 4 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 3.", 3, vertexes.size());
                    assertEquals("The first item should be 4.", 4, (int) vertexes.get(0));
                    assertEquals("The second item should be 1.", 1, (int) vertexes.get(1));
                    assertEquals("The third item should be 0.", 0, (int) vertexes.get(2));
                    break;
                case 5:
                case 6:
                    // There should be no path from 0 to 5 or 6, therefore, the path should be null.
                    assertNull(path);
                    break;
            }
        }
    }

    /**
     * Tests that the BFS algorithm works properly.
     */
    @Test
    public void BFSTest()
    {
        BasicUndirectedUnweightedGraph newGraph = new BasicUndirectedUnweightedGraph(7);
        // The following edges are added: 0-1, 0-2, 2-3, 2-4, 1-4 and 5-6.
        newGraph.addEdge(0, 1); newGraph.addEdge(0, 2); newGraph.addEdge(2, 3);
        newGraph.addEdge(2, 4); newGraph.addEdge(1, 4); newGraph.addEdge(5, 6);
        // A DFS from the origin is created.
        BasicBFS bfs = new BasicBFS(newGraph, 0);
        // There should be a path from all vertexes except 5 and 6.
        for(int i = 0; i < 5; ++i)
            assertTrue("There should be a path to the vertex " + i + ".", bfs.hasPathTo(i));
        for(int i = 5; i < 7; ++i)
            assertFalse("There shouldn't be a path to the vertex " + i + ".", bfs.hasPathTo(i));
        // The BFS checks the vertexes in the list in added order instead of numerical order, therefore,
        // since the 0-1 edge was first added for the vertex 0, the first vertex added to the queue is 1,
        // then the same for each vertex remaining in the adjacent list.
        ArrayList<Integer> vertexes;
        Iterable<Integer> path;
        for(int i = 1; i < 7; ++i)
        {
            vertexes = new ArrayList<>();
            path = bfs.pathTo(i);
            switch(i)
            {
                case 1:
                    // Path from 0 to 1 should be 0, 1 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 2.", 2, vertexes.size());
                    assertEquals("The first item should be 1.", 1, (int) vertexes.get(0));
                    assertEquals("The second item should be 0.", 0, (int) vertexes.get(1));
                    break;
                case 2:
                    // Path from 0 to 2 should be 0, 2 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 2.", 2, vertexes.size());
                    assertEquals("The first item should be 2.", 2, (int) vertexes.get(0));
                    assertEquals("The second item should be 0.", 0, (int) vertexes.get(1));
                    break;
                case 3:
                    // Path from 0 to 3 should be 0, 2, 3 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 3.", vertexes.size(), 3);
                    assertEquals("The first item should be 3.", 3, (int) vertexes.get(0));
                    assertEquals("The second item should be 2.", 2, (int) vertexes.get(1));
                    assertEquals("The third item should be 0.", 0, (int) vertexes.get(2));
                    break;
                case 4:
                    // Path from 0 to 4 should be 0, 1, 4 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 3.", 3, vertexes.size());
                    assertEquals("The first item should be 4.", 4, (int) vertexes.get(0));
                    assertEquals("The second item should be 1.", 1, (int) vertexes.get(1));
                    assertEquals("The third item should be 0.", 0, (int) vertexes.get(2));
                    break;
                case 5:
                case 6:
                    // There should be no path from 0 to 5 or 6, therefore, the path should be null.
                    assertNull(path);
                    break;
            }
        }
    }
}