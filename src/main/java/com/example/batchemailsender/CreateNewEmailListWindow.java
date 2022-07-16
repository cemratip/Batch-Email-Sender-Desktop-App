package com.example.batchemailsender;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.FileWriter;
import javafx.scene.input.KeyCode;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.batchemailsender.EmailListSelectorController.editing;

public class CreateNewEmailListWindow {

    @FXML
    private TextField nameInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextArea emails;

    public void create() throws IOException {
        if (EmailListSelectorController.allEmailListSelectors.size() < 8) {
            if (!EmailListController.emailListFiles.contains(nameInput.getText())) {
                if (!emails.getText().equals("")) {
                    String emailListName = nameInput.getText();
                    String allEmails = emails.getText();
                    nameInput.clear();
                    emailInput.clear();
                    emails.clear();
                    if (!editing) {
                        EmailListController.closeNewEmailListWindow();
                    } else {
                        EmailListSelectorController.selectedEmailList.clear();
                        EmailListSelectorController.closeEditEmailListWindow();
                        EmailListSelectorController.editing = false;
                    }

                    // create JSON file
                    JSONObject jsonObject = new JSONObject();
                    ArrayList<String> emailsArrayList = new ArrayList<>();
                    String[] emailArray = allEmails.split(",");
                    Collections.addAll(emailsArrayList, emailArray);
                    jsonObject.put("emails", emailsArrayList);

                    try {
                        FileWriter file = new FileWriter("src/main/resources/local database/" + emailListName + ".json");
                        file.write(jsonObject.toJSONString());
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MainController.replaceEmailLists();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Please fill the email list.");
                    a.show();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "You already have an email list with this name.");
                a.show();
            }
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR, "You can only create up to 8 email lists. Please delete one to continue.");
            a.show();
        }
    }

    public void checkKeyPressed() {
        emailInput.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                addEmail();
            }
        });
    }

    public void addEmail() {
        String email = emailInput.getText();
        if (!email.equals("")) {
            if (email.contains("@")) {
                if (!emails.getText().equals("")) {
                    emails.setText(emails.getText() + ", " + email);
                } else {
                    emails.setText(emails.getText() + email);
                }
                emailInput.clear();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Please enter a valid email address.");
                a.show();
            }
        }
    }
}
