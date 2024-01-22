// StudentResponseController.java
package com.example.quizapp.controller;

import com.example.quizapp.dao.StudentResponseDAO;
import com.example.quizapp.model.StudentResponse;

import java.util.List;

public class StudentResponseController {
    private final StudentResponseDAO studentResponseDAO;

    public StudentResponseController() {
        this.studentResponseDAO = new StudentResponseDAO();
    }

    public StudentResponse getStudentResponseById(int studentResponseId) {
        return studentResponseDAO.getStudentResponseById(studentResponseId);
    }

    public void createStudentResponse(StudentResponse studentResponse) {
        studentResponseDAO.createStudentResponse(studentResponse);
    }

    public void updateStudentResponse(StudentResponse updatedStudentResponse) {
        studentResponseDAO.updateStudentResponse(updatedStudentResponse);
    }

    public void deleteStudentResponse(int studentResponseId) {
        studentResponseDAO.deleteStudentResponse(studentResponseId);
    }

    public List<StudentResponse> getAllStudentResponses() {
        return studentResponseDAO.getAllStudentResponses();
    }
}
