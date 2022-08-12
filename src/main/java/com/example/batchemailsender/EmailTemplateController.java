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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class EmailTemplateController {
    private static Stage createNewEmailTemplateStage;

    public static final String emailListPath = "/Users/Cem/IdeaProjects/batchemailsender/out/artifacts/BatchEmailSender_jar/local database/";

    public static BorderPane display() throws IOException {
        EmailTemplateSelectorController.allEmailTemplateSelectors.clear();
        BorderPane emailTemplateList = FXMLLoader.load(Objects.requireNonNull(EmailTemplateController.class.getResource("EmailTemplates.fxml")));
        VBox list = (VBox) emailTemplateList.getChildren().get(1);

        ArrayList<String> emailTemplateFiles = new ArrayList<>();
        File[] files = new File(MainController.databasePath).listFiles();
        for (File file : Objects.requireNonNull(files)) {
            if (file.isFile()) {
                String name = file.getName();
                if (name.contains(".html")) {
                    emailTemplateFiles.add(name.replace(".html", ""));
                }
            }
        }

        for (String emailTemplateFile : emailTemplateFiles) {
            try {
                File myObj = new File(MainController.databasePath+emailTemplateFile+".html");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    File file = new File(data);
                    if (file.exists()) {
                        VBox selector = FXMLLoader.load(Objects.requireNonNull(EmailTemplateController.class.getResource("EmailTemplateSelector.fxml")));
                        HBox hbox = (HBox) selector.getChildren().get(0);
                        Label name = (Label) hbox.getChildren().get(0);
                        name.setText(emailTemplateFile);
                        list.getChildren().add(selector);
                        EmailTemplateSelectorController.allEmailTemplateSelectors.add(selector);
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e) {e.printStackTrace();}
        }

        Label createBtn = FXMLLoader.load(Objects.requireNonNull(EmailTemplateController.class.getResource("CreateNewEmailTemplateButton.fxml")));
        list.getChildren().add(createBtn);

        return emailTemplateList;
    }

    public void openNewEmailTemplateWindow() throws IOException {
        createNewEmailTemplateStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CreateNewEmailTemplateWindow.fxml")));
        Scene scene = new Scene(root, 350, 400);
        createNewEmailTemplateStage.setTitle("Add a new email template");
        createNewEmailTemplateStage.setScene(scene);
        createNewEmailTemplateStage.setResizable(false);
        createNewEmailTemplateStage.show();
    }

    public static void closeNewEmailTemplateWindow() {
        createNewEmailTemplateStage.close();
    }

}
