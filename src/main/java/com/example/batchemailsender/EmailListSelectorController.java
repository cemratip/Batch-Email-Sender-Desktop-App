package com.example.batchemailsender;

import com.google.common.io.Files;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class EmailListSelectorController extends MainController {

    @FXML
    private ImageView duplicateIcon;
    @FXML
    private ImageView editIcon;
    @FXML
    private ImageView deleteIcon;
    @FXML
    private Label name;
    @FXML
    private VBox root;
    public static boolean editing = false;
    public static Stage openExistingEmailListStage;
    public static ArrayList<String> selectedEmailList = new ArrayList<>();
    public static ArrayList<VBox> allEmailListSelectors = new ArrayList<>();

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

    public void select() {
        // if the current selector's tag is unselected
        HBox hbox = (HBox) root.getChildren().get(0);
        if (hbox.getId().equals("unselected")) {
            // make all selectors in the list grey etc
            for (VBox selector : allEmailListSelectors) {
                selector.setStyle("-fx-background-color:#f8f4f4");
                // set tag to unselected
                HBox allHBox = (HBox) selector.getChildren().get(0);
                allHBox.setId("unselected");
                selectedEmailList.clear();
            }
            // make the current selector blue etc
            root.setStyle("-fx-background-color:#64c4e4");
            // set tag to selected
            hbox.setId("selected");
            JSONParser jsonParser = new JSONParser();
            String fileName = name.getText() + ".json";
            Path filePath = Path.of("src/main/resources/local database/" + fileName);
            try (FileReader reader = new FileReader(String.valueOf(filePath))) {
                Object obj = jsonParser.parse(reader);
                JSONObject emails = (JSONObject) obj;
                JSONArray emailList = (JSONArray) emails.get("emails");
                for (Object email : emailList) {
                    selectedEmailList.add((String) email);
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
        // else if the current selector's tag is selected
        else if (hbox.getId().equals("selected")) {
            // make the current selector grey etc
            root.setStyle("-fx-background-color:#f8f4f4");
            // set tag to unselected
            hbox.setId("unselected");
            selectedEmailList.clear();
        }
    }

    public void duplicate() throws IOException {
        try {
            String contents = Files.asCharSource(new File("src/main/resources/local database/" + name.getText() + ".json"), StandardCharsets.UTF_8).read();
            FileWriter newFile = new FileWriter("src/main/resources/local database/" + name.getText() + " copy.json");
            newFile.write(contents);
            newFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainController.replaceEmailLists();
    }

    public void edit() throws IOException {
        openExistingEmailListStage = new Stage();
        BorderPane stageRoot = FXMLLoader.load(Objects.requireNonNull(EmailListController.class.getResource("CreateNewEmailListWindow.fxml")));
        Scene scene = new Scene(stageRoot, 350, 400);
        openExistingEmailListStage.setTitle("Edit an email list");
        openExistingEmailListStage.setScene(scene);
        openExistingEmailListStage.setResizable(false);
        openExistingEmailListStage.initStyle(StageStyle.UNDECORATED);

        VBox labelContainer = (VBox) stageRoot.getChildren().get(0);
        Label label = (Label) labelContainer.getChildren().get(0);
        label.setText("Edit an email list");

        VBox fieldContainer = (VBox) stageRoot.getChildren().get(1);

        HBox nameFieldContainer = (HBox) fieldContainer.getChildren().get(0);
        TextField nameTextField = (TextField) nameFieldContainer.getChildren().get(1);
        nameTextField.setText(name.getText());

        TextArea emailTextArea = (TextArea) fieldContainer.getChildren().get(2);
        String contents = Files.asCharSource(new File("src/main/resources/local database/" + name.getText() + ".json"), StandardCharsets.UTF_8).read();

        HBox buttonContainer = (HBox) stageRoot.getChildren().get(2);
        Button saveButton = (Button) buttonContainer.getChildren().get(0);
        saveButton.setText("Save");

        contents = contents.substring(12);
        contents = contents.replaceAll("}", "");
        contents = contents.replaceAll("]", "");
        contents = contents.replaceAll("\"", "");
        emailTextArea.setText(contents);
        editing = true;
        delete();
        openExistingEmailListStage.show();
    }

    public void delete() {
        root.getChildren().clear();
        if (selectedEmailList.contains(name.getText())){
            selectedEmailList.clear();
        }
        EmailListController.emailListFiles.remove(name.getText());
        String fileName = name.getText() + ".json";
        Path filePath = Path.of("src/main/resources/local database/"+fileName);
        File f = new File(String.valueOf(filePath));
        f.delete();
        allEmailListSelectors.remove(root);
    }

    public static void closeEditEmailListWindow() {
        openExistingEmailListStage.close();
    }
}
