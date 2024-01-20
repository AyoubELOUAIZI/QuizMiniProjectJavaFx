// QuestionController.java
package com.example.quizapp.controller;

import com.example.quizapp.dao.QuestionDAO;
import com.example.quizapp.model.Question;

import java.util.List;

public class QuestionController {
    private QuestionDAO questionDAO;

    public QuestionController(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public Question getQuestionById(String questionId) {
        return questionDAO.getQuestionById(questionId);
    }

    public void createQuestion(Question question) {
        questionDAO.createQuestion(question);
    }

    public void updateQuestion(Question updatedQuestion) {
        questionDAO.updateQuestion(updatedQuestion);
    }

    public void deleteQuestion(String questionId) {
        questionDAO.deleteQuestion(questionId);
    }

    public List<Question> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }
}
