package com.example.batchemailsender;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;
import com.google.common.io.Files;

public class MainController extends Application {
    private static Pane root;
    @FXML
    private TextField subject;

    public static String databasePath;

    @Override
    public void start(Stage stage) throws Exception {
        Path currentRelativePath = Paths.get("");
        databasePath = currentRelativePath.toAbsolutePath().toString() + "/local database/";
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("Batch Email Sender");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        loadEmailLists();
        loadEmailTemplates();
        loadEmailAccounts();
        loadSend();
    }

    public void initialize() throws IOException {
    }

    public static void loadEmailLists() throws IOException {
        HBox hbox = (HBox) root.getChildren().get(1);
        hbox.getChildren().add(EmailListController.display());
    }

    public static void replaceEmailLists() throws IOException {
        HBox hbox = (HBox) root.getChildren().get(1);
        BorderPane borderpane = (BorderPane) hbox.getChildren().get(0);
        borderpane.getChildren().clear();
        borderpane.getChildren().add(EmailListController.display());
    }

    public static void loadEmailTemplates() throws IOException {
        HBox hbox = (HBox) root.getChildren().get(1);
        hbox.getChildren().add(EmailTemplateController.display());
    }

    public static void replaceEmailTemplates() throws IOException {
        HBox hbox = (HBox) root.getChildren().get(1);
        BorderPane borderpane = (BorderPane) hbox.getChildren().get(1);
        borderpane.getChildren().clear();
        borderpane.getChildren().add(EmailTemplateController.display());
    }

    public void loadEmailAccounts() throws IOException {
        HBox hbox = (HBox) root.getChildren().get(1);
        hbox.getChildren().add(EmailAccountController.display());
    }

    public static void replaceEmailAccounts() throws IOException {
        HBox hbox = (HBox) root.getChildren().get(1);
        BorderPane borderpane = (BorderPane) hbox.getChildren().get(2);
        borderpane.getChildren().clear();
        borderpane.getChildren().add(EmailAccountController.display());
    }

    public void loadSend() throws IOException {
        BorderPane loadEmailLists = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EmailDetails.fxml")));
        HBox hbox = (HBox) root.getChildren().get(1);
        hbox.getChildren().add(loadEmailLists);
    }

    public void send () {
        if (!EmailListSelectorController.selectedEmailList.isEmpty() && !EmailAccountSelectorController.selectedEmailAccountDetails.isEmpty() && !EmailTemplateSelectorController.selectedEmailTemplatePath.equals("")) {
            sendEmails();
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please make a selection from each column.");
            a.show();
        }
    }

    private void sendEmails() {
        String email = EmailAccountSelectorController.selectedEmailAccountDetails.get(0);
        StringBuilder sb = new StringBuilder(EmailAccountSelectorController.selectedEmailAccountDetails.get(1));
        String password = sb.reverse().toString();

        // Assuming you are sending email from through gmail's smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        for (String recipient : EmailListSelectorController.selectedEmailList) {
            try {
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(email));

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

                // Set Subject: header field
                message.setSubject(subject.getText());

                String content = Files.asCharSource(new File(EmailTemplateSelectorController.selectedEmailTemplatePath), StandardCharsets.UTF_8).read();
                message.setContent(content, "text/html");

                // Send message
                Transport.send(message);

            } catch (MessagingException e) {
                Alert a = new Alert(Alert.AlertType.ERROR, "An error occurred. Please try again.");
                a.show();
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Error accessing file.");
                a.show();
            }
        }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Email batch sent.");
        a.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
