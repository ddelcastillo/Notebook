// @formatter:off

package graph.undirectedUnweighted;

import graph.algorithms.misc.BasicTwoColor;
import graph.algorithms.search.BasicBFS;
import graph.algorithms.search.BasicDFS;
import graph.algorithms.misc.BasicIsAcyclic;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Class that tests the BasicUndirectedUnweightedCCGraph class.
 */
public class BasicUndirectedUnweightedCCGraphTest
{
    // Attributes

    /**
     * The graph.
     */
    private BasicUndirectedUnweightedCCGraph graph;

    // Setups

    /**
     * Initializes the graph with 5 nodes.
     */
    @Before
    public void setup()
    { graph = new BasicUndirectedUnweightedCCGraph(5); }

    // Tests

    /**
     * Tests that the graph is initialized properly.
     */
    @Test
    public void initializationTest1()
    {
        assertNotNull("The graph shouldn't be null.", graph);
        assertEquals("The number of edges should be 0.", graph.E(), 0);
        assertEquals("The number of vertices should be 5.", graph.V(), 5);
        assertEquals("The number of components should be 5.", graph.numberOfComponents(), 5);
        // We check that the adjacent list of each vertex isn't null and is empty.
        for(int i = 0; i < 5; ++i)
        {
            assertNotNull("The list shouldn't be null.", graph.adjacent(i));
            assertTrue("The list should be empty.", graph.adjacent(i).isEmpty());
            assertEquals("The size of the component should be 1.", graph.sizeOfComponent(i), 1);
        }
    }

    /**
     * Tests that the graph is initialized properly.
     */
    @Test
    public void initializationTest2()
    {
        BasicUndirectedUnweightedCCGraph newGraph = new BasicUndirectedUnweightedCCGraph(graph);
        assertNotNull("The graph shouldn't be null.", newGraph);
        assertEquals("The number of edges should be 0.", newGraph.E(), 0);
        assertEquals("The number of vertices should be 5.", newGraph.V(), 5);
        assertEquals("The number of components should be 5.", graph.numberOfComponents(), 5);
        // We check that the adjacent list of each vertex isn't null and is empty.
        for(int i = 0; i < 5; ++i)
        {
            assertNotNull("The list shouldn't be null.", newGraph.adjacent(i));
            assertTrue("The list should be empty.", newGraph.adjacent(i).isEmpty());
            assertEquals("The size of the component should be 1.", graph.sizeOfComponent(i), 1);
        }
    }

    /**
     * Tests that a graph copy stores information properly.
     */
    @Test
    public void initializationTest3()
    {
        graph.addEdge(0, 4); graph.addEdge(1, 2);
        BasicUndirectedUnweightedCCGraph newGraph = new BasicUndirectedUnweightedCCGraph(graph);
        assertNotNull("The graph shouldn't be null.", newGraph);
        assertEquals("The number of edges should be 2.", 2, newGraph.E());
        assertEquals("The number of vertices should be 5.", 5, newGraph.V());
        assertEquals("The number of lists should be 5.", 5, newGraph.adjacent().length);
        assertEquals("The number of connected components should be 3.", 3, newGraph.numberOfComponents());
        // We check that the adjacent list of each vertex isn't null. List should have 1 or 0 vertices.
        for(int i = 0; i < 5; ++i)
        {
            assertNotNull("The list shouldn't be null.", newGraph.adjacent(i));
            if(i == 3)
            {
                assertEquals("The list should be empty.", 0, newGraph.adjacent(i).size());
                assertEquals("The size of the component should be 1.", 1, newGraph.sizeOfComponent(i));
            }
            else
            {
                assertEquals("The size of the component should be 2.", 2, newGraph.sizeOfComponent(i));
                assertEquals("The list should contain one vertex.", 1, newGraph.adjacent(i).size());
            }
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
        assertEquals("The number of edges should be 1.", graph.E(), 1);
        assertEquals("The number of vertices should be 5.", graph.V(), 5);
        assertEquals("There should be 4 components.", graph.numberOfComponents(), 4);
        assertEquals("The size of the list should be 1.", graph.adjacent(0).size(), 1);
        assertEquals("The size of the list should be 0.", graph.adjacent(1).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(2).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(3).size(), 0);
        assertEquals("The size of the list should be 1.", graph.adjacent(4).size(), 1);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(0), 2);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(1), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(2), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(3), 1);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(4), 2);
        // We check that the items on the list are correct.
        for(Integer integer: graph.adjacent(0))
            assertEquals("The only item on the list should be 4.", (int) integer, 4);
        for(Integer integer: graph.adjacent(4))
            assertEquals("The only item on the list should be 0.", (int) integer, 0);
        // Now, the method should allow to add a self-cycle.
        graph.addEdge(0, 0);
        assertEquals("The number of edges should be 2.", graph.E(), 2);
        assertEquals("There should be 4 components.", graph.numberOfComponents(), 4);
        assertEquals("The size of the list should be 2.", graph.adjacent(0).size(), 2);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(0), 2);
        // Nothing else should have changed.
        assertEquals("The size of the list should be 0.", graph.adjacent(1).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(2).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(3).size(), 0);
        assertEquals("The size of the list should be 1.", graph.adjacent(4).size(), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(1), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(2), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(3), 1);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(4), 2);
        // Now, the method should allow to add the same edge twice.
        graph.addEdge(4, 0);
        assertEquals("The number of edges should be 3.", graph.E(), 3);
        assertEquals("There should be 4 components.", graph.numberOfComponents(), 4);
        assertEquals("The size of the list should be 3.", graph.adjacent(0).size(), 3);
        assertEquals("The size of the list should be 2.", graph.adjacent(4).size(), 2);
        // Nothing else should have changed.
        assertEquals("The number of vertices should be 5.", graph.V(), 5);
        assertEquals("The size of the list should be 0.", graph.adjacent(1).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(2).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(3).size(), 0);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(0), 2);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(1), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(2), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(3), 1);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(4), 2);
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
     * Tests that the number of components when adding edges updates properly.
     */
    @Test
    public void addEdgeCheckedTest1()
    {
        // We will add the edge 0-4 and 0-1.
        graph.addEdgeChecked(0, 4); graph.addEdgeChecked(0, 1);
        assertEquals("There should be 3 components.", graph.numberOfComponents(), 3);
        assertEquals("The size of the component should be 3.", graph.sizeOfComponent(0), 3);
        assertEquals("The size of the component should be 3.", graph.sizeOfComponent(1), 3);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(2), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(3), 1);
        assertEquals("The size of the component should be 3.", graph.sizeOfComponent(4), 3);
        // We will add the edge 2-3.
        graph.addEdgeChecked(2, 3);
        assertEquals("There should be 2 components.", graph.numberOfComponents(), 2);
        assertEquals("The size of the component should be 3.", graph.sizeOfComponent(0), 3);
        assertEquals("The size of the component should be 3.", graph.sizeOfComponent(1), 3);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(2), 2);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(3), 2);
        assertEquals("The size of the component should be 3.", graph.sizeOfComponent(4), 3);
        // Now we will join both components.
        graph.addEdgeChecked(1, 2);
        assertEquals("There should be 1 component.", graph.numberOfComponents(), 1);
        assertEquals("The size of the component should be 5.", graph.sizeOfComponent(0), 5);
        assertEquals("The size of the component should be 5.", graph.sizeOfComponent(1), 5);
        assertEquals("The size of the component should be 5.", graph.sizeOfComponent(2), 5);
        assertEquals("The size of the component should be 5.", graph.sizeOfComponent(3), 5);
        assertEquals("The size of the component should be 5.", graph.sizeOfComponent(4), 5);
    }

    /**
     * Tests that the graph adds edges properly and checks that no self-cycles or double edges are formed.
     */
    @Test
    public void addEdgeCheckedTest2()
    {
        // We will add the edge 0-4.
        graph.addEdgeChecked(0, 4);
        assertEquals("The number of edges should be 1.", graph.E(), 1);
        assertEquals("The number of vertices should be 5.", graph.V(), 5);
        assertEquals("There should be 4 components.", graph.numberOfComponents(), 4);
        assertEquals("The size of the list should be 1.", graph.adjacent(0).size(), 1);
        assertEquals("The size of the list should be 0.", graph.adjacent(1).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(2).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(3).size(), 0);
        assertEquals("The size of the list should be 1.", graph.adjacent(4).size(), 1);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(0), 2);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(1), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(2), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(3), 1);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(4), 2);
        // We check that the items on the list are correct.
        for(Integer integer: graph.adjacent(0))
            assertEquals("The only item on the list should be 4.", (int) integer, 4);
        for(Integer integer: graph.adjacent(4))
            assertEquals("The only item on the list should be 0.", (int) integer, 0);
        // Now, the method shouldn't allow to add a self-cycle.
        graph.addEdgeChecked(0, 0);
        assertEquals("The number of edges should be 1.", graph.E(), 1);
        assertEquals("There should be 4 components.", graph.numberOfComponents(), 4);
        assertEquals("The size of the list should be 1.", graph.adjacent(0).size(), 1);
        assertEquals("The size of the list should be 0.", graph.adjacent(1).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(2).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(3).size(), 0);
        assertEquals("The size of the list should be 1.", graph.adjacent(4).size(), 1);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(0), 2);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(1), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(2), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(3), 1);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(4), 2);
        // Now, the method shouldn't allow to add the same edge twice.
        graph.addEdgeChecked(4, 0);
        assertEquals("The number of edges should be 1.", graph.E(), 1);
        assertEquals("There should be 4 components.", graph.numberOfComponents(), 4);
        assertEquals("The size of the list should be 1.", graph.adjacent(0).size(), 1);
        assertEquals("The size of the list should be 0.", graph.adjacent(1).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(2).size(), 0);
        assertEquals("The size of the list should be 0.", graph.adjacent(3).size(), 0);
        assertEquals("The size of the list should be 1.", graph.adjacent(4).size(), 1);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(0), 2);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(1), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(2), 1);
        assertEquals("The size of the component should be 1.", graph.sizeOfComponent(3), 1);
        assertEquals("The size of the component should be 2.", graph.sizeOfComponent(4), 2);
    }

    /**
     * Tests that the graph manages the adjacency list properly.
     */
    @Test
    public void adjacentTest1()
    {
        // Edges 0-1 and 1-4 will be added.
        graph.addEdge(0, 1); graph.addEdge(1, 4);
        // The adjacent list of vertices 2 and 3 should be empty.
        assertTrue("The list should be empty.", graph.adjacent(2).isEmpty());
        assertTrue("The list should be empty.", graph.adjacent(3).isEmpty());
        // The list of vertex 0 should just contain vertex 1, the list of vertex 1 should contain
        // vertices 0 and 4, and the list of vertex 4 should only contain vertex 1.
        ArrayList<Integer> adjacent = (ArrayList<Integer>) graph.adjacent(0);
        assertEquals("The size of the list should be 1.", 1, adjacent.size());
        assertEquals("The only vertex should be 1.", 1, (int) adjacent.get(0));
        adjacent = (ArrayList<Integer>) graph.adjacent(1);
        assertEquals("The size of the list should be 2.", 2, adjacent.size());
        assertEquals("The first vertex should be 0.", 0, (int) adjacent.get(0));
        assertEquals("The second vertex should be 4.", 4, (int) adjacent.get(1));
        adjacent = (ArrayList<Integer>) graph.adjacent(4);
        assertEquals("The size of the list should be 1.", 1, adjacent.size());
        assertEquals("The only vertex should be 1.", 1, (int) adjacent.get(0));
        // There should only be 5 adjacency lists in the array.
        assertEquals("The size of the array should be 5.", 5, graph.adjacent().length);
    }

    /**
     * Tests that access to the adjacency list of an invalid vertex ends up with a ArrayIndexOutOfBoundsException.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void adjacentTest2()
    {
        graph.adjacent(100);
    }

    /**
     * Tests that the graph manages the adjacency list properly and returns null for invalid vertices.
     */
    @Test
    public void adjacentCheckedTest()
    {
        // Edges 0-1 and 1-4 will be added.
        graph.addEdge(0, 1); graph.addEdge(1, 4);
        // The adjacent list of vertices 2 and 3 should be empty.
        assertTrue("The list should be empty.", graph.adjacentChecked(2).isEmpty());
        assertTrue("The list should be empty.", graph.adjacentChecked(3).isEmpty());
        // The list of vertex 0 should just contain vertex 1, the list of vertex 1 should contain
        // vertices 0 and 4, and the list of vertex 4 should only contain vertex 1.
        ArrayList<Integer> adjacent = (ArrayList<Integer>) graph.adjacentChecked(0);
        assertEquals("The size of the list should be 1.", 1, adjacent.size());
        assertEquals("The only vertex should be 1.", 1, (int) adjacent.get(0));
        adjacent = (ArrayList<Integer>) graph.adjacentChecked(1);
        assertEquals("The size of the list should be 2.", 2, adjacent.size());
        assertEquals("The first vertex should be 0.", 0, (int) adjacent.get(0));
        assertEquals("The second vertex should be 4.", 4, (int) adjacent.get(1));
        adjacent = (ArrayList<Integer>) graph.adjacentChecked(4);
        assertEquals("The size of the list should be 1.", 1, adjacent.size());
        assertEquals("The only vertex should be 1.", 1, (int) adjacent.get(0));
        // Adjacency list of invalid nodes should return null.
        assertNull("The adjacency list should be null.", graph.adjacentChecked(200));
        assertNull("The adjacency list should be null.", graph.adjacentChecked(5));
        assertNull("The adjacency list should be null.", graph.adjacentChecked(-1));
    }

    /**
     * Tests that the graph manages the size of the components properly.
     */
    @Test
    public void sizeOfComponentTest1()
    {
        // Edge 0-1 will be added.
        graph.addEdge(0, 1);
        // There should be 4 components: 0-1 of size 2 and 2, 3 and 4 of size 1.
        assertEquals("There should be 4 components.", 4, graph.numberOfComponents());
        for(int i = 0; i < 5; ++i)
        {
            if(i == 0 || i == 1)
                    assertEquals("The size of the component should be 2.", 2, graph.sizeOfComponent(i));
            else
                    assertEquals("The size of the component should be 1.", 1, graph.sizeOfComponent(i));
        }
        // The size of components and number of components doesn't break with the addition
        // of self-cycles or with repeated edges, due to the union finder's logic.
        graph.addEdge(1, 0); graph.addEdge(0, 0); graph.addEdge(2, 2);
        assertEquals("There should be 4 components.", 4, graph.numberOfComponents());
        for(int i = 0; i < 5; ++i)
        {
            if(i == 0 || i == 1)
                assertEquals("The size of the component should be 2.", 2, graph.sizeOfComponent(i));
            else
                assertEquals("The size of the component should be 1.", 1, graph.sizeOfComponent(i));
        }
    }

    /**
     * Tests that access to the size of a component of an invalid vertex ends up in an ArrayIndexOutOfBoundsException.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void sizeOfComponentTest2()
    {
        graph.sizeOfComponent(200);
    }

    /**
     * Tests that the graph manages the size of the components properly and results null for invalid vertices.
     */
    @Test
    public void sizeOfComponentCheckedTest()
    {
        // Edge 0-1 will be added.
        graph.addEdge(0, 1);
        // There should be 4 components: 0-1 of size 2 and 2, 3 and 4 of size 1.
        assertEquals("There should be 4 components.", 4, graph.numberOfComponents());
        for(int i = 0; i < 5; ++i)
        {
            if(i == 0 || i == 1)
                assertEquals("The size of the component should be 2.", 2, (int) graph.sizeOfComponentChecked(i));
            else
                assertEquals("The size of the component should be 1.", 1, (int) graph.sizeOfComponentChecked(i));
        }
        // The size of components and number of components doesn't break with the addition
        // of self-cycles or with repeated edges, due to the union finder's logic.
        graph.addEdge(1, 0); graph.addEdge(0, 0); graph.addEdge(2, 2);
        assertEquals("There should be 4 components.", 4, graph.numberOfComponents());
        for(int i = 0; i < 5; ++i)
        {
            if(i == 0 || i == 1)
                assertEquals("The size of the component should be 2.", 2, (int) graph.sizeOfComponentChecked(i));
            else
                assertEquals("The size of the component should be 1.", 1, (int) graph.sizeOfComponentChecked(i));
        }
        // Access to the size of the components of invalid vertices should result in null.
        assertNull("The result should be null.", graph.sizeOfComponentChecked(200));
        assertNull("The result should be null.", graph.sizeOfComponentChecked(5));
        assertNull("The result should be null.", graph.sizeOfComponentChecked(-1));
    }

    /**
     * Tests that the DFS algorithm works properly.
     */
    @Test
    public void DFSTest()
    {
        BasicUndirectedUnweightedCCGraph newGraph = new BasicUndirectedUnweightedCCGraph(7);
        // The following edges are added: 0-1, 0-2, 2-3, 2-4, 1-4 and 5-6.
        newGraph.addEdge(0, 1); newGraph.addEdge(0, 2); newGraph.addEdge(2, 3);
        newGraph.addEdge(2, 4); newGraph.addEdge(1, 4); newGraph.addEdge(5, 6);
        // A DFS from the origin is created.
        BasicDFS dfs = new BasicDFS(newGraph, 0);
        // There should be a path from all vertices except 5 and 6.
        for(int i = 0; i < 5; ++i)
            assertTrue("There should be a path to the vertex " + i + ".", dfs.hasPathTo(i));
        for(int i = 5; i < 7; ++i)
            assertFalse("There shouldn't be a path to the vertex " + i + ".", dfs.hasPathTo(i));
        // The DFS checks the vertices in the list in added order instead of numerical order, therefore,
        // since the 0-1 edge was first added for the vertex 0, the first edge checked from 0 will be 0-1,
        // then the same for the vertex 1 and so on.
        // Now we check the paths.
        ArrayList<Integer> vertices;
        Iterable<Integer> path;
        for(int i = 1; i < 7; ++i)
        {
            vertices = new ArrayList<>();
            path = dfs.pathTo(i);
            switch(i)
            {
                case 1:
                    // Path from 0 to 1 should be 0, 1 in that order.
                    for(int vertex : path)
                        vertices.add(vertex);
                    assertEquals("The size should be 2.", vertices.size(), 2);
                    assertEquals("The first item should be 1.", (int) vertices.get(0), 1);
                    assertEquals("The second item should be 0.", (int) vertices.get(1), 0);
                    break;
                case 2:
                    // Path from 0 to 2 should be 0, 1, 4, 2 in that order.
                    for(int vertex : path)
                        vertices.add(vertex);
                    assertEquals("The size should be 4.", vertices.size(), 4);
                    assertEquals("The first item should be 2.", (int) vertices.get(0), 2);
                    assertEquals("The second item should be 4.", (int) vertices.get(1), 4);
                    assertEquals("The third item should be 1.", (int) vertices.get(2), 1);
                    assertEquals("The fourth item should be 0.", (int) vertices.get(3), 0);
                    break;
                case 3:
                    // Path from 0 to 3 should be 0, 1, 4, 2, 3 in that order.
                    for(int vertex : path)
                        vertices.add(vertex);
                    assertEquals("The size should be 5.", vertices.size(), 5);
                    assertEquals("The first item should be 3.", (int) vertices.get(0), 3);
                    assertEquals("The second item should be 2.", (int) vertices.get(1), 2);
                    assertEquals("The third item should be 4.", (int) vertices.get(2), 4);
                    assertEquals("The fourth item should be 1.", (int) vertices.get(3), 1);
                    assertEquals("The fifth item should be 0.", (int) vertices.get(4), 0);
                    break;
                case 4:
                    // Path from 0 to 4 should be 0, 1, 4 in that order.
                    for(int vertex : path)
                        vertices.add(vertex);
                    assertEquals("The size should be 3.", vertices.size(), 3);
                    assertEquals("The first item should be 4.", (int) vertices.get(0), 4);
                    assertEquals("The second item should be 1.", (int) vertices.get(1), 1);
                    assertEquals("The third item should be 0.", (int) vertices.get(2), 0);
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
        BasicUndirectedUnweightedCCGraph newGraph = new BasicUndirectedUnweightedCCGraph(7);
        // The following edges are added: 0-1, 0-2, 2-3, 2-4, 1-4 and 5-6.
        newGraph.addEdge(0, 1); newGraph.addEdge(0, 2); newGraph.addEdge(2, 3);
        newGraph.addEdge(2, 4); newGraph.addEdge(1, 4); newGraph.addEdge(5, 6);
        // A DFS from the origin is created.
        BasicBFS bfs = new BasicBFS(newGraph, 0);
        // There should be a path from all vertices except 5 and 6.
        for(int i = 0; i < 5; ++i)
            assertTrue("There should be a path to the vertex " + i + ".", bfs.hasPathTo(i));
        for(int i = 5; i < 7; ++i)
            assertFalse("There shouldn't be a path to the vertex " + i + ".", bfs.hasPathTo(i));
        // The BFS checks the vertices in the list in added order instead of numerical order, therefore,
        // since the 0-1 edge was first added for the vertex 0, the first vertex added to the queue is 1,
        // then the same for each vertex remaining in the adjacent list.
        ArrayList<Integer> vertices;
        Iterable<Integer> path;
        for(int i = 1; i < 7; ++i)
        {
            vertices = new ArrayList<>();
            path = bfs.pathTo(i);
            switch(i)
            {
                case 1:
                    // Path from 0 to 1 should be 0, 1 in that order.
                    for(int vertex : path)
                        vertices.add(vertex);
                    assertEquals("The size should be 2.", vertices.size(), 2);
                    assertEquals("The first item should be 1.", (int) vertices.get(0), 1);
                    assertEquals("The second item should be 0.", (int) vertices.get(1), 0);
                    break;
                case 2:
                    // Path from 0 to 2 should be 0, 2 in that order.
                    for(int vertex : path)
                        vertices.add(vertex);
                    assertEquals("The size should be 2.", vertices.size(), 2);
                    assertEquals("The first item should be 2.", (int) vertices.get(0), 2);
                    assertEquals("The second item should be 0.", (int) vertices.get(1), 0);
                    break;
                case 3:
                    // Path from 0 to 3 should be 0, 2, 3 in that order.
                    for(int vertex : path)
                        vertices.add(vertex);
                    assertEquals("The size should be 3.", vertices.size(), 3);
                    assertEquals("The first item should be 3.", (int) vertices.get(0), 3);
                    assertEquals("The second item should be 2.", (int) vertices.get(1), 2);
                    assertEquals("The third item should be 0.", (int) vertices.get(2), 0);
                    break;
                case 4:
                    // Path from 0 to 4 should be 0, 1, 4 in that order.
                    for(int vertex : path)
                        vertices.add(vertex);
                    assertEquals("The size should be 3.", vertices.size(), 3);
                    assertEquals("The first item should be 4.", (int) vertices.get(0), 4);
                    assertEquals("The second item should be 1.", (int) vertices.get(1), 1);
                    assertEquals("The third item should be 0.", (int) vertices.get(2), 0);
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
     * Tests that the IsAcyclic algorithm works properly when there's no cycles.
     */
    @Test
    public void isAcyclicTest1()
    {
        // Edges 0-1, 1-2 and 1-3 will be added.
        graph.addEdge(0, 1); graph.addEdge(1, 2); graph.addEdge(1, 3);
        // None of the graph's components isn't acyclic, therefore, any combination
        // of searches for the graph should result in it being acyclic.
        BasicIsAcyclic isAcyclic1 = new BasicIsAcyclic(graph);
        assertEquals("The number of components should be 2.", 2, graph.numberOfComponents());
        assertEquals("The size of the component should be 4.", 4, graph.sizeOfComponent(0));
        assertEquals("The size of the component should be 4.", 4, graph.sizeOfComponent(1));
        assertEquals("The size of the component should be 4.", 4, graph.sizeOfComponent(2));
        assertEquals("The size of the component should be 4.", 4, graph.sizeOfComponent(3));
        assertEquals("The size of the component should be 1.", 1, graph.sizeOfComponent(4));
        assertTrue("The graph should be acyclic.", isAcyclic1.isAcyclic());
        for(int v = 0; v < 5; ++v)
        {
            BasicIsAcyclic isAcyclic3 = new BasicIsAcyclic(graph, v);
            assertTrue("The graph should be acyclic.", isAcyclic3.isAcyclic());
        }
    }

    /**
     * Tests that the IsAcyclic algorithm throws an ArrayIndexOutOfBoundsException with an invalid vertex.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void isAcyclicTest2()
    {
        BasicIsAcyclic isAcyclic = new BasicIsAcyclic(graph, 100);
    }

    /**
     * Tests that the IsAcyclic algorithm works properly when there's a cycle.
     */
    @Test
    public void isAcyclicTest3()
    {
        // Edges 0-1, 1-2 and 2-0 will be added.
        graph.addEdge(0, 1); graph.addEdge(1, 2); graph.addEdge(2, 0);
        // The graph has one cycle in the 0-1-2 component.
        BasicIsAcyclic isAcyclic1 = new BasicIsAcyclic(graph);
        assertEquals("The number of components should be 3.", 3, graph.numberOfComponents());
        assertFalse("The graph should not be acyclic.", isAcyclic1.isAcyclic());
        BasicIsAcyclic isAcyclic2;
        // Vertex 3 and 4 are not in the same connected component as 0, 1 and 2, therefore,
        // the algorithm should return that the graph is acyclic for these vertices.
        for(int v = 0; v < 5; ++v)
        {
            switch(v)
            {
                case 0:
                case 1:
                case 2:
                    isAcyclic2 = new BasicIsAcyclic(graph, v);
                    assertFalse("The graph should not be acyclic.", isAcyclic2.isAcyclic());
                    assertEquals("The size of the component should be 3.", 3, graph.sizeOfComponent(v));
                    break;
                default:
                    isAcyclic2 = new BasicIsAcyclic(graph, v);
                    assertTrue("The graph should be acyclic.", isAcyclic2.isAcyclic());
                    assertEquals("The size of the component should be 1.", 1, graph.sizeOfComponent(v));
            }
        }
    }

    /**
     * Tests that the isAcyclic algorithm works properly when there's a self-cycle.
     */
    @Test
    public void isAcyclicTest4()
    {
        // Edge 0-0 will be added.
        graph.addEdge(0, 0);
        // The graph now has a self-cycle.
        BasicIsAcyclic isAcyclic1 = new BasicIsAcyclic(graph);
        BasicIsAcyclic isAcyclic2 = new BasicIsAcyclic(graph, 0);
        assertEquals("The number of components should be 5.", 5, graph.numberOfComponents());
        assertFalse("The graph should not be acyclic.", isAcyclic1.isAcyclic());
        assertFalse("The graph should not be acyclic.", isAcyclic2.isAcyclic());
    }

    /**
     * Tests that the TwoColor algorithm works properly when the graph is two-colorable.
     */
    @Test
    public void twoColorableTest1()
    {
        // Edges 0-1, 1-3, 3-2 and 2-4 will be added.
        graph.addEdge(0, 1); graph.addEdge(1, 3); graph.addEdge(3, 2); graph.addEdge(2, 4);
        assertEquals("The number of components should be 1.", 1, graph.numberOfComponents());
        for(int vertex = 0; vertex < 5; ++vertex)
            assertEquals("The size of the component should be 5.", 5, graph.sizeOfComponent(vertex));
        BasicTwoColor b2c = new BasicTwoColor(graph);
        // The graph should be two-colorable.
        assertTrue("The graph should be two-colorable.", b2c.isTwoColorable());
        // The array of colors (booleans) shouldn't be null.
        assertNotNull("The array shouldn't be null.", b2c.getColor());
        assertNotNull("The array shouldn't be null.", b2c.getColorNum());
        boolean[] color = b2c.getColor();
        int[] colorNum = b2c.getColorNum();
        for(int vertex = 0; vertex < 5; ++vertex)
        {
            for(int adjacent : graph.adjacent(vertex))
            {
                assertEquals("The color should be different.", color[vertex], !color[adjacent]);
                assertNotEquals("The color should be different.", colorNum[vertex], colorNum[adjacent]);
            }
        }
    }

    /**
     * Tests that the TwoColor algorithm works properly when the graph isn't two-colorable.
     */
    @Test
    public void twoColorableTest2()
    {
        // Edges 0-1, 0-2, 1-2 and 2-4 will be added.
        graph.addEdge(0, 1); graph.addEdge(0, 2); graph.addEdge(1, 2); graph.addEdge(2, 4);
        assertEquals("The number of components should be 2.", 2, graph.numberOfComponents());
        for(int vertex = 0; vertex < 5; ++vertex)
        {
            switch (vertex)
            {
                case 0:
                case 1:
                case 2:
                case 4:
                    assertEquals("The size of the component should be 4.", 4, graph.sizeOfComponent(vertex));
                    break;
                default:
                    assertEquals("The size of the component should be 1.", 1, graph.sizeOfComponent(vertex));

            }
        }
        BasicTwoColor b2c = new BasicTwoColor(graph);
        // The graph shouldn't be two-colorable.
        assertFalse("The graph should be two-colorable.", b2c.isTwoColorable());
        // The array of colors should be null.
        assertNull("The array should be null.", b2c.getColor());
        assertNull("The array should be null.", b2c.getColorNum());
    }
}