package com.example.batchemailsender;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class EmailListController {
    private static Stage createNewEmailListStage;

    public static final ArrayList<String> emailListFiles = new ArrayList<>();

    public static BorderPane display() throws IOException {
        EmailListSelectorController.allEmailListSelectors.clear();
        BorderPane emailList = FXMLLoader.load(Objects.requireNonNull(EmailListController.class.getResource("EmailLists.fxml")));
        VBox list = (VBox) emailList.getChildren().get(1);

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
            EmailListSelectorController.allEmailListSelectors.add(selector);
        }

        Label createBtn = FXMLLoader.load(Objects.requireNonNull(EmailListController.class.getResource("CreateNewEmailListButton.fxml")));
        list.getChildren().add(createBtn);

        return emailList;
    }



    public void openNewEmailListWindow() throws IOException {
        createNewEmailListStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(EmailListController.class.getResource("CreateNewEmailListWindow.fxml")));
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
