package com.example.ca2mapview;

import java.util.*;

public class Dijkstra {
    private Graph graph;

    public Dijkstra(Graph graph) {
        this.graph = graph;
    }

    public List<Node> findShortestPath(Node start, Node end) {
        Map<Node, Double> dist = new HashMap<>();
        Map<Node, Node> prev = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparing(dist::get));

        for (Node node : graph.getNodes()) {
            dist.put(node, Double.MAX_VALUE);
            prev.put(node, null);
            pq.add(node);
        }
        dist.put(start, 0.0);

        while (!pq.isEmpty()) {
            Node u = pq.poll();
            if (u.equals(end)) {
                return reconstructPath(end, prev);
            }

            for (Edge edge : graph.getEdgesFromNode(u)) {
                Node v = edge.getEnd();
                double weight = edge.getDistance();
                double distanceThroughU = dist.get(u) + weight;
                if (distanceThroughU < dist.get(v)) {
                    dist.put(v, distanceThroughU);
                    prev.put(v, u);
                    pq.add(v);
                }
            }
        }
        return Collections.emptyList(); // return an empty list if no path found
    }

    private List<Node> reconstructPath(Node end, Map<Node, Node> prev) {
        List<Node> path = new ArrayList<>();
        for (Node at = end; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}

