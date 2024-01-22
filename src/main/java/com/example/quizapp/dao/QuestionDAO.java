package com.example.quizapp.dao;

import com.example.quizapp.model.Question;
import com.example.quizapp.model.Response;
import com.example.quizapp.database.DatabaseConnector;

import java.sql.*;
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
            preparedStatement.setInt(1, question.getQuestionId());
            preparedStatement.setInt(2, question.getQuizId());
            preparedStatement.setString(3, question.getText());
            preparedStatement.setString(4, question.getImage());
            // Assuming responses are stored in a separate table or as a serialized form
            // Modify this part according to your database schema
            saveResponses(question.getQuestionId(), question.getResponses());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuestion(Question updatedQuestion) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET quizId=?, text=?, image=? WHERE questionId=?")) {
            preparedStatement.setInt(1, updatedQuestion.getQuizId());
            preparedStatement.setString(2, updatedQuestion.getText());
            preparedStatement.setString(3, updatedQuestion.getImage());
            preparedStatement.setInt(4, updatedQuestion.getQuestionId());

            // Update responses separately
            updateResponses(updatedQuestion.getQuestionId(), updatedQuestion.getResponses());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuestion(String questionId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            // Delete responses first
            deleteResponses(questionId);

            // Then delete the question
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE questionId=?")) {
                preparedStatement.setString(1, questionId);
                preparedStatement.executeUpdate();
            }
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
        int questionId = resultSet.getInt("questionId");
        int quizId = resultSet.getInt("quizId");
        String text = resultSet.getString("text");
        String image = resultSet.getString("image");
        // Load responses from the database
        List<Response> responses = loadResponses(questionId);

        return new Question(questionId, quizId, text, image, responses);
    }

    private void saveResponses(int questionId, List<Response> responses) {
        // Implement logic to save responses to the database
        // Modify this part according to your database schema
    }

    private void updateResponses(int questionId, List<Response> responses) {
        // Implement logic to update responses in the database
        // Modify this part according to your database schema
    }

    private void deleteResponses(String questionId) {
        // Implement logic to delete responses from the database
        // Modify this part according to your database schema
    }

    private List<Response> loadResponses(int questionId) {
        // Implement logic to load responses from the database
        // Modify this part according to your database schema
        return new ArrayList<>();
    }
}
