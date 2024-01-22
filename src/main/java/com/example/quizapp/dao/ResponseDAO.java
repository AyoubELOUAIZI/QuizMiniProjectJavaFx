package com.example.quizapp.dao;

import com.example.quizapp.model.Response;
import com.example.quizapp.database.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResponseDAO {
    private static final String TABLE_NAME = "responses";

    public Response getResponseById(String responseId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE responseId = ?")) {
            preparedStatement.setString(1, responseId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createResponseFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createResponse(Response response) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setInt(1, response.getResponseId());
            preparedStatement.setInt(2, response.getQuestionId());
            preparedStatement.setString(3, response.getText());
            preparedStatement.setString(4, response.getImage());
            preparedStatement.setBoolean(5, response.isCorrect());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateResponse(Response updatedResponse) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET questionId=?, text=?, image=?, isCorrect=? WHERE responseId=?")) {
            preparedStatement.setInt(1, updatedResponse.getQuestionId());
            preparedStatement.setString(2, updatedResponse.getText());
            preparedStatement.setString(3, updatedResponse.getImage());
            preparedStatement.setBoolean(4, updatedResponse.isCorrect());
            preparedStatement.setInt(5, updatedResponse.getResponseId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteResponse(String responseId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE responseId=?")) {
            preparedStatement.setString(1, responseId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Response> getAllResponses() {
        List<Response> responses = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                responses.add(createResponseFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responses;
    }

    private Response createResponseFromResultSet(ResultSet resultSet) throws SQLException {
        int responseId = resultSet.getInt("responseId");
        int questionId = resultSet.getInt("questionId");
        String text = resultSet.getString("text");
        String image = resultSet.getString("image");
        boolean isCorrect = resultSet.getBoolean("isCorrect");

        return new Response(responseId, questionId, text, image, isCorrect);
    }
}
