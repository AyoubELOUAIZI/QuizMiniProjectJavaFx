package com.example.quizapp.model;

import java.util.List;

public class Question {
    private String questionId;
    private String quizId; // Assuming the quiz it belongs to
    private String text;
    private String image; // URL or path to the image
    private List<String> responses; // List of possible responses

    // Constructors
    public Question(String questionId, String quizId, String text, String image, List<String> responses) {
        this.questionId = questionId;
        this.quizId = quizId;
        this.text = text;
        this.image = image;
        this.responses = responses;
    }

    // Getters and Setters
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
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

    public List<String> getResponses() {
        return responses;
    }

    public void setResponses(List<String> responses) {
        this.responses = responses;
    }

    // Additional methods if needed
    public void addResponse(String response) {
        responses.add(response);
    }

    public void removeResponse(String response) {
        responses.remove(response);
    }

    // You can add more methods based on your application's requirements
}
