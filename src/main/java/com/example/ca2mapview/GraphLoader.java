package com.example.ca2mapview;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphLoader {
    private Graph graph;

    public GraphLoader(Graph graph) {
        this.graph = graph;
    }

    public void loadFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Assuming format: id, x, y, culturalValue
                String id = parts[0];
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double culturalValue = Double.parseDouble(parts[3]);

                Node node = new Node(id, x, y, culturalValue);
                graph.addNode(node);
            }

            // Assuming edges are defined in the same file or a separate loop
            // Reset reader or open a new file as needed
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Assuming format: startId, endId, distance
                Node startNode = graph.getNode(parts[0]);
                Node endNode = graph.getNode(parts[1]);
                double distance = Double.parseDouble(parts[2]);

                graph.addEdge(startNode, endNode, distance);
            }
        }
    }
