package com.example.quizapp.dao;

import com.example.quizapp.model.Question;
import com.example.quizapp.model.Response;
import com.example.quizapp.database.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private static final String TABLE_NAME = "Question";

    public void createQuestion(Question question) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO " + TABLE_NAME +
                             " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setInt(1, question.getQuestionId());
            preparedStatement.setInt(2, question.getQuizId());
            preparedStatement.setString(3, question.getText());
            preparedStatement.setString(4, question.getImage());
            preparedStatement.setTimestamp(5, question.getCreatedAt());
            preparedStatement.setTimestamp(6, question.getUpdatedAt());
            preparedStatement.setString(7, question.getFirstChoice());
            preparedStatement.setString(8, question.getSecondChoice());
            preparedStatement.setString(9, question.getThirdChoice());
            preparedStatement.setString(10, question.getFourthChoice());
            preparedStatement.setString(11, question.getFifthChoice());
            preparedStatement.setInt(12, question.getQuestionMarkV());
            preparedStatement.setString(13, question.getCorrectChoice());
            preparedStatement.setTimestamp(5, question.getCreatedAt());
            preparedStatement.setTimestamp(6, question.getUpdatedAt());
            preparedStatement.setString(7, question.getFirstChoice());
            preparedStatement.setString(8, question.getSecondChoice());
            preparedStatement.setString(9, question.getThirdChoice());
            preparedStatement.setString(10, question.getFourthChoice());
            preparedStatement.setString(11, question.getFifthChoice());
            preparedStatement.setInt(12, question.getQuestionMark());
            preparedStatement.setString(13, question.getCorrectChoice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateQuestion(Question updatedQuestion) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE " + TABLE_NAME + " SET quizId=?, text=?, image=?, " +
                             "createdAt=?, updatedAt=?, firstChoice=?, secondChoice=?, " +
                             "thirdChoice=?, fourthChoice=?, fifthChoice=?, questionMark=?, correctChoice=? " +
                             "WHERE questionId=?")) {
            preparedStatement.setInt(1, updatedQuestion.getQuizId());
            preparedStatement.setString(2, updatedQuestion.getText());
            preparedStatement.setString(3, updatedQuestion.getImage());
            preparedStatement.setInt(13, updatedQuestion.getQuestionId());
           // preparedStatement.setInt(1, updatedQuestion.getQuizId());
            //preparedStatement.setString(2, updatedQuestion.getText());
           // preparedStatement.setString(3, updatedQuestion.getImage());
            preparedStatement.setTimestamp(4, updatedQuestion.getCreatedAt());
            preparedStatement.setTimestamp(5, updatedQuestion.getUpdatedAt());
            preparedStatement.setString(6, updatedQuestion.getFirstChoice());
            preparedStatement.setString(7, updatedQuestion.getSecondChoice());
            preparedStatement.setString(8, updatedQuestion.getThirdChoice());
            preparedStatement.setString(9, updatedQuestion.getFourthChoice());
            preparedStatement.setString(10, updatedQuestion.getFifthChoice());
            preparedStatement.setInt(11, updatedQuestion.getQuestionMarkV());
            preparedStatement.setString(12, updatedQuestion.getCorrectChoice());
            preparedStatement.setInt(4, updatedQuestion.getQuestionId());
            preparedStatement.setInt(1, updatedQuestion.getQuizId());
            preparedStatement.setString(2, updatedQuestion.getText());
            preparedStatement.setString(3, updatedQuestion.getImage());
            preparedStatement.setTimestamp(5, updatedQuestion.getCreatedAt());
            preparedStatement.setTimestamp(6, updatedQuestion.getUpdatedAt());
            preparedStatement.setString(7, updatedQuestion.getFirstChoice());
            preparedStatement.setString(8, updatedQuestion.getSecondChoice());
            preparedStatement.setString(9, updatedQuestion.getThirdChoice());
            preparedStatement.setString(10, updatedQuestion.getFourthChoice());
            preparedStatement.setString(11, updatedQuestion.getFifthChoice());
            preparedStatement.setInt(12, updatedQuestion.getQuestionMark());
            preparedStatement.setString(13, updatedQuestion.getCorrectChoice());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Question> retrieveSelectedQuizQuestions(int quizId) {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM " + TABLE_NAME + " WHERE quizId=?")) {
            preparedStatement.setInt(1, quizId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    questions.add(createQuestionFromResultSet(resultSet));
                }
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
        Timestamp createdAt = resultSet.getTimestamp("createdAt");
        Timestamp updatedAt = resultSet.getTimestamp("updatedAt");
        String firstChoice = resultSet.getString("firstChoice");
        String secondChoice = resultSet.getString("secondChoice");
        String thirdChoice = resultSet.getString("thirdChoice");
        String fourthChoice = resultSet.getString("fourthChoice");
        String fifthChoice = resultSet.getString("fifthChoice");
        int questionMark = resultSet.getInt("questionMark");
        String correctChoice = resultSet.getString("correctChoice");

        return new Question(questionId, quizId, text, image, createdAt, updatedAt,
                firstChoice, secondChoice, thirdChoice, fourthChoice, fifthChoice,
                questionMark, correctChoice);
    }

    public Question getQuestionById(int questionId) {
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE questionId = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, questionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Question(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }

        return null; // Return null if the question is not found

    }

    public void deleteQuestion(int questionId) {
            try (Connection connection = DatabaseConnector.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE questionId=?")) {
                preparedStatement.setInt(1, questionId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}