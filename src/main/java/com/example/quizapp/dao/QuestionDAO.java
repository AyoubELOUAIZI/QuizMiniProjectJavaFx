// QuestionDAO.java
package com.example.quizapp.dao;

import com.example.quizapp.model.Question;
import com.example.quizapp.database.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private static final String TABLE_NAME = "questions";

    public Question getQuestionById(String questionId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE questionId = ?")) {
            preparedStatement.setString(1, questionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createQuestionFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createQuestion(Question question) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, question.getQuestionId());
            preparedStatement.setString(2, question.getQuizId());
            preparedStatement.setString(3, question.getText());
            preparedStatement.setString(4, question.getImage());
            // Assuming responses are stored in a separate table or as a serialized form
            // Modify this part according to your database schema
            preparedStatement.setString(5, String.join(",", question.getResponses()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuestion(Question updatedQuestion) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET quizId=?, text=?, image=?, responses=? WHERE questionId=?")) {
            preparedStatement.setString(1, updatedQuestion.getQuizId());
            preparedStatement.setString(2, updatedQuestion.getText());
            preparedStatement.setString(3, updatedQuestion.getImage());
            preparedStatement.setString(4, String.join(",", updatedQuestion.getResponses()));
            preparedStatement.setString(5, updatedQuestion.getQuestionId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuestion(String questionId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE questionId=?")) {
            preparedStatement.setString(1, questionId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                questions.add(createQuestionFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    private Question createQuestionFromResultSet(ResultSet resultSet) throws SQLException {
        String questionId = resultSet.getString("questionId");
        String quizId = resultSet.getString("quizId");
        String text = resultSet.getString("text");
        String image = resultSet.getString("image");
        // Assuming responses are stored in a separate table or as a serialized form
        // Modify this part according to your database schema
        List<String> responses = List.of(resultSet.getString("responses").split(","));

        return new Question(questionId, quizId, text, image, responses);
    }
}
