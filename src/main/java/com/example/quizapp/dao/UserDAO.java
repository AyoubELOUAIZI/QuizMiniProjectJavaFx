package com.example.quizapp.dao;

import com.example.quizapp.model.User;
import com.example.quizapp.model.Module;
import com.example.quizapp.database.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String TABLE_NAME = "users";

    public User getUserById(int userId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE userId = ?")) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createUser(User user) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getFirstname());
            preparedStatement.setString(3, user.getLastname());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getRole());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User updatedUser) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            // Update user information
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET firstname=?, lastname=?, email=?, password=?, role=? WHERE userId=?")) {
                preparedStatement.setString(1, updatedUser.getFirstname());
                preparedStatement.setString(2, updatedUser.getLastname());
                preparedStatement.setString(3, updatedUser.getEmail());
                preparedStatement.setString(4, updatedUser.getPassword());
                preparedStatement.setString(5, updatedUser.getRole());
                preparedStatement.setInt(6, updatedUser.getUserId());

                preparedStatement.executeUpdate();
            }

            // Update associated modules
            updateModulesForUser(updatedUser);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            // Delete associated modules
            deleteModulesForUser(userId);

            // Delete user
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE userId=?")) {
                preparedStatement.setInt(1, userId);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("userId");
        String firstname = resultSet.getString("firstname");
        String lastname = resultSet.getString("lastname");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String role = resultSet.getString("role");

        // Fetch associated modules
        List<Module> modules = getModulesForUser(userId);

        User user = new User(userId, firstname, lastname, email, password, role, modules);
        return user;
    }

    private List<Module> getModulesForUser(int userId) {
        List<Module> modules = new ArrayList<>();
        // Implement logic to retrieve modules associated with the user from the database
        // Modify this part according to your database schema

        // Example: Assume you have a table named "user_modules" with columns userId and moduleId
        String query = "SELECT moduleId FROM user_modules WHERE userId=?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int moduleId = resultSet.getInt("moduleId");
                    // Fetch module details based on moduleId
                    Module module = getModuleById(moduleId);
                    if (module != null) {
                        modules.add(module);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    private Module getModuleById(int moduleId) {
        // Implement logic to retrieve a module by its ID from the database
        // Modify this part according to your database schema
        return null;
    }

    private void updateModulesForUser(User user) {
        // Implement logic to update modules associated with the user in the database
        // Modify this part according to your database schema
    }

    private void deleteModulesForUser(int userId) {
        // Implement logic to delete modules associated with the user in the database
        // Modify this part according to your database schema
    }
}
