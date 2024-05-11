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
        }
        dist.put(start, 0.0);
        pq.add(start);  // adding only the starting node with a distance of 0

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
                    pq.remove(v); // Remove the node first to ensure proper ordering
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
//    public List<Node> findMostCulturalPath(Node start, Node end) {
//        return dijkstra(start, end, true);
//    }
    public List<Node> findMostCulturalPath(Node start, Node end) {
        // This map holds the highest cultural value attainable at each node.
        Map<Node, Double> maxCulturalValue = new HashMap<>();
        // This map keeps track of the previous node in the optimal path.
        Map<Node, Node> prev = new HashMap<>();
        // Priority queue to explore the nodes with the highest cultural values first.
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(node -> -maxCulturalValue.get(node)));

        // Initialize distances and previous nodes
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

            graph.getEdgesFromNode(current).forEach(edge -> {
                Node neighbor = edge.getEnd();
                double newCulturalValue = maxCulturalValue.get(current) + neighbor.getCulturalValue();
                if (newCulturalValue > maxCulturalValue.get(neighbor)) {
                    maxCulturalValue.put(neighbor, newCulturalValue);
                    prev.put(neighbor, current);
                    // Update the priority queue
                    pq.remove(neighbor); // Remove the neighbor to update its priority
                    pq.add(neighbor); // Re-add the neighbor with the new higher cultural value
                }
            });
        }

        return Collections.emptyList(); // Return empty list if no path is found
    }
}

