package com.example.quizapp.dao;

import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Question;
import com.example.quizapp.database.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    private static final String TABLE_NAME = "quizzes";

    public Quiz getQuizById(int quizId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE quizId = ?")) {
            preparedStatement.setInt(1, quizId);
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
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " (quizName, moduleId, createdAt, updatedAt, startAt, duration) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, quiz.getQuizName());
            preparedStatement.setInt(2, quiz.getModuleId());
            preparedStatement.setTimestamp(3, new Timestamp(quiz.getCreatedAt().getTime()));
            preparedStatement.setTimestamp(4, new Timestamp(quiz.getUpdatedAt().getTime()));
            preparedStatement.setTimestamp(5, new Timestamp(quiz.getStartAt().getTime()));
            preparedStatement.setInt(6, quiz.getDuration());

            preparedStatement.executeUpdate();

            // Get the auto-generated quizId
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int quizId = generatedKeys.getInt(1);
                    // Update the quiz object with the generated quizId
                    quiz.setQuizId(quizId);
                }
            }

            // Add questions for the quiz
            addQuestionsForQuiz(quiz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuiz(Quiz updatedQuiz) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET quizName=?, moduleId=?, updatedAt=?, startAt=?, duration=? WHERE quizId=?")) {
            preparedStatement.setString(1, updatedQuiz.getQuizName());
            preparedStatement.setInt(2, updatedQuiz.getModuleId());
            preparedStatement.setTimestamp(3, new Timestamp(updatedQuiz.getUpdatedAt().getTime()));
            preparedStatement.setTimestamp(4, new Timestamp(updatedQuiz.getStartAt().getTime()));
            preparedStatement.setInt(5, updatedQuiz.getDuration());
            preparedStatement.setInt(6, updatedQuiz.getQuizId());

            preparedStatement.executeUpdate();

            // Update questions for the quiz
            updateQuestionsForQuiz(updatedQuiz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuiz(int quizId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE quizId=?")) {
            preparedStatement.setInt(1, quizId);

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
        int quizId = resultSet.getInt("quizId");
        String quizName = resultSet.getString("quizName");
        int moduleId = resultSet.getInt("moduleId");
        // Retrieve timestamp values from the database
        Timestamp createdAt = resultSet.getTimestamp("createdAt");
        Timestamp updatedAt = resultSet.getTimestamp("updatedAt");
        Timestamp startAt = resultSet.getTimestamp("startAt");
        int duration = resultSet.getInt("duration");

        // Fetch questions for the quiz from the questions table
        List<Question> questions = getQuestionsForQuiz(quizId);

        // Create and return a Quiz object
        return new Quiz(quizId, moduleId,quizName, questions, createdAt, updatedAt, startAt, duration);
    }

    private void addQuestionsForQuiz(Quiz quiz) {
        // Implement logic to add questions for the quiz to the database
        // You may need to have a separate table for quiz questions mapping
        // Modify this part according to your database schema
    }

    private void updateQuestionsForQuiz(Quiz updatedQuiz) {
        // Implement logic to update questions for the quiz in the database
        // You may need to have a separate table for quiz questions mapping
        // Modify this part according to your database schema
    }

    private List<Question> getQuestionsForQuiz(int quizId) {
        // Implement logic to retrieve questions for the quiz from the database
        // You may need to have a separate table for quiz questions mapping
        // and fetch questions based on quizId
        // Modify this part according to your database schema
        return new ArrayList<>();
    }
}
