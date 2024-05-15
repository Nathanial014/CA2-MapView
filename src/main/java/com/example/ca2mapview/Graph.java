package com.example.ca2mapview;

import java.util.*;

public class Graph {
    private Map<String, Node> nodes;
    private List<Edge> edges = new ArrayList<>();
    private Map<Node, List<Edge>> adjacencyList = new HashMap<>();

    private static final double TOLERANCE = 0.01;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    public void addNode(Node node) {
        if (!nodes.containsKey(node.getId())) {
            nodes.put(node.getId(), node);
            adjacencyList.put(node, new ArrayList<>()); // Ensures there is a list to hold edges
        }
    }

    public void addEdge(Node start, Node end, double distance) {
        if (!adjacencyList.containsKey(start)) {
            adjacencyList.put(start, new ArrayList<>());
        }
        if (!nodes.containsKey(start.getId()) || !nodes.containsKey(end.getId())) {
            throw new IllegalArgumentException("Both nodes must be in the graph.");
        }
        Edge edge = new Edge(start, end, distance);
        edges.add(edge);
        adjacencyList.get(start).add(edge);
    }

    public Node getNode(String id) {
        Node node = nodes.get(id);
        System.out.println("Retrieving node for id: " + id + " -> " + (node != null ? "Found" : "Not found"));
        return node;
    }

    public Node getNodeByCoordinates(double x, double y) {
        for (Node node : nodes.values()) {
            if (Math.abs(node.getX() - x) <= TOLERANCE && Math.abs(node.getY() - y) <= TOLERANCE) {
                return node;
            }
        }
        return null;
    }

    public Node getStartNode() {
        // Example logic to fetch the start node
        if (edges.isEmpty()) {
            return null;
        }
        return edges.get(0).getStartNode();
    }

    public Node getEndNode() {
        // Example logic to fetch the end node
        if (edges.isEmpty()) {
            return null;
        }
        return edges.get(0).getEndNode();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Edge> getEdgesFromNode(Node node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    public Collection<Node> getNodes() {
        return nodes.values();
    }

    public Node getNodeById(int id) {
        return nodes.get(id);
    }

    public void updateEdge(Node start, Node end, double newDistance) {
        List<Edge> edges = adjacencyList.get(start);
        if (edges != null) {
            for (Edge edge : edges) {
                if (edge.getEnd().equals(end)) {
                    edge.setDistance(newDistance);
                    return; // Break after updating to avoid unnecessary iterations
                }
            }
        }
    }

}