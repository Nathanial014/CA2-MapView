package com.example.ca2mapview;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    public List<Node> loadLandmarks(String resourcePath) throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new IOException("Cannot find resource: " + resourcePath);
        }
        try (Reader in = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(in)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            List<Node> landmarks = new ArrayList<>();
            for (CSVRecord record : records) {
                String id = record.get("ID"); // Changed to String
                double x = Double.parseDouble(record.get("X"));
                double y = Double.parseDouble(record.get("Y"));
                double culturalValue = Double.parseDouble(record.get("CulturalValue"));
                landmarks.add(new Node(id, x, y, culturalValue));
            }
            return landmarks;
        }
    }

    public List<Edge> loadRoads(String resourcePath, List<Node> nodes) throws Exception {
        try (InputStreamReader in = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(resourcePath), StandardCharsets.UTF_8)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(new BufferedReader(in));
            List<Edge> edges = new ArrayList<>();
            for (CSVRecord record : records) {
                String startId = record.get("StartID"); // Changed to String
                String endId = record.get("EndID"); // Changed to String
                double distance = Double.parseDouble(record.get("Distance"));

                Node startNode = findNodeById(nodes, startId);
                Node endNode = findNodeById(nodes, endId);
                if (startNode != null && endNode != null) {
                    edges.add(new Edge(startNode, endNode, distance));
                } else {
                    // Handle error or log when nodes are not found
                    System.err.println("One of the nodes for the edge was not found: " + startId + ", " + endId);
                }
            }
            return edges;
        }
    }

    private Node findNodeById(List<Node> nodes, String id) {
        return nodes.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElse(null); // Changed to return null instead of throwing an exception for better error handling
    }
}
