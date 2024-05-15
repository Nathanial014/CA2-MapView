package java.com.example.ca2mapview;

import com.example.ca2mapview.BFS;
import com.example.ca2mapview.Graph;
import com.example.ca2mapview.Node;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class BFSTest {

    @Test
    public void testFindRoute() {
        // Create a sample graph for testing
        Graph graph = createSampleGraph();
        BFS bfs = new BFS(graph);

        // Define start and end nodes for testing
        Node start = graph.getNode("A");
        Node end = graph.getNode("C");

        // Perform the BFS search
        List<Node> route = bfs.findRoute(start, end);

        // Assert that a route is found and is correct
        assertNotNull(route);
        assertEquals(3, route.size()); // Assuming a simple graph with 3 nodes
        assertEquals("A", route.get(0).getId());
        assertEquals("B", route.get(1).getId());
        assertEquals("C", route.get(2).getId());
    }

    // Helper method to create a sample graph for testing
    private Graph createSampleGraph() {
        Graph graph = new Graph();
        Node nodeA = new Node("A", 0, 0, 0);
        Node nodeB = new Node("B", 0, 0, 0);
        Node nodeC = new Node("C", 0, 0, 0);
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addEdge(nodeA, nodeB, 1);
        graph.addEdge(nodeB, nodeC, 1);
        return graph;
    }
  
}