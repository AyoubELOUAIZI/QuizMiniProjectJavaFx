package com.example.quizapp.dao;

import com.example.quizapp.model.Module;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.database.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleDAO {
    private static final String TABLE_NAME = "modules";

    public Module getModuleById(int moduleId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE moduleId = ?")) {
            preparedStatement.setInt(1, moduleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createModuleFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createModule(Module module) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?)")) {
            preparedStatement.setInt(1, module.getModuleId());
            preparedStatement.setString(2, module.getModuleName());
            preparedStatement.setString(3, module.getModulePassword());
            preparedStatement.setInt(4, module.getTeacherId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateModule(Module updatedModule) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET moduleName=?, modulePassword=?, teacherId=? WHERE moduleId=?")) {
            preparedStatement.setString(1, updatedModule.getModuleName());
            preparedStatement.setString(2, updatedModule.getModulePassword());
            preparedStatement.setInt(3, updatedModule.getTeacherId());
            preparedStatement.setInt(4, updatedModule.getModuleId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteModule(int moduleId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE moduleId=?")) {
            preparedStatement.setInt(1, moduleId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Module> getAllModules() {
        List<Module> modules = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                modules.add(createModuleFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    private Module createModuleFromResultSet(ResultSet resultSet) throws SQLException {
        int moduleId = resultSet.getInt("moduleId");
        String moduleName = resultSet.getString("moduleName");
        String modulePassword = resultSet.getString("modulePassword");
        int teacherId = resultSet.getInt("teacherId");

        // Fetch quizzes for the module from the quizzes table
        List<Quiz> quizzes = getQuizzesForModule(moduleId);

        return new Module(moduleId, moduleName, modulePassword, quizzes, teacherId);
    }

    private List<Quiz> getQuizzesForModule(int moduleId) {
        // Implement logic to retrieve quizzes for the module from the database
        // You may need to have a separate table for module quizzes mapping
        // and fetch quizzes based on moduleId
        // Modify this part according to your database schema
        return new ArrayList<>();
    }
}
