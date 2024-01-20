package com.example.quizapp.model;

import java.util.List;

public class Quiz {
    private String quizId;
    private String moduleId; // The module to which this quiz belongs
    private List<Question> questions;

    // Constructors
    public Quiz(String quizId, String moduleId, List<Question> questions) {
        this.quizId = quizId;
        this.moduleId = moduleId;
        this.questions = questions;
    }

    // Getters and Setters
    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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

