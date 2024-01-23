package com.example.quizapp.controller;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//import application.Navigation;
//import application.entities.AccountType;
//import application.entities.StudentInformations;
//import application.services.AuthentificationService;
//import application.services.CommonService;

import com.example.quizapp.dao.UserDAO;
import com.example.quizapp.model.Student;
import com.example.quizapp.model.Teacher;
import com.example.quizapp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class AuthenticationController {

    @FXML
    private Button suivantbtn1, suivantbtn2, suivantbtn3, login_btn;

    @FXML
    private Button starting_signup, backtologin1, backtologin2, backtologin3;

    @FXML
    private Pane login_forum, signup1, signup2, signup3;

    @FXML
    private ChoiceBox<String> bactype_choice, bacville_choice, bacyear_choice, studentcity_choice;

    @FXML
    private DatePicker signup_birthday;

    @FXML
    private TextField signup_cne, signup_confirm_password, signup_lastname, signup_name, signup_telephon, signup_password, sign_email;

    @FXML
    private RadioButton signup_female, signup_male;

    @FXML
    private TextField email_login;

    @FXML
    private PasswordField password_login;

    public void handleLogin() {
       // Stage stage = (Stage) login_btn.getScene().getWindow();

    //    String email = email_login.getText();
     //   String password = password_login.getText();
//        AccountType userType = AuthentificationService.isLoginValid(email, password);
//       if (userType != AccountType.NotFound) {
//            // Login succeeded
//            Navigation navigation = new Navigation();
//            navigation.toAfterLogin(stage,userType);
//        } else {
//            // Login failed
//            Alert alert = new Alert(AlertType.ERROR);
//            alert.setTitle("Login Failed");
//            alert.setHeaderText("Invalid email or password");
//            alert.setContentText("Please try again.");
//            alert.showAndWait();
//        }

        //-----------------implementation of the login-------------------------
        String email = email_login.getText();
        String password = password_login.getText();

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.loginUser(email, password);

            if (user != null) {
                // Successfully logged in
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

//    StudentInformations signedstudent= new StudentInformations();
    ToggleGroup toggleGroup = new ToggleGroup();
    @FXML
    void suivantbtn1(ActionEvent event) {
        signup2.toFront();
        if(signup_cne.getText().length()==0 || signup_lastname.getText().length()==0 || signup_name.getText().length()==0
                || studentcity_choice.getSelectionModel().isEmpty() || signup_birthday.getValue() == null
                || !signup_female.isSelected() && !signup_male.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez d'abord remplir les conditions");
            alert.showAndWait();
        }
        else {
            LocalDate selectedDate =  signup_birthday.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = selectedDate.format(formatter);
//            signedstudent.setDateNaissance(formattedDate);
//            signedstudent.setCity(studentcity_choice.getValue());
//            signedstudent.setCne(signup_cne.getText());
//            signedstudent.setFirstName(signup_name.getText());
//            signedstudent.setLastName(signup_lastname.getText());
//            if(((Labeled) toggleGroup.getSelectedToggle()).getText().equals("Fémenin"))
//                signedstudent.setSexe("f");
//            else if(((Labeled) toggleGroup.getSelectedToggle()).getText().equals("Masculin"))
//                signedstudent.setSexe("m");

            signup2.toFront();
        }
    }
    @FXML
    void suivantbtn2(ActionEvent event) {
        signup3.toFront();
        if(bactype_choice.getSelectionModel().isEmpty() || bacville_choice.getSelectionModel().isEmpty() || bacyear_choice.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez d'abord remplir les conditions");
            alert.showAndWait();
        }
        else {
            signup3.toFront();
//            signedstudent.setBacYear(bacyear_choice.getValue());
//            signedstudent.setBac(bactype_choice.getValue());
//            signedstudent.setBacCity(bacville_choice.getValue());
            signup3.toFront();
        }
    }
    @FXML
    void suivantbtn3(ActionEvent event) {
        if(signup_confirm_password.getText().length()==0 || signup_password.getText().length()==0
                || signup_telephon.getText().length()==0 || sign_email.getText().length()==0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill the requirements first");
            alert.showAndWait();
        }
        else if(!signup_confirm_password.getText().equals(signup_password.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Mots de passe incompatibles");
            alert.showAndWait();
        }
        else {
//            signedstudent.setEmail(sign_email.getText());
//            signedstudent.setPassword(signup_password.getText());
//            signedstudent.setTelephone(signup_telephon.getText());
//            AuthentificationService.signUp(signedstudent);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Opération réussie");
            alert.setHeaderText(null);
//            alert.setContentText("Se connectez-vous avec vos informations \n"+"Email : "+ signedstudent.getEmail()+"\n Mot de pass :"+ signedstudent.getPassword());
            alert.showAndWait();
            login_forum.toFront();
        }

    }

    @FXML
    void backtologin(ActionEvent event) {
        login_forum.toFront();
    }
    @FXML
    void handleSexe(ActionEvent event) {
        signup_female.setToggleGroup(toggleGroup);
        signup_male.setToggleGroup(toggleGroup);
    }
}
