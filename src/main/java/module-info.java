module com.example.batchemailsender {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires json.simple;

    opens com.example.batchemailsender to javafx.fxml;
    exports com.example.batchemailsender;
}