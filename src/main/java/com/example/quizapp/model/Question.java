package com.example.quizapp.model;

import java.sql.Timestamp;
import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class Question {
    private int questionId;
    private int quizId;
    private String text;
    private String image;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String firstChoice;
    private String secondChoice;
    private String thirdChoice;
    private String fourthChoice;
    private String fifthChoice;
    //private int questionMark;
    private String correctChoice;
    private int questionMarkV;



    // Other fields and methods remain the same...

    // Constructors
    public Question(int questionId, int quizId, String text, String image, Timestamp createdAt, Timestamp updatedAt,
                    String firstChoice, String secondChoice, String thirdChoice, String fourthChoice, String fifthChoice,
                    int questionMark, String correctChoice) {
        this.questionId = questionId;
        this.quizId = quizId;
        this.text = text;
        this.image = image;

        //this.questionMark = questionMark;

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.firstChoice = firstChoice;
        this.secondChoice = secondChoice;
        this.thirdChoice = thirdChoice;
        this.fourthChoice = fourthChoice;
        this.fifthChoice = fifthChoice;
        this.questionMarkV = questionMark;
        this.correctChoice = correctChoice;
    }
    // Constructor taking a ResultSet
    public Question(ResultSet resultSet) throws SQLException {
        this.questionId = resultSet.getInt("questionId");
        this.quizId = resultSet.getInt("quizId");
        this.text = resultSet.getString("text");
        this.image = resultSet.getString("image");
        this.createdAt = resultSet.getTimestamp("createdAt");
        this.updatedAt = resultSet.getTimestamp("updatedAt");
        this.firstChoice = resultSet.getString("firstChoice");
        this.secondChoice = resultSet.getString("secondChoice");
        this.thirdChoice = resultSet.getString("thirdChoice");
        this.fourthChoice = resultSet.getString("fourthChoice");
        this.fifthChoice = resultSet.getString("fifthChoice");
        this.questionMarkV = resultSet.getInt("questionMark");
        this.correctChoice = resultSet.getString("correctChoice");
    }
    public  Question (String questiontext,int mark){
        this.text = questiontext;
        this.questionMarkV=mark;
    }


    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public Timestamp getCreatedAt() {
        return createdAt;
    }


    public Timestamp getUpdatedAt() {
        return updatedAt;
    }



    public String getFirstChoice() {
        return firstChoice;
    }



    public String getSecondChoice() {
        return secondChoice;
    }

   /* public void setSecondChoice(String secondChoice) {
        this.secondChoice = secondChoice;
    } */

    public String getThirdChoice() {
        return thirdChoice;
    }

    public void setThirdChoice(String thirdChoice) {
        this.thirdChoice = thirdChoice;
    }

    public String getFourthChoice() {
        return fourthChoice;
    }

    public void setFourthChoice(String fourthChoice) {
        this.fourthChoice = fourthChoice;
    }

    public String getFifthChoice() {
        return fifthChoice;
    }

    public void setFifthChoice(String fifthChoice) {
        this.fifthChoice = fifthChoice;
    }


    public int getQuestionMarkV() {
        return questionMarkV;
    }
    public  int getIdQuestion(){
         return questionId;
    }


    public void setQuestionMark(int questionMark) {
        this.questionMarkV = questionMark;
    }
    public void setIdQuestion(int idQuestion) {
        this.questionId = idQuestion;
    }

    public String getCorrectChoice() {
        return correctChoice;
    }

    public void setFirstChoice(String firstChoice) {
        this.firstChoice = firstChoice;
    }
    public void setCorrectChoice(String correctChoice) {
        this.correctChoice = correctChoice;
    }


    @Override
    public String toString() {
        return "Question{" +
                "idQuestion=" + questionId +
                ", quizId=" + quizId +
                ", text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", firstChoice='" + firstChoice + '\'' +
                ", secondChoice='" + secondChoice + '\'' +
                ", thirdChoice='" + thirdChoice + '\'' +
                ", fourthChoice='" + fourthChoice + '\'' +
                ", fifthChoice='" + fifthChoice + '\'' +
                ", questionMark=" + questionMark +
                ", correctChoice='" + correctChoice + '\'' +
                '}';
    }
    private final SimpleStringProperty questionText = new SimpleStringProperty(this, "questionText");
    private final SimpleStringProperty questionMark = new SimpleStringProperty(this, "questionMark");
    private final SimpleStringProperty idQuestion = new SimpleStringProperty(this, "idQuestion");


    public Question(String questionText, String questionMark,int questionId) {
        this.questionText.set(questionText);
        this.questionMark.set(questionMark);
        this.idQuestion.set(String.valueOf(questionId));

    }


    public int getQuestionMark() {
        return questionMarkV;
    }


    public String getQuestionText() {
        return questionText.get();
    }

    public SimpleStringProperty questionTextProperty() {
        return questionText;
    }

   /* public String getQuestionMark() {
        return questionMark.get();
    }

    public SimpleStringProperty questionMarkProperty() {
        return questionMark;
    }
   public SimpleStringProperty getIdQuestion(){
         return idQuestion;
    }*/
}
