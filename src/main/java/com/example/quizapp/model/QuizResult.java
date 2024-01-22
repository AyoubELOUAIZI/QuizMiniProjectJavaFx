package com.example.quizapp.model;

import java.util.Date;

public class QuizResult {
    private int quizResultId;
    private int studentId;
    private int quizId;
    private float mark;
    private Date createdAt;

    // Constructors
    public QuizResult(int quizResultId, int studentId, int quizId, float mark, Date createdAt) {
        this.quizResultId = quizResultId;
        this.studentId = studentId;
        this.quizId = quizId;
        this.mark = mark;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getQuizResultId() {
        return quizResultId;
    }

    public void setQuizResultId(int quizResultId) {
        this.quizResultId = quizResultId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
