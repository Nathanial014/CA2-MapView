package com.example.ca2mapview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MapViewController {

    @FXML
    private ImageView imageView;

    private Scene mainScene;

    @FXML
    private Label filenameLabel;

    @FXML
    private Label imageSizeLabel;

    @FXML
    private Slider resizeSlider;

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
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
            imageView.setImage(image);

            filenameLabel.setText("Filename: " + selectedFile.getName());
            imageSizeLabel.setText("Image Size: " + (int) image.getWidth() + " x " + (int) image.getHeight());

            resizeSlider.setValue(1.0);
        }
    }
    @FXML
    private void displayDetails() {
        // Get the currently displayed image
        Image displayedImage = imageView.getImage();
        if (displayedImage != null) {
            filenameLabel.setText("Filename: " + "Untitled");
            imageSizeLabel.setText("Image Size: " + (int) displayedImage.getWidth() + " x " + (int) displayedImage.getHeight());
        } else {
            showAlert("No Image", "No image is currently displayed.");
        }
    }

    @FXML
    private void resizeImage() {
        // Resize the image based on the slider value
        double scaleFactor = resizeSlider.getValue();
        imageView.setScaleX(scaleFactor);
        imageView.setScaleY(scaleFactor);
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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





