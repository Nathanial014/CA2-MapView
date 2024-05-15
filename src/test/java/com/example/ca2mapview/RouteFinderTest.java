package java.com.example.ca2mapview;

import com.example.ca2mapview.Graph;
import com.example.ca2mapview.Node;
import com.example.ca2mapview.RouteFinder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RouteFinderTest {

    @Test
    public void testFindRouteWithWaypoints() {
        // Create nodes for the graph
        Node nodeA = new Node("A", 0, 0, 0);
        Node nodeB = new Node("B", 0, 0, 0);
        Node nodeC = new Node("C", 0, 0, 0);
        Node nodeD = new Node("D", 0, 0, 0);

        // Create edges between the nodes
        Graph graph = new Graph();
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addEdge(nodeA, nodeB, 1); // Edge from A to B
        graph.addEdge(nodeB, nodeC, 1); // Edge from B to C
        graph.addEdge(nodeC, nodeD, 1); // Edge from C to D

        // Create a RouteFinder object to test
        RouteFinder routeFinder = new RouteFinder(graph);

        // Define start, end, waypoints, and avoid nodes
        Node start = nodeA;
        Node end = nodeD;
        List<Node> waypoints = new ArrayList<>();
        waypoints.add(nodeB);
        List<Node> avoid = new ArrayList<>();

        // Find the route with waypoints
        List<Node> route = routeFinder.findRouteWithWaypoints(start, end, waypoints, avoid);

        // Define the expected route
        List<Node> expectedRoute = new ArrayList<>();
        expectedRoute.add(nodeA);
        expectedRoute.add(nodeB);
        expectedRoute.add(nodeC);
        expectedRoute.add(nodeD);

        // Verify that the actual route matches the expected route
        assertEquals(expectedRoute, route, "Route should be A -> B -> C -> D");
    }

    @Test
    public void testFindMostCulturalRoute() {
        // Create nodes for the graph
        Node nodeA = new Node("A", 0, 0, 50);
        Node nodeB = new Node("B", 0, 0, 70);
        Node nodeC = new Node("C", 0, 0, 60);
        Node nodeD = new Node("D", 0, 0, 80);

        // Create edges between the nodes
        Graph graph = new Graph();
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addEdge(nodeA, nodeB, 1); // Edge from A to B
        graph.addEdge(nodeB, nodeC, 1); // Edge from B to C
        graph.addEdge(nodeC, nodeD, 1); // Edge from C to D

        // Create a RouteFinder object to test
        RouteFinder routeFinder = new RouteFinder(graph);

        // Define start, end, and avoid nodes
        Node start = nodeA;
        Node end = nodeD;
        List<Node> avoid = new ArrayList<>();

        // Find the most cultural route
        List<Node> route = routeFinder.findMostCulturalRoute(start, end, avoid);

        // Define the expected route
        List<Node> expectedRoute = new ArrayList<>();
        expectedRoute.add(nodeA);
        expectedRoute.add(nodeB);
        expectedRoute.add(nodeC);
        expectedRoute.add(nodeD);

        // Verify that the actual route matches the expected route
        assertEquals(expectedRoute, route, "Route should be A -> B -> C -> D");
    }

}