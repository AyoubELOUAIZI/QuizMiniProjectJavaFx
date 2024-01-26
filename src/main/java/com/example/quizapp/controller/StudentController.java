// StudentController.java
package com.example.quizapp.controller;

import com.example.quizapp.UserSession;
import com.example.quizapp.dao.QuestionDAO;
import com.example.quizapp.dao.StudentDAO;
import com.example.quizapp.dao.StudentResponseDAO;
import com.example.quizapp.dao.UserDAO;
import com.example.quizapp.model.*;
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
import java.util.Objects;
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
    public Label tquizStartDate;
    public Pane paneQuizTest5choice;
    public Pane paneQuestion3;
    public Pane paneQuestion4;
    public Pane paneQuestion5;
    public Pane paneQuestionHider;
    public Label tfp5Response1;
    public Label tfp5Response2;
    public Label tfp5Response3;
    public Label tfp5Response4;
    public Label tfp5Response5;
    public Label tfp5Question;
    public Label p5tQuizName;
    public Label p5QuestionIndex;
    public Label p2TotalNumberQuestions;
    public Button btnSuivant;
    public Label tcurrentQuestionMark;
    public Label ttotalNotes;
    public Pane panelQuizEnded;
    public RadioButton p5cm1;
    public RadioButton p5cm2;
    public RadioButton p5cm3;
    public RadioButton p5cm4;
    public RadioButton p5cm5;
    public Pane paneQuizResults;
    public Button btnSuivant1;
    public Label p5tQuizName1;
    public Label tfp5Question1;
    public Label tfp5Response11;
    public Label tfp5Response21;
    public Pane paneQuestion31;
    public Label tfp5Response31;
    public Pane paneQuestion41;
    public Label tfp5Response41;
    public Pane paneQuestion51;
    public Label tfp5Response51;
    public Pane paneQuestionHider1;
    public Label p5QuestionIndex1;
    public Label p2TotalNumberQuestions1;
    public Label tcurrentQuestionMark1;
    public Label ttotalNotes1;
    public Label tcurrentQuestionMark11;
    public Label ttotalNotes11;
    public Pane paneQuestion11;
    public Pane paneQuestion21;
    public Label tQuizStudentMark;
    private StudentDAO studentDAO;
    private UserDAO userDAO;
    private QuestionDAO questionDAO;
    private StudentResponseDAO studentResponseDAO;

    private User currentUser;

    private  Quiz selectedQuiz;

    // Add a list of questions
    private List<Question> currentQuizQuestions;
    private List<StudentResponse> currentQuizStudentResponses;
    private StudentResponse currentQuestionStudentResponses;

    private int currentQuestionIndex = -1; // Initialize with -1 to indicate no current question selected
    private Question currentQuestion;

    // No-argument constructor
    // Constructor to initialize StudentDAO
    public StudentController() {
        this.userDAO = new UserDAO();
        this.studentDAO = new StudentDAO();
        this.questionDAO = new QuestionDAO();
        this.studentResponseDAO = new StudentResponseDAO();
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
        tquizStartDate.setText(formattedStartDate);

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
        // Assuming you have the userId and selectedQuizId available

        // Check if the quiz has been tested
        boolean hasTested = studentDAO.checkIfQuizHasBeenTested(currentUser.getUserId(), selectedQuiz.getQuizId());
        System.out.println("🚩hasTested : "+hasTested);

        if (!hasTested) {
            // The quiz has not been tested yet
            panelStartQuizTest.toFront();
        } else {
            // The quiz has already been tested
            panelQuizEnded.toFront();
        }
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
        String expression = tfExpression.getText();
        String userPassword = tfPasswordCurrent.getText();

        // Validate data
        if (expression.isEmpty() || userPassword.isEmpty()) {
            // Show an error message to the user indicating that both expression and password are required
            showErrorMessage("Les champs de l'expression et du mot de passe sont requis.");
            return;
        }

        // Validate the expression to be "supprime mon compte" (case-insensitive)
        if (!expression.equalsIgnoreCase("supprime mon compte")) {
            // Show an error message indicating that the expression is incorrect
            showErrorMessage("L'expression est incorrecte. Veuillez entrer la bonne expression pour supprimer votre compte.");
            return;
        }

        // Compare the userPassword with User.getPassword() (replace 'currentUser' with the actual instance)
        if (!userPassword.equals(currentUser.getPassword())) {
            // Show an error message indicating that the password is incorrect
            showErrorMessage("Le mot de passe est incorrect. Veuillez entrer le mot de passe correct pour supprimer votre compte.");
            return;
        }

        // If all validations pass, proceed with deleting the user account
        boolean isUserDeleted = userDAO.deleteStudentUser(currentUser.getUserId());

        if (isUserDeleted) {
            // Optionally, you can show a success message to the user
            showSuccessMessage("Votre compte a été supprimé avec succès.");

            // Navigate to the login screen (you need to implement this based on your UI design)
            navigateToLoginScreen();
        } else {
            // If deletion fails, show an error message
            showErrorMessage("Échec de la suppression du compte. Veuillez réessayer.");
        }
    }

    // Helper method to navigate to the login screen
    private void navigateToLoginScreen() {
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

    public void handleStartQcmQuiz(ActionEvent actionEvent) {
        System.out.println("🦆🦆🦆the Student want to start the quiz");

        //start the quiz for the student
        //retrieve the quiz questions by the quizId and put them in a table
        currentQuizQuestions=questionDAO.retrieveSelectedQuizQuestions(selectedQuiz.getQuizId());
        System.out.println("current quiz questions :");
        System.out.println(currentQuizQuestions);


        if (!currentQuizQuestions.isEmpty()) {
            // If there are questions, set the currentQuestionIndex to the first question
            currentQuestionIndex = 0;
            paneQuizTest5choice.toFront();
            p5tQuizName.setText(selectedQuiz.getQuizName());
            p2TotalNumberQuestions.setText(String.valueOf(currentQuizQuestions.size()));
            ttotalNotes.setText(String.valueOf(calculateSomeMarks()));
            showCurrentQuestion();
        } else {
            System.out.println("No questions found for the selected quiz.");
        }

    }

    private int calculateSomeMarks() {
        int totalMarks = 0;

        for (Question question : currentQuizQuestions) {
            totalMarks += question.getQuestionMark();
        }

        return totalMarks;
    }

    public void handleBtnNextQuestionClicked(ActionEvent actionEvent) {
        //handle the Student previous response
        handleStudentResponse();

        // Check if there are more questions
        if (currentQuestionIndex < currentQuizQuestions.size() - 1) {
            // Move to the next question
            currentQuestionIndex++;
            showCurrentQuestion();
        }
        else{
            System.out.println("Invalid question index. Means Student finish all the questions");
            //go to an other panel
            System.out.println("💔💔congratulation");
            System.out.println("No more questions available.");
            System.out.println("🚓🚓🚗Lets go to an other view");
            panelQuizEnded.toFront();

            //also update the studentQuiz hasTested to true
            studentDAO.updateQuizTestedStatus(currentUser.getUserId(), selectedQuiz.getQuizId());


        }
    }




    private void handleStudentResponse() {
            // Retrieve the chosen response
            char chosenResponse = getChosenResponse();

            // Save the student's response to the database or a table
            saveStudentResponse(chosenResponse);

    }

    private char getChosenResponse() {
        // Implement this method to retrieve the chosen response based on the UI (e.g., radio buttons)
        if (p5cm1.isSelected()) {
            return '1';
        } else if (p5cm2.isSelected()) {
            return '2';
        } else if (p5cm3.isSelected()) {
            return '3';
        } else if (p5cm4.isSelected()) {
            return '4';
        } else if (p5cm5.isSelected()) {
            return '5';
        } else {
            // No response selected
            return '0';
        }
    }

    private void saveStudentResponse(char chosenResponse) {
        try {
            // Get the necessary information from the current question and the logged-in user
            int userId = currentUser.getUserId();  // Replace with the actual method to get the user ID
            int quizId = selectedQuiz.getQuizId(); // Replace with the actual method to get the quiz ID
            int questionId = currentQuestion.getQuestionId(); // Replace with the actual method to get the question ID

            // Use your StudentDAO or another appropriate DAO class to save the response
            boolean success = studentDAO.saveStudentResponse(userId, quizId, questionId, chosenResponse);

            if (success) {
                System.out.println("Student response "+currentQuestionIndex+" saved successfully.");
            } else {
                System.out.println("Failed to save student response : "+currentQuestionIndex);
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log or show an error message)
            e.printStackTrace();
            System.out.println("An error occurred while saving the student response.");
        }
    }


    private void showCurrentQuestion() {
        // Check if the currentQuestionIndex is valid
        if (currentQuestionIndex >= 0 && currentQuestionIndex < currentQuizQuestions.size()-1) {
            // Retrieve the current question
             currentQuestion = currentQuizQuestions.get(currentQuestionIndex);

            // Print the current question to the console
            System.out.println("Current Question: " + currentQuestion);

            displayCurrentQuestionInCorrectPanel();
        } else{
            //this means the last question
            // Retrieve the current question
            currentQuestion = currentQuizQuestions.get(currentQuestionIndex);
            // Print the current question to the console
            System.out.println("Current Question: " + currentQuestion);

            displayCurrentQuestionInCorrectPanel();

            //change the button to be finish
            btnSuivant.setText("Terminer");
        }
    }

    private void displayCurrentQuestionInCorrectPanel() {
        paneQuestionHider.toFront();
        //clear the check marks
        p5cm1.setSelected(false);
        p5cm2.setSelected(false);
        p5cm3.setSelected(false);
        p5cm4.setSelected(false);
        p5cm5.setSelected(false);
        //set text for the question
        tfp5Question.setText(currentQuestion.getText());
        p5QuestionIndex.setText(String.valueOf(currentQuestionIndex+1));
        tcurrentQuestionMark.setText(String.valueOf(currentQuestion.getQuestionMark()));


        //set text for the responses one and tow
        tfp5Response1.setText(currentQuestion.getFirstChoice());
        tfp5Response2.setText(currentQuestion.getSecondChoice());

        if(currentQuestion.getThirdChoice()!=null){
            //show the question in the paneQuizTest2choice
            paneQuestion3.toFront();
            tfp5Response3.setText(currentQuestion.getThirdChoice());

        }else{

        }
        if(currentQuestion.getFourthChoice()!=null){
            //show the question in the paneQuizTest3choice
            tfp5Response4.setText(currentQuestion.getFourthChoice());
            paneQuestion4.toFront();


        }else {
            return;
        }
        if(currentQuestion.getFifthChoice()!=null){
            //show the question in the paneQuizTest4choice
            tfp5Response5.setText(currentQuestion.getFifthChoice());
            paneQuestion5.toFront();

        }

    }
//-----------------------------------------Quiz Results-------------------------------------------------
        //private List<StudentResponse> currentQuizStudentResponses;
        //private StudentResponse currentQuestionStudentResponses;
    public void handleShowQuizResults(ActionEvent actionEvent) {
        System.out.println("the function handleShowQuizResults");
        System.out.println("🦆🚚🚚🚚the Student want to see his Quiz Results");

        //start the quiz for the student
        //retrieve the quiz questions by the quizId and put them in a table
        currentQuizQuestions=questionDAO.retrieveSelectedQuizQuestions(selectedQuiz.getQuizId());
        currentQuizStudentResponses=studentResponseDAO.retrieveSelectedQuizStudentResponses(selectedQuiz.getQuizId(),currentUser.getUserId());

        System.out.println("current quiz questions :");
        System.out.println(currentQuizQuestions);

        System.out.println("'''''''''''''''''''''current quiz student Responses :'''''''''''''''''''''");
        System.out.println(currentQuizStudentResponses);


        if (!currentQuizQuestions.isEmpty()) {
            // If there are questions, set the currentQuestionIndex to the first question
            currentQuestionIndex = 0;
            //show the panel of results
            paneQuizResults.toFront();

            p5tQuizName1.setText(selectedQuiz.getQuizName());
            p2TotalNumberQuestions1.setText(String.valueOf(currentQuizQuestions.size()));
            ttotalNotes1.setText(String.valueOf(calculateSomeMarks()));
            //no need for calculating again
            ttotalNotes11.setText(ttotalNotes1.getText());

            //set the Student Mark 19/20 now I need to define the function calculateCurrentQuizStudentMark correctly
            tQuizStudentMark.setText(String.valueOf(calculateCurrentQuizStudentMark()));
            showCurrentQuestionFoResults();
        } else {
            System.out.println("No questions found for the selected quiz.");
        }

    }

    private int calculateCurrentQuizStudentMark() {
        int totalMark = 0;

        // Iterate through each question in the current quiz
        for (Question question : currentQuizQuestions) {
            // Find the corresponding student response
            StudentResponse response = findCurrentQuestionStudentResponse(question.getQuestionId());

            if (response != null) {
                // Check if the student's response matches the correct answer
                if (String.valueOf(response.getChosenResponse()).equals(question.getCorrectChoice())) {
                    // Add the question's mark to the total mark
                    totalMark += question.getQuestionMark();
                }
            }
        }
        return totalMark;
    }

            private void showCurrentQuestionFoResults() {
        // Check if the currentQuestionIndex is valid
        if (currentQuestionIndex >= 0 && currentQuestionIndex < currentQuizQuestions.size()-1) {
            // Retrieve the current question
            currentQuestion = currentQuizQuestions.get(currentQuestionIndex);
            currentQuestionStudentResponses=findCurrentQuestionStudentResponse(currentQuestion.getQuestionId());

            // Print the current question to the console
            System.out.println("Current Question: " + currentQuestion);

            displayCurrentQuestionInCorrectPanelFoResults();
            changeBackgroundOfCorrectResponse();

        } else{
            //this means the last question
            // Retrieve the current question
            currentQuestion = currentQuizQuestions.get(currentQuestionIndex);
            // Print the current question to the console
            System.out.println("Current Question: " + currentQuestion);

            displayCurrentQuestionInCorrectPanelFoResults();
            changeBackgroundOfCorrectResponse();


            //change the button to be finish
            btnSuivant1.setText("Terminer");
        }
    }

    private StudentResponse findCurrentQuestionStudentResponse(int questionId) {
        // Iterate through all student responses for the quiz
        for (StudentResponse response : currentQuizStudentResponses) {
            // Check if the response belongs to the current question
            if (response.getQuestionId() == questionId) {
                System.out.println("👓👓👓we find the current question student response");
                return response;
            }
        }

        // Return null if no matching response is found for the current question
        return null;
    }


    private void displayCurrentQuestionInCorrectPanelFoResults() {
        paneQuestionHider1.toFront();
        //clear the check marks

        //set text for the question
        tfp5Question1.setText(currentQuestion.getText());
        p5QuestionIndex1.setText(String.valueOf(currentQuestionIndex+1));
        tcurrentQuestionMark1.setText(String.valueOf(currentQuestion.getQuestionMark()));


        //set text for the responses one and tow
        tfp5Response11.setText(currentQuestion.getFirstChoice());
        tfp5Response21.setText(currentQuestion.getSecondChoice());

        if(currentQuestion.getThirdChoice()!=null){
            //show the question in the paneQuizTest2choice
            paneQuestion31.toFront();
            tfp5Response31.setText(currentQuestion.getThirdChoice());

        }else{
            return;
        }
        if(currentQuestion.getFourthChoice()!=null){
            //show the question in the paneQuizTest3choice
            tfp5Response41.setText(currentQuestion.getFourthChoice());
            paneQuestion41.toFront();

        }else {
            return;
        }
        if(currentQuestion.getFifthChoice()!=null){
            //show the question in the paneQuizTest4choice
            tfp5Response51.setText(currentQuestion.getFifthChoice());
            paneQuestion51.toFront();
        }


    }

    private void changeBackgroundOfCorrectResponse() {
        // Reset all backgrounds to default
        paneQuestion11.setStyle("-fx-background-color: #dadada;");
        paneQuestion21.setStyle("-fx-background-color: #dadada;");
        paneQuestion31.setStyle("-fx-background-color: #dadada;");
        paneQuestion41.setStyle("-fx-background-color: #dadada;");
        paneQuestion51.setStyle("-fx-background-color: #dadada;");

        // Highlight the correct answer in blue
        switch (currentQuestion.getCorrectChoice()) {
            case "1":
                paneQuestion11.setStyle("-fx-background-color: #1E90FF;"); // Blue for correct
                break;
            case "2":
                paneQuestion21.setStyle("-fx-background-color: #1E90FF;"); // Blue for correct
                break;
            case "3":
                paneQuestion31.setStyle("-fx-background-color: #1E90FF;"); // Blue for correct
                break;
            case "4":
                paneQuestion41.setStyle("-fx-background-color: #1E90FF;"); // Blue for correct
                break;
            case "5":
                paneQuestion51.setStyle("-fx-background-color: #1E90FF;"); // Blue for correct
                break;
            default:
                break; // In case of an unexpected value
        }

        // Check the user's response and highlight incorrect ones in red
        if (currentQuestionStudentResponses != null) {
            String userResponse = String.valueOf(currentQuestionStudentResponses.getChosenResponse());
            // If user's response is not correct and not '0' (indicating a response was made)
            if (!userResponse.equals(currentQuestion.getCorrectChoice()) && !userResponse.equals("0")) {
                switch (userResponse) {
                    case "1":
                        paneQuestion11.setStyle("-fx-background-color: #FF6347;"); // Red for
                        break;
                    case "2":
                        paneQuestion21.setStyle("-fx-background-color: #FF6347;"); // Blue for correct
                        break;
                    case "3":
                        paneQuestion31.setStyle("-fx-background-color: #FF6347;"); // Blue for correct
                        break;
                    case "4":
                        paneQuestion41.setStyle("-fx-background-color: #FF6347;"); // Blue for correct
                        break;
                    case "5":
                        paneQuestion51.setStyle("-fx-background-color: #FF6347;"); // Blue for correct
                        break;
                    default:
                        break; // In case of an unexpected value
                }
            }
        }
    }


                        public void handleBtnNextQuestionFoResultsClicked(ActionEvent actionEvent) {

        // Check if there are more questions
        if (currentQuestionIndex < currentQuizQuestions.size() - 1) {
            // Move to the next question
            currentQuestionIndex++;
            showCurrentQuestionFoResults();
        }
        else{
            System.out.println("Invalid question index. Means Student finish all the questions");
            //go to another panel
            System.out.println("🔍🔍No more Results available.");
            panelQuizEnded.toFront();

        }
    }


}

