// TeacherController.java
package com.example.quizapp.controller;

import com.example.quizapp.UserSession;
import com.example.quizapp.dao.QuestionDAO;
import com.example.quizapp.dao.QuizDAO;
import com.example.quizapp.dao.TeacherDAO;
import com.example.quizapp.dao.UserDAO;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Teacher;
import com.example.quizapp.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
    @FXML
    private HBox quizzesHBox;
    @FXML
      private ListView<String> quizzesListView;
    @FXML
    private Pane panelQuizSelected;
    private List<Quiz> teacherQuizzes;
    private User currentUser;
    private  Quiz selectedQuiz;
    @FXML
      private  Pane panelAddNewQuiz;
    @FXML
       private Pane panelNoQuizSelected;
    @FXML
     private Label textQuizName;
    @FXML
      private  Label textQuizPassword;
    @FXML
       private Label textDuration;
    @FXML
     private Label textDate;
    @FXML
    private TextField quizDurationField;
    @FXML
    private  DatePicker quizDateField;
    @FXML
    private  TextField quizPasswordField;
    @FXML
    private  TextField quizNameField;
    @FXML
      private  TextField hourFieldQuiz ;
    @FXML
      private  TextField minuteFieldQuiz;
    @FXML
     private  Pane viewUpdateQuiz;
    @FXML
     private  TextField inputQuestion ;
    @FXML
    private    TextField inputChoiceA;
    @FXML
    private    TextField inputChoiceB;
    @FXML
    private    TextField inputChoiceC;
    @FXML
    private    TextField inputChoiceD;
    @FXML
    private    TextField inputChoiceE;
    @FXML
      private   RadioButton radioA;
    @FXML
    private   RadioButton radioB;
    @FXML
    private   RadioButton radioC;
    @FXML
    private   RadioButton radioD;
    @FXML
    private   RadioButton radioE;
    @FXML
    private TextField inputNote;
    @FXML
     private  ToggleGroup rightChoice;


    @FXML
    private TableView<Question> tableViewQuestions;

    @FXML
    private TableColumn<Question, String> questionColumn;

    @FXML
    private TableColumn<Question, Integer> markColumn;
    @FXML
    private TableColumn<Question, Integer> idColumn;
     private Question selectedQuestionGlobal ;
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
            stage.setScene(new Scene(root,900,700));
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
        // Initialization logic here, like setting user details
         currentUser = UserSession.getCurrentUser();

        if (currentUser != null) {
            //load the Teacher Quizzes
            loadTeacherQuizzes();
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
    private void handleSelectedQuizChanged(String  quizName) {
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



    public void handleDeleteQuiz(ActionEvent actionEvent) {
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
                loadTeacherQuizzes();

                // Show the "No Quiz Selected" panel
                panelNoQuizSelected.toFront();
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
        String   hourQuiz=hourFieldQuiz.getText();
        String   minuteQuiz=minuteFieldQuiz.getText();


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
           int selectedHour,selectedMinute;
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
        QuizDAO quizDAO = new QuizDAO();
        quizDAO.createQuiz(quiz);

        // Show success message
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Succès");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Quiz ajouté avec succès.");

        ButtonType okButton = new ButtonType("OK");
        successAlert.getButtonTypes().setAll(okButton);

        successAlert.showAndWait();

        // Reload quizzes and update UI
        loadTeacherQuizzes();
        panelNoQuizSelected.toFront();
    }
    public void cancelQuizAdding(){
        panelNoQuizSelected.toFront();
    }

    public void displayQuestions(int quizId) {
        tableViewQuestions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // For single selection

        // Assume you have a method to retrieve questions, replace it with your logic
        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questionss = questionDAO.retrieveSelectedQuizQuestions(quizId);

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
   public void  updateQcmLabelClicked(){
       viewUpdateQuiz.toFront();
       displayQuestions(selectedQuiz.getQuizId());
       fillQuizInfoInputs();

       tableViewQuestions.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
           if (newSelection != null) {
               Question selectedQuestion = tableViewQuestions.getSelectionModel().getSelectedItem();
               System.out.println("Selected Question: " + selectedQuestion.getIdQuestion());
               // You can now use selectedQuestion object to get details about the selected question
               fillInputsQuestion(selectedQuestion);
               selectedQuestionGlobal = selectedQuestion;
           }
       });

    }

    private void fillInputsQuestion(Question question) {
         inputQuestion.setText(question.getText());
         inputChoiceA.setText(question.getFirstChoice());
         inputChoiceB.setText(question.getSecondChoice());
         inputChoiceC.setText(question.getThirdChoice());
         inputChoiceD.setText(question.getFourthChoice());
         inputChoiceE.setText(question.getFifthChoice());
         inputNote.setText(String.valueOf(question.getQuestionMarkV()));
         switch (question.getCorrectChoice()){
             case "1": radioA.setSelected(true); break;
             case "2": radioB.setSelected(true); break;
             case "3": radioC.setSelected(true); break;
             case "4": radioD.setSelected(true); break;
             case "5": radioE.setSelected(true); break;
             default: showWarning("tous les choix incorrect");
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
     if (quest.isEmpty() || A.isEmpty() || B.isEmpty() ) {
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
            case "radioA": return "1";
            case "radioB": return "2";
            case "radioC": return "3";
            case "radioD": return "4";
            case "radioE": return "5";
            default: return null; // Consider handling this case
        }
    }
    public void deleteQuestion(){
     if(selectedQuestionGlobal==null){
           showWarning("selectioner la question à supprimé");
     }else{
         QuestionDAO questionDAO = new QuestionDAO();
         questionDAO.deleteQuestion(selectedQuestionGlobal.getQuestionId());
         displayQuestions(selectedQuiz.getQuizId());
     }

    }
    public void updateQuestion(){
        int quizId = selectedQuiz != null ? selectedQuiz.getQuizId() : -1; // Ensure selectedQuiz is not null
        String quest = inputQuestion.getText();
        String A = inputChoiceA.getText();
        String B = inputChoiceB.getText();
        String C = inputChoiceC.getText();
        String D = inputChoiceD.getText();
        String E = inputChoiceE.getText();
        String NStr = inputNote.getText();
        int N;
        if (selectedQuestionGlobal==null) {
            showWarning("selectioner une question à modifier");
            return;

        }
        // Check if the strings are not empty
        if (quest.isEmpty() || A.isEmpty() || B.isEmpty() ) {
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
          boolean  modified = questionDAO.updateQuestion(question);
          if(modified){
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
          }else{
              showWarning("on ne peut pas la modifier");
          }


    }

 public void fillQuizInfoInputs(){
     quizNameInput.setText(selectedQuiz.getQuizName());
     quizPasswordInput.setText(selectedQuiz.getPasswordQuiz());
     quizDurationInput.setText(String.valueOf(selectedQuiz.getDuration()));
     Date date = selectedQuiz.getStartAt();
     // Create a Calendar instance and set it to the given Date
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(date);
     // Extract the parts
    /* int year = calendar.get(Calendar.YEAR);
     int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
     int day = calendar.get(Calendar.DAY_OF_MONTH);*/
     int hour = calendar.get(Calendar.HOUR_OF_DAY);
     int minute = calendar.get(Calendar.MINUTE);
     // Convert Date to LocalDate
     LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
     quizDateInput.setValue(localDate);
     quizHourInput.setText(String.valueOf(hour));
     quizMinuteInput.setText(String.valueOf(minute));
     loadTeacherQuizzes();

 }
 public  void updateQuizInfo(){
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
     int selectedHour,selectedMinute;
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
     if (selectedHour < 1 || selectedHour > 23 ) {
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

    public  void PanelQuizSelectedTofront(){
      panelQuizSelected.toFront();
    }

}
