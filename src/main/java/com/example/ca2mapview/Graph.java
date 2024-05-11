package com.example.ca2mapview;

import java.util.*;

public class Graph {
    private Map<String, Node> nodes = new HashMap<>();
    private List<Edge> edges = new ArrayList<>();
    private Map<Node, List<Edge>> adjacencyList = new HashMap<>();

    public Graph() {
        // Initialize graph components if necessary
    }

    public void addNode(Node node) {
        if (!nodes.containsKey(node.getId())) {
            nodes.put(node.getId(), node);
            adjacencyList.put(node, new ArrayList<>()); // Ensure there is a list to hold edges
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
        return nodes.get(id);
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
}
