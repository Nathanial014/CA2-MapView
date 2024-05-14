package com.example.ca2mapview;

public class GraphManager {
    private Graph graph;

    public GraphManager(Graph graph) {
        this.graph = graph;
    }

    public void addLandmark(String id, double x, double y, double culturalValue) {
        Node node = new Node(id, x, y, culturalValue);
        graph.addNode(node);
        // Optionally, write this change back to the landmarks CSV
    }

    public void updateRoad(String startId, String endId, double newDistance) {
        Node startNode = graph.getNode(startId);
        Node endNode = graph.getNode(endId);
        if (startNode != null && endNode != null) {
            graph.updateEdge(startNode, endNode, newDistance);
        } else {
            System.out.println("One of the nodes was not found in the graph.");
        }
        // Optionally, persist this change to the roads CSV
    }
}
