package com.example.batchemailsender;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class EmailListController {

    @FXML
    private ImageView duplicateIcon;
    @FXML
    private ImageView editIcon;
    @FXML
    private ImageView deleteIcon;

    private static Stage createNewEmailListStage;

    public static final ArrayList<String> emailListFiles = new ArrayList<>();

    public static BorderPane display() throws IOException {
        BorderPane emailList = null;
        VBox list = null;
        emailList = FXMLLoader.load(Objects.requireNonNull(EmailListController.class.getResource("EmailLists.fxml")));
        list = (VBox) emailList.getChildren().get(1);

        emailListFiles.clear();
        File[] files = new File("src/main/resources/local database").listFiles();
        for (File file : Objects.requireNonNull(files)) {
            if (file.isFile()) {
                String name = file.getName();
                if (name.contains(".json")) {
                    emailListFiles.add(name.replace(".json", ""));
                }
            }
        }

        for (String emailListFile : emailListFiles) {
            VBox selector = FXMLLoader.load(Objects.requireNonNull(EmailListController.class.getResource("EmailListSelector.fxml")));
            HBox hbox = (HBox) selector.getChildren().get(0);
            Label name = (Label) hbox.getChildren().get(0);
            name.setText(emailListFile);
            list.getChildren().add(selector);
        }

        Label createBtn = FXMLLoader.load(Objects.requireNonNull(EmailListController.class.getResource("CreateNewEmailListButton.fxml")));
        list.getChildren().add(createBtn);

        return emailList;
    }

    public void showIcons() {
        duplicateIcon.setVisible(true);
        editIcon.setVisible(true);
        deleteIcon.setVisible(true);
    }

    public void hideIcons() {
        duplicateIcon.setVisible(false);
        editIcon.setVisible(false);
        deleteIcon.setVisible(false);
    }

    public void openNewEmailListWindow() throws IOException {
        // open new window
        createNewEmailListStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CreateNewEmailListWindow.fxml")));
        Scene scene = new Scene(root, 350, 400);
        createNewEmailListStage.setTitle("Create a new email list");
        createNewEmailListStage.setScene(scene);
        createNewEmailListStage.setResizable(false);
        createNewEmailListStage.show();
    }

    public static void closeNewEmailListWindow() {
        createNewEmailListStage.close();
    }
}
