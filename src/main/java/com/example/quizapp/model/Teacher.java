package com.example.quizapp.model;

import java.util.List;

// Teacher.java
public class Teacher extends User {

    // Constructor for a teacher without associated modules
    public Teacher(int userId, String firstname, String lastname, String email, String password,String sex) {
        super(userId, firstname, lastname, email, password, "teacher",sex, null);
    }

    // Constructor for a teacher with associated modules
    public Teacher(int userId, String firstname, String lastname, String email, String password,String sex, List<Module> modules) {
        super(userId, firstname, lastname, email, password, "teacher", sex, modules);
    }

    public Teacher() {

    }

    // Additional methods if needed
    // ...

    // You may need to add methods for managing modules specific to teachers
    // such as addModule, removeModule, etc.
}
