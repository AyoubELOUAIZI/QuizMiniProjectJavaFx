package com.example.quizapp.controller;


import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.example.quizapp.UserSession;
import com.example.quizapp.dao.UserDAO;
import com.example.quizapp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class AuthenticationController {


    @FXML
    private Pane login_forum, signup1;

    @FXML
    private RadioButton signup_female, signup_male;

    @FXML
    private TextField email_login;

    @FXML
    private PasswordField password_login;

    public void handleLogin() {
        String email = email_login.getText();
        String password = password_login.getText();

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.loginUser(email, password);

            if (user != null) {
                // Successfully logged in
                UserSession.setCurrentUser(user);
                // Now you can check the role and perform role-specific actions
                String role = user.getRole();
                if ("STUDENT".equals(role)) {
                    // Handle student login
                    System.out.println("student login");
                    System.out.println("user");
                    System.out.println(user);
                    navigateToStudentScreen();

                } else if ("TEACHER".equals(role)) {
                    // Handle teacher login
                    System.out.println("teacher login");
                    navigateToTeacherScreen();
                }
            } else {
                // Login failed
                // Show an error message or take appropriate action
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Invalid email or password");
                alert.setContentText("Please try again.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
    }

    private void navigateToStudentScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizapp/fxml/StudentScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            // Close the current login window
            ((Stage) email_login.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
    }

    private void navigateToTeacherScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizapp/fxml/TeacherScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            // Close the current login window
            ((Stage) email_login.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
    }


    @FXML
    void starting_signup(ActionEvent event) {
        signup1.toFront();

    }

    @FXML
    private TextField signup_name;
    @FXML
    private TextField signup_lastname;
    @FXML
    private RadioButton signup_student, signup_teacher;
    @FXML
    private TextField signup_email;
    @FXML
    private PasswordField signup_password;
    @FXML
    private PasswordField signup_confirm_password;

    @FXML
    void signUp(ActionEvent event) {
        // Validate input fields
        if (!validateInput()) {
            // Show error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please fill in all fields correctly.");
            alert.setContentText("All fields are required, and passwords must match.");
            alert.showAndWait();
            return;
        }

        String firstname = signup_name.getText();
        String lastname = signup_lastname.getText();
        String email = signup_email.getText();
        String password = signup_password.getText(); // You should hash this password before storing it
        String role = (signup_student.isSelected()) ? "STUDENT" : "TEACHER";
        String sex = (signup_female.isSelected()) ? "female" : "male";

        User newUser = new User(0,firstname, lastname, email, password, role, sex,null);
        UserDAO userDAO = new UserDAO();

        User createdUser = userDAO.createUser(newUser);
//        this will redirect the user to the login and show a message comfirmation
        if(createdUser != null) {
            backtologin(event);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Compte Créé avec Succès !");
            alert.setHeaderText("Votre compte a été créé avec succès.");
            alert.setContentText("Félicitations "+firstname.toUpperCase()+" ! Vous pouvez maintenant vous connecter avec vos identifiants.");
            alert.showAndWait();
            password_login.setText(password);
            email_login.setText(email);
        }

}

    private boolean validateInput() {
        // Vérifier si un champ est vide
        if (signup_name.getText().isEmpty() ||
                signup_lastname.getText().isEmpty() ||
                signup_email.getText().isEmpty() ||
                signup_password.getText().isEmpty() ||
                signup_confirm_password.getText().isEmpty()) {
            showAlert("Erreur de Validation", "Tous les champs sont obligatoires.", Alert.AlertType.ERROR);
            return false;
        }

        // Validation de l'email
        if (!isValidEmail(signup_email.getText())) {
            showAlert("Erreur de Validation", "Veuillez entrer une adresse email valide.", Alert.AlertType.ERROR);
            return false;
        }

        // Vérifier si les mots de passe correspondent
        if (!signup_password.getText().equals(signup_confirm_password.getText())) {
            showAlert("Erreur de Validation", "Les mots de passe ne correspondent pas.", Alert.AlertType.ERROR);
            return false;
        }

        // Vérifier si l'un des rôles (étudiant ou enseignant) est sélectionné
        if (!signup_student.isSelected() && !signup_teacher.isSelected()) {
            showAlert("Erreur de Validation", "Veuillez sélectionner un rôle (Étudiant ou Enseignant).", Alert.AlertType.ERROR);
            return false;
        }

        if (signup_student.isSelected() && signup_teacher.isSelected()) {
            showAlert("Erreur de Validation", "Veuillez sélectionner un Seul rôle (Étudiant ou Enseignant).", Alert.AlertType.ERROR);
            return false;
        }

        // Vérifier si l'un des genres (masculin ou féminin) est sélectionné
        if (!signup_male.isSelected() && !signup_female.isSelected()) {
            showAlert("Erreur de Validation", "Veuillez sélectionner un genre.", Alert.AlertType.ERROR);
            return false;
        }

        if (signup_male.isSelected() && signup_female.isSelected()) {
            showAlert("Erreur de Validation", "Veuillez sélectionner un Seul genre.", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }




    public void backtologin(ActionEvent actionEvent) {
        login_forum.toFront();
    }



}
