package com.example.quizapp.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Quiz {
    private int quizId;
    private int teacherId;
    private String quizName; // Add the quiz name

    private String passwordQuiz;
    private List<Question> questions;
    private Date createdAt;
    private Date updatedAt;
    private Timestamp startAt;
    private int duration; // Duration in minutes

    // Constructors
    public Quiz(int quizId, int teacherId,String quizName, List<Question> questions, Date updatedAt, Timestamp startAt, int duration,String password) {
        this.quizId = quizId;
        this.teacherId=teacherId;
        this.passwordQuiz = password;
        this.quizName = quizName;
        this.questions = questions;
        this.createdAt = new Date();
        this.updatedAt = updatedAt;
        this.startAt = startAt;
        this.duration = duration;
    }
    public Quiz(int quizId, int teacherId,String quizName, List<Question> questions, Timestamp createdAt,Date updatedAt, Timestamp startAt, int duration,String password) {
        this.quizId = quizId;
        this.teacherId=teacherId;
        this.passwordQuiz = password;
        this.quizName = quizName;
        this.questions = questions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.startAt = startAt;
        this.duration = duration;
    }

    public Quiz() {

    }

    // Getters and Setters
    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }


    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getPasswordQuiz() {
        return passwordQuiz;
    }

    public void setPasswordQuiz(String passwordQuiz) {
        this.passwordQuiz = passwordQuiz;
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
        this.startAt = (Timestamp) startAt;
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

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", teacherId=" + teacherId +
                ", quizName='" + quizName + '\'' +
                ", passwordQuiz='" + passwordQuiz + '\'' +
                ", questions=" + questions +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", startAt=" + startAt +
                ", duration=" + duration +
                '}';
    }

    // You can add more methods based on your application's requirements
}
