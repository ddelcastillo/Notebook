// @formatter:off

package Graph;

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
    public void initialization1()
    {
        assertNotNull("The graph shouldn't be null.", graph);
        assertEquals("The number of edges should be 0.", graph.E(), 0);
        assertEquals("The number of vertexes should be 5.", graph.V(), 5);
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
    public void initialization2()
    {
        BasicUndirectedUnweightedGraph newGraph = new BasicUndirectedUnweightedGraph(graph);
        assertNotNull("The graph shouldn't be null.", newGraph);
        assertEquals("The number of edges should be 0.", newGraph.E(), 0);
        assertEquals("The number of vertexes should be 5.", newGraph.V(), 5);
        // We check that the adjacent list of each vertex isn't null and is empty.
        for(int i = 0; i < 5; ++i)
        {
            assertNotNull("The list shouldn't be null.", newGraph.adjacent(i));
            assertTrue("The list should be empty.", newGraph.adjacent(i).isEmpty());
        }
    }

    /**
     * Tests that the graph adds edges properly.
     */
    @Test
    public void testAddEdge()
    {
        // We will add the edge 0-4.
        graph.addEdge(0, 4);
        assertEquals("The number of edges should be 1.", graph.E(), 1);
        assertEquals("The number of vertexes should be 5.", graph.V(), 5);
        assertEquals("The size of the list should be 1.", graph.adjacent(0).size(), 1);
        assertEquals("The size of the list should be 0.", graph.adjacent(1).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(2).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(3).size(), 0);
        assertEquals("The size of the list should be 1.", graph.adjacent(4).size(), 1);
        // We check that the items on the list are correct.
        for(Integer integer: graph.adjacent(0))
            assertEquals("The only item on the list should be 4.", (int) integer, 4);
        for(Integer integer: graph.adjacent(4))
            assertEquals("The only item on the list should be 0.", (int) integer, 0);
        // Now, the method should allow to add a self-cycle.
        graph.addEdge(0, 0);
        assertEquals("The number of edges should be 2.", graph.E(), 2);
        assertEquals("The size of the list should be 2.", graph.adjacent(0).size(), 2);
        // Nothing else should have changed.
        assertEquals("The size of the list should be 0.", graph.adjacent(1).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(2).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(3).size(), 0);
        assertEquals("The size of the list should be 1.", graph.adjacent(4).size(), 1);
        // Now, the method should allow to add the same edge twice.
        graph.addEdge(4, 0);
        assertEquals("The number of edges should be 3.", graph.E(), 3);
        assertEquals("The size of the list should be 3.", graph.adjacent(0).size(), 3);
        assertEquals("The size of the list should be 2.", graph.adjacent(4).size(), 2);
        // Nothing else should have changed.
        assertEquals("The number of vertexes should be 5.", graph.V(), 5);
        assertEquals("The size of the list should be 0.", graph.adjacent(1).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(2).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(3).size(), 0);
    }

    /**
     * Tests that the graph adds edges properly and checks that no self-cycles or double edges are formed.
     */
    @Test
    public void testAddEdgeChecked()
    {
        // We will add the edge 0-4.
        graph.addEdgeChecked(0, 4);
        assertEquals("The number of edges should be 1.", graph.E(), 1);
        assertEquals("The number of vertexes should be 5.", graph.V(), 5);
        assertEquals("The size of the list should be 1.", graph.adjacent(0).size(), 1);
        assertEquals("The size of the list should be 0.", graph.adjacent(1).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(2).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(3).size(), 0);
        assertEquals("The size of the list should be 1.", graph.adjacent(4).size(), 1);
        // We check that the items on the list are correct.
        for(Integer integer: graph.adjacent(0))
            assertEquals("The only item on the list should be 4.", (int) integer, 4);
        for(Integer integer: graph.adjacent(4))
            assertEquals("The only item on the list should be 0.", (int) integer, 0);
        // Now, the method shouldn't allow to add a self-cycle.
        graph.addEdgeChecked(0, 0);
        assertEquals("The number of edges should be 1.", graph.E(), 1);
        assertEquals("The size of the list should be 1.", graph.adjacent(0).size(), 1);
        assertEquals("The size of the list should be 0.", graph.adjacent(1).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(2).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(3).size(), 0);
        assertEquals("The size of the list should be 1.", graph.adjacent(4).size(), 1);
        // Now, the method shouldn't allow to add the same edge twice.
        graph.addEdgeChecked(4, 0);
        assertEquals("The number of edges should be 1.", graph.E(), 1);
        assertEquals("The number of vertexes should be 5.", graph.V(), 5);
        assertEquals("The size of the list should be 1.", graph.adjacent(0).size(), 1);
        assertEquals("The size of the list should be 0.", graph.adjacent(1).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(2).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(3).size(), 0);
        assertEquals("The size of the list should be 1.", graph.adjacent(4).size(), 1);
    }

    /**
     * Tests that the DFS algorithm works properly.
     */
    @Test
    public void testDFS()
    {
        BasicUndirectedUnweightedGraph newGraph = new BasicUndirectedUnweightedGraph(7);
        // The following edges are added: 0-1, 0-2, 2-3, 2-4, 1-4 and 5-6.
        newGraph.addEdge(0, 1); newGraph.addEdge(0, 2); newGraph.addEdge(2, 3);
        newGraph.addEdge(2, 4); newGraph.addEdge(1, 4); newGraph.addEdge(5, 6);
        // A DFS from the origin is created.
        BasicUndirectedUnweightedGraph.BasicDFS dfs = new BasicUndirectedUnweightedGraph.BasicDFS(newGraph, 0);
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
                    assertEquals("The size should be 2.", vertexes.size(), 2);
                    assertEquals("The first item should be 1.", (int) vertexes.get(0), 1);
                    assertEquals("The second item should be 0.", (int) vertexes.get(1), 0);
                    break;
                case 2:
                    // Path from 0 to 2 should be 0, 1, 4, 2 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 4.", vertexes.size(), 4);
                    assertEquals("The first item should be 2.", (int) vertexes.get(0), 2);
                    assertEquals("The second item should be 4.", (int) vertexes.get(1), 4);
                    assertEquals("The third item should be 1.", (int) vertexes.get(2), 1);
                    assertEquals("The fourth item should be 0.", (int) vertexes.get(3), 0);
                    break;
                case 3:
                    // Path from 0 to 3 should be 0, 1, 4, 2, 3 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 5.", vertexes.size(), 5);
                    assertEquals("The first item should be 3.", (int) vertexes.get(0), 3);
                    assertEquals("The second item should be 2.", (int) vertexes.get(1), 2);
                    assertEquals("The third item should be 4.", (int) vertexes.get(2), 4);
                    assertEquals("The fourth item should be 1.", (int) vertexes.get(3), 1);
                    assertEquals("The fifth item should be 0.", (int) vertexes.get(4), 0);
                    break;
                case 4:
                    // Path from 0 to 4 should be 0, 1, 4 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 3.", vertexes.size(), 3);
                    assertEquals("The first item should be 4.", (int) vertexes.get(0), 4);
                    assertEquals("The second item should be 1.", (int) vertexes.get(1), 1);
                    assertEquals("The third item should be 0.", (int) vertexes.get(2), 0);
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
    public void testBFS()
    {
        BasicUndirectedUnweightedGraph newGraph = new BasicUndirectedUnweightedGraph(7);
        // The following edges are added: 0-1, 0-2, 2-3, 2-4, 1-4 and 5-6.
        newGraph.addEdge(0, 1); newGraph.addEdge(0, 2); newGraph.addEdge(2, 3);
        newGraph.addEdge(2, 4); newGraph.addEdge(1, 4); newGraph.addEdge(5, 6);
        // A DFS from the origin is created.
        BasicUndirectedUnweightedGraph.BasicBFS bfs = new BasicUndirectedUnweightedGraph.BasicBFS(newGraph, 0);
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
                    assertEquals("The size should be 2.", vertexes.size(), 2);
                    assertEquals("The first item should be 1.", (int) vertexes.get(0), 1);
                    assertEquals("The second item should be 0.", (int) vertexes.get(1), 0);
                    break;
                case 2:
                    // Path from 0 to 2 should be 0, 2 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 2.", vertexes.size(), 2);
                    assertEquals("The first item should be 2.", (int) vertexes.get(0), 2);
                    assertEquals("The second item should be 0.", (int) vertexes.get(1), 0);
                    break;
                case 3:
                    // Path from 0 to 3 should be 0, 2, 3 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 3.", vertexes.size(), 3);
                    assertEquals("The first item should be 3.", (int) vertexes.get(0), 3);
                    assertEquals("The second item should be 2.", (int) vertexes.get(1), 2);
                    assertEquals("The third item should be 0.", (int) vertexes.get(2), 0);
                    break;
                case 4:
                    // Path from 0 to 4 should be 0, 1, 4 in that order.
                    for(int vertex : path)
                        vertexes.add(vertex);
                    assertEquals("The size should be 3.", vertexes.size(), 3);
                    assertEquals("The first item should be 4.", (int) vertexes.get(0), 4);
                    assertEquals("The second item should be 1.", (int) vertexes.get(1), 1);
                    assertEquals("The third item should be 0.", (int) vertexes.get(2), 0);
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