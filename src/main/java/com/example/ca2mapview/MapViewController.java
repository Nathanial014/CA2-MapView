package com.example.ca2mapview;

import javafx.scene.image.Image;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapViewController {

    @FXML
    private Canvas canvas;
    @FXML
    private ComboBox<String> routeTypeCombo;
    @FXML
    private TextField startPoint;
    @FXML
    private TextField endPoint;
    @FXML
    private TextField waypointsField;  // TextField for waypoints
    @FXML
    private TextField avoidField;      // TextField for nodes to avoid

    private GraphicsContext gc;
    private RouteFinder routeFinder;
    private Graph graph; // Ensure this is initialized, perhaps passed through a constructor or setter

    @FXML
    private ImageView MapViewer;

    private Scene mainScene; // Field to hold the reference to the main scene

    public void setGraph(Graph graph) {
        this.graph = graph;
        this.routeFinder = new RouteFinder(graph);
    }

    @FXML
    public void initialize() {
        this.graph = new Graph();  // Initialize graph here if it's not shared
        this.routeFinder = new RouteFinder(graph); // Ensure that the graph is initialized before this line
        // Example data to populate the graph
        populateGraph();
        // Draw all edges on the canvas
        drawAllEdgesOnCanvas();
        // Load and display image if mapView is already linked
        if (MapViewer != null) {
            processImageForRoutes();
        }
        gc = canvas.getGraphicsContext2D();
        drawMap();
        canvas.setOnMouseClicked(this::handleCanvasClick);
        routeFinder = new RouteFinder(graph);  // Ensures graph is properly initialized

        // Initialize ComboBox with route type options
        routeTypeCombo.getItems().addAll("BFS", "Cultural", "With Waypoints", "DFS");
        routeTypeCombo.setValue("BFS"); // Default selection
    }

    private void drawAllEdgesOnCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

        List<Edge> edges = graph.getEdges();
        for (Edge edge : edges) {
            Node startNode = edge.getStartNode();
            Node endNode = edge.getEndNode();
            gc.strokeLine(startNode.getX(), startNode.getY(), endNode.getX(), endNode.getY());
        }
    }

    private void populateGraph() {
        // Example method to populate the graph with nodes and edges
        Node nodeA = new Node("A", 50, 50, 0);
        Node nodeB = new Node("B", 150, 150, 0);
        Node nodeC = new Node("C", 250, 100, 0);

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);

        graph.addEdge(nodeA, nodeB, 1);
        graph.addEdge(nodeB, nodeC, 1);
        graph.addEdge(nodeC, nodeA, 1);

        Node startNode = graph.getStartNode();
        Node endNode = graph.getEndNode();

        if (startNode != null && endNode != null) {
            System.out.println("Start Node: " + startNode.getId());
            System.out.println("End Node: " + endNode.getId());
        } else {
            System.out.println("No start or end node found.");
        }
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    private void handleCanvasClick(MouseEvent event) {
        double px = event.getX();
        double py = event.getY();
        double[] mapCoords = MapUtils.pixelToMap(px, py);
        double x = mapCoords[0];
        double y = mapCoords[1];

        if (startPoint.getText().isEmpty()) {
            startPoint.setText(String.format("X: %.2f, Y: %.2f", x, y));
        } else if (endPoint.getText().isEmpty()) {
            endPoint.setText(String.format("X: %.2f, Y: %.2f", x, y));
            // Optionally trigger route drawing here if desired
        }
    }

    private void drawMap() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    private void handleDrawRouteButtonAction(ActionEvent event) {
        String startId = startPoint.getText().trim();
        String endId = endPoint.getText().trim();

        Node startNode = graph.getNode(startId); // Assume getNode() fetches a node based on a unique ID.
        Node endNode = graph.getNode(endId);

        if (startNode != null && endNode != null) {
            drawRouteOnCanvas(startNode, endNode);
        } else {
            // Handle null nodes, possibly showing an alert to the user
            System.out.println("Invalid start or end node ID.");
        }
    }

    private void drawRouteOnCanvas(Node startNode, Node endNode) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);

        // Example uses direct coordinates; adjust as needed if using map scaling.
        gc.strokeLine(startNode.getX(), startNode.getY(), endNode.getX(), endNode.getY());
    }

    @FXML
    private void findRoute() {
        try {
            double[] startCoords = parseCoordinate(startPoint.getText().trim());
            double[] endCoords = parseCoordinate(endPoint.getText().trim());
            Node startNode = graph.getNodeByCoordinates(startCoords[0], startCoords[1]);
            Node endNode = graph.getNodeByCoordinates(endCoords[0], endCoords[1]);

            if (startNode != null && endNode != null) {
                switch (routeTypeCombo.getValue()) {
                    case "DFS":
                        DFS dfs = new DFS(graph, 3); // Assuming a limit of 3 routes
                        List<List<Node>> routes = dfs.findRoutes(startNode, endNode);
                        if (!routes.isEmpty()) {
                            drawRouteOnCanvas(routes.get(0)); // Display the first found route
                        } else {
                            showAlert("No routes found.");
                        }
                        break;
                    case "BFS":
                        BFS bfs = new BFS(graph);
                        List<Node> bfsRoute = bfs.findRoute(startNode, endNode);
                        drawRouteOnCanvas(bfsRoute);
                        break;
                    case "Cultural":
                        List<Node> avoid = parseNodes(avoidField.getText());
                        List<Node> route = routeFinder.findMostCulturalRoute(startNode, endNode, avoid);
                        drawRouteOnCanvas(route);
                        break;
                    case "With Waypoints":
                        List<Node> waypoints = parseNodes(waypointsField.getText());
                        List<Node> avoidList = parseNodes(avoidField.getText());
                        route = routeFinder.findRouteWithWaypoints(startNode, endNode, waypoints, avoidList);
                        drawRouteOnCanvas(route);
                        break;
                    default:
                        showAlert("Please select a valid route type!");
                        break;
                }

                // Calculate and display distance between start and end nodes
                double distance = MapUtils.calculateDistance(startNode.getX(), startNode.getY(), endNode.getX(), endNode.getY());
                showAlert(String.format("Distance between nodes: %.2f units", distance));
            } else {
                    showAlert("Invalid start or end node!");
                    System.out.println("Invalid nodes: " + (startNode == null ? "Start node not found" : "") + (endNode == null ? "End node not found" : ""));
                }

        } catch (NumberFormatException e) {
            showAlert("Invalid coordinate format!");
        }
    }

    private double[] parseCoordinate(String coordinate) {
        String[] parts = coordinate.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Coordinate must be in format 'X: value, Y: value'");
        }

        try {
            double x = Double.parseDouble(parts[0].trim().substring(2).trim());
            double y = Double.parseDouble(parts[1].trim().substring(2).trim());
            return new double[]{x, y};
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in coordinates");
        }
    }


    private void processImageForRoutes() {
        Image image = MapViewer.getImage();
        if (image == null) return;

        PixelReader pixelReader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // Example: scan each pixel to determine if it's part of a path
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                if (color.equals(Color.WHITE)) { // Assuming white is the path color
                    // This pixel is part of a path
                }
            }
        }
    }

    private List<Node> parseNodes(String input) {
        if (input == null || input.isEmpty()) return Arrays.asList();
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(graph::getNode)
                .collect(Collectors.toList());
    }

    private void drawRouteOnCanvas(List<Node> route) {
        clearCanvas();
        if (route.isEmpty()) {
            showAlert("No route available to display.");
            return;
        }
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        Node prevNode = route.get(0);
        for (int i = 1; i < route.size(); i++) {
            Node currentNode = route.get(i);
            gc.strokeLine(prevNode.getX(), prevNode.getY(), currentNode.getX(), currentNode.getY());

            // Using createLine method to draw the route
            Line line = MapUtils.createLine(prevNode.getX(), prevNode.getY(), currentNode.getX(), currentNode.getY());
            gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

            prevNode = currentNode;
        }
    }

    private void clearCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    private void openImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Load and display image on the canvas
            Image image = new Image(selectedFile.toURI().toString());
            gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Route Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void closeApplication() {
        System.exit(0);
    }
}
