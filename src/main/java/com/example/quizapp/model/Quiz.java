package com.example.quizapp.model;

import java.util.Date;
import java.util.List;

public class Quiz {
    private int quizId;
    private int moduleId; // The module to which this quiz belongs
    private String quizName; // Add the quiz name
    private List<Question> questions;
    private Date createdAt;
    private Date updatedAt;
    private Date startAt;
    private int duration; // Duration in minutes

    // Constructors
    public Quiz(int quizId, int moduleId, String quizName, List<Question> questions, Date createdAt, Date updatedAt, Date startAt, int duration) {
        this.quizId = quizId;
        this.moduleId = moduleId;
        this.quizName = quizName;
        this.questions = questions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.startAt = startAt;
        this.duration = duration;
    }

    // Getters and Setters
    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Additional methods if needed
    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }

    // You can add more methods based on your application's requirements
}
