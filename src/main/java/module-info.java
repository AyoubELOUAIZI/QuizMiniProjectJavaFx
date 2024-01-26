module com.example.quizapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.quizapp to javafx.fxml;
    exports com.example.quizapp;
    exports com.example.quizapp.controller;
    opens com.example.quizapp.controller to javafx.fxml;
    opens com.example.quizapp.model to javafx.base;
}