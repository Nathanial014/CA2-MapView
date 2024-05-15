package com.example.ca2mapview;

public class Edge {
    private Node start;
    private Node end;
    private double distance;

    public Edge(Node start, Node end, double distance) {
        this.start = start;
        this.end = end;
        this.distance = distance;
        this.distance = MapUtils.calculateDistance(start.getX(), start.getY(), end.getX(), end.getY());
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double newDistance) { this.distance = distance; }

    public Node getStartNode() {
        return start;
    }

    public Node getEndNode() {
        return end;
    }
}
