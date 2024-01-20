package com.example.quizapp.model;

public class Todo {
    private int id;
    private String title;
    private String description;


    // Constructors
    public Todo() {
        // Default constructor
    }

    public Todo(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




    // Other methods, if needed
    @Override
    public String toString() {
        return "Todo {" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description +
                '}';
    }
}
