package com.example.ca2mapview;

public class GraphManager {
    private Graph graph;

    public GraphManager(Graph graph) {
        this.graph = graph;
    }

    public void addLandmark(int id, double x, double y, double culturalValue) {
        Node node = new Node(id, x, y, culturalValue);
        graph.addNode(node);
        // Optionally, write this change back to the landmarks CSV
    }

    public void updateRoad(int startId, int endId, double newDistance) {
        // Find and update the road in the graph
        Node startNode = graph.getNodeById(startId);
        Node endNode = graph.getNodeById(endId);
        graph.updateEdge(startNode, endNode, newDistance);
        // Optionally, persist this change to the roads CSV
    }
}
