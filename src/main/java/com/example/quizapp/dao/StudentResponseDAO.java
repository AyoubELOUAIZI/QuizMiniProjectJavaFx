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
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " (userId, responseId, isMatch) VALUES (?, ?, ?)")) {
            preparedStatement.setInt(1, studentResponse.getUserId());
            preparedStatement.setInt(2, studentResponse.getResponseId());
            preparedStatement.setBoolean(3, studentResponse.isMatch());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudentResponse(StudentResponse updatedStudentResponse) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET userId=?, responseId=?, isMatch=? WHERE studentResponseId=?")) {
            preparedStatement.setInt(1, updatedStudentResponse.getUserId());
            preparedStatement.setInt(2, updatedStudentResponse.getResponseId());
            preparedStatement.setBoolean(3, updatedStudentResponse.isMatch());
            preparedStatement.setInt(4, updatedStudentResponse.getStudentResponseId());

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
        int studentResponseId = resultSet.getInt("studentResponseId");
        int userId = resultSet.getInt("userId");
        int responseId = resultSet.getInt("responseId");
        boolean isMatch = resultSet.getBoolean("isMatch");
        Date createdAt = resultSet.getTimestamp("createdAt");

        return new StudentResponse(studentResponseId, userId, responseId, isMatch, createdAt);
    }
}
