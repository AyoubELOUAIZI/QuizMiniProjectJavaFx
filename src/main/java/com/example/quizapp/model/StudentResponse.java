package com.example.quizapp.model;

import java.util.Date;

public class StudentResponse {
    private int userId;
    private int quizId;  // Added quizId field
    private int questionId;
    private char chosenResponse;  // Updated field name
    private Date createdAt;

    // Constructors
    public StudentResponse(int userId, int quizId, int questionId, char chosenResponse, Date createdAt) {
        this.userId = userId;
        this.quizId = quizId;
        this.questionId = questionId;
        this.chosenResponse = chosenResponse;
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public char getChosenResponse() {
        return chosenResponse;
    }

    public void setChosenResponse(char chosenResponse) {
        this.chosenResponse = chosenResponse;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}