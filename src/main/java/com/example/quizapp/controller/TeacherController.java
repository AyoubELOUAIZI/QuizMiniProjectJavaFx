// TeacherController.java
package com.example.quizapp.controller;

import com.example.quizapp.UserSession;
import com.example.quizapp.dao.TeacherDAO;
import com.example.quizapp.model.Teacher;
import com.example.quizapp.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherController {
    public ImageView profileImageView;
    private TeacherDAO teacherDAO;



    public TeacherController() {
        this.teacherDAO = new TeacherDAO();
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
    private Button teacher_logout; // Ensure this matches the fx:id in your FXML

    @FXML
    private Hyperlink school_website;
    @FXML
    private TableView<?> formation_inview;
    // ... (other FXML components)

    // Event handlers
    @FXML
    private void handleButtonAction() {
        // Your logic here
    }


    @FXML
    private void handleLogout() {
        // Clear the session
        UserSession.clearSession();

        // Load the Authentication screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizapp/fxml/AuthenticationScreen.fxml")); // replace with your actual path
            Parent root = loader.load();

            // Get the current stage from any control, like a button

            Stage stage = (Stage) teacher_logout.getScene().getWindow(); // replace 'someButton' with any @FXML injected control in your controller

            // Set the scene to the stage
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, perhaps show an error dialog
        }



    }


    // You can have more handlers for other buttons and actions

    // Initialize method if needed
    @FXML
    public void initialize() {
        // Initialization logic here, like setting user details
        User currentUser = UserSession.getCurrentUser();
        if (currentUser != null) {
            fullname_toshow.setText(currentUser.getFullName());
            email_toshow.setText(currentUser.getEmail());
            // ... Set other user details

                if ("female".equals(currentUser.getSexe())) {
                    // Set the profile image to the female version
                    profileImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/quizapp/images/teacherFemale.png")));
                } else {
                    // Set the profile image to the male version
                    profileImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/quizapp/images/teacherMale.png")));
                }

        }
    }


//    public Teacher getTeacherById(int userId) {
//        return teacherDAO.getTeacherById(userId);
//    }
//
//    public void createTeacher(Teacher teacher) {
//        teacherDAO.createTeacher(teacher);
//    }
//
//    public void updateTeacher(Teacher updatedTeacher) {
//        teacherDAO.updateTeacher(updatedTeacher);
//    }
//
//    public void deleteTeacher(int userId) {
//        teacherDAO.deleteTeacher(userId);
//    }
//
//    public List<Teacher> getAllTeachers() {
//        return teacherDAO.getAllTeachers();
//    }






}
