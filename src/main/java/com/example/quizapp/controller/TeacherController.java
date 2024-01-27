// TeacherController.java
package com.example.quizapp.controller;

import com.example.quizapp.UserSession;
import com.example.quizapp.dao.*;
import com.example.quizapp.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.print.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TeacherController {
    public ImageView profileImageView;
    public Label tUserEmail;
    public TextField tfNewEmail;
    public TextField tfConfirmEmail;
    public TextField tfNewPassword;
    public TextField tfConfirmNewPassword;
    public TextField tfcurrentPassword;
    public TextField tfExpression;
    public TextField tfPasswordCurrent;
    public Label tUserName;
    public TextField tfNewlastName;
    public TextField tfNewFirstName;
    public Pane panelShowSetting;
    private TeacherDAO teacherDAO;
    private UserDAO userDAO;


    public TeacherController() {

        this.teacherDAO = new TeacherDAO();
        this.userDAO = new UserDAO();
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
    @FXML
    private HBox quizzesHBox;
    @FXML
    private ListView<String> quizzesListView;
    @FXML
    private Pane panelQuizSelected;
    private List<Quiz> teacherQuizzes;
    private User currentUser;
    private Quiz selectedQuiz;
    @FXML
    private Pane panelAddNewQuiz;
    @FXML
    private Pane panelNoQuizSelected;
    @FXML
    private Label textQuizName;
    @FXML
    private Label textQuizPassword;
    @FXML
    private Label textDuration;
    @FXML
    private Label textDate;
    @FXML
    private TextField quizDurationField;
    @FXML
    private DatePicker quizDateField;
    @FXML
    private TextField quizPasswordField;
    @FXML
    private TextField quizNameField;
    @FXML
    private TextField hourFieldQuiz;
    @FXML
    private TextField minuteFieldQuiz;
    @FXML
    private Pane viewUpdateQuiz;
    @FXML
    private TextField inputQuestion;
    @FXML
    private TextField inputChoiceA;
    @FXML
    private TextField inputChoiceB;
    @FXML
    private TextField inputChoiceC;
    @FXML
    private TextField inputChoiceD;
    @FXML
    private TextField inputChoiceE;
    @FXML
    private RadioButton radioA;
    @FXML
    private RadioButton radioB;
    @FXML
    private RadioButton radioC;
    @FXML
    private RadioButton radioD;
    @FXML
    private RadioButton radioE;
    @FXML
    private TextField inputNote;
    @FXML
    private ToggleGroup rightChoice;


    @FXML
    private TableView<Question> tableViewQuestions;

    @FXML
    private TableColumn<Question, String> questionColumn;

    @FXML
    private TableColumn<Question, Integer> markColumn;
    @FXML
    private TableColumn<Question, Integer> idColumn;
    private Question selectedQuestionGlobal;
    @FXML
    private TextField quizNameInput;
    @FXML
    private TextField quizPasswordInput;
    @FXML
    private TextField quizDurationInput;
    @FXML
    private DatePicker quizDateInput;
    @FXML
    private TextField quizHourInput;
    @FXML
    private TextField quizMinuteInput;
    /////////////////
    @FXML
    private Pane panelQuizResult;
    @FXML
    private Label quizDateLabel;
    @FXML
    private Text nameProfResultat;
    @FXML
    private Text nameQuizResultat;

    private QuizDAO quizDAO = new QuizDAO();
    /////////////////////////////////////////
    @FXML
    private TableView<Result> tableViewResult;

    @FXML
    private TableColumn<Result, String> columnNom;

    @FXML
    private TableColumn<Result, String> columnPrenom;
    @FXML
    private TableColumn<Result, Double> columnNote;
    //////////////////////////////////////
    @FXML
    private Button printBtn;
    @FXML
    private Pane panelResultToPrint;

    private ObservableList<Question> questionsList = FXCollections.observableArrayList();

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
            clearLoginState();

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

    private void clearLoginState() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("userLogin.txt"))) {
            writer.write(""); // Clear the file content
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // You can have more handlers for other buttons and actions

    // Initialize method if needed
    @FXML
    public void initialize() {
        printBtn.setOnAction(event -> printPanel(panelResultToPrint));
        // Initialization logic here, like setting user details
        currentUser = UserSession.getCurrentUser();

        if (currentUser != null) {
            //load the Teacher Quizzes
            loadTeacherQuizzes();
            fullname_toshow.setText(currentUser.getFullName());
            email_toshow.setText(currentUser.getEmail());
            tUserEmail.setText(currentUser.getEmail());
            // ... Set other user details

            if ("female".equals(currentUser.getSexe())) {
                // Set the profile image to the female version
                profileImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/quizapp/images/teacherFemale.png")));
            } else {
                // Set the profile image to the male version
                profileImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/quizapp/images/teacherMale.png")));
            }

            // Listen for selection changes in the quizzesListView
            quizzesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    panelQuizSelected.toFront();
                    handleSelectedQuizChanged((String) newValue); // Call your method with the selected quiz name
                }
            });

        }

    }


    private void loadTeacherQuizzes() {
        teacherQuizzes = retrieveTeacherQuizzes(currentUser.getUserId());

        // Display quizzes in the ListView
        displayQuizzesInListView(teacherQuizzes);
    }

    private void displayQuizzesInListView(List<Quiz> quizzes) {
        // Clear existing items in the ListView
        quizzesListView.getItems().clear();

        // Add quiz names to the ListView
        for (Quiz quiz : quizzes) {
            quizzesListView.getItems().add(quiz.getQuizName());
        }

    }

    private void handleSelectedQuizChanged(String quizName) {
        // Retrieve the selected quiz details from the list of studentQuizzes
        selectedQuiz = findQuizByName(quizName);
        // Check if the selectedQuiz is not null (for safety)
        if (selectedQuiz != null) {
            //select data from database if you need
            // Update the content of the AnchorPane with the selected quiz details
            updateAnchorPaneContent(selectedQuiz);
        }
    }

    private void updateAnchorPaneContent(Quiz selectedQuiz) {
        textQuizName.setText(selectedQuiz.getQuizName());
        textQuizPassword.setText(selectedQuiz.getPasswordQuiz());
        textDuration.setText(String.valueOf(selectedQuiz.getDuration()));
        // Assuming getStartAt() returns a Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedStartDate = dateFormat.format(selectedQuiz.getStartAt());
        textDate.setText(formattedStartDate);
    }


    public List<Quiz> retrieveTeacherQuizzes(int teacherId) {
        return teacherDAO.getQuizzesForUser(teacherId);
    }

    private Quiz findQuizByName(String quizName) {
        for (Quiz quiz : teacherQuizzes) {
            if (quiz.getQuizName().equals(quizName)) {
                return quiz;
            }
        }
        return null; // Quiz not found, handle this case as needed
    }

    public Teacher retrieveSelectedQuizTeacher(int teacherId) {
        return teacherDAO.getQuizTeacherByTeacherId(teacherId);
    }


    public void handleAddNewQuiz(ActionEvent actionEvent) {
        panelAddNewQuiz.toFront();
    }


    public void handleDeleteQuiz() {
        // Create a confirmation dialog with OK and Cancel buttons
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Dialog de Confirmation");
        alert.setHeaderText("Supprimer le Quiz");
        alert.setContentText("Attention : Si vous supprimez ce quiz, vous ne pourrez plus y accéder");

        // Set custom button configuration
        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        // Show the confirmation dialog and wait for the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeOK) {
                QuizDAO quizDAO = new QuizDAO();
                // User clicked OK, proceed with the delete operation
                // teacherDAO.deleteStudentQuiz(currentUser.getUserId(), selectedQuiz.getQuizId());
                quizDAO.deleteQuiz(selectedQuiz.getQuizId());
                // Retrieve the Quizzes again to refresh the data
                // Show the "No Quiz Selected" panel
                panelNoQuizSelected.toFront();
                loadTeacherQuizzes();

            }
            // If the user clicked Cancel or closed the dialog, do nothing
        });
    }


    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();
    }

    public void saveQuizTeacher() {
        String quizName = quizNameField.getText().trim();
        String quizPassword = quizPasswordField.getText().trim();
        String durationText = quizDurationField.getText().trim();
        String hourQuiz = hourFieldQuiz.getText();
        String minuteQuiz = minuteFieldQuiz.getText();


        // Validate quiz name
        if (quizName.isEmpty()) {
            showWarning("Le nom du quiz ne peut pas être vide.");
            return;
        }

        // Validate password
        if (quizPassword.isEmpty()) {
            showWarning("Le mot de passe ne peut pas être vide.");
            return;
        }

        // Validate duration
        if (durationText.isEmpty()) {
            showWarning("La durée ne peut pas être vide.");
            return;
        }

        int duration;
        try {
            duration = Integer.parseInt(durationText);
        } catch (NumberFormatException e) {
            showWarning("Format de durée invalide. Veuillez entrer un nombre valide.");
            return;
        }
        int selectedHour, selectedMinute;
        try {
            selectedHour = Integer.parseInt(hourQuiz);
        } catch (NumberFormatException e) {
            showWarning("Format d'heure invalide! Veuillez entrer un nombre valide.");
            return;
        }
        try {
            selectedMinute = Integer.parseInt(minuteQuiz);
        } catch (NumberFormatException e) {
            showWarning("Format de minute invalide ! Veuillez entrer un nombre valide.");
            return;
        }
        // Validate selected hour
        if (selectedHour < 0 || selectedHour > 23) {
            showWarning("Veuillez sélectionner une heure valide (1-23).");
            return;
        }

        // Validate selected minute
        if (selectedMinute < 0 || selectedMinute > 59) {
            showWarning("Veuillez sélectionner une minute valide (0-59).");
            return;
        }
        // Get the selected value from the DatePicker
        LocalDate selectedDate = quizDateField.getValue();

        // Check if a date is selected
        if (selectedDate == null) {
            // Handle the case where no date is selected
            showWarning("La date est invalide !!");
            return;
        }

        // Combine date and time into LocalDateTime
        LocalDateTime combinedDateTime = LocalDateTime.of(selectedDate, LocalTime.of(selectedHour, selectedMinute));

        // Convert LocalDateTime to Timestamp (java.sql.Timestamp)
        Timestamp timestamp = Timestamp.valueOf(combinedDateTime);

        // Create Quiz object
        Quiz quiz = new Quiz(0, currentUser.getUserId(), quizName, null, null, timestamp, duration, quizPassword);

        // Save the quiz

        quizDAO.createQuiz(quiz);
        loadTeacherQuizzes();

        // Show success message
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Succès");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Quiz ajouté avec succès.");

        ButtonType okButton = new ButtonType("OK");
        successAlert.getButtonTypes().setAll(okButton);

        successAlert.showAndWait();

        // and update UI
        panelNoQuizSelected.toFront();
    }

    public void cancelQuizAdding() {
        panelNoQuizSelected.toFront();
    }

    public void displayQuestions(int quizId) {

        // Assume you have a method to retrieve questions, replace it with your logic
        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questionss = questionDAO.retrieveSelectedQuizQuestions(quizId);

        tableViewQuestions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // For single selection
// Initialize the columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("questionId"));
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        markColumn.setCellValueFactory(new PropertyValueFactory<>("questionMarkV"));

        // Example data
        ObservableList<Question> questions = FXCollections.observableArrayList(
                questionss

        );

        // Set items in the TableView
        tableViewQuestions.setItems(questions);
    }

    public void updateQcmLabelClicked() {
        viewUpdateQuiz.toFront();
        fillQuizInfoInputs();
        displayQuestions(selectedQuiz.getQuizId());
        if (tableViewQuestions != null) {
            tableViewQuestions.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    Question selectedQuestion = tableViewQuestions.getSelectionModel().getSelectedItem();
                    //You can now use selectedQuestion object to get details about the selected question
                    fillInputsQuestion(selectedQuestion);
                    selectedQuestionGlobal = selectedQuestion;
                }
            });
        }


    }

    private void fillInputsQuestion(Question question) {
        inputQuestion.setText(question.getText());
        inputChoiceA.setText(question.getFirstChoice());
        inputChoiceB.setText(question.getSecondChoice());
        inputChoiceC.setText(question.getThirdChoice());
        inputChoiceD.setText(question.getFourthChoice());
        inputChoiceE.setText(question.getFifthChoice());
        inputNote.setText(String.valueOf(question.getQuestionMarkV()));
        switch (question.getCorrectChoice()) {
            case "1":
                radioA.setSelected(true);
                break;
            case "2":
                radioB.setSelected(true);
                break;
            case "3":
                radioC.setSelected(true);
                break;
            case "4":
                radioD.setSelected(true);
                break;
            case "5":
                radioE.setSelected(true);
                break;
        }

    }


    public void AddNewQuestionToQuiz() {
        int quizId = selectedQuiz != null ? selectedQuiz.getQuizId() : -1; // Ensure selectedQuiz is not null
        String quest = inputQuestion.getText();
        String A = inputChoiceA.getText();
        String B = inputChoiceB.getText();
        String C = inputChoiceC.getText();
        String D = inputChoiceD.getText();
        String E = inputChoiceE.getText();
        String NStr = inputNote.getText();
        int N;
        // Check if the strings are not empty
        if (quest.isEmpty() || A.isEmpty() || B.isEmpty()) {
            showWarning("Veuillez remplir la question et minimum 2 choix .");
            return; // Exit the method to prevent further processing
        }

        try {
            N = Integer.parseInt(NStr);
        } catch (NumberFormatException e) {
            showWarning("La note doit être un nombre entier.");
            return; // Exit the method if the note is not a valid integer
        }
        // Ensure a correct answer is selected
        if (rightChoice.getSelectedToggle() == null) {
            showWarning("Veuillez choisir le choix correct.");
            return;
        }

        RadioButton selectedRadioButton = (RadioButton) rightChoice.getSelectedToggle();
        String correctAnswer = getCorrectAnswer(selectedRadioButton);


        // Now create the Question object and persist it
        Question question = new Question(0, quizId, quest, null, null, null, A, B, C, D, E, N, correctAnswer);
        QuestionDAO questionDAO = new QuestionDAO();
        questionDAO.createQuestion(question);
        // Show success message
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Succès");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Question ajouté avec succès.");
        ButtonType okButton = new ButtonType("OK");
        successAlert.getButtonTypes().setAll(okButton);

        successAlert.showAndWait();

        // Update the UI or perform any other necessary actions
        displayQuestions(selectedQuiz.getQuizId());
    }

    private String getCorrectAnswer(RadioButton selectedRadioButton) {
        switch (selectedRadioButton.getId()) {
            case "radioA":
                return "1";
            case "radioB":
                return "2";
            case "radioC":
                return "3";
            case "radioD":
                return "4";
            case "radioE":
                return "5";
            default:
                return null; // Consider handling this case
        }
    }

    public void deleteQuestion() {
        if (selectedQuestionGlobal == null) {
            showWarning("selectioner la question à supprimé");
        } else {
            QuestionDAO questionDAO = new QuestionDAO();
            questionDAO.deleteQuestion(selectedQuestionGlobal.getQuestionId());
            displayQuestions(selectedQuiz.getQuizId());
        }

    }

    public void updateQuestion() {
        int quizId = selectedQuiz != null ? selectedQuiz.getQuizId() : -1; // Ensure selectedQuiz is not null
        String quest = inputQuestion.getText();
        String A = inputChoiceA.getText();
        String B = inputChoiceB.getText();
        String C = inputChoiceC.getText();
        String D = inputChoiceD.getText();
        String E = inputChoiceE.getText();
        String NStr = inputNote.getText();
        int N;
        if (selectedQuestionGlobal == null) {
            showWarning("selectioner une question à modifier");
            return;

        }
        // Check if the strings are not empty
        if (quest.isEmpty() || A.isEmpty() || B.isEmpty()) {
            showWarning("Veuillez remplir la question et minimum 2 choix .");
            return; // Exit the method to prevent further processing
        }

        try {
            N = Integer.parseInt(NStr);
        } catch (NumberFormatException e) {
            showWarning("La note doit être un nombre entier.");
            return; // Exit the method if the note is not a valid integer
        }
        // Ensure a correct answer is selected
        if (rightChoice.getSelectedToggle() == null) {
            showWarning("Veuillez choisir le choix correct.");
            return;
        }
        RadioButton selectedRadioButton = (RadioButton) rightChoice.getSelectedToggle();
        String correctAnswer = getCorrectAnswer(selectedRadioButton);
        Question question = new Question(selectedQuestionGlobal.getIdQuestion(), quizId, quest, null, null, null, A, B, C, D, E, N, correctAnswer);
        QuestionDAO questionDAO = new QuestionDAO();
        boolean modified = questionDAO.updateQuestion(question);
        if (modified) {
            // Show success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Question modifier avec succès.");
            ButtonType okButton = new ButtonType("OK");
            successAlert.getButtonTypes().setAll(okButton);
            successAlert.showAndWait();

            // Update the UI or perform any other necessary actions
            displayQuestions(selectedQuiz.getQuizId());
        } else {
            showWarning("on ne peut pas la modifier");
        }


    }

    public void fillQuizInfoInputs() {
        quizNameInput.setText(selectedQuiz.getQuizName());
        quizPasswordInput.setText(selectedQuiz.getPasswordQuiz());
        quizDurationInput.setText(String.valueOf(selectedQuiz.getDuration()));
        Date date = selectedQuiz.getStartAt();
        // Create a Calendar instance and set it to the given Date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Extract the parts
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        // Convert Date to LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        quizDateInput.setValue(localDate);
        quizHourInput.setText(String.valueOf(hour));
        quizMinuteInput.setText(String.valueOf(minute));


    }

    public void updateQuizInfo() {
        int idQuiz = selectedQuiz.getQuizId();
        String quizName = quizNameInput.getText();
        String quizPassword = quizPasswordInput.getText();
        String hour = quizHourInput.getText();
        String minute = quizMinuteInput.getText();
        String durationText = quizDurationInput.getText().trim();


        // Validate quiz name
        if (Objects.equals(quizName, "")) {
            showWarning("Le nom du quiz ne peut pas être vide.");
            return;
        }

        // Validate password
        if (quizPassword.equals("")) {
            showWarning("Le mot de passe ne peut pas être vide.");
            return;
        }

        // Validate duration
        if (durationText.isEmpty()) {
            showWarning("La durée ne peut pas être vide.");
            return;
        }

        int duration;
        try {
            duration = Integer.parseInt(durationText);
        } catch (NumberFormatException e) {
            showWarning("Format de durée invalide. Veuillez entrer un nombre valide.");
            return;
        }
        int selectedHour, selectedMinute;
        try {
            selectedHour = Integer.parseInt(hour);
        } catch (NumberFormatException e) {
            showWarning("Format d'heure invalide! Veuillez entrer un nombre valide.");
            return;
        }
        try {
            selectedMinute = Integer.parseInt(minute);
        } catch (NumberFormatException e) {
            showWarning("Format de minute invalide ! Veuillez entrer un nombre valide.");
            return;
        }
        // Validate selected hour
        if (selectedHour < 1 || selectedHour > 23) {
            showWarning("Veuillez sélectionner une heure valide (1-23).");
            return;
        }

        // Validate selected minute
        if (selectedMinute < 0 || selectedMinute > 59) {
            showWarning("Veuillez sélectionner une minute valide (0-59).");
            return;
        }

        // Combine date and time into LocalDateTime
        LocalDateTime combinedDateTime = LocalDateTime.of(quizDateInput.getValue(), LocalTime.of(selectedHour, selectedMinute));

        // Convert LocalDateTime to Timestamp (java.sql.Timestamp)
        Timestamp timestamp = Timestamp.valueOf(combinedDateTime);
        Date currentDate = new Date();

        Quiz quiz = new Quiz(idQuiz, currentUser.getUserId(), quizName, null, currentDate, timestamp, duration, quizPassword);

        // Assuming QuizDAO and loadTeacherQuizzes() methods handle exceptions appropriately
        QuizDAO quizDAO = new QuizDAO();
        quizDAO.updateQuiz(quiz);
        loadTeacherQuizzes();

    }

    public void PanelQuizSelectedTofront() {
        panelQuizSelected.toFront();
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
            tUserEmail.setText(newEmail);

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
            Stage stage = (Stage) teacher_logout.getScene().getWindow(); // replace 'someButton' with any @FXML injected control in your controller

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


    private void showErrorMessage(String message) {
        // Implement this method to show an error message to the user (you can use an Alert)
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showResultSelectedQuiz() {
        if (selectedQuiz != null) {
            panelQuizResult.toFront();
            nameProfResultat.setText(currentUser.getFullName());
            nameQuizResultat.setText(selectedQuiz.getQuizName());
            // Get the Date object from selectedQuiz.getStartAt()
            Date startDate = selectedQuiz.getStartAt();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = dateFormat.format(startDate);
            quizDateLabel.setText(formattedDate);
            // showTableResult(selectedQuiz.getQuizId());
            System.out.println("Quiz selected id : " + selectedQuiz.getQuizId());
            showTableResult(selectedQuiz.getQuizId());

        }


    }

    public void showTableResult(int quizId) {
        tableViewResult.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // For single selection

        // Assume you have a method to retrieve questions, replace it with your logic
        QuizResultDAO quizResultDAO = new QuizResultDAO();
        List<Result> resultsS = quizResultDAO.getQuizResultByQuizIdStudentId(quizId);
        System.out.println(" resultss is empty : " + resultsS.isEmpty());
// Initialize the columns
        columnNom.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        columnPrenom.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        columnNote.setCellValueFactory(new PropertyValueFactory<>("mark"));
        // Set the placeholder text when the TableView is empty
        Label placeholderLabel = new Label("Aucun étudiant n'a passé le quiz");
        tableViewResult.setPlaceholder(placeholderLabel);

        // Example data
        ObservableList<Result> results = FXCollections.observableArrayList(
                resultsS

        );

        // Set items in the TableView
        tableViewResult.setItems(results);

    }

    /* private void printPanel(Pane panel) {
         PrinterJob job = PrinterJob.createPrinterJob();
         if (job != null) {
             boolean success = job.showPrintDialog(panelResultToPrint.getScene().getWindow());
             if (success) {
                 PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
                 job.getJobSettings().setPageLayout(pageLayout);
                 boolean printed = job.printPage(panelResultToPrint);
                 if (printed) {
                     job.endJob();
                 }
             }
         }

     }
     */
    private void printPanel(Pane panel) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.showPrintDialog(panelResultToPrint.getScene().getWindow());

            if (success) {
                PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

                // Calculate the translation to center the content
                double translateX = (pageLayout.getPrintableWidth() - panelResultToPrint.getBoundsInParent().getWidth())-50 ;

                // Apply the translation to the Pane
                panelResultToPrint.setTranslateX(translateX);


                boolean printed = job.printPage(panelResultToPrint);
                if (printed) {
                    job.endJob();
                }

                // Reset the translation after printing
                panelResultToPrint.setTranslateX(0);
                panelResultToPrint.setTranslateY(0);
            }
        }
    }

}
