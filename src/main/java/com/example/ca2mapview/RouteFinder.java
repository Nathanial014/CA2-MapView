package com.example.ca2mapview;

import java.util.List;
import java.util.ArrayList;

public class RouteFinder {
    private Graph graph;

    public RouteFinder(Graph graph) {
        this.graph = graph;
    }

    public List<Node> findRouteWithWaypoints(Node start, Node end, List<Node> waypoints, List<Node> avoid) {
        Dijkstra dijkstra = new Dijkstra(graph);
        List<Node> route = new ArrayList<>();
        Node current = start;

        if (waypoints != null && !waypoints.isEmpty()) {
            for (Node waypoint : waypoints) {
                route.addAll(dijkstra.findShortestPath(current, waypoint, avoid));
                current = waypoint;
            }
        }
        route.addAll(dijkstra.findShortestPath(current, end, avoid));
        return route;
    }

    public List<Node> findMostCulturalRoute(Node start, Node end, List<Node> avoid) {
        Dijkstra dijkstra = new Dijkstra(graph);
        return dijkstra.findMostCulturalPath(start, end, avoid);
    }

    public List<Node> findShortestRoute(Node startNode, Node endNode) {
        return null;
    }

}

