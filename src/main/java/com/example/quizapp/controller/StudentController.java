// StudentController.java
package com.example.quizapp.controller;

import com.example.quizapp.UserSession;
import com.example.quizapp.dao.StudentDAO;
import com.example.quizapp.dao.UserDAO;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Student;
import com.example.quizapp.model.Teacher;
import com.example.quizapp.model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class StudentController {

    public Label textTeacherEmail;
    public Label textQuizName;
    public Label textDuration;
    public Label textTeacherNam;
    public Label textDate;
    public ImageView teacherImageView;
    public ImageView backgroundImageQuizView;
    public Pane panelNoQuizSelected;
    public Pane panelQuizSelected;
    public Pane panelAddNewQuiz;
    public Pane panelStartQuizTest;
    public TextField tfQuizPassword;
    public TextField tfConfirmQuizPassword;
    public Pane panelShowSetting;
    public Label tUserName;
    public TextField tfNewlastName;
    public TextField tfNewFirstName;
    public TextField tfPasswordCurrent;
    public TextField tfExpression;
    public TextField tfcurrentPassword;
    public TextField tfConfirmNewPassword;
    public TextField tfNewPassword;
    public TextField tfConfirmEmail;
    public TextField tfNewEmail;
    public Label tUserEmail;
    private StudentDAO studentDAO;
    private UserDAO userDAO;

    private User currentUser;

    private  Quiz selectedQuiz;

    // No-argument constructor
    // Constructor to initialize StudentDAO
    public StudentController() {
        this.userDAO = new UserDAO();
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
        currentUser = UserSession.getCurrentUser();
        if (currentUser != null) {
            fullname_toshow.setText(currentUser.getFullName());
            email_toshow.setText(currentUser.getEmail());

            //set the data for the Setting//
            tUserName.setText(currentUser.getFullName());

            // ... Set other user details
            updateProfileImage(currentUser.getSexe());

            //load the student Quizzes
            loadStudentQuizzes();
            // Listen for selection changes in the quizzesListView
            quizzesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    panelQuizSelected.toFront();
                    handleSelectedQuizChanged(newValue); // Call your method with the selected quiz name
                }
            });
        }
    }

    private void loadStudentQuizzes() {
        studentQuizzes = retrieveStudentQuizzes(currentUser.getUserId());

        // Display quizzes in the ListView
        displayQuizzesInListView(studentQuizzes);
    }

    private void handleSelectedQuizChanged(String quizName) {
        // Retrieve the selected quiz details from the list of studentQuizzes
        selectedQuiz = findQuizByName(quizName);



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
        panelAddNewQuiz.toFront();
    }

    public void handOpenQuizTest(MouseEvent mouseEvent) {
        panelStartQuizTest.toFront();
    }

    public void handOpenQuizDetails(MouseEvent mouseEvent) {
        panelQuizSelected.toFront();
    }

    public void handleDeleteQuiz(ActionEvent actionEvent) {
        // Create a confirmation dialog with OK and Cancel buttons
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Dialog de Confirmation");
        alert.setHeaderText("Supprimer le Quiz");
        alert.setContentText("Attention : Si vous supprimez ce quiz, vous ne pourrez plus y accéder "
                + "jusqu'à ce que vous l'ajoutiez à nouveau en utilisant le mot de passe du quiz. "
                + "Êtes-vous sûr de vouloir supprimer ce quiz?");

        // Set custom button configuration
        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        // Show the confirmation dialog and wait for the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeOK) {
                // User clicked OK, proceed with the delete operation
                studentDAO.deleteStudentQuiz(currentUser.getUserId(), selectedQuiz.getQuizId());

                // Retrieve the Quizzes again to refresh the data
                loadStudentQuizzes();

                // Show the "No Quiz Selected" panel
                panelNoQuizSelected.toFront();
            }
            // If the user clicked Cancel or closed the dialog, do nothing
        });
    }



    public void handleAddNewQuizOperation(ActionEvent actionEvent) {
        // Get the password and confirm password from the user (You need to have corresponding fields in your FXML)
        String password = tfQuizPassword.getText(); // Implement this method to get the password from user input
        String confirmPassword = tfConfirmQuizPassword.getText();// Implement this method to get the confirm password from user input

        // Verify the data
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            // Show an error message to the user indicating that both password and confirm password are required
            showErrorMessage("Les champs du mot de passe et de confirmation sont requis.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            // Show an error message to the user indicating that passwords do not match
            showErrorMessage("Les mots de passe ne correspondent pas. Veuillez entrer des mots de passe correspondants.");
            return;
        }

        // Add new row in the database (you need to implement this based on your database logic)
        boolean isQuizAdded = studentDAO.addNewStudentQuizToDatabase(password,currentUser.getUserId());
        System.out.println("isQuizAdded for student "+ isQuizAdded);

        if (isQuizAdded) {
            // If adding to the database is successful, load the quizzes to refresh
            loadStudentQuizzes();

            // Switch to the panel where the quizzes are displayed (adjust this based on your UI design)
            panelNoQuizSelected.toFront();
        } else {
            // If adding to the database fails, show an error message
            showErrorMessage("Vous ne pouvez pas ajouter ce QCM, peut-être l'avez-vous déjà ou le mot de passe que vous avez fourni n'est pas correct.");
        }
    }

    private void showErrorMessage(String message) {
        // Implement this method to show an error message to the user (you can use an Alert)
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

//---------------------------------------Functions for Setting they can work also for Teacher--------------------------------------------//
    public void handShowSetting(ActionEvent actionEvent) {
        panelShowSetting.toFront();
    }

    public void handleUpdateEmail(ActionEvent actionEvent) {
        String newEmail = tfNewEmail.getText();
        String confirmNewEmail = tfConfirmEmail.getText();

        // Validate email format
        if (!isValidEmail(newEmail)) {
            // Show an error message for invalid email format
            showErrorMessage("Veuillez entrer une adresse e-mail valide.");
            return;
        }

        // Validate that new email and confirm email match
        if (!newEmail.equals(confirmNewEmail)) {
            // Show an error message for email mismatch
            showErrorMessage("Les adresses e-mail ne correspondent pas. Veuillez entrer des adresses e-mail correspondantes.");
            return;
        }

        // If the data is valid, update the email using the userDAO
        boolean isEmailUpdated = userDAO.updateStudentUserEmail(currentUser.getUserId(), newEmail);

        if (isEmailUpdated) {
            // If updating is successful, update the displayed email in the screen
            email_toshow.setText(newEmail);

            // Optionally, you can show a success message to the user
            showSuccessMessage("L'adresse e-mail a été mise à jour avec succès.");
        } else {
            // If updating fails, show an error message
            showErrorMessage("Échec de la mise à jour de l'adresse e-mail. Veuillez réessayer.");
        }
    }

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        // You can implement a more sophisticated email validation based on your requirements
        // This is a simple example, you may need to use a regular expression for more precise validation
        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }


    public void handleUpdateUserPassword(ActionEvent actionEvent) {
        String currentPassword = tfcurrentPassword.getText();
        String newPassword = tfNewPassword.getText();
        String confirmNewPassword = tfConfirmNewPassword.getText();

        // Validate the data
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            // Show an error message to the user indicating that all password fields are required
            showErrorMessage("Les champs du mot de passe actuel, du nouveau mot de passe et de la confirmation du nouveau mot de passe sont requis.");
            return;
        }

        // Compare the currentPassword with User.getPassword() (replace 'currentUser' with the actual instance)
        if (!currentPassword.equals(currentUser.getPassword())) {
            // Show an error message indicating that the current password is incorrect
            showErrorMessage("Le mot de passe actuel est incorrect. Veuillez entrer le mot de passe actuel correct.");
            return;
        }

        // Check if the new password and confirm new password match
        if (!newPassword.equals(confirmNewPassword)) {
            // Show an error message indicating that the new passwords do not match
            showErrorMessage("Les nouveaux mots de passe ne correspondent pas. Veuillez entrer des mots de passe correspondants.");
            return;
        }

        // If all validations pass, update the password in the database
        boolean isPasswordUpdated = userDAO.updateStudentUserPassword(currentUser.getUserId(), newPassword);

        if (isPasswordUpdated) {
            // Optionally, you can show a success message to the user
            showSuccessMessage("Le mot de passe a été mis à jour avec succès.");
        } else {
            // If updating fails, show an error message
            showErrorMessage("Échec de la mise à jour du mot de passe. Veuillez réessayer.");
        }
    }

    public void handleDeleteUserAccount(ActionEvent actionEvent) {
    }

    public void handleUpdateUserName(ActionEvent actionEvent) {
        String firstName = tfNewFirstName.getText();
        String lastName = tfNewlastName.getText();

        // Validate data
        if (firstName.isEmpty() || lastName.isEmpty()) {
            // Show an error message to the user indicating that both first name and last name are required
            showErrorMessage("Les champs du prénom et du nom de famille sont requis.");
            return;
        }

        // If the data is valid, update the userName using the studentDAO
        boolean isUserNameUpdated = userDAO.updateStudentUserName(currentUser.getUserId(), firstName, lastName);

        if (isUserNameUpdated) {
            // If updating is successful, update the displayed name in the screen
            fullname_toshow.setText(firstName + " " + lastName);
            tUserName.setText(firstName + " " + lastName);

            // Optionally, you can show a success message to the user
            showSuccessMessage("Le nom d'utilisateur a été mis à jour avec succès.");
        } else {
            // If updating fails, show an error message
            showErrorMessage("Échec de la mise à jour du nom d'utilisateur. Veuillez réessayer.");
        }
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
