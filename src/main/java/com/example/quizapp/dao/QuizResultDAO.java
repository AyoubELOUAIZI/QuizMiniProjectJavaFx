// QuizResultDAO.java
package com.example.quizapp.dao;

import com.example.quizapp.model.QuizResult;
import com.example.quizapp.database.DatabaseConnector;
import com.example.quizapp.model.Result;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizResultDAO {
    private static final String TABLE_NAME = "QuizResult";

    public void createQuizResult(QuizResult quizResult) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " (studentId, quizId, mark) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, quizResult.getStudentId());
            preparedStatement.setInt(2, quizResult.getQuizId());
            preparedStatement.setFloat(3, quizResult.getMark());

            preparedStatement.executeUpdate();

            // Retrieve the auto-generated quizResultId
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    quizResult.setQuizResultId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating quiz result failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<QuizResult> getQuizResultsByStudent(int studentId) {
        List<QuizResult> quizResults = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE studentId = ?")) {
            preparedStatement.setInt(1, studentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    quizResults.add(createQuizResultFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizResults;
    }

    // Other methods as needed

    private QuizResult createQuizResultFromResultSet(ResultSet resultSet) throws SQLException {
        int quizResultId = resultSet.getInt("quizResultId");
        int studentId = resultSet.getInt("studentId");
        int quizId = resultSet.getInt("quizId");
        float mark = resultSet.getFloat("mark");
        Timestamp createdAt = resultSet.getTimestamp("createdAt");

        return new QuizResult(quizResultId, studentId, quizId, mark, new Date(createdAt.getTime()));
    }

    public List<Result> getQuizResultByQuizIdStudentId(int quizId) {

        List<Result> resultList = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT QuizResult.mark, User.firstname, User.lastname " +
                    "FROM QuizResult " +
                    "INNER JOIN User ON QuizResult.studentId = User.userId " +
                    "WHERE QuizResult.quizId = ? AND LOWER(User.role) = 'student'";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, quizId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Récupérer les résultats de la requête
                        double mark = resultSet.getDouble("mark");
                        String firstname = resultSet.getString("firstname");
                        String lastname = resultSet.getString("lastname");

                        // Créer un objet Result et l'ajouter à la liste
                        Result result = new Result(mark, firstname, lastname);
                        resultList.add(result);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;

    }
}
