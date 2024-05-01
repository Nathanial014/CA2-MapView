package com.example.ca2mapview;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DFS {
    private Graph graph;
    private int maxRoutes;

    public DFS(Graph graph, int maxRoutes) {
        this.graph = graph;
        this.maxRoutes = maxRoutes;
    }

    public List<List<Node>> findRoutes(Node start, Node end) {
        List<List<Node>> routes = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(start);
        explore(stack, end, routes, new ArrayList<>());
        return routes;
    }

    private void explore(Stack<Node> stack, Node end, List<List<Node>> routes, List<Node> visited) {
        Node current = stack.peek();
        visited.add(current);

        if (current.equals(end)) {
            routes.add(new ArrayList<>(stack));
            if (routes.size() >= maxRoutes) return;
        } else {
            for (Edge edge : graph.getEdgesFromNode(current)) {
                Node nextNode = edge.getEnd();
                if (!visited.contains(nextNode)) {
                    stack.push(nextNode);
                    explore(stack, end, routes, visited);
                    stack.pop();
                }
            }
        }
        visited.remove(current);
    }
}

