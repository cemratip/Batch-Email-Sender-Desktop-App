package com.example.batchemailsender;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class EmailTemplateSelectorController extends MainController {

    @FXML
    private ImageView deleteIcon;
    @FXML
    private VBox root;
    @FXML
    private Label name;
    public static String selectedEmailTemplatePath = "";
    public static ArrayList<VBox> allEmailTemplateSelectors = new ArrayList<>();

    public void select() {
        // if the current selector's tag is unselected
        HBox hbox = (HBox) root.getChildren().get(0);
        if (hbox.getId().equals("unselected")) {
            // make all selectors in the list grey etc
            for (VBox selector : allEmailTemplateSelectors) {
                selector.setStyle("-fx-background-color:#f8f4f4");
                // set tag to unselected
                HBox allHBox = (HBox) selector.getChildren().get(0);
                allHBox.setId("unselected");
                selectedEmailTemplatePath = "";
            }
            try {
                String fileName = name.getText()+".html";
                Path filePath = Path.of(MainController.databasePath+fileName);
                selectedEmailTemplatePath = Files.readString(filePath);
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR, "File not found.");
                a.show();
            }
            // make the current selector blue etc
            root.setStyle("-fx-background-color:#64c4e4");
            // set tag to selected
            hbox.setId("selected");
        }
        // else if the current selector's tag is selected
        else if (hbox.getId().equals("selected")) {
            // make the current selector grey etc
            root.setStyle("-fx-background-color:#f8f4f4");
            // set tag to unselected
            hbox.setId("unselected");
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
        selectedEmailTemplatePath = "";
        String fileName = name.getText()+".html";
        Path filePath = Path.of(MainController.databasePath+fileName);
        File f = new File(String.valueOf(filePath));
        f.delete();
        allEmailTemplateSelectors.remove(root);
    }

}
