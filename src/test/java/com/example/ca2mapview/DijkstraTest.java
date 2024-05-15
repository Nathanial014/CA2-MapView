package com.example.ca2mapview;

import static org.junit.jupiter.api.Assertions.*;

import com.example.ca2mapview.Dijkstra;
import com.example.ca2mapview.Graph;
import com.example.ca2mapview.Node;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

class DijkstraTest {

    @Test
    public void testFindShortestPath() {
        // Create nodes for the graph
        Node nodeA = new Node("A", 0, 0, 0);
        Node nodeB = new Node("B", 0, 0, 0);
        Node nodeC = new Node("C", 0, 0, 0);

        // Create edges between the nodes
        Graph graph = new Graph();
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addEdge(nodeA, nodeB, 2); // Edge from A to B with weight 2
        graph.addEdge(nodeB, nodeC, 3); // Edge from B to C with weight 3
        graph.addEdge(nodeA, nodeC, 5); // Edge from A to C with weight 5

        // Create Dijkstra object to test
        Dijkstra dijkstra = new Dijkstra(graph);

        // Find the shortest path from nodeA to nodeC
        List<Node> shortestPath = dijkstra.findShortestPath(nodeA, nodeC, null);

        // Define the expected shortest path
        List<Node> expectedPath = Arrays.asList(nodeA, nodeB, nodeC);

        // Verify that the actual shortest path matches the expected path
        assertEquals(expectedPath, shortestPath, "Shortest path should be A -> B -> C");
    }

}