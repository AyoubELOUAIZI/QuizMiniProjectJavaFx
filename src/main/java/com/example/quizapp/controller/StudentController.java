// StudentController.java
package com.example.quizapp.controller;

import com.example.quizapp.UserSession;
import com.example.quizapp.dao.StudentDAO;
import com.example.quizapp.model.Student;
import com.example.quizapp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StudentController {
    public ImageView studentProfileImageView;
    //private final StudentDAO studentDAO;

    // No-argument constructor
    public StudentController() {
    }


    @FXML
    private Label fullname_toshow;
    @FXML
    private Label email_toshow;
    @FXML
    private Label cne_toshow;
    @FXML
    private Label bactype_toshow;
    @FXML
    private Label age_toshow;
    @FXML
    private Label bacyear_toshow;
    @FXML
    private Label city_toshow;
    @FXML
    private ImageView school_bg;
    @FXML
    private ImageView school_logo;
    @FXML
    private Label school_title;
    @FXML
    private Label school_description;
    @FXML
    private Label school_fax;
    @FXML
    private Label school_telephone;
    @FXML
    private Label school_adress;
    @FXML
    private Label school_email;

    @FXML
    private Button student_logout;

    @FXML
    private Hyperlink school_website;
    @FXML
    private TableView<?> formation_inview;


    @FXML
    private void handleButtonAction() {
        // Your logic here
    }


    @FXML
    private void handleLogout() {
        // Clear the session
        UserSession.clearSession();
        clearLoginState();
        // Load the Authentication screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizapp/fxml/AuthenticationScreen.fxml")); // replace with your actual path
            Parent root = loader.load();

            // Get the current stage from any control, like a button

            Stage stage = (Stage) student_logout.getScene().getWindow(); // replace 'someButton' with any @FXML injected control in your controller

            // Set the scene to the stage
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, perhaps show an error dialog
        }
    }

    private void clearLoginState() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("userLogin.txt"))) {
            writer.write(""); // Clear the file content
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        // Initialization logic here, like setting user details
        User currentUser = UserSession.getCurrentUser();
        if (currentUser != null) {
            fullname_toshow.setText(currentUser.getFullName());
            email_toshow.setText(currentUser.getEmail());
            // ... Set other user details

            if ("female".equalsIgnoreCase(currentUser.getSexe())) {
                System.out.println(currentUser.getSexe());
                // Set the profile image to the female version
                studentProfileImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/quizapp/images/studentFemale.png")));
            } else {
                // Set the profile image to the male version
                studentProfileImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/quizapp/images/studentMale.png")));
            }

        }
    }
//    public void createStudent(Student student) {
//        studentDAO.createStudent(student);
//    }
//
//    public void updateStudent(Student updatedStudent) {
//        studentDAO.updateStudent(updatedStudent);
//    }
//
//    public void deleteStudent(int userId) {
//        studentDAO.deleteStudent(userId);
//    }

}
