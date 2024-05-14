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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

    private Scene mainScene; // Field to hold the reference to the main scene

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        drawMap();
        canvas.setOnMouseClicked(this::handleCanvasClick);
        routeFinder = new RouteFinder(graph);  // Ensure graph is properly initialized

        // Initialize ComboBox with route type options
        routeTypeCombo.getItems().addAll("BFS", "Cultural", "With Waypoints", "DFS");
        routeTypeCombo.setValue("BFS"); // Default selection
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    private void handleCanvasClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
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
    private void findRoute() {
        String startId = startPoint.getText().trim();
        String endId = endPoint.getText().trim();
        Node startNode = graph.getNode(startId);
        Node endNode = graph.getNode(endId);

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
        } else {
            showAlert("Invalid start or end node!");
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

    @FXML
    public void returnToMainGUI(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGUIApp.fxml"));
        Parent secondSceneParent = loader.load();
        Scene secondScene = new Scene(secondSceneParent);

        // Get the stage information
        Stage window = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

        // Set the new scene
        window.setScene(secondScene);
        window.show();
    }
}