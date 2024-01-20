// StudentDAO.java
package com.example.quizapp.dao;

import com.example.quizapp.model.Student;
import com.example.quizapp.database.DatabaseConnector;
import com.example.quizapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends UserDAO {
    private static final String TABLE_NAME = "students";

    public Student getStudentById(int userId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE userId = ?")) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createStudentFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createStudent(Student student) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?)")) {
            preparedStatement.setInt(1, student.getUserId());
            preparedStatement.setString(2, student.getStudentCode());

            // Insert the user part into the users table
            createUser(student);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(Student updatedStudent) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET studentCode=? WHERE userId=?")) {
            preparedStatement.setString(1, updatedStudent.getStudentCode());
            preparedStatement.setInt(2, updatedStudent.getUserId());

            // Update the user part in the users table
            updateUser(updatedStudent);

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

    private Student createStudentFromResultSet(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("userId");

        // Fetch user details
        User user = getUserById(userId);

        String studentCode = resultSet.getString("studentCode");

        return new Student(userId, user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword(), studentCode, new ArrayList<>());
    }
}
