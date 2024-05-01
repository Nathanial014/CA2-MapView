package com.example.ca2mapview;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DijkstraTest {
    private Graph graph;
    private Node nodeA, nodeB, nodeC, nodeD;

    @BeforeEach
    void setUp() {
        graph = new Graph();
        // Create nodes with cultural values
        nodeA = new Node("A", 0, 0, 10);
        nodeB = new Node("B", 1, 1, 20);
        nodeC = new Node("C", 2, 2, 30);
        nodeD = new Node("D", 3, 3, 40);

        // Add nodes to the graph
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);

        // Connect nodes with edges
        graph.addEdge(new Edge(nodeA, nodeB, 5));
        graph.addEdge(new Edge(nodeB, nodeC, 10));
        graph.addEdge(new Edge(nodeC, nodeD, 15));
        graph.addEdge(new Edge(nodeA, nodeD, 50));  // Longer but high cultural value path
    }

    @Test
    void testFindShortestPath() {
        Dijkstra dijkstra = new Dijkstra(graph);
        List<Node> path = dijkstra.findShortestPath(nodeA, nodeD);
        assertEquals(3, path.size(), "Path should include three nodes.");
        assertEquals(nodeD, path.get(2), "Path should end at node D.");
    }

    @Test
    void testFindMostCulturalPath() {
        Dijkstra dijkstra = new Dijkstra(graph);
        List<Node> path = dijkstra.findMostCulturalPath(nodeA, nodeD);
        assertEquals(4, path.size(), "Path should include four nodes due to cultural value.");
        assertTrue(path.contains(nodeC), "Path should contain node C with high cultural value.");
    }

    @Test
    void testNoPathScenario() {
        Node nodeE = new Node("E", 4, 4, 10);
        graph.addNode(nodeE);  // This node is not connected
        Dijkstra dijkstra = new Dijkstra(graph);
        List<Node> path = dijkstra.findShortestPath(nodeA, nodeE);
        assertTrue(path.isEmpty(), "Path should be empty as there is no connection.");
    }
}
