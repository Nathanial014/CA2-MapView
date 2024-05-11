package com.example.ca2mapview;

import java.util.*;

public class Graph {
    private Map<String, Node> nodes = new HashMap<>();
    private List<Edge> edges = new ArrayList<>();

    public void addNode(Node node) {
        nodes.put(String.valueOf(node.getId()), node);
        adjacencyList.put(node, new ArrayList<>());  // Initialize adjacency list for the new node
    }

    public void addEdge(Node start, Node end, double distance) {
        Edge edge = new Edge(start, end, distance);
        edges.add(edge);
        adjacencyList.get(start).add(edge);  // Add the edge to the start node's list
    }

    public void updateEdge(Node start, Node end, double newDistance) {
        List<Edge> edges = adjacencyList.get(start.getId());
        if (edges != null) {
            for (Edge edge : edges) {
                if (edge.getEnd().equals(end)) {
                    edge.setDistance(newDistance);
                }
            }
        }
    }
    public Node getNodeById(int id) {
        return nodes.get(id);
    }

    public Node getNode(String id) {
        return nodes.get(id);
    }

    public List<Node> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<Edge> getEdges() {
        return edges;
    }
    // Method to get all edges from a given node
    public List<Edge> getEdgesFromNode(Node current) {
        return adjacencyList.getOrDefault(current, new ArrayList<>());
    }

    private Map<Node, List<Edge>> adjacencyList = new HashMap<>();

}



