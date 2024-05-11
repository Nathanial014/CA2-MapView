package com.example.ca2mapview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MapViewController {

    @FXML
    private Canvas canvas;

    @FXML
    private ComboBox<String> routeTypeCombo;

    @FXML
    private TextField startPoint;
    @FXML
    private TextField endPoint;
    private GraphicsContext gc;
    private double startX, startY, endX, endY;

    private Scene mainScene;

    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        drawMap();
        canvas.setOnMouseClicked(this::handleCanvasClick);
    }

    private void clearCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
    }
    private void handleCanvasClick(MouseEvent event) {
        if (startPoint.getText().isEmpty()) {
            startX = event.getX();
            startY = event.getY();
            startPoint.setText(String.format("X: %.2f, Y: %.2f", startX, startY));
        } else if (endPoint.getText().isEmpty()) {
            endX = event.getX();
            endY = event.getY();
            endPoint.setText(String.format("X: %.2f, Y: %.2f", endX, endY));
            drawRoute(startX, startY, endX, endY);
        }
    }

    private void drawMap() {
        // Here you would load and draw your base map
        // and set a background color
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    @FXML
    public void openImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load image: " + e.getMessage());
                alert.show();
            }
        }
    }

    // You can add methods here to draw routes on the canvas
    public void drawRoute(double startX, double startY, double endX, double endY) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        gc.strokeLine(startX, startY, endX, endY);
    }
    @FXML
    private void findRoute() {
        //Placeholder for route finding logic
        String routeType = routeTypeCombo.getValue();
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setContentText("FINDING ROUTE OF TYPE: " + routeType);
        alert.show();
    }
    @FXML
    private void closeApplication(){
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