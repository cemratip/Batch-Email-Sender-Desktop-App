package com.example.batchemailsender;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateNewEmailTemplateWindow {

    @FXML
    private Label fileName;

    private String filePath;

    public void create() throws IOException {
        if (EmailTemplateSelectorController.allEmailTemplateSelectors.size() < 8) {
            if (filePath != null) {
                try {
                    EmailTemplateController.closeNewEmailTemplateWindow();
                    FileWriter file = new FileWriter(MainController.databasePath + fileName.getText());
                    file.write(filePath);
                    file.close();
                } catch (IOException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Please upload an email template.");
                    a.show();
                }
                MainController.replaceEmailTemplates();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Please upload an email template.");
                a.show();
            }
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR, "You can only create up to 8 email templates. Please delete one to continue.");
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
    }
}
