package com.example.quizapp.model;

import java.util.List;

public class Student extends User {
    private String studentCode; // or 'cne' or any other identifier for students

    // Constructor
    public Student(int userId, String firstname, String lastname, String email, String password, String studentCode, List<Module> modules) {
        super(userId, firstname, lastname, email, password, "student", modules);
        this.studentCode = studentCode;
    }

    public Student() {

    }

    // Getter and Setter for studentCode
    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    // Additional methods for managing modules specific to students


    // You can add more methods based on your application's requirements

    @Override
    public String toString() {
        return "Student{" +
                "studentCode='" + studentCode + '\'' +
                ", userId=" + getUserId() +
                ", firstname='" + getFirstname() + '\'' +
                ", lastname='" + getLastname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", role='" + getRole() + '\'' +
                ", modules=" + getModules() +
                '}';
    }
}
