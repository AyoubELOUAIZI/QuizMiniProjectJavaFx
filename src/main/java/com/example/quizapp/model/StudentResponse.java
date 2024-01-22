// StudentResponse.java
package com.example.quizapp.model;

import java.util.Date;

public class StudentResponse {
    private int studentResponseId;
    private int userId;
    private int responseId;
    private boolean isMatch;
    private Date createdAt;

    // Constructors
    public StudentResponse(int studentResponseId, int userId, int responseId, boolean isMatch, Date createdAt) {
        this.studentResponseId = studentResponseId;
        this.userId = userId;
        this.responseId = responseId;
        this.isMatch = isMatch;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getStudentResponseId() {
        return studentResponseId;
    }

    public void setStudentResponseId(int studentResponseId) {
        this.studentResponseId = studentResponseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public boolean isMatch() {
        return isMatch;
    }

    public void setMatch(boolean isMatch) {
        this.isMatch = isMatch;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
