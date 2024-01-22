package com.example.quizapp.model;

import java.util.List;

public class Question {
    private int questionId;
    private int quizId; // Assuming the quiz it belongs to
    private String text;
    private String image; // URL or path to the image
    private List<Response> responses; // List of possible responses

    // Constructors
    public Question(int questionId, int quizId, String text, String image, List<Response> responses) {
        this.questionId = questionId;
        this.quizId = quizId;
        this.text = text;
        this.image = image;
        this.responses = responses;
    }

    // Getters and Setters
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

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    // Additional methods if needed
    public void addResponse(Response response) {
        responses.add(response);
    }

    public void removeResponse(Response response) {
        responses.remove(response);
    }

    // You can add more methods based on your application's requirements
}
