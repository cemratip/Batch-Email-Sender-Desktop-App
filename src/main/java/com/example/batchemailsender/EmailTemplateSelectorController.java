package com.example.batchemailsender;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class EmailTemplateSelectorController extends MainController {

    @FXML
    private ImageView deleteIcon;

    @FXML
    private VBox root;

    @FXML
    private Label name;

    private boolean selected = false;

    public static String selectedEmailTemplatePath = "";

    public void select() {
        if (!selected) {
            try {
                String fileName = name.getText()+".html";
                Path filePath = Path.of("src/main/resources/local database/"+fileName);
                selectedEmailTemplatePath = Files.readString(filePath);
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR, "File not found.");
                a.show();
            }
            root.setStyle("-fx-background-color:#64c4e4");
            selected = true;
        }
        else {
            root.setStyle("-fx-background-color:#f8f4f4");
            selected = false;
            selectedEmailTemplatePath = "";
        }
    }

    public void showIcons() {
        deleteIcon.setVisible(true);
    }

    public void hideIcons() {
        deleteIcon.setVisible(false);
    }

    public void delete() {
        root.getChildren().clear();
        selected = false;
        selectedEmailTemplatePath = "";
        String fileName = name.getText()+".html";
        Path filePath = Path.of("src/main/resources/local database/"+fileName);
        File f = new File(String.valueOf(filePath));
        f.delete();
    }

}
