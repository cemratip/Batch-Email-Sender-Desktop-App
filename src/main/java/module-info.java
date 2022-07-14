module com.example.batchemailsender {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires json.simple;
    requires java.desktop;
    requires javafx.web;
    requires java.mail;
    requires com.google.common;

    opens com.example.batchemailsender to javafx.fxml;
    exports com.example.batchemailsender;
}