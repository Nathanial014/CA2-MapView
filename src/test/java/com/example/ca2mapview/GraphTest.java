package com.example.ca2mapview;

import com.example.ca2mapview.Edge;
import com.example.ca2mapview.Graph;
import com.example.ca2mapview.Node;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    public void testAddNode() {
        // Create a new graph
        Graph graph = new Graph();

        // Add a node to the graph
        Node nodeA = new Node("A", 0, 0, 0);
        graph.addNode(nodeA);

        // Retrieve the node and check if it was added successfully
        Node retrievedNode = graph.getNode("A");
        assertNotNull(retrievedNode, "Node A should be added to the graph");
        assertEquals(nodeA, retrievedNode, "Retrieved node should be equal to the added node");
    }

    @Test
    public void testAddEdge() {
        // Create a new graph
        Graph graph = new Graph();

        // Add nodes to the graph
        Node nodeA = new Node("A", 0, 0, 0);
        Node nodeB = new Node("B", 0, 0, 0);
        graph.addNode(nodeA);
        graph.addNode(nodeB);

        // Add an edge between the nodes
        graph.addEdge(nodeA, nodeB, 1);

        // Retrieve edges from the start node and check if the edge was added successfully
        List<Edge> edgesFromNodeA = graph.getEdgesFromNode(nodeA);
        assertEquals(1, edgesFromNodeA.size(), "There should be one edge from node A");
        assertEquals(nodeB, edgesFromNodeA.get(0).getEnd(), "Edge should end at node B");
    }

    @Test
    public void testGetNodeByCoordinates() {
        // Create a new graph
        Graph graph = new Graph();

        // Add nodes to the graph
        Node nodeA = new Node("A", 1, 1, 0);
        Node nodeB = new Node("B", 2, 2, 0);
        graph.addNode(nodeA);
        graph.addNode(nodeB);

        // Retrieve node by coordinates with a tolerance of 0.5
        Node retrievedNode = graph.getNodeByCoordinates(1.5, 1.5, 0.5);

        // Check if the correct node was retrieved
        assertNotNull(retrievedNode, "Node should be retrieved");
        assertEquals(nodeA, retrievedNode, "Retrieved node should be node A");
    }


}