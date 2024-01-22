package com.example.quizapp.model;

public class Response {
    private int responseId;
    private int questionId; // The question to which this response belongs
    private String text;
    private String image; // URL or path to the image
    private boolean isCorrect;

    // Constructors
    public Response(int responseId, int questionId, String text, String image, boolean isCorrect) {
        this.responseId = responseId;
        this.questionId = questionId;
        this.text = text;
        this.image = image;
        this.isCorrect = isCorrect;
    }

    // Getters and Setters
    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
