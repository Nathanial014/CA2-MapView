package com.example.ca2mapview;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String id;
    private double x, y;
    private double culturalValue;
    private List<Edge> edges;

    public Node(String id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.edges = new ArrayList<>();
    }

    public Node(String id, double x, double y, double culturalValue) {
        this(id, x, y);
        this.culturalValue = culturalValue;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getCulturalValue() {
        return culturalValue;
    }
    public void setCulturalValue(double culturalValue) {
        this.culturalValue = culturalValue;
    }
    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        if (!edges.contains(edge)) {
            edges.add(edge);
        }
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }
}
