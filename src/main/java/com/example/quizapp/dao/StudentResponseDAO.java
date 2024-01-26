// StudentResponseDAO.java
package com.example.quizapp.dao;

import com.example.quizapp.model.StudentResponse;
import com.example.quizapp.database.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentResponseDAO {
    private static final String TABLE_NAME = "StudentResponse";

    public StudentResponse getStudentResponseById(int studentResponseId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE studentResponseId = ?")) {
            preparedStatement.setInt(1, studentResponseId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createStudentResponseFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createStudentResponse(StudentResponse studentResponse) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " (userId, quizId, questionId, chosenResponse, createdAt) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setInt(1, studentResponse.getUserId());
            preparedStatement.setInt(2, studentResponse.getQuizId());
            preparedStatement.setInt(3, studentResponse.getQuestionId());
            preparedStatement.setString(4, String.valueOf(studentResponse.getChosenResponse()));
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(studentResponse.getCreatedAt().getTime()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudentResponse(StudentResponse updatedStudentResponse) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET userId=?, quizId=?, questionId=?, chosenResponse=?, createdAt=? WHERE studentResponseId=?")) {
            preparedStatement.setInt(1, updatedStudentResponse.getUserId());
            preparedStatement.setInt(2, updatedStudentResponse.getQuizId());
            preparedStatement.setInt(3, updatedStudentResponse.getQuestionId());
            preparedStatement.setString(4, String.valueOf(updatedStudentResponse.getChosenResponse()));
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(updatedStudentResponse.getCreatedAt().getTime()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudentResponse(int studentResponseId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE studentResponseId=?")) {
            preparedStatement.setInt(1, studentResponseId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<StudentResponse> getAllStudentResponses() {
        List<StudentResponse> studentResponses = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                studentResponses.add(createStudentResponseFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentResponses;
    }

    private StudentResponse createStudentResponseFromResultSet(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("userId");
        int quizId = resultSet.getInt("quizId");
        int questionId = resultSet.getInt("questionId");
        char chosenResponse = resultSet.getString("chosenResponse").charAt(0);
        Date createdAt = resultSet.getTimestamp("createdAt");

        return new StudentResponse( userId, quizId, questionId, chosenResponse, createdAt);
    }

    public List<StudentResponse> retrieveSelectedQuizStudentResponses(int quizId, int userId) {
        List<StudentResponse> studentResponses = new ArrayList<>();

        // Query to retrieve student responses for a specific quiz and user
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE quizId = ? AND userId = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the query
            preparedStatement.setInt(1, quizId);
            preparedStatement.setInt(2, userId);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    studentResponses.add(createStudentResponseFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentResponses;
    }

}
