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

public class EmailTemplateController {

    @FXML
    private ImageView duplicateIcon;
    @FXML
    private ImageView editIcon;
    @FXML
    private ImageView deleteIcon;

    private static Stage createNewEmailTemplateStage;

    public static BorderPane display() throws IOException {
        BorderPane emailTemplateList = null;
        VBox list = null;
        emailTemplateList = FXMLLoader.load(Objects.requireNonNull(EmailListController.class.getResource("EmailTemplates.fxml")));
        list = (VBox) emailTemplateList.getChildren().get(1);

        ArrayList<String> emailTemplateFiles = new ArrayList<>();
        File[] files = new File("src/main/resources/local database").listFiles();
        for (File file : Objects.requireNonNull(files)) {
            if (file.isFile()) {
                String name = file.getName();
                if (name.contains(".html")) {
                    emailTemplateFiles.add(name.replace(".html", ""));
                }
            }
        }

        for (String emailTemplateFile : emailTemplateFiles) {
            VBox selector = FXMLLoader.load(Objects.requireNonNull(EmailTemplateController.class.getResource("EmailTemplateSelector.fxml")));
            HBox hbox = (HBox) selector.getChildren().get(0);
            Label name = (Label) hbox.getChildren().get(0);
            name.setText(emailTemplateFile);
            list.getChildren().add(selector);
        }

        Label createBtn = FXMLLoader.load(Objects.requireNonNull(EmailTemplateController.class.getResource("CreateNewEmailTemplateButton.fxml")));
        list.getChildren().add(createBtn);

        return emailTemplateList;
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

    public void openNewEmailTemplateWindow() throws IOException {
        // open new window
        createNewEmailTemplateStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CreateNewEmailTemplateWindow.fxml")));
        Scene scene = new Scene(root, 350, 400);
        createNewEmailTemplateStage.setTitle("Create a new email list");
        createNewEmailTemplateStage.setScene(scene);
        createNewEmailTemplateStage.setResizable(false);
        createNewEmailTemplateStage.show();
    }

    public static void closeNewEmailTemplateWindow() {
        createNewEmailTemplateStage.close();
    }

}
