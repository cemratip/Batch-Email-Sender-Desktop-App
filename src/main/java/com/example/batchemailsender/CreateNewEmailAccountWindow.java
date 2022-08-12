package com.example.batchemailsender;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CreateNewEmailAccountWindow {

    @FXML
    private TextField emailInput;
    @FXML
    private TextField passwordInput;

    public void create() throws IOException {
        if (EmailAccountSelectorController.allEmailAccountSelectors.size() < 8) {
            String email = emailInput.getText();
            String password = passwordInput.getText();
            String fileName = email.replace("@gmail.com", "");
            fileName += ".txt";
            boolean exists = false;

            File[] files = new File(MainController.databasePath).listFiles();
            for (File file : Objects.requireNonNull(files)) {
                if (file.isFile()) {
                    String name = file.getName();
                    if (name.equals(fileName)) {
                        Alert a = new Alert(Alert.AlertType.ERROR, "You have already added this email account.");
                        a.show();
                        exists = true;
                    }
                }
            }

            if (!exists) {
                if (!email.equals("") && !password.equals("")) {
                    // verify email exists by sending confirmation email letting user know they've paired an account
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

                    try {
                        // Create a default MimeMessage object.
                        MimeMessage message = new MimeMessage(session);

                        // Set From: header field of the header.
                        message.setFrom(new InternetAddress(email));

                        // Set To: header field of the header.
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

                        // Set Subject: header field
                        message.setSubject("Confirmation email");

                        // Now set the actual message
                        message.setText("You have successfully paired your email account.");

                        // Send message
                        Transport.send(message);

                        // if it does exist, store username and hashed password in a .txt file with the password on a new line
                        try {
                            EmailAccountController.closeNewEmailAccountWindow();
                            FileWriter file = new FileWriter(MainController.databasePath + fileName);
                            StringBuilder sb = new StringBuilder(password);
                            file.write(email + "\n" + sb.reverse());
                            file.close();
                        } catch (IOException e) {
                            Alert a = new Alert(Alert.AlertType.ERROR, "An error occurred.");
                            a.show();
                        }
                    } catch (MessagingException e) {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Error logging in.");
                        a.show();
                    }
                    MainController.replaceEmailAccounts();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "One or more fields are missing.");
                    a.show();
                }
            }
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR, "You can only pair up to 8 email accounts. Please delete one to continue.");
            a.show();
        }
    }
}
