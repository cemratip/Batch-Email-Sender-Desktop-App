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

public class EmailAccountController {
    private static Stage createNewEmailAccountStage;

    public static BorderPane display() throws IOException {
        EmailAccountSelectorController.allEmailAccountSelectors.clear();
        BorderPane emailAccountList = FXMLLoader.load(Objects.requireNonNull(EmailAccountController.class.getResource("EmailAccounts.fxml")));
        VBox list = (VBox) emailAccountList.getChildren().get(1);

        ArrayList<String> emailAccounts = new ArrayList<>();
        File[] files = new File("src/main/resources/local database").listFiles();
        for (File file : Objects.requireNonNull(files)) {
            if (file.isFile()) {
                String name = file.getName();
                if (name.contains(".txt")) {
                    emailAccounts.add(name.replace(".txt", ""));
                }
            }
        }

        for (String emailAccount : emailAccounts) {
            VBox selector = FXMLLoader.load(Objects.requireNonNull(EmailAccountController.class.getResource("EmailAccountSelector.fxml")));
            HBox hbox = (HBox) selector.getChildren().get(0);
            Label name = (Label) hbox.getChildren().get(0);
            name.setText(emailAccount+"@gmail.com");
            list.getChildren().add(selector);
            EmailAccountSelectorController.allEmailAccountSelectors.add(selector);
        }

        Label createBtn = FXMLLoader.load(Objects.requireNonNull(EmailAccountController.class.getResource("CreateNewEmailAccountButton.fxml")));
        list.getChildren().add(createBtn);

        return emailAccountList;
    }

    public void openNewEmailAccountWindow() throws IOException {
        createNewEmailAccountStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CreateNewEmailAccountWindow.fxml")));
        Scene scene = new Scene(root, 350, 400);
        createNewEmailAccountStage.setTitle("Pair a new Gmail account");
        createNewEmailAccountStage.setScene(scene);
        createNewEmailAccountStage.setResizable(false);
        createNewEmailAccountStage.show();
    }

    public static void closeNewEmailAccountWindow() {
        createNewEmailAccountStage.close();
    }
}
