package com.example.batchemailsender;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

    private boolean selected = false;

    public static ArrayList<String> selectedEmailAccountDetails = new ArrayList<>();

    public void select() {
        if (!selected) {
            root.setStyle("-fx-background-color:#64c4e4");
            selected = true;
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
        else {
            root.setStyle("-fx-background-color:#f8f4f4");
            selected = false;
            selectedEmailAccountDetails.clear();
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
        selectedEmailAccountDetails.clear();
        String fileName = name.getText();
        fileName = fileName.replace("@gmail.com", "");
        File file = new File("src/main/resources/local database/" + fileName + ".txt");
        file.delete();
    }

}
