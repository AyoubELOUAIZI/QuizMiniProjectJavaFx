// StudentController.java
package com.example.quizapp.controller;

import com.example.quizapp.UserSession;
import com.example.quizapp.dao.StudentDAO;
import com.example.quizapp.model.Student;
import com.example.quizapp.model.User;
import javafx.event.ActionEvent;

public class StudentController {
    private final StudentDAO studentDAO;



    public StudentController() {
        this.studentDAO = new StudentDAO();
    }

    public void initialize() {
        User currentUser = UserSession.getCurrentUser();
        // Use currentUser to initialize student-specific data
    }
//    public void createStudent(Student student) {
//        studentDAO.createStudent(student);
//    }
//
//    public void updateStudent(Student updatedStudent) {
//        studentDAO.updateStudent(updatedStudent);
//    }
//
//    public void deleteStudent(int userId) {
//        studentDAO.deleteStudent(userId);
//    }

    public void handleButtonAction(ActionEvent event) {


    }

    public void handleLogout(ActionEvent actionEvent) {
        // Your logout logic...

        // Clear the session
        UserSession.clearSession();
    }



}
