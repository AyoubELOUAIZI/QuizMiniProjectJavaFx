// TeacherDAO.java
package com.example.quizapp.dao;

import com.example.quizapp.model.Teacher;
import com.example.quizapp.database.DatabaseConnector;
import com.example.quizapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO extends UserDAO {
    private static final String TABLE_NAME = "teachers";

    public Teacher getTeacherById(int userId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE userId = ?")) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createTeacherFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createTeacher(Teacher teacher) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?)")) {
            preparedStatement.setInt(1, teacher.getUserId());

            // Insert the user part into the users table
            createUser(teacher);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTeacher(Teacher updatedTeacher) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET userId=? WHERE userId=?")) {
            preparedStatement.setInt(1, updatedTeacher.getUserId());

            // Update the user part in the users table
            updateUser(updatedTeacher);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTeacher(int userId) {
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

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                teachers.add(createTeacherFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    private Teacher createTeacherFromResultSet(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("userId");

        // Fetch user details
        User user = getUserById(userId);

        return new Teacher(userId, user.getFirstname(), user.getLastname(), user.getEmail(),user.getSexe(), user.getPassword());
    }
}
