package com.example.batchemailsender;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class EmailListSelectorController extends MainController {

    @FXML
    private ImageView duplicateIcon;
    @FXML
    private ImageView editIcon;
    @FXML
    private ImageView deleteIcon;
    @FXML
    private Label name;

    private boolean selected = false;

    public static ArrayList<String> selectedEmailList = new ArrayList<>();

    @FXML
    private VBox root;

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
        if (!selected) {
            root.setStyle("-fx-background-color:#64c4e4");
            selected = true;
            JSONParser jsonParser = new JSONParser();
            String fileName = name.getText() + ".json";
            Path filePath = Path.of("src/main/resources/local database/"+fileName);
            try (FileReader reader = new FileReader(String.valueOf(filePath)))
            {
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
        else {
            root.setStyle("-fx-background-color:#f8f4f4");
            selected = false;
            selectedEmailList.clear();
        }
    }

    public void duplicate() {

    }

    public void edit() {

    }

    public void delete() {
        root.getChildren().clear();
        selected = false;
        selectedEmailList.clear();
        String fileName = name.getText() + ".json";
        Path filePath = Path.of("src/main/resources/local database/"+fileName);
        File f = new File(String.valueOf(filePath));
        f.delete();
    }
}
