package com.example.ca2mapview;

import java.util.List;

public class Route {
    private List<Node> nodes;
    private double totalDistance;

    public Route(List<Node> nodes, double totalDistance) {
        this.nodes = nodes;
        this.totalDistance = totalDistance;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public double getTotalDistance() {
        return totalDistance;
    }
}

