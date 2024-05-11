package com.example.ca2mapview;

import java.util.*;

public class Dijkstra {
    private Graph graph;

    public Dijkstra(Graph graph) {
        this.graph = graph;
    }

    public List<Node> findShortestPath(Node start, Node end, List<Node> avoid) {
        Map<Node, Double> dist = new HashMap<>();
        Map<Node, Node> prev = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparing(dist::get));

        graph.getNodes().forEach(node -> {
            dist.put(node, Double.MAX_VALUE);
            prev.put(node, null);
        });
        dist.put(start, 0.0);
        pq.add(start);

        while (!pq.isEmpty()) {
            Node u = pq.poll();
            if (u.equals(end)) {
                return reconstructPath(end, prev);
            }

            for (Edge edge : graph.getEdgesFromNode(u)) {
                if (avoid != null && avoid.contains(edge.getEnd())) {
                    continue;  // Skip if the node is in the avoid list
                }
                Node v = edge.getEnd();
                double weight = edge.getDistance();
                double distanceThroughU = dist.get(u) + weight;
                if (distanceThroughU < dist.get(v)) {
                    dist.put(v, distanceThroughU);
                    prev.put(v, u);
                    pq.remove(v); // This might be inefficient without a decrease-key operation
                    pq.add(v);
                }
            }
        }
        return Collections.emptyList();
    }

    public List<Node> findMostCulturalPath(Node start, Node end, List<Node> avoid) {
        Map<Node, Double> maxCulturalValue = new HashMap<>();
        Map<Node, Node> prev = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(node -> -maxCulturalValue.get(node)));

        graph.getNodes().forEach(node -> {
            maxCulturalValue.put(node, Double.NEGATIVE_INFINITY);
            prev.put(node, null);
        });
        maxCulturalValue.put(start, start.getCulturalValue());
        pq.add(start);

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (current.equals(end)) {
                return reconstructPath(end, prev);
            }

            for (Edge edge : graph.getEdgesFromNode(current)) {
                if (avoid != null && avoid.contains(edge.getEnd())) {
                    continue;  // Skip nodes in the avoid list
                }
                Node neighbor = edge.getEnd();
                double newCulturalValue = maxCulturalValue.get(current) + neighbor.getCulturalValue();
                if (newCulturalValue > maxCulturalValue.get(neighbor)) {
                    maxCulturalValue.put(neighbor, newCulturalValue);
                    prev.put(neighbor, current);
                    pq.remove(neighbor); // Update priority
                    pq.add(neighbor);
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Node> reconstructPath(Node end, Map<Node, Node> prev) {
        LinkedList<Node> path = new LinkedList<>();
        Node at = end;
        while (at != null) {
            path.addFirst(at);
            at = prev.get(at);
        }
        return path;
    }
}
