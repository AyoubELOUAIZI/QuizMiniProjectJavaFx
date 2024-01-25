// StudentController.java
package com.example.quizapp.controller;

import com.example.quizapp.UserSession;
import com.example.quizapp.dao.StudentDAO;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Student;
import com.example.quizapp.model.Teacher;
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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

public class StudentController {

    public Label textTeacherEmail;
    public Label textQuizName;
    public Label textDuration;
    public Label textTeacherNam;
    public Label textDate;
    public ImageView teacherImageView;
    public ImageView backgroundImageQuizView;
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
    private List<Quiz> studentQuizzes;

    @FXML
    public void initialize() {
        // Initialization logic here, like setting user details
        User currentUser = UserSession.getCurrentUser();
        if (currentUser != null) {
            fullname_toshow.setText(currentUser.getFullName());
            email_toshow.setText(currentUser.getEmail());
            // ... Set other user details
            updateProfileImage(currentUser.getSexe());
            studentQuizzes = retrieveStudentQuizzes(currentUser.getUserId());

            // Display quizzes in the ListView
            displayQuizzesInListView(studentQuizzes);

            // Listen for selection changes in the quizzesListView
            quizzesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    handleSelectedQuizChanged(newValue); // Call your method with the selected quiz name
                }
            });
        }
    }

    private void handleSelectedQuizChanged(String quizName) {
        // Retrieve the selected quiz details from the list of studentQuizzes
        Quiz selectedQuiz = findQuizByName(quizName);



        // Check if the selectedQuiz is not null (for safety)
        if (selectedQuiz != null) {
            //select data from database if you need
           Teacher selectedQuizTeacher = retrieveSelectedQuizTeacher(selectedQuiz.getTeacherId());
        

            // Update the content of the AnchorPane with the selected quiz details
            updateAnchorPaneContent(selectedQuiz,selectedQuizTeacher);
        }
    }

    private void updateAnchorPaneContent(Quiz selectedQuiz,Teacher selectedQuizTeacher) {
        textQuizName.setText(selectedQuiz.getQuizName());
        textTeacherNam.setText(selectedQuizTeacher.getFullName());
        textTeacherEmail.setText(selectedQuizTeacher.getEmail());

        // Assuming getDuration() returns an int representing duration in minutes
        textDuration.setText(String.valueOf(selectedQuiz.getDuration()));

        // Assuming getStartAt() returns a Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedStartDate = dateFormat.format(selectedQuiz.getStartAt());
        textDate.setText(formattedStartDate);

        //updating the image
        updateQuizTeacherImage(selectedQuizTeacher.getSexe());

        //updateToRandomBackground();
        updateToRandomBackground();
    }

    private void updateToRandomBackground() {
        List<String> imageNames = List.of("image.jpg", "splash.png", "splash.png","randombackgroundquiz.png","randombackgroundquiz2.png");
        // Generate a random index to select a random image name from the list
        Random random = new Random();
        int randomIndex = random.nextInt(imageNames.size());

        // Get the random image name
        String randomImageName = imageNames.get(randomIndex);
        backgroundImageQuizView.setImage(new Image(getClass().getResourceAsStream("/com/example/quizapp/images/"+randomImageName)));

    }

    // Helper method to find the Quiz object by quizName in the list of studentQuizzes
    private Quiz findQuizByName(String quizName) {
        for (Quiz quiz : studentQuizzes) {
            if (quiz.getQuizName().equals(quizName)) {
                return quiz;
            }
        }
        return null; // Quiz not found, handle this case as needed
    }

    public List<Quiz> retrieveStudentQuizzes(int studentId) {
        return studentDAO.getQuizzesForStudent(studentId);
    }
    public Teacher retrieveSelectedQuizTeacher(int teacherId) {
        return studentDAO.getQuizTeacherByTeacherId(teacherId);
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

    private void updateQuizTeacherImage(String gender) {
        if ("female".equalsIgnoreCase(gender)) {
            System.out.println(gender);
            // Set the profile image to the female version
            teacherImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/quizapp/images/teacherFemale.png")));
        } else {
            // Set the profile image to the male version
            teacherImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/quizapp/images/teacherMale.png")));
        }

    }

    public void handleAddNewQuiz(ActionEvent actionEvent) {
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
