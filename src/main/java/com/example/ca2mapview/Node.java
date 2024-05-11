package com.example.ca2mapview;

import java.util.List;

public class Node {
    private int id; // Assuming ID is an integer, change this type if it's supposed to be a String
    private double x;
    private double y;
    private double culturalValue;
    private List<Edge> edges;

    public Node(int id, double x, double y, double culturalValue) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.culturalValue = culturalValue;
    }

    public int getId() {
        return Integer.parseInt(String.valueOf(id));
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
