// StudentDAO.java
package com.example.quizapp.dao;

import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Student;
import com.example.quizapp.database.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StudentDAO extends UserDAO {
    private static final String TABLE_NAME = "Student";

    public void createStudent(Student student) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?)")) {
            preparedStatement.setInt(1, student.getUserId());
            // Insert the user part into the users table
            createUser(student);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int userId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE userId=?")) {
            preparedStatement.setInt(1, userId);

            // Delete the user part from the users table
            deleteUser(userId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Quiz> getQuizzesForStudent(int studentId) {
        List<Quiz> quizzes = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT q.* FROM Quiz q " +
                     "JOIN StudentQuiz sq ON q.quizId = sq.quizId " +
                     "WHERE sq.studentId = ?")) {
            preparedStatement.setInt(1, studentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Quiz quiz = new Quiz();
                    quiz.setQuizId(resultSet.getInt("quizId"));
                    quiz.setTeacherId(resultSet.getInt("teacherId"));
                    quiz.setCreatedAt(resultSet.getTimestamp("createdAt"));
                    quiz.setUpdatedAt(resultSet.getTimestamp("updatedAt"));
                    quiz.setStartAt(resultSet.getTimestamp("startAt"));
                    quiz.setDuration(resultSet.getInt("duration"));
                    quiz.setQuizName(resultSet.getString("quizName"));
                    quiz.setPasswordQuiz(resultSet.getString("passwordQuiz"));

                    quizzes.add(quiz);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("😎 Result of Fetching the Student Quizzes by his Id:");
        System.out.println(quizzes);
        return quizzes;
    }

    public void deleteStudentQuiz(int studentId, int quizId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM StudentQuiz WHERE studentId = ? AND quizId = ?")) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, quizId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
