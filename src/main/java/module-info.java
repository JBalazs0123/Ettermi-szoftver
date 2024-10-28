module com.example.vendeglatas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.databind;


    opens com.example.vendeglatas to javafx.fxml;
    exports com.example.vendeglatas;

    opens com.example.vendeglatas.controllers to javafx.fxml;
    exports com.example.vendeglatas.controllers;

    opens com.example.vendeglatas.modules to com.fasterxml.jackson.databind;
}