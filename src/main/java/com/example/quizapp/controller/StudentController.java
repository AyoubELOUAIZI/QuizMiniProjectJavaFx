// StudentController.java
package com.example.quizapp.controller;

import com.example.quizapp.UserSession;
import com.example.quizapp.dao.StudentDAO;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Student;
import com.example.quizapp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StudentController {

    private StudentDAO studentDAO;

    // No-argument constructor
    // Constructor to initialize StudentDAO
    public StudentController() {
        this.studentDAO = new StudentDAO();
    }

    @FXML
    public ImageView studentProfileImageView;
    @FXML
    private Label fullname_toshow;
    @FXML
    private Label email_toshow;
    @FXML
    private Button student_logout;
    @FXML
    private ListView<String> quizzesListView;

    @FXML
    private void handleButtonAction() {
        // Your logic here
    }

    @FXML
    public void initialize() {
        // Initialization logic here, like setting user details
        User currentUser = UserSession.getCurrentUser();
        if (currentUser != null) {
            fullname_toshow.setText(currentUser.getFullName());
            email_toshow.setText(currentUser.getEmail());
            // ... Set other user details
            updateProfileImage(currentUser.getSexe());
            List<Quiz> studentQuizzes = retrieveStudentQuizzes(currentUser.getUserId());

            // Display quizzes in the ListView
            displayQuizzesInListView(studentQuizzes);
        }
    }

    public List<Quiz> retrieveStudentQuizzes(int studentId) {
        return studentDAO.getQuizzesForStudent(studentId);
    }

    private void displayQuizzesInListView(List<Quiz> quizzes) {
        // Clear existing items in the ListView
        quizzesListView.getItems().clear();

        // Add quiz names to the ListView
        for (Quiz quiz : quizzes) {
            quizzesListView.getItems().add(quiz.getQuizName());
        }
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


    private void updateProfileImage(String gender) {
        if ("female".equalsIgnoreCase(gender)) {
            System.out.println(gender);
            // Set the profile image to the female version
            studentProfileImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/quizapp/images/studentFemale.png")));
        } else {
            // Set the profile image to the male version
            studentProfileImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/quizapp/images/studentMale.png")));
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
