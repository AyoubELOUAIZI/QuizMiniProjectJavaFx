// QuizDAO.java
package com.example.quizapp.dao;

import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Question;
import com.example.quizapp.database.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    private static final String TABLE_NAME = "quizzes";

    public Quiz getQuizById(String quizId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE quizId = ?")) {
            preparedStatement.setString(1, quizId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createQuizFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createQuiz(Quiz quiz) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?)")) {
            preparedStatement.setString(1, quiz.getQuizId());
            preparedStatement.setString(2, quiz.getModuleId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuiz(Quiz updatedQuiz) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET moduleId=? WHERE quizId=?")) {
            preparedStatement.setString(1, updatedQuiz.getModuleId());
            preparedStatement.setString(2, updatedQuiz.getQuizId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuiz(String quizId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE quizId=?")) {
            preparedStatement.setString(1, quizId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                quizzes.add(createQuizFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    private Quiz createQuizFromResultSet(ResultSet resultSet) throws SQLException {
        String quizId = resultSet.getString("quizId");
        String moduleId = resultSet.getString("moduleId");

        // Fetch questions for the quiz from the questions table
        List<Question> questions = getQuestionsForQuiz(quizId);

        return new Quiz(quizId, moduleId, questions);
    }

    private List<Question> getQuestionsForQuiz(String quizId) {
        // Implement logic to retrieve questions for the quiz from the database
        // You may need to have a separate table for quiz questions mapping
        // and fetch questions based on quizId
        // Modify this part according to your database schema
        return new ArrayList<>();
    }
}
