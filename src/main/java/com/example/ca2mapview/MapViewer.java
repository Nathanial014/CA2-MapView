package com.example.ca2mapview;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MapViewer extends Application {

    public static void main(String[] args) {
        launch();
    }

    public static Scene scene;
    private Graph graph = new Graph();
    private DataLoader dataLoader = new DataLoader();

    @Override
    public void init() throws Exception {
        List<Node> nodes = dataLoader.loadLandmarks("landmarks.csv");
        List<Edge> edges = dataLoader.loadRoads("roads.csv", nodes); // Pass nodes, not graph

        nodes.forEach(graph::addNode);
        edges.forEach(edge -> graph.addEdge(edge.getStartNode(), edge.getEndNode(), edge.getDistance()));
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MapViewer.class.getResource("MapView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

        // Set the title and scene on the stage
        stage.setTitle("Map Route Finder");
        stage.setScene(scene);
        stage.show();

        // Pass the graph reference to the controller
        MapViewController controller = fxmlLoader.getController();
        controller.setGraph(graph); // Ensure there is a method in MapViewController to accept the Graph object
    }
    private void loadGraphData() throws Exception {
        // Load nodes and edges into the graph
        List<Node> nodes = dataLoader.loadLandmarks("landmarks.csv");
        nodes.forEach(graph::addNode);  // Add nodes to the graph

        List<Edge> edges = dataLoader.loadRoads("roads.csv", nodes);  // Pass the list of nodes, not the graph
        edges.forEach(edge -> graph.addEdge(edge.getStartNode(), edge.getEndNode(), edge.getDistance()));
    }

}