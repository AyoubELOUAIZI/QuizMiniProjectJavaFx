package com.example.quizapp.model;

public class Response {
    private String responseId;
    private String questionId; // The question to which this response belongs
    private String text;
    private boolean isCorrect;

    // Constructors
    public Response(String responseId, String questionId, String text, boolean isCorrect) {
        this.responseId = responseId;
        this.questionId = questionId;
        this.text = text;
        this.isCorrect = isCorrect;
    }

    // Getters and Setters
    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
