package com.example.batchemailsender;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController extends Application {

    @FXML
    private HBox rootCentre;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("Batch Email Sender");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void initialize() throws IOException {
        loadEmailLists();
        loadEmailTemplates();
        loadFrom();
        loadSend();
    }

    public void loadEmailLists() throws IOException {
        rootCentre.getChildren().add(EmailListController.display());
    }

    public void loadEmailTemplates() throws IOException {
        rootCentre.getChildren().add(EmailTemplateController.display());
    }

    public void loadFrom() throws IOException {
        BorderPane loadEmailLists = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("From.fxml")));
        rootCentre.getChildren().add(loadEmailLists);
    }

    public void loadSend() throws IOException {
        BorderPane loadEmailLists = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Send.fxml")));
        rootCentre.getChildren().add(loadEmailLists);
    }
}
