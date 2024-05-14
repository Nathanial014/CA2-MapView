package com.example.ca2mapview;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MapViewer extends Application {

    public static void main(String[] args) {
        launch();
    }

    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ca2mapview/MapView.fxml")); // Ensure the path starts with a slash if it's in the resources folder.
            Parent root = fxmlLoader.load();

            MapViewController controller = fxmlLoader.getController();
            Graph graph = new Graph();
            controller.setGraph(graph); // Ensure graph is set before any use in initialize method.

            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("Map Route Finder");
            stage.setScene(scene);
            stage.show();

            controller.setMainScene(scene); // Ensure this is done after the stage is shown if needed.
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Problem starting the application", e);
        }
    }
}
