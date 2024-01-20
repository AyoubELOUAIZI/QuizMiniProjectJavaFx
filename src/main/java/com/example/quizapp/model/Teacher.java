package com.example.quizapp.model;

// Teacher.java
public class Teacher extends User {

    // Constructor
    public Teacher(int userId, String firstname, String lastname, String email, String password) {
        super(userId, firstname, lastname, email, password, "teacher");
    }
}
