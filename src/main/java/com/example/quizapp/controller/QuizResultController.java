// QuizResultController.java
package com.example.quizapp.controller;

import com.example.quizapp.dao.QuizResultDAO;
import com.example.quizapp.model.QuizResult;

import java.util.List;

public class QuizResultController {
    private final QuizResultDAO quizResultDAO;

    public QuizResultController() {
        this.quizResultDAO = new QuizResultDAO();
    }

    public void saveQuizResult(QuizResult quizResult) {
        quizResultDAO.createQuizResult(quizResult);
    }

    public List<QuizResult> getQuizResultsByStudent(int studentId) {
        return quizResultDAO.getQuizResultsByStudent(studentId);
    }

    // Other methods as needed
}
