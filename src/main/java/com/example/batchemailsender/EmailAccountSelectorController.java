package com.example.batchemailsender;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmailAccountSelectorController extends MainController {

    @FXML
    private ImageView deleteIcon;
    @FXML
    private VBox root;
    @FXML
    private Label name;
    public static ArrayList<String> selectedEmailAccountDetails = new ArrayList<>();
    public static ArrayList<VBox> allEmailAccountSelectors = new ArrayList<>();

    public void select() {
        // if the current selector's tag is unselected
        HBox hbox = (HBox) root.getChildren().get(0);
        if (hbox.getId().equals("unselected")) {
            // make all selectors in the list grey etc
            for (VBox selector : allEmailAccountSelectors) {
                selector.setStyle("-fx-background-color:#f8f4f4");
                // set tag to unselected
                HBox allHBox = (HBox) selector.getChildren().get(0);
                allHBox.setId("unselected");
                selectedEmailAccountDetails.clear();
            }
            // make the current selector blue etc
            root.setStyle("-fx-background-color:#64c4e4");
            // set tag to selected
            hbox.setId("selected");
            String fileName = name.getText();
            fileName = fileName.replace("@gmail.com", "");
            try {
                File myObj = new File("src/main/resources/local database/"+fileName+".txt");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    selectedEmailAccountDetails.add(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // else if the current selector's tag is selected
        else if (hbox.getId().equals("selected")) {
            // make the current selector grey etc
            root.setStyle("-fx-background-color:#f8f4f4");
            // set tag to unselected
            hbox.setId("unselected");
            selectedEmailAccountDetails.clear();
        }
    }

    public void showIcons() {deleteIcon.setVisible(true);}

    public void hideIcons() {deleteIcon.setVisible(false);}

    public void delete() {
        root.getChildren().clear();
        selectedEmailAccountDetails.clear();
        String fileName = name.getText();
        fileName = fileName.replace("@gmail.com", "");
        File file = new File("src/main/resources/local database/" + fileName + ".txt");
        file.delete();
        allEmailAccountSelectors.remove(root);
    }
}
