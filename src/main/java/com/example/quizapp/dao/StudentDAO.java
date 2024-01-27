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

    public boolean addNewStudentQuizToDatabase(String quizPassword, int studentId) {
        // Query to check if a quiz with the given password exists
        String checkQuizQuery = "SELECT quizId FROM Quiz WHERE passwordQuiz = ?";

        // Query to insert a new row in the StudentQuiz table
        String insertStudentQuizQuery = "INSERT INTO StudentQuiz (quizId,studentId) VALUES (?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement checkQuizStatement = connection.prepareStatement(checkQuizQuery);
             PreparedStatement insertStudentQuizStatement = connection.prepareStatement(insertStudentQuizQuery)) {

            // Set the password parameter for the first query
            checkQuizStatement.setString(1, quizPassword);

            // Execute the first query to check if a quiz with the given password exists
            try (ResultSet resultSet = checkQuizStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Quiz with the given password exists, retrieve its ID
                    int quizId = resultSet.getInt("quizId");

                    // Set parameters for the second query (insert StudentQuiz)
                    insertStudentQuizStatement.setInt(1, quizId);
                    insertStudentQuizStatement.setInt(2, studentId);

                    // Execute the second query to insert a new row in the StudentQuiz table
                    int rowsAffected = insertStudentQuizStatement.executeUpdate();

                    // Return true if the insert operation was successful (rowsAffected > 0)
                    return rowsAffected > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If no quiz with the given password was found, or there was an error, return false
        return false;
    }

    public boolean saveStudentResponse(int userId, int quizId, int questionId, char chosenResponse) {
        // Query to insert a new row in the StudentResponse table
        String insertStudentResponseQuery = "INSERT INTO StudentResponse (userId, quizId, questionId, chosenResponse) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement insertStudentResponseStatement = connection.prepareStatement(insertStudentResponseQuery)) {

            // Set parameters for the query
            insertStudentResponseStatement.setInt(1, userId);
            insertStudentResponseStatement.setInt(2, quizId);
            insertStudentResponseStatement.setInt(3, questionId);
            insertStudentResponseStatement.setString(4, String.valueOf(chosenResponse)); // Assuming chosenResponse is a character

            // Execute the query to insert a new row in the StudentResponse table
            int rowsAffected = insertStudentResponseStatement.executeUpdate();

            // Return true if the insert operation was successful (rowsAffected > 0)
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If there was an error, or the insert operation failed, return false
        return false;
    }

    public boolean checkIfQuizHasBeenTested(int userId, int quizId) {
        // Query to check if a record exists in StudentQuiz for the given userId and quizId
        String checkQuizQuery = "SELECT quizHasTested FROM StudentQuiz WHERE studentId = ? AND quizId = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement checkQuizStatement = connection.prepareStatement(checkQuizQuery)) {

            // Set the parameters for the query
            checkQuizStatement.setInt(1, userId);
            checkQuizStatement.setInt(2, quizId);

            // Execute the query
            try (ResultSet resultSet = checkQuizStatement.executeQuery()) {
                // Return true if a record is found and quizHasTested is true
                return resultSet.next() && resultSet.getBoolean("quizHasTested");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return false if there was an error or no record was found
        return false;
    }


    public void updateQuizTestedStatus(int userId, int quizId) {
        // Query to update the quizHasTested column to true for the given userId and quizId
        String updateQuizTestedQuery = "UPDATE StudentQuiz SET quizHasTested = true WHERE studentId = ? AND quizId = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement updateQuizTestedStatement = connection.prepareStatement(updateQuizTestedQuery)) {

            // Set the parameters for the query
            updateQuizTestedStatement.setInt(1, userId);
            updateQuizTestedStatement.setInt(2, quizId);

            // Execute the query to update quizHasTested to true
            updateQuizTestedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addStudentQuizResultDatabase(int studentId, int quizId, float mark) {
        // Query to insert a new row in the QuizResult table
        String insertQuizResultQuery = "INSERT INTO QuizResult (studentId, quizId, mark) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement insertQuizResultStatement = connection.prepareStatement(insertQuizResultQuery)) {

            // Set parameters for the query
            insertQuizResultStatement.setInt(1, studentId);
            insertQuizResultStatement.setInt(2, quizId);
            insertQuizResultStatement.setFloat(3, mark);

            // Execute the query to insert the student's mark for the quiz
            int rowsAffected = insertQuizResultStatement.executeUpdate();

            // Return true if the insert operation was successful (rowsAffected > 0)
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If there was an error or the insert operation failed, return false
        return false;
    }

}
