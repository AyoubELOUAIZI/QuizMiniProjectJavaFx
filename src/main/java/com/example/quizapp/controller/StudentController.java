// StudentController.java
package com.example.quizapp.controller;

import com.example.quizapp.dao.StudentDAO;
import com.example.quizapp.model.Student;
import javafx.event.ActionEvent;

public class StudentController {
    private final StudentDAO studentDAO;



    public StudentController() {
        this.studentDAO = new StudentDAO();
    }

    public Student getStudentById(int userId) {
        return studentDAO.getStudentById(userId);
    }

    public void createStudent(Student student) {
        studentDAO.createStudent(student);
    }

    public void updateStudent(Student updatedStudent) {
        studentDAO.updateStudent(updatedStudent);
    }

    public void deleteStudent(int userId) {
        studentDAO.deleteStudent(userId);
    }

    public void handleButtonAction(ActionEvent event) {


    }
}
