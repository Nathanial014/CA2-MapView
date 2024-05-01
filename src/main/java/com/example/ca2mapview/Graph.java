package com.example.ca2mapview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<String, Node> nodes = new HashMap<>();
    private List<Edge> edges = new ArrayList<>();

    //public void addNode(Node node) {
   //     nodes.put(node.getId(), node);
    //}

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
        adjacencyList.put(node, new ArrayList<>());  // Initialize adjacency list for the new node
    }

    public void addEdge(Node start, Node end, double distance) {
        Edge edge = new Edge(start, end, distance);
        edges.add(edge);
        adjacencyList.get(start).add(edge);  // Add the edge to the start node's list
    }



    // void addEdge(Node start, Node end, double distance) {
    //    Edge edge = new Edge(start, end, distance);
    //    edges.add(edge);
    //}

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


    public void addEdge(Edge edge) {
    }
}



