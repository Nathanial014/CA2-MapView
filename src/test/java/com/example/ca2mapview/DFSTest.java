package com.example.ca2mapview;

import com.example.ca2mapview.DFS;
import com.example.ca2mapview.Graph;
import com.example.ca2mapview.Node;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DFSTest {

    @Test
    public void testFindRoutes() {
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
        graph.addEdge(nodeA, nodeC, 1); // Edge from A to C
        graph.addEdge(nodeB, nodeD, 1); // Edge from B to D
        graph.addEdge(nodeC, nodeD, 1); // Edge from C to D

        // Create DFS object to test
        DFS dfs = new DFS(graph, 5); // Set max routes to 5

        // Find routes from nodeA to nodeD
        List<List<Node>> routes = dfs.findRoutes(nodeA, nodeD);

        // Define the expected routes
        List<List<Node>> expectedRoutes = new ArrayList<>();
        List<Node> route1 = new ArrayList<>();
        route1.add(nodeA);
        route1.add(nodeB);
        route1.add(nodeD);
        expectedRoutes.add(route1);

        List<Node> route2 = new ArrayList<>();
        route2.add(nodeA);
        route2.add(nodeC);
        route2.add(nodeD);
        expectedRoutes.add(route2);

        // Verify that the actual routes match the expected routes
        assertEquals(expectedRoutes, routes, "Routes should be A -> B -> D and A -> C -> D");
    }

}