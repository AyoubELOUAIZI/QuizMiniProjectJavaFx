package com.example.quizapp.model;


public class Student extends User {

    public Student() {

    }

    @Override
    public String toString() {
        return "Student{" +
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
