import com.example.ca2mapview.Edge;
import com.example.ca2mapview.Graph;
import com.example.ca2mapview.Node;
import com.example.ca2mapview.RouteFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RouteFinderTest {
    private Graph graph;
    private RouteFinder routeFinder;
    private Node startNode, endNode;

    @BeforeEach
    void setUp() {
        graph = new Graph();
        // Create nodes with integer IDs and names
        startNode = new Node(1, "Start", 0, 0, 10);
        endNode = new Node(3, "End", 10, 10, 20);
        Node middleNode = new Node(2, "Middle", 5, 5, 15);

        // Add nodes to the graph
        graph.addNode(startNode);
        graph.addNode(middleNode);
        graph.addNode(endNode);

        // Create edges between nodes
        graph.addEdge(new Edge(startNode, middleNode, 5));
        graph.addEdge(new Edge(middleNode, endNode, 5));

        // Initialize RouteFinder with the graph
        routeFinder = new RouteFinder(graph);
    }

    @Test
    void testFindShortestRouteByDistance() {
        List<Node> route = routeFinder.findShortestRouteByDistance(startNode, endNode);
        assertNotNull(route);
        assertFalse(route.isEmpty());
        assertEquals(startNode.getId(), route.get(0).getId()); // Use integer IDs for comparison
        assertEquals(endNode.getId(), route.get(route.size() - 1).getId()); // Use integer IDs for comparison
    }

    @Test
    void testFindShortestRouteByHops() {
        List<Node> route = routeFinder.findShortestRouteByHops(startNode, endNode);
        assertNotNull(route);
        assertFalse(route.isEmpty());
        assertEquals(startNode.getId(), route.get(0).getId());
        assertEquals(endNode.getId(), route.get(route.size() - 1).getId());
    }

    @Test
    void testFindRoutePermutations() {
        List<List<Node>> routes = routeFinder.findRoutePermutations(startNode, endNode, 2);
        assertNotNull(routes);
        assertFalse(routes.isEmpty());
        assertTrue(routes.size() <= 2); // Ensure it doesn't exceed maxRoutes
    }

    @Test
    void testFindMostCulturalRoute() {
        List<Node> route = routeFinder.findMostCulturalRoute(startNode, endNode);
        assertNotNull(route);
        assertFalse(route.isEmpty());
        assertEquals(startNode.getId(), route.get(0).getId());
        assertTrue(route.stream().anyMatch(node -> node.getId() == endNode.getId()));
    }
}
