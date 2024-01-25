package com.example.quizapp.model;

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
    private int questionMark;
    private String correctChoice;

    // Other fields and methods remain the same...

    // Constructors
    public Question(int questionId, int quizId, String text, String image, Timestamp createdAt, Timestamp updatedAt,
                    String firstChoice, String secondChoice, String thirdChoice, String fourthChoice, String fifthChoice,
                    int questionMark, String correctChoice) {
        this.questionId = questionId;
        this.quizId = quizId;
        this.text = text;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.firstChoice = firstChoice;
        this.secondChoice = secondChoice;
        this.thirdChoice = thirdChoice;
        this.fourthChoice = fourthChoice;
        this.fifthChoice = fifthChoice;
        this.questionMark = questionMark;
        this.correctChoice = correctChoice;
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

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFirstChoice() {
        return firstChoice;
    }

    public void setFirstChoice(String firstChoice) {
        this.firstChoice = firstChoice;
    }

    public String getSecondChoice() {
        return secondChoice;
    }

    public void setSecondChoice(String secondChoice) {
        this.secondChoice = secondChoice;
    }

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

    public int getQuestionMark() {
        return questionMark;
    }

    public void setQuestionMark(int questionMark) {
        this.questionMark = questionMark;
    }

    public String getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(String correctChoice) {
        this.correctChoice = correctChoice;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
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
}
