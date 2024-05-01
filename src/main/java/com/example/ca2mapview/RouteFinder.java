package com.example.ca2mapview;

import java.util.List;
import java.util.ArrayList;

public class RouteFinder {
    private Graph graph;

    public RouteFinder(Graph graph) {
        this.graph = graph;
    }

    // Find the shortest route by distance using Dijkstra's algorithm
    public List<Node> findShortestRouteByDistance(Node start, Node end) {
        Dijkstra dijkstra = new Dijkstra(graph);
        return dijkstra.findShortestPath(start, end);
    }

    // Find the shortest route in terms of hops using BFS
    public List<Node> findShortestRouteByHops(Node start, Node end) {
        BFS bfs = new BFS(graph);
        return bfs.findRoute(start, end);
    }

    // Find multiple route permutations using DFS
    public List<List<Node>> findRoutePermutations(Node start, Node end, int maxRoutes) {
        DFS dfs = new DFS(graph, maxRoutes);
        return dfs.findRoutes(start, end);
    }

    // Find the most cultural or historic route using a modified Dijkstra's algorithm
    public List<Node> findMostCulturalRoute(Node start, Node end) {
        Dijkstra dijkstra = new Dijkstra(graph);
        return dijkstra.findMostCulturalPath(start, end);
    }
}
