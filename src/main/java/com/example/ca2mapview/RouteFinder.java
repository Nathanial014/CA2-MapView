package com.example.ca2mapview;

public class RouteFinder {
    private Graph graph;

    public RouteFinder(Graph graph) {
        this.graph = graph;
    }

    public List<Node> findShortestRoute(Node start, Node end) {
        Dijkstra dijkstra = new Dijkstra(graph);
        return dijkstra.findShortestPath(start, end);
    }

    public List<Node> findBFSRoute(Node start, Node end) {
        BFS bfs = new BFS(graph);
        return bfs.findRoute(start, end);
    }

    public List<List<Node>> findDFSPermutations(Node start, Node end, int maxRoutes) {
        DFS dfs = new DFS(graph, maxRoutes);
        return dfs.findRoutes(start, end);
    }
}

