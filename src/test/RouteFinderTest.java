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
        // Setup graph and nodes here
        graph = new Graph();
        startNode = new Node("Start", 0, 0, 10);
        endNode = new Node("End", 10, 10, 20);
        Node middleNode = new Node("Middle", 5, 5, 15);

        graph.addNode(startNode);
        graph.addNode(middleNode);
        graph.addNode(endNode);

        graph.addEdge(new Edge(startNode, middleNode, 5));
        graph.addEdge(new Edge(middleNode, endNode, 5));

        routeFinder = new RouteFinder(graph);
    }

    @Test
    void testFindShortestRouteByDistance() {
        List<Node> route = routeFinder.findShortestRouteByDistance(startNode, endNode);
        assertNotNull(route);
        assertFalse(route.isEmpty());
        assertEquals("Start", route.get(0).getId());
        assertEquals("End", route.get(route.size() - 1).getId());
    }

    @Test
    void testFindShortestRouteByHops() {
        List<Node> route = routeFinder.findShortestRouteByHops(startNode, endNode);
        assertNotNull(route);
        assertFalse(route.isEmpty());
        assertEquals("Start", route.get(0).getId());
        assertEquals("End", route.get(route.size() - 1).getId());
    }

    @Test
    void testFindRoutePermutations() {
        List<List<Node>> routes = routeFinder.findRoutePermutations(startNode, endNode, 2);
        assertNotNull(routes);
        assertFalse(routes.isEmpty());
        assertTrue(routes.size() <= 2); // Ensuring it doesn't exceed maxRoutes
    }

    @Test
    void testFindMostCulturalRoute() {
        List<Node> route = routeFinder.findMostCulturalRoute(startNode, endNode);
        assertNotNull(route);
        assertFalse(route.isEmpty());
        assertEquals("Start", route.get(0).getId());
        assertTrue(route.stream().anyMatch(node -> node.getId().equals("End")));
    }
}
