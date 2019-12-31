package graph.undirectedUnweighted;

import graph.algorithms.misc.IsAcyclic;
import graph.algorithms.misc.TwoColor;
import graph.algorithms.search.BFS;
import graph.algorithms.search.DFS;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Class that tests the UndirectedUnweightedGraph class.
 */
public class UndirectedUnweightedCCGraphTest
{
    // Attributes

    /**
     * Numerical graph.
     */
    private UndirectedUnweightedCCGraph<Integer> graph1;

    /**
     * String graph.
     */
    private UndirectedUnweightedCCGraph<String> graph2;

    // Setups

    /**
     * Initializes the empty graphs.
     */
    public void setup1()
    {
        graph1 = new UndirectedUnweightedCCGraph<>();
        graph2 = new UndirectedUnweightedCCGraph<>();
    }

    /**
     * Initializes the empty graphs with an initial capacity of 3.
     */
    public void setup2()
    {
        graph1 = new UndirectedUnweightedCCGraph<>(3);
        graph2 = new UndirectedUnweightedCCGraph<>(3);
    }

    /**
     * Initializes a numerical graph with vertexes 1, 3, 5 and 7.
     */
    public void setup3()
    {
        Integer[] vertexes = {1, 3, 5, 7};
        graph1 = new UndirectedUnweightedCCGraph<>(vertexes);
    }

    /**
     * Initializes a String graph with vertexes a, b, c and d.
     */
    public void setup4()
    {
        String[] vertexes = {"a", "b", "c", "d"};
        graph2 = new UndirectedUnweightedCCGraph<>(vertexes);
    }

    /**
     * Initializes a numerical graph with vertexes 1, 3, 5 and 7, with initial list capacity of 3.
     */
    public void setup5()
    {
        Integer[] vertexes = {1, 3, 5, 7};
        graph1 = new UndirectedUnweightedCCGraph<>(vertexes, 3);
    }

    // Tests

    /**
     * Tests that the graph is initialized properly.
     */
    @Test
    public void initializationTest1()
    {
        setup1();
        // The graph shouldn't be null and there shouldn't be any vertexes or edges.
        assertNotNull("The graph shouldn't be null.", graph1);
        assertNotNull("The graph shouldn't be null.", graph2);
        assertEquals("The number of vertexes should be 0.", 0, graph1.V());
        assertEquals("The number of vertexes should be 0.", 0, graph2.V());
        assertEquals("The number of edges should be 0.", 0, graph1.E());
        assertEquals("The number of edges should be 0.", 0, graph2.E());
        // The number of components should be 0.
        assertEquals("The number of components should be 0.", 0, graph1.numberOfComponents());
        assertEquals("The number of components should be 0.", 0, graph2.numberOfComponents());
        // There shouldn't be any adjacency lists.
        assertEquals("There shouldn't be any lists.", 0, graph1.adjacent().size());
        assertEquals("There shouldn't be any lists.", 0, graph2.adjacent().size());
        assertEquals("There shouldn't be any lists.", 0, graph1.adjacentNumber().size());
        assertEquals("There shouldn't be any lists.", 0, graph2.adjacentNumber().size());
        // There shouldn't be any assigned keys.
        assertEquals("The HashMap should be empty.", 0, graph1.keyToNumber().size());
        assertEquals("The HashMap should be empty.", 0, graph1.numberToKey().size());
        assertEquals("The HashMap should be empty.", 0, graph2.keyToNumber().size());
        assertEquals("The HashMap should be empty.", 0, graph2.numberToKey().size());
    }

    /**
     * Tests that the graph is initialized properly.
     */
    @Test
    public void initializationTest2()
    {
        setup2();
        // The graph shouldn't be null and there shouldn't be any vertexes or edges.
        assertNotNull("The graph shouldn't be null.", graph1);
        assertNotNull("The graph shouldn't be null.", graph2);
        assertEquals("The number of vertexes should be 0.", 0, graph1.V());
        assertEquals("The number of vertexes should be 0.", 0, graph2.V());
        assertEquals("The number of edges should be 0.", 0, graph1.E());
        assertEquals("The number of edges should be 0.", 0, graph2.E());
        // The number of components should be 0.
        assertEquals("The number of components should be 0.", 0, graph1.numberOfComponents());
        assertEquals("The number of components should be 0.", 0, graph2.numberOfComponents());
        // There shouldn't be any adjacency lists.
        assertEquals("There shouldn't be any lists.", 0, graph1.adjacent().size());
        assertEquals("There shouldn't be any lists.", 0, graph2.adjacent().size());
        assertEquals("There shouldn't be any lists.", 0, graph1.adjacentNumber().size());
        assertEquals("There shouldn't be any lists.", 0, graph2.adjacentNumber().size());
        // There shouldn't be any assigned keys.
        assertEquals("The HashMap should be empty.", 0, graph1.keyToNumber().size());
        assertEquals("The HashMap should be empty.", 0, graph1.numberToKey().size());
        assertEquals("The HashMap should be empty.", 0, graph2.keyToNumber().size());
        assertEquals("The HashMap should be empty.", 0, graph2.numberToKey().size());
    }

    /**
     * Tests that the graph is initialized properly.
     */
    @Test
    public void initializationTest3()
    {
        setup3();
        // There should be 4 vertexes, 0 edges and 4 adjacency lists.
        assertNotNull("The graph shouldn't be null.", graph1);
        assertEquals("The number of vertexes should be 4.", 4, graph1.V());
        assertEquals("The number of edges should be 0.", 0, graph1.E());
        // The number of components should be 4.
        assertEquals("The number of components should be 4.", 4, graph1.numberOfComponents());
        // There should be 4 pairs.
        assertEquals("The HashMap should have 4 pairs.", 4, graph1.keyToNumber().size());
        assertEquals("The HashMap should have 4 pairs.", 4, graph1.numberToKey().size());
        // There should be 4 adjacency lists.
        assertEquals("There should be 4 adjacency lists.", 4, graph1.adjacent().size());
        assertEquals("There should be 4 adjacency lists.", 4, graph1.adjacentNumber().size());
        // Each adjacency should be empty. The size of each component should be 1.
        Integer[] vertexes = {1, 3, 5, 7};
        for(Integer vertex : vertexes)
        {
            assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));
            assertTrue("The list should be empty.", graph1.adjacentNumber(vertex).isEmpty());
            assertTrue("The list should be empty.", graph1.adjacent(vertex).isEmpty());
        }
    }

    /**
     * Tests that the graph is initialized properly.
     */
    @Test
    public void initializationTest4()
    {
        setup4();
        // There should be 4 vertexes, 0 edges and 4 adjacency lists.
        assertNotNull("The graph shouldn't be null.", graph2);
        assertEquals("The number of vertexes should be 4.", 4, graph2.V());
        assertEquals("The number of edges should be 0.", 0, graph2.E());
        // The number of components should be 4.
        assertEquals("The number of components should be 4.", 4, graph2.numberOfComponents());
        // There should be 4 pairs.
        assertEquals("The HashMap should have 4 pairs.", 4, graph2.keyToNumber().size());
        assertEquals("The HashMap should have 4 pairs.", 4, graph2.numberToKey().size());
        // There should be 4 adjacency lists.
        assertEquals("There should be 4 adjacency lists.", 4, graph2.adjacent().size());
        assertEquals("There should be 4 adjacency lists.", 4, graph2.adjacentNumber().size());
        // Each adjacency should be empty. The size of each component should be 1.
        String[] vertexes = {"a", "b", "c", "d"};
        for (String vertex : vertexes)
        {
            assertEquals("The size of the component should be 1.", 1, graph2.sizeOfComponent(vertex));
            assertTrue("The list should be empty.", graph2.adjacentNumber(vertex).isEmpty());
            assertTrue("The list should be empty.", graph2.adjacent(vertex).isEmpty());
        }
    }

    /**
     * Tests that the graph is initialized properly.
     */
    @Test
    public void initializationTest5()
    {
        setup5();
        // There should be 4 vertexes, 0 edges and 4 adjacency lists.
        assertNotNull("The graph shouldn't be null.", graph1);
        assertEquals("The number of vertexes should be 4.", 4, graph1.V());
        assertEquals("The number of edges should be 0.", 0, graph1.E());
        // The number of components should be 4.
        assertEquals("The number of components should be 4.", 4, graph1.numberOfComponents());
        // There should be 4 pairs.
        assertEquals("The HashMap should have 4 pairs.", 4, graph1.keyToNumber().size());
        assertEquals("The HashMap should have 4 pairs.", 4, graph1.numberToKey().size());
        // There should be 4 adjacency lists.
        assertEquals("There should be 4 adjacency lists.", 4, graph1.adjacent().size());
        assertEquals("There should be 4 adjacency lists.", 4, graph1.adjacentNumber().size());
        // Each adjacency should be empty. The size of each component should be 1.
        Integer[] vertexes = {1, 3, 5, 7};
        for(Integer vertex : vertexes)
        {
            assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));
            assertTrue("The list should be empty.", graph1.adjacentNumber(vertex).isEmpty());
            assertTrue("The list should be empty.", graph1.adjacent(vertex).isEmpty());
        }
    }

    /**
     * Tests that a graph copy is initialized properly.
     */
    @Test
    public void initializationTest6()
    {
        setup3();
        UndirectedUnweightedGraph<Integer> newGraph = new UndirectedUnweightedGraph<>(graph1);
        // There should be 4 vertexes, 0 edges and 4 adjacency lists.
        assertNotNull("The graph shouldn't be null.", newGraph);
        assertEquals("The number of vertexes should be 4.", 4, newGraph.V());
        assertEquals("The number of edges should be 0.", 0, newGraph.E());
        // The number of components should be 4.
        assertEquals("The number of components should be 4.", 4, graph1.numberOfComponents());
        // There should be 4 pairs.
        assertEquals("The HashMap should have 4 pairs.", 4, graph1.keyToNumber().size());
        assertEquals("The HashMap should have 4 pairs.", 4, graph1.numberToKey().size());
        // There should be 4 adjacency lists.
        assertEquals("There should be 4 adjacency lists.", 4, newGraph.adjacent().size());
        assertEquals("There should be 4 adjacency lists.", 4, graph1.adjacentNumber().size());
        // Each adjacency should be empty.
        Integer[] vertexes = {1, 3, 5, 7};
        for(Integer vertex : vertexes)
        {
            assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));
            assertTrue("The list should be empty.", graph1.adjacentNumber(vertex).isEmpty());
            assertTrue("The list should be empty.", newGraph.adjacent(vertex).isEmpty());
        }
    }

    /**
     * Tests that a graph copy stores information properly.
     */
    @Test
    public void initializationTest7()
    {
        setup3();
        // Edges 1-3 and 5-7 will be added.
        graph1.addEdge(1, 3); graph1.addEdge(5, 7);
        UndirectedUnweightedGraph<Integer> newGraph = new UndirectedUnweightedGraph<>(graph1);
        // There should be 4 vertexes, 2 edges and 4 adjacency lists.
        assertNotNull("The graph shouldn't be null.", newGraph);
        assertEquals("The number of vertexes should be 4.", 4, newGraph.V());
        assertEquals("The number of edges should be 2.", 2, newGraph.E());
        // The number of components should be 2.
        assertEquals("The number of components should be 4.", 2, graph1.numberOfComponents());
        // There should be 4 pairs.
        assertEquals("The HashMap should have 4 pairs.", 4, graph1.keyToNumber().size());
        assertEquals("The HashMap should have 4 pairs.", 4, graph1.numberToKey().size());
        // There should be 4 adjacency lists.
        assertEquals("There should be 4 adjacency lists.", 4, newGraph.adjacent().size());
        assertEquals("There should be 4 adjacency lists.", 4, graph1.adjacentNumber().size());
        // Each adjacency should be empty.
        Integer[] vertexes = {1, 3, 5, 7};
        for(Integer vertex : vertexes)
        {
            assertEquals("The size of the component should be 2.", 2, graph1.sizeOfComponent(vertex));
            assertEquals("The list should have 1 vertex.", 1, newGraph.adjacent(vertex).size());
            assertEquals("The list should have 1 vertex.", 1, newGraph.adjacentNumber(vertex).size());
        }
    }

    /**
     * Tests that the graph adds vertexes properly.
     */
    @Test
    public void addVertexTest1()
    {
        setup1();
        // Vertexes 10, 20 and 30 will be added.
        graph1.addVertex(10); graph1.addVertex(20); graph1.addVertex(30);
        assertEquals("The number of vertexes should be 3.", 3, graph1.V());
        assertEquals("The number of edges should be 0.", 0, graph1.E());
        // The number of components should be 3.
        assertEquals("The number of components should be 3.", 3, graph1.numberOfComponents());
        // There should be 3 adjacency lists.
        assertEquals("There should be 3 adjacency lists.", 3, graph1.adjacent().size());
        assertEquals("There should be 3 adjacency lists.", 3, graph1.adjacentNumber().size());
        // Each adjacency should be empty. The size of each component should be 1.
        Integer[] vertexes = {10, 20, 30};
        for(Integer vertex : vertexes)
        {
            assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));
            assertTrue("The list should be empty.", graph1.adjacent(vertex).isEmpty());
            assertTrue("The list should be empty.", graph1.adjacentNumber(vertex).isEmpty());
        }
        // Vertexes 100 and 200 will be added.
        graph1.addVertex(100); graph1.addVertex(200);
        assertEquals("The number of vertexes should be 5.", 5, graph1.V());
        assertEquals("The number of edges should be 0.", 0, graph1.E());
        // The number of components should be 5.
        assertEquals("The number of components should be 5.", 5, graph1.numberOfComponents());
        // There should be 5 adjacency lists.
        assertEquals("There should be 5 adjacency lists.", 5, graph1.adjacent().size());
        assertEquals("There should be 5 adjacency lists.", 5, graph1.adjacentNumber().size());
        // Each adjacency should be empty. The size of each component should be 1.
        vertexes = new Integer[]{10, 20, 30, 100, 200};
        for(Integer vertex : vertexes)
        {
            assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));
            assertTrue("The list should be empty.", graph1.adjacent(vertex).isEmpty());
            assertTrue("The list should be empty.", graph1.adjacentNumber(vertex).isEmpty());
        }
    }

    /**
     * Tests that the graph's logic breaks when adding vertexes twice.
     */
    @Test
    public void addVertexTest2()
    {
        setup1();
        // Vertex 10 will be added twice.
        graph1.addVertex(10); graph1.addVertex(10);
        // The graph should detect two vertexes and not one.
        assertEquals("The number of vertexes should be 2.", 2, graph1.V());
        // The number of components should be 2.
        assertEquals("The number of components should be 2.", 2, graph1.numberOfComponents());
        // There should be 2 adjacency lists.
        assertEquals("There should be 2 adjacency lists.", 2, graph1.adjacent().size());
        assertEquals("There should be 2 adjacency lists.", 2, graph1.adjacentNumber().size());
        assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(10));
        assertTrue("The list should be empty.", graph1.adjacent(10).isEmpty());
        assertTrue("The list should be empty.", graph1.adjacentNumber(10).isEmpty());
        // A null edge can be added.
        graph1.addVertex(null);
        assertEquals("The number of vertexes should be 3.", 3, graph1.V());
        // There should be 2 adjacency lists.
        assertEquals("There should be 3 adjacency lists.", 3, graph1.adjacent().size());
        assertEquals("There should be 3 adjacency lists.", 3, graph1.adjacentNumber().size());
        assertTrue("The list should be empty.", graph1.adjacent(10).isEmpty());
        assertTrue("The list should be empty.", graph1.adjacentNumber(10).isEmpty());
        assertTrue("The list should be empty.", graph1.adjacent(null).isEmpty());
        assertTrue("The list should be empty.", graph1.adjacentNumber(null).isEmpty());
    }

    /**
     * Tests that the graph adds vertexes properly and checks that the vertex is not repeated.
     */
    @Test
    public void addVertexCheckedTest()
    {
        setup1();
        // Vertexes 10, 20 and 30 will be added.
        graph1.addVertexChecked(10); graph1.addVertexChecked(20); graph1.addVertexChecked(30);
        assertEquals("The number of vertexes should be 3.", 3, graph1.V());
        assertEquals("The number of edges should be 0.", 0, graph1.E());
        // The number of components should be 3.
        assertEquals("The number of components should be 3.", 3, graph1.numberOfComponents());
        // There should be 3 adjacency lists.
        assertEquals("There should be 3 adjacency lists.", 3, graph1.adjacent().size());
        assertEquals("There should be 3 adjacency lists.", 3, graph1.adjacentNumber().size());
        // Each adjacency should be empty.
        Integer[] vertexes = {10, 20, 30};
        for(Integer vertex : vertexes)
        {
            assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));
            assertTrue("The list should be empty.", graph1.adjacent(vertex).isEmpty());
            assertTrue("The list should be empty.", graph1.adjacentNumber(vertex).isEmpty());
        }
        // Vertexes 100 and 200 will be added.
        graph1.addVertex(100); graph1.addVertex(200);
        assertEquals("The number of vertexes should be 5.", 5, graph1.V());
        assertEquals("The number of edges should be 0.", 0, graph1.E());
        // The number of components should be 5.
        assertEquals("The number of components should be 5.", 5, graph1.numberOfComponents());
        // There should be 5 adjacency lists.
        assertEquals("There should be 5 adjacency lists.", 5, graph1.adjacent().size());
        assertEquals("There should be 5 adjacency lists.", 5, graph1.adjacentNumber().size());
        // Each adjacency should be empty.
        vertexes = new Integer[]{10, 20, 30, 100, 200};
        for(Integer vertex : vertexes)
        {
            assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));
            assertTrue("The list should be empty.", graph1.adjacent(vertex).isEmpty());
            assertTrue("The list should be empty.", graph1.adjacentNumber(vertex).isEmpty());
        }
        // Adding the same vertexes again shouldn't change anything.
        graph1.addVertexChecked(10); graph1.addVertexChecked(20); graph1.addVertexChecked(30);
        // Also, a null edge cannot be added.
        graph1.addVertexChecked(null);
        assertEquals("The number of vertexes should be 5.", 5, graph1.V());
        assertEquals("The number of edges should be 0.", 0, graph1.E());
        // The number of components should be 5.
        assertEquals("The number of components should be 5.", 5, graph1.numberOfComponents());
        // There should be 5 adjacency lists.
        assertEquals("There should be 5 adjacency lists.", 5, graph1.adjacent().size());
        assertEquals("There should be 5 adjacency lists.", 5, graph1.adjacentNumber().size());
        // Each adjacency should be empty.
        vertexes = new Integer[]{10, 20, 30, 100, 200};
        for(Integer vertex : vertexes)
        {
            assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));
            assertTrue("The list should be empty.", graph1.adjacent(vertex).isEmpty());
            assertTrue("The list should be empty.", graph1.adjacentNumber(vertex).isEmpty());
        }
    }

    /**
     * Tests that the graph adds edges properly.
     */
    @Test
    public void addEdgeTest1()
    {
        setup4();
        // Edges a-b and c-d will be added.
        graph2.addEdge("a", "b"); graph2.addEdge("c", "d");
        assertEquals("The number of vertexes should be 4.", 4, graph2.V());
        assertEquals("The number of edges should be 2.", 2, graph2.E());
        // The number of components should be 2.
        assertEquals("The number of components should be 2.", 2, graph2.numberOfComponents());
        String[] vertexes = {"a", "b", "c", "d"};
        ArrayList<String> adjacent;
        ArrayList<Integer> adjacentNumber;
        String adjacentVertex;
        int adjacentVertexNumber;
        for(String vertex : vertexes)
        {
            assertEquals("The size of the list should be 1.", 1, graph2.adjacent(vertex).size());
            assertEquals("The size of the list should be 1.", 1, graph2.adjacentNumber(vertex).size());
            assertEquals("The size of the component should be 2.", 2, graph2.sizeOfComponent(vertex));
            adjacent = (ArrayList<String>) graph2.adjacent(vertex);
            adjacentVertex = adjacent.get(0);
            adjacentNumber = (ArrayList<Integer>) graph2.adjacentNumber(vertex);
            adjacentVertexNumber = adjacentNumber.get(0);
            switch(vertex)
            {
                case "a":
                    assertEquals("The only item should be b.", "b", adjacentVertex);
                    assertEquals("The only item should be 1.", 1, adjacentVertexNumber);
                    break;
                case "b":
                    assertEquals("The only item should be a.", "a", adjacentVertex);
                    assertEquals("The only item should be 0.", 0, adjacentVertexNumber);
                    break;
                case "c":
                    assertEquals("The only item should be d.", "d", adjacentVertex);
                    assertEquals("The only item should be 3.", 3, adjacentVertexNumber);
                    break;
                case "d":
                    assertEquals("The only item should be c.", "c", adjacentVertex);
                    assertEquals("The only item should be 2.", 2, adjacentVertexNumber);
                    break;
            }
        }
        // A self-cycle on c will be added.
        graph2.addEdge("c", "c");
        assertEquals("The number of vertexes should be 4.", 4, graph2.V());
        assertEquals("The number of edges should be 3.", 3, graph2.E());
        // The number of components should be 2.
        assertEquals("The number of components should be 2.", 2, graph2.numberOfComponents());
        for(String vertex : vertexes)
        {
            adjacent = (ArrayList<String>) graph2.adjacent(vertex);
            adjacentVertex = adjacent.get(0);
            adjacentNumber = (ArrayList<Integer>) graph2.adjacentNumber(vertex);
            adjacentVertexNumber = adjacentNumber.get(0);
            assertEquals("The size of the component should be 2.", 2, graph2.sizeOfComponent(vertex));
            switch(vertex)
            {
                case "a":
                    assertEquals("The only item should be b.", "b", adjacentVertex);
                    assertEquals("The only item should be 1.", 1, adjacentVertexNumber);
                    assertEquals("The size of the list should be 1.", 1, graph2.adjacent(vertex).size());
                    break;
                case "b":
                    assertEquals("The only item should be a.", "a", adjacentVertex);
                    assertEquals("The only item should be 0.", 0, adjacentVertexNumber);
                    assertEquals("The size of the list should be 1.", 1, graph2.adjacent(vertex).size());
                    break;
                case "c":
                    assertEquals("The first item should be d.", "d", adjacentVertex);
                    assertEquals("The first item should be 3.", 3, adjacentVertexNumber);
                    assertEquals("The second item should be c.", "c", adjacent.get(1));
                    assertEquals("The second item should be 2.", 2, (int) adjacentNumber.get(1));
                    assertEquals("The size of the list should be 2.", 2, graph2.adjacent(vertex).size());
                    break;
                case "d":
                    assertEquals("The only item should be c.", "c", adjacentVertex);
                    assertEquals("The only item should be 2.", 2, adjacentVertexNumber);
                    assertEquals("The size of the list should be 1.", 1, graph2.adjacent(vertex).size());
                    break;
            }
        }
        // The same edge can be added twice.
        graph2.addEdge("b", "a");
        assertEquals("The number of vertexes should be 4.", 4, graph2.V());
        assertEquals("The number of edges should be 4.", 4, graph2.E());
        // The number of components should be 2.
        assertEquals("The number of components should be 2.", 2, graph2.numberOfComponents());
        for(String vertex : vertexes)
        {
            adjacent = (ArrayList<String>) graph2.adjacent(vertex);
            adjacentVertex = adjacent.get(0);
            adjacentNumber = (ArrayList<Integer>) graph2.adjacentNumber(vertex);
            adjacentVertexNumber = adjacentNumber.get(0);
            assertEquals("The size of the component should be 2.", 2, graph2.sizeOfComponent(vertex));
            switch(vertex)
            {
                case "a":
                    assertEquals("The first item should be b.", "b", adjacentVertex);
                    assertEquals("The first item should be 1.", 1, adjacentVertexNumber);
                    assertEquals("The second item should be b.", "b", adjacent.get(1));
                    assertEquals("The second item should be 1.", 1, (int) adjacentNumber.get(1));
                    assertEquals("The size of the list should be 2.", 2, graph2.adjacent(vertex).size());
                    break;
                case "b":
                    assertEquals("The first item should be a.", "a", adjacentVertex);
                    assertEquals("The first item should be 0.", 0, adjacentVertexNumber);
                    assertEquals("The second item should be a.", "a", adjacent.get(1));
                    assertEquals("The second item should be 0.", 0, (int) adjacentNumber.get(1));
                    assertEquals("The size of the list should be 2.", 2, graph2.adjacent(vertex).size());
                    break;
                case "c":
                    assertEquals("The first item should be d.", "d", adjacentVertex);
                    assertEquals("The first item should be 3.", 3, adjacentVertexNumber);
                    assertEquals("The second item should be c.", "c", adjacent.get(1));
                    assertEquals("The second item should be 2.", 2, (int) adjacentNumber.get(1));
                    assertEquals("The size of the list should be 2.", 2, graph2.adjacent(vertex).size());
                    break;
                case "d":
                    assertEquals("The only item should be c.", "c", adjacentVertex);
                    assertEquals("The only item should be 2.", 2, adjacentVertexNumber);
                    assertEquals("The size of the list should be 1.", 1, graph2.adjacent(vertex).size());
                    break;
            }
        }
    }

    /**
     * Tests that access to a null edge ends up in a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void addEdgeTest2()
    {
        setup3();
        graph1.addEdge(null, 3);
    }

    /**
     * Tests that adding an edge between non-existent vertexes ends up in a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void addEdgeTest3()
    {
        setup3();
        graph1.addEdge(100, 200);
    }

    /**
     * Tests that the graph adds edges properly and checks for null vertexes, self-cycles and duplicate edges-
     */
    @Test
    public void addEdgeCheckedTest()
    {
        setup4();
        // Edges a-b and c-d will be added.
        graph2.addEdgeChecked("a", "b"); graph2.addEdgeChecked("c", "d");
        assertEquals("The number of vertexes should be 4.", 4, graph2.V());
        assertEquals("The number of edges should be 2.", 2, graph2.E());
        // The number of components should be 2.
        assertEquals("The number of components should be 2.", 2, graph2.numberOfComponents());
        String[] vertexes = {"a", "b", "c", "d"};
        ArrayList<String> adjacent;
        ArrayList<Integer> adjacentNumber;
        String adjacentVertex;
        int adjacentVertexNumber;
        for(String vertex : vertexes)
        {
            assertEquals("The size of the list should be 1.", 1, graph2.adjacent(vertex).size());
            adjacent = (ArrayList<String>) graph2.adjacent(vertex);
            adjacentVertex = adjacent.get(0);
            adjacentNumber = (ArrayList<Integer>) graph2.adjacentNumber(vertex);
            adjacentVertexNumber = adjacentNumber.get(0);
            assertEquals("The size of the component should be 2.", 2, graph2.sizeOfComponent(vertex));
            switch(vertex)
            {
                case "a":
                    assertEquals("The only item should be b.", "b", adjacentVertex);
                    assertEquals("The only item should be 1.", 1, adjacentVertexNumber);
                    break;
                case "b":
                    assertEquals("The only item should be a.", "a", adjacentVertex);
                    assertEquals("The only item should be 0.", 0, adjacentVertexNumber);
                    break;
                case "c":
                    assertEquals("The only item should be d.", "d", adjacentVertex);
                    assertEquals("The only item should be 3.", 3, adjacentVertexNumber);
                    break;
                case "d":
                    assertEquals("The only item should be c.", "c", adjacentVertex);
                    assertEquals("The only item should be 2.", 2, adjacentVertexNumber);
                    break;
            }
        }
        // A self-cycle on c will be added. Nothing should change.
        graph2.addEdgeChecked("c", "c");
        assertEquals("The number of vertexes should be 4.", 4, graph2.V());
        assertEquals("The number of edges should be 2.", 2, graph2.E());
        // The number of components should be 2.
        assertEquals("The number of components should be 2.", 2, graph2.numberOfComponents());
        for(String vertex : vertexes)
        {
            assertEquals("The size of the list should be 1.", 1, graph2.adjacent(vertex).size());
            adjacent = (ArrayList<String>) graph2.adjacent(vertex);
            adjacentVertex = adjacent.get(0);
            adjacentNumber = (ArrayList<Integer>) graph2.adjacentNumber(vertex);
            adjacentVertexNumber = adjacentNumber.get(0);
            assertEquals("The size of the component should be 2.", 2, graph2.sizeOfComponent(vertex));
            switch(vertex)
            {
                case "a":
                    assertEquals("The only item should be b.", "b", adjacentVertex);
                    assertEquals("The only item should be 1.", 1, adjacentVertexNumber);
                    break;
                case "b":
                    assertEquals("The only item should be a.", "a", adjacentVertex);
                    assertEquals("The only item should be 0.", 0, adjacentVertexNumber);
                    break;
                case "c":
                    assertEquals("The only item should be d.", "d", adjacentVertex);
                    assertEquals("The only item should be 3.", 3, adjacentVertexNumber);
                    break;
                case "d":
                    assertEquals("The only item should be c.", "c", adjacentVertex);
                    assertEquals("The only item should be 2.", 2, adjacentVertexNumber);
                    break;
            }
        }
        // The same edge can be added twice. Nothing should change.
        graph2.addEdgeChecked("b", "a");
        assertEquals("The number of vertexes should be 4.", 4, graph2.V());
        assertEquals("The number of edges should be 2.", 2, graph2.E());
        // The number of components should be 2.
        assertEquals("The number of components should be 2.", 2, graph2.numberOfComponents());
        for(String vertex : vertexes)
        {
            assertEquals("The size of the list should be 1.", 1, graph2.adjacent(vertex).size());
            adjacent = (ArrayList<String>) graph2.adjacent(vertex);
            adjacentVertex = adjacent.get(0);
            adjacentNumber = (ArrayList<Integer>) graph2.adjacentNumber(vertex);
            adjacentVertexNumber = adjacentNumber.get(0);
            assertEquals("The size of the component should be 2.", 2, graph2.sizeOfComponent(vertex));
            switch(vertex)
            {
                case "a":
                    assertEquals("The only item should be b.", "b", adjacentVertex);
                    assertEquals("The only item should be 1.", 1, adjacentVertexNumber);
                    break;
                case "b":
                    assertEquals("The only item should be a.", "a", adjacentVertex);
                    assertEquals("The only item should be 0.", 0, adjacentVertexNumber);
                    break;
                case "c":
                    assertEquals("The only item should be d.", "d", adjacentVertex);
                    assertEquals("The only item should be 3.", 3, adjacentVertexNumber);
                    break;
                case "d":
                    assertEquals("The only item should be c.", "c", adjacentVertex);
                    assertEquals("The only item should be 2.", 2, adjacentVertexNumber);
                    break;
            }
        }
        // Adding edges between null values or non-existent edges doesn't change anything.
        graph2.addEdgeChecked(null, null); graph2.addEdgeChecked(null, "a");
        graph2.addEdgeChecked("b", null); graph2.addEdgeChecked("hi", "hello");
        graph2.addEdgeChecked("b", "hello"); graph2.addEdgeChecked("a", "hi");
        assertEquals("The number of vertexes should be 4.", 4, graph2.V());
        assertEquals("The number of edges should be 2.", 2, graph2.E());
        // The number of components should be 2.
        assertEquals("The number of components should be 2.", 2, graph2.numberOfComponents());
        for(String vertex : vertexes)
        {
            assertEquals("The size of the list should be 1.", 1, graph2.adjacent(vertex).size());
            adjacent = (ArrayList<String>) graph2.adjacent(vertex);
            adjacentVertex = adjacent.get(0);
            adjacentNumber = (ArrayList<Integer>) graph2.adjacentNumber(vertex);
            adjacentVertexNumber = adjacentNumber.get(0);
            assertEquals("The size of the component should be 2.", 2, graph2.sizeOfComponent(vertex));
            switch(vertex)
            {
                case "a":
                    assertEquals("The only item should be b.", "b", adjacentVertex);
                    assertEquals("The only item should be 1.", 1, adjacentVertexNumber);
                    break;
                case "b":
                    assertEquals("The only item should be a.", "a", adjacentVertex);
                    assertEquals("The only item should be 0.", 0, adjacentVertexNumber);
                    break;
                case "c":
                    assertEquals("The only item should be d.", "d", adjacentVertex);
                    assertEquals("The only item should be 3.", 3, adjacentVertexNumber);
                    break;
                case "d":
                    assertEquals("The only item should be c.", "c", adjacentVertex);
                    assertEquals("The only item should be 2.", 2, adjacentVertexNumber);
                    break;
            }
        }
    }

    @Test
    public void adjacentTest1()
    {
        setup5();
        // Edges 1-3, 3-5 and 1-7 will be added.
        graph1.addEdge(1, 3); graph1.addEdge(3, 5); graph1.addEdge(1, 7);
        // There should be 4 adjacency lists. The list of vertex 1 should contain: 3 and 7,
        // the list of 3 should contain: 1 and 5, the list of 5 should contain 3 and the list
        // of 7 should contain 1.
        assertEquals("There should be 4 adjacency lists.", 4, graph1.adjacent().size());
        assertEquals("There should be 4 adjacency lists.", 4, graph1.adjacentNumber().size());
        // The number of components should be 1.
        assertEquals("The number of components should be 1.", 1, graph1.numberOfComponents());
        Integer[] vertexes = {1, 3, 5, 7};
        ArrayList<Integer> adjacent;
        ArrayList<Integer> adjacentNumber;
        for(Integer vertex : vertexes)
        {
            adjacent = (ArrayList<Integer>) graph1.adjacent(vertex);
            adjacentNumber = graph1.adjacentNumber(vertex);
            assertEquals("The size of the component should be 4.", 4, graph1.sizeOfComponent(vertex));
            switch(vertex)
            {
                case 1:
                    assertEquals("The size of the list should be 2.", 2, adjacent.size());
                    assertEquals("The first element should be 3.", 3, (int) adjacent.get(0));
                    assertEquals("The first element should be 1.", 1, (int) adjacentNumber.get(0));
                    assertEquals("The second element should be 7.", 7, (int) adjacent.get(1));
                    assertEquals("The second element should be 3.", 3, (int) adjacentNumber.get(1));
                    break;
                case 3:
                    assertEquals("The size of the list should be 2.", 2, adjacent.size());
                    assertEquals("The first element should be 1.", 1, (int) adjacent.get(0));
                    assertEquals("The first element should be 0.", 0, (int) adjacentNumber.get(0));
                    assertEquals("The second element should be 5.", 5, (int) adjacent.get(1));
                    assertEquals("The second element should be 2.", 2, (int) adjacentNumber.get(1));
                    break;
                case 5:
                    assertEquals("The size of the list should be 1.", 1, adjacent.size());
                    assertEquals("The only element should be 3.", 3, (int) adjacent.get(0));
                    assertEquals("The only element should be 1.", 1, (int) adjacentNumber.get(0));
                    break;
                case 7:
                    assertEquals("The size of the list should be 1.", 1, adjacent.size());
                    assertEquals("The only element should be 1.", 1, (int) adjacent.get(0));
                    assertEquals("The only element should be 0.", 0, (int) adjacentNumber.get(0));
            }
        }
    }

    /**
     * Tests that access to the adjacency list of a non-existent vertex results in a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void adjacentTest2()
    {
        setup1();
        graph1.adjacent(200);
    }

    /**
     * Tests that the graph manages adjacency lists properly and checks for null or non-existent vertexes.
     */
    @Test
    public void adjacentCheckedTest()
    {
        setup5();
        // Edges 1-3, 3-5 and 1-7 will be added.
        graph1.addEdge(1, 3); graph1.addEdge(3, 5); graph1.addEdge(1, 7);
        // There should be 4 adjacency lists. The list of vertex 1 should contain: 3 and 7,
        // the list of 3 should contain: 1 and 5, the list of 5 should contain 3 and the list
        // of 7 should contain 1.
        assertEquals("There should be 4 adjacency lists.", 4, graph1.adjacent().size());
        assertEquals("There should be 4 adjacency lists.", 4, graph1.adjacentNumber().size());
        // The number of components should be 1.
        assertEquals("The number of components should be 1.", 1, graph1.numberOfComponents());
        Integer[] vertexes = {1, 3, 5, 7};
        ArrayList<Integer> adjacent;
        ArrayList<Integer> adjacentNumber;
        for(Integer vertex : vertexes)
        {
            adjacent = (ArrayList<Integer>) graph1.adjacentChecked(vertex);
            adjacentNumber = graph1.adjacentNumber(vertex);
            assertEquals("The size of the component should be 4.", 4, graph1.sizeOfComponent(vertex));
            switch(vertex)
            {
                case 1:
                    assertEquals("The size of the list should be 2.", 2, adjacent.size());
                    assertEquals("The first element should be 3.", 3, (int) adjacent.get(0));
                    assertEquals("The first element should be 1.", 1, (int) adjacentNumber.get(0));
                    assertEquals("The second element should be 7.", 7, (int) adjacent.get(1));
                    assertEquals("The second element should be 3.", 3, (int) adjacentNumber.get(1));
                    break;
                case 3:
                    assertEquals("The size of the list should be 2.", 2, adjacent.size());
                    assertEquals("The first element should be 1.", 1, (int) adjacent.get(0));
                    assertEquals("The first element should be 0.", 0, (int) adjacentNumber.get(0));
                    assertEquals("The second element should be 5.", 5, (int) adjacent.get(1));
                    assertEquals("The second element should be 2.", 2, (int) adjacentNumber.get(1));
                    break;
                case 5:
                    assertEquals("The size of the list should be 1.", 1, adjacent.size());
                    assertEquals("The only element should be 3.", 3, (int) adjacent.get(0));
                    assertEquals("The only element should be 1.", 1, (int) adjacentNumber.get(0));
                    break;
                case 7:
                    assertEquals("The size of the list should be 1.", 1, adjacent.size());
                    assertEquals("The only element should be 1.", 1, (int) adjacent.get(0));
                    assertEquals("The only element should be 0.", 0, (int) adjacentNumber.get(0));
            }
        }
        // Access to the adjacency list of non-existing vertexes should result in null.
        assertNull("The list should be null.", graph1.adjacentChecked(100));
        assertNull("The list should be null.", graph1.adjacentChecked(null));
        assertNull("The list should be null.", graph1.adjacentChecked(4));
    }

    /**
     * Tests that the graph manages sizes of components properly.
     */
    @Test
    public void testSizeOfComponent1()
    {
        Integer[] vertexes = {10, 15, 20, 25, 30};
        graph1 = new UndirectedUnweightedCCGraph<>(vertexes);
        // The number of components should be 5.
        assertEquals("The number of components should be 5.", 5, graph1.numberOfComponents());
        for(Integer vertex : vertexes)
            assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));
        // Edge 10-15 will be added.
        graph1.addEdge(10, 15);
        // The number of components should be 4.
        assertEquals("The number of components should be 4.", 4, graph1.numberOfComponents());
        for(Integer vertex : vertexes)
        {
            if(vertex == 10 || vertex == 15)
                assertEquals("The size of the component should be 2.", 2, graph1.sizeOfComponent(vertex));
            else
                assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));

        }
        // Edge 20-30 will be added.
        graph1.addEdge(20, 30);
        // The number of components should be 3.
        assertEquals("The number of components should be 3.", 3, graph1.numberOfComponents());
        for(Integer vertex : vertexes)
        {
            if(vertex == 10 || vertex == 15 || vertex == 20 || vertex == 30)
                assertEquals("The size of the component should be 2.", 2, graph1.sizeOfComponent(vertex));
            else
                assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));

        }
        // The same edge 20-30 will be added again. Nothing should change.
        graph1.addEdge(20, 30);
        // The number of components should be 3.
        assertEquals("The number of components should be 3.", 3, graph1.numberOfComponents());
        for(Integer vertex : vertexes)
        {
            if(vertex == 10 || vertex == 15 || vertex == 20 || vertex == 30)
                assertEquals("The size of the component should be 2.", 2, graph1.sizeOfComponent(vertex));
            else
                assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));

        }
        // A self-cycle will be added for vertex 20 and nothing should change.
        graph1.addEdge(20, 20);
        // The number of components should be 3.
        assertEquals("The number of components should be 3.", 3, graph1.numberOfComponents());
        for(Integer vertex : vertexes)
        {
            if(vertex == 10 || vertex == 15 || vertex == 20 || vertex == 30)
                assertEquals("The size of the component should be 2.", 2, graph1.sizeOfComponent(vertex));
            else
                assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));

        }
        // Edge 10-20 will be added.
        graph1.addEdge(10, 20);
        // The number of components should be 3.
        assertEquals("The number of components should be 2.", 2, graph1.numberOfComponents());
        for(Integer vertex : vertexes)
        {
            if(vertex == 10 || vertex == 15 || vertex == 20 || vertex == 30)
                assertEquals("The size of the component should be 4.", 4, graph1.sizeOfComponent(vertex));
            else
                assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(vertex));

        }
    }

    /**
     * Tests that the component's size of a null vertex results in a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void testSizeOfComponent2()
    {
        setup1();
        int num = graph1.sizeOfComponent(null);
    }

    /**
     * Tests that the component's size of a non-existent vertex results in a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void testSizeOfComponent3()
    {
        setup1();
        int num = graph1.sizeOfComponent(100);
    }

    /**
     * Tests that the graph manages sizes of components properly and checks for null and non-existent vertexes.
     */
    @Test
    public void testSizeOfComponentChecked()
    {
        Integer[] vertexes = {10, 15, 20, 25, 30};
        graph1 = new UndirectedUnweightedCCGraph<>(vertexes);
        // The number of components should be 5.
        assertEquals("The number of components should be 5.", 5, graph1.numberOfComponents());
        for(Integer vertex : vertexes)
            assertEquals("The size of the component should be 1.", 1, (int) graph1.sizeOfComponentChecked(vertex));
        // Edge 10-15 will be added.
        graph1.addEdge(10, 15);
        // The number of components should be 4.
        assertEquals("The number of components should be 4.", 4, graph1.numberOfComponents());
        for(Integer vertex : vertexes)
        {
            if(vertex == 10 || vertex == 15)
                assertEquals("The size of the component should be 2.", 2, (int) graph1.sizeOfComponentChecked(vertex));
            else
                assertEquals("The size of the component should be 1.", 1, (int) graph1.sizeOfComponentChecked(vertex));

        }
        // Edge 20-30 will be added.
        graph1.addEdge(20, 30);
        // The number of components should be 3.
        assertEquals("The number of components should be 3.", 3, graph1.numberOfComponents());
        for(Integer vertex : vertexes)
        {
            if(vertex == 10 || vertex == 15 || vertex == 20 || vertex == 30)
                assertEquals("The size of the component should be 2.", 2, (int) graph1.sizeOfComponentChecked(vertex));
            else
                assertEquals("The size of the component should be 1.", 1, (int) graph1.sizeOfComponentChecked(vertex));

        }
        // The same edge 20-30 will be added again. Nothing should change.
        graph1.addEdge(20, 30);
        // The number of components should be 3.
        assertEquals("The number of components should be 3.", 3, graph1.numberOfComponents());
        for(Integer vertex : vertexes)
        {
            if(vertex == 10 || vertex == 15 || vertex == 20 || vertex == 30)
                assertEquals("The size of the component should be 2.", 2, (int) graph1.sizeOfComponentChecked(vertex));
            else
                assertEquals("The size of the component should be 1.", 1, (int) graph1.sizeOfComponentChecked(vertex));

        }
        // A self-cycle will be added for vertex 20 and nothing should change.
        graph1.addEdge(20, 20);
        // The number of components should be 3.
        assertEquals("The number of components should be 3.", 3, graph1.numberOfComponents());
        for(Integer vertex : vertexes)
        {
            if(vertex == 10 || vertex == 15 || vertex == 20 || vertex == 30)
                assertEquals("The size of the component should be 2.", 2, (int) graph1.sizeOfComponentChecked(vertex));
            else
                assertEquals("The size of the component should be 1.", 1, (int) graph1.sizeOfComponentChecked(vertex));

        }
        // Edge 10-20 will be added.
        graph1.addEdge(10, 20);
        // The number of components should be 3.
        assertEquals("The number of components should be 2.", 2, graph1.numberOfComponents());
        for(Integer vertex : vertexes)
        {
            if(vertex == 10 || vertex == 15 || vertex == 20 || vertex == 30)
                assertEquals("The size of the component should be 4.", 4, (int) graph1.sizeOfComponentChecked(vertex));
            else
                assertEquals("The size of the component should be 1.", 1, (int) graph1.sizeOfComponentChecked(vertex));

        }
        // Size of null or non-existent vertexes should return null.
        assertNull("The result should be null.", graph1.sizeOfComponentChecked(null));
        assertNull("The result should be null.", graph1.sizeOfComponentChecked(-1));
        assertNull("The result should be null.", graph1.sizeOfComponentChecked(400));
    }

    /**
     * Tests that the DFS algorithm works properly.
     */
    @Test
    public void DFSTest()
    {
        Integer[] newVertexes = {0, 1, 2, 3, 4, 5, 6};
        UndirectedUnweightedCCGraph<Integer> newGraph = new UndirectedUnweightedCCGraph<>(newVertexes);
        // The following edges are added: 0-1, 0-2, 2-3, 2-4, 1-4 and 5-6.
        newGraph.addEdge(0, 1); newGraph.addEdge(0, 2); newGraph.addEdge(2, 3);
        newGraph.addEdge(2, 4); newGraph.addEdge(1, 4); newGraph.addEdge(5, 6);
        // The number of components should be 2.
        assertEquals("The number of components should be 2.", 2, newGraph.numberOfComponents());
        // The first component 0-1-2-3-4 is of size 5 and the second 5-6 is of size 2.
        // A DFS from the origin is created.
        DFS<Integer> dfs = new DFS<>(newGraph, 0);
        // There should be a path from all vertexes except 5 and 6.
        for(int i = 0; i < 5; ++i)
        {
            assertEquals("The size of the component should be 5.", 5, newGraph.sizeOfComponent(i));
            assertTrue("There should be a path to the vertex " + i + ".", dfs.hasPathTo(i));
        }
        for(int i = 5; i < 7; ++i)
        {
            assertEquals("The size of the component should be 2.", 2, newGraph.sizeOfComponent(i));
            assertFalse("There shouldn't be a path to the vertex " + i + ".", dfs.hasPathTo(i));
        }
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
        Integer[] newVertexes = {0, 1, 2, 3, 4, 5, 6};
        UndirectedUnweightedCCGraph<Integer> newGraph = new UndirectedUnweightedCCGraph<>(newVertexes);
        // The following edges are added: 0-1, 0-2, 2-3, 2-4, 1-4 and 5-6.
        newGraph.addEdge(0, 1); newGraph.addEdge(0, 2); newGraph.addEdge(2, 3);
        newGraph.addEdge(2, 4); newGraph.addEdge(1, 4); newGraph.addEdge(5, 6);
        // The number of components should be 2.
        assertEquals("The number of components should be 2.", 2, newGraph.numberOfComponents());
        // A DFS from the origin is created.
        BFS<Integer> bfs = new BFS<>(newGraph, 0);
        // There should be a path from all vertexes except 5 and 6.
        for(int i = 0; i < 5; ++i)
        {
            assertEquals("The size of the component should be 5.", 5, newGraph.sizeOfComponent(i));
            assertTrue("There should be a path to the vertex " + i + ".", bfs.hasPathTo(i));
        }
        for(int i = 5; i < 7; ++i)
        {
            assertEquals("The size of the component should be 2.", 2, newGraph.sizeOfComponent(i));
            assertFalse("There shouldn't be a path to the vertex " + i + ".", bfs.hasPathTo(i));
        }
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

    /**
     * Tests that the IsAcyclic algorithm works properly when there's no cycles.
     */
    @Test
    public void isAcyclicTest1()
    {
        Integer[] vertexes = {10, 20, 30, 100, 200};
        graph1 = new UndirectedUnweightedCCGraph<>(vertexes);
        // Edges 10-20, 10-30 and 100-30 will be added.
        graph1.addEdge(10, 20); graph1.addEdge(10, 30); graph1.addEdge(100, 30);
        // The number of components should be 2.
        assertEquals("The number of components should be 2.", 2, graph1.numberOfComponents());
        // None of the graph's components isn't acyclic, therefore, any combination
        // of searches for the graph should result in it being acyclic.
        IsAcyclic<Integer> isAcyclic1 = new IsAcyclic<>(graph1);
        assertTrue("The graph should be acyclic.", isAcyclic1.isAcyclic());
        for(Integer v : vertexes)
        {
            IsAcyclic<Integer> isAcyclic2 = new IsAcyclic<>(graph1, v);
            assertTrue("The graph should be acyclic.", isAcyclic2.isAcyclic());
            if(v == 10 || v == 20 || v == 30 || v == 100)
                assertEquals("The size of the component should be 4.", 4, graph1.sizeOfComponent(v));
            else
                assertEquals("The size of the component should be 1.", 1, graph1.sizeOfComponent(v));
        }
    }

    /**
     * Tests that the IsAcyclic algorithm works properly when there's a cycle.
     */
    @Test(expected = NullPointerException.class)
    public void isAcyclicTest2()
    {
        setup1();
        IsAcyclic<String> isAcyclic = new IsAcyclic<>(graph2, "Hello");
    }

    /**
     * Tests that the IsAcyclic algorithm works properly when there's a cycle.
     */
    @Test
    public void isAcyclicTest3()
    {
        Integer[] vertexes = {10, 20, 30, 100, 200, 300};
        graph1 = new UndirectedUnweightedCCGraph<>(vertexes);
        // Edges 10-20, 10-30, 100-10, 100-30 and 200-300 will be added.
        graph1.addEdge(10, 20); graph1.addEdge(10, 30); graph1.addEdge(100, 10);
        graph1.addEdge(100, 30); graph1.addEdge(200, 300);
        // The number of components should be 2.
        assertEquals("The number of components should be 2.", 2, graph1.numberOfComponents());
        // None of the graph's components isn't acyclic, therefore, any combination
        // of searches for the graph should result in it being acyclic.
        IsAcyclic<Integer> isAcyclic1 = new IsAcyclic<>(graph1);
        assertFalse("The graph should not be acyclic.", isAcyclic1.isAcyclic());
        for(Integer v : vertexes)
        {
            IsAcyclic<Integer> isAcyclic2 = new IsAcyclic<>(graph1, v);
            switch (v)
            {
                case 10:
                case 20:
                case 30:
                case 100:
                    assertEquals("The size of the component should be 4.", 4, graph1.sizeOfComponent(v));
                    assertFalse("The graph should not be acyclic.", isAcyclic2.isAcyclic());
                    break;
                default:
                    assertEquals("The size of the component should be 2.", 2, graph1.sizeOfComponent(v));
                    assertTrue("The graph should be acyclic.", isAcyclic2.isAcyclic());
            }
        }
    }

    /**
     * Tests that the isAcyclic algorithm works properly when there's a self-cycle.
     */
    @Test
    public void isAcyclicTest4()
    {
        setup2();
        // Vertex 'Hi' will be added.
        graph2.addVertex("Hi");
        // Edge 'Hi'-'Hi' will be added.
        graph2.addEdge("Hi", "Hi");
        // The number of components should be 1.
        assertEquals("The number of components should be 1.", 1, graph2.numberOfComponents());
        assertEquals("The size of the component should be 1.", 1, graph2.sizeOfComponent("Hi"));
        // Now the graph has a self-cycle.
        IsAcyclic<String> isAcyclic1 = new IsAcyclic<>(graph2);
        IsAcyclic<String> isAcyclic2 = new IsAcyclic<>(graph2, "Hi");
        assertFalse("The graph should not be acyclic.", isAcyclic1.isAcyclic());
        assertFalse("The graph should not be acyclic.", isAcyclic2.isAcyclic());
    }

    /**
     * Tests that the TwoColor algorithm works properly when the graph is two-colorable.
     */
    @Test
    public void twoColorableTest1()
    {
        setup4();
        // Vertex e will be added.
        graph2.addVertex("e");
        // Edges a-b, b-d, d-c, and b-e.
        graph2.addEdge("a", "b"); graph2.addEdge("b", "d"); graph2.addEdge("d", "c"); graph2.addEdge("b", "e");
        // The number of components should be 1.
        assertEquals("The number of components should be 1.", 1, graph2.numberOfComponents());
        TwoColor<String> twoColor = new TwoColor<>(graph2);
        // The graph should be two colorable.
        assertTrue("The graph should be two colorable.", twoColor.isTwoColorable());
        // The HashMaps shouldn't be null.
        assertNotNull("The HashMap shouldn't be null.", twoColor.getColor());
        assertNotNull("The HashMap shouldn't be null.", twoColor.getColorNum());
        HashMap<String, Boolean> color = twoColor.getColor();
        HashMap<String, Integer> colorNum = twoColor.getColorNum();
        for(String vertex : graph2.keyToNumber().keySet())
        {
            assertEquals("The size of the component should be 5.", 5, graph2.sizeOfComponent(vertex));
            for(String adjacent : graph2.adjacent(vertex))
            {
                assertEquals("The color should be different.", color.get(vertex), !color.get(adjacent));
                assertNotEquals("The color should be different.", colorNum.get(vertex), colorNum.get(adjacent));
            }
        }
    }

    /**
     * Tests that the TwoColor algorithm works properly when the graph isn't two-colorable.
     */
    @Test
    public void twoColorableTest2()
    {
        setup4();
        // Vertex e will be added.
        graph2.addVertex("e");
        // Edges a-b, a-c, b-c, and b-e will be added.
        graph2.addEdge("a", "b"); graph2.addEdge("a", "c"); graph2.addEdge("b", "c"); graph2.addEdge("b", "e");
        // The number of components should be 2.
        assertEquals("The number of components should be 2.", 2, graph2.numberOfComponents());
        String[] vertexes = {"a", "b", "c", "d", "e"};
        for(String vertex : vertexes)
        {
            if(vertex.equals("a") || vertex.equals("b") || vertex.equals("c") || vertex.equals("e"))
                assertEquals("The size of the component should be 4.", 4, graph2.sizeOfComponent(vertex));
            else
                assertEquals("The size of the component should be 1.", 1, graph2.sizeOfComponent(vertex));

        }
        TwoColor<String> twoColor = new TwoColor<>(graph2);
        // The graph shouldn't be two-colorable.
        assertFalse("The graph shouldn't be two-colorable.", twoColor.isTwoColorable());
        // The HashMaps should be null.
        assertNull("The HashMap should be null.", twoColor.getColor());
        assertNull("The HashMap should be null.", twoColor.getColorNum());
    }
}