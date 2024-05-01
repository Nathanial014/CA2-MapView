package com.example.ca2mapview;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
        FXMLLoader fxmlLoader = new FXMLLoader(MapViewer.class.getResource("MapView.fxml"));
        scene = new Scene(fxmlLoader.load(), 1000, 700);

        stage.setTitle("Map Viewer");
        stage.setScene(scene);
        stage.show();

        // Pass the main scene reference to the controller
        MapViewController MapViewController = fxmlLoader.getController();
        MapViewController.setMainScene(stage.getScene());
    }
}