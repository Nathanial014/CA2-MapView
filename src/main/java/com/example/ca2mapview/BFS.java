package com.example.ca2mapview;

import java.util.*;

public class BFS {
    private Graph graph;

    public BFS(Graph graph) {
        this.graph = graph;
    }

    public List<Node> findRoute(Node start, Node end) {
        Map<Node, Node> parentMap = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(start);
        parentMap.put(start, null);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.equals(end)) {
                return constructPath(end, parentMap);
            }

            for (Edge edge : graph.getEdgesFromNode(current)) {
                Node nextNode = edge.getEnd();
                if (!parentMap.containsKey(nextNode)) {
                    queue.add(nextNode);
                    parentMap.put(nextNode, current);
                }
            }
        }
        return Collections.emptyList(); // return an empty path if no path found
    }

    private List<Node> constructPath(Node end, Map<Node, Node> parentMap) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = end;
        while (current != null) {
            path.addFirst(current);
            current = parentMap.get(current);
        }
        return path;
    }
}
