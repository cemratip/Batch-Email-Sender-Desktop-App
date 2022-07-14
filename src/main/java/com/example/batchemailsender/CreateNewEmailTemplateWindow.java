package com.example.batchemailsender;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.web.WebView;

public class CreateNewEmailTemplateWindow {

    @FXML
    private Label fileName;
    @FXML
    private WebView displayer;

    private String filePath;

    public void create() throws IOException {
        if (filePath != null) {
            try {
                EmailTemplateController.closeNewEmailTemplateWindow();
                FileWriter file = new FileWriter("src/main/resources/local database/" + fileName.getText());
                file.write(filePath);
                file.close();
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Please upload an email template.");
                a.show();
            }
            MainController.replaceEmailTemplates();
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please upload an email template.");
            a.show();
        }
    }

    public void upload() {
        Stage fileOpenStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open HTML Email Template");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HTML Files", "*.html"));
        File selectedFile = fileChooser.showOpenDialog(fileOpenStage);
        fileName.setText(selectedFile.getName());
        filePath = selectedFile.getPath();
        WebEngine webEngine = displayer.getEngine();
        displayer.setVisible(true);
        displayer.setZoom(0.5);
        webEngine.load("file://"+filePath);
    }
}
