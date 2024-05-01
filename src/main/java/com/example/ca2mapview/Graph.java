package com.example.ca2mapview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<String, Node> nodes = new HashMap<>();
    private List<Edge> edges = new ArrayList<>();

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    public void addEdge(Node start, Node end, double distance) {
        Edge edge = new Edge(start, end, distance);
        edges.add(edge);
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
}

