// QuizController.java
package com.example.quizapp.controller;

import com.example.quizapp.dao.QuizDAO;
import com.example.quizapp.model.Quiz;

import java.util.List;

public class QuizController {
    private QuizDAO quizDAO;

    public QuizController(QuizDAO quizDAO) {
        this.quizDAO = quizDAO;
    }

    public Quiz getQuizById(int quizId) {
        return quizDAO.getQuizById(quizId);
    }

    public void createQuiz(Quiz quiz) {
        quizDAO.createQuiz(quiz);
    }

    public void updateQuiz(Quiz updatedQuiz) {
        quizDAO.updateQuiz(updatedQuiz);
    }

    public void deleteQuiz(int quizId) {
        quizDAO.deleteQuiz(quizId);
    }

    public List<Quiz> getAllQuizzes() {
        return quizDAO.getAllQuizzes();
    }
}
