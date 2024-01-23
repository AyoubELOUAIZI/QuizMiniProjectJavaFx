// StudentDAO.java
package com.example.quizapp.dao;

import com.example.quizapp.model.Student;
import com.example.quizapp.database.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


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

}
