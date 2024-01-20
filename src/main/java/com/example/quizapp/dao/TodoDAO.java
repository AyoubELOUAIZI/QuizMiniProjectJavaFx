package com.example.quizapp.dao;

import com.example.quizapp.database.DatabaseConnector;
import com.example.quizapp.model.Todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TodoDAO {
    private static final Logger LOGGER = Logger.getLogger(TodoDAO.class.getName());

    private static final String SELECT_ALL_TODOS = "SELECT * FROM todos";
    private static final String INSERT_TODO = "INSERT INTO todos (title, description) VALUES (?, ?)";
    private static final String DELETE_TODO = "DELETE FROM todos WHERE id = ?";

    public List<Todo> getAllTodos() {
        List<Todo> todos = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TODOS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");

                Todo todo = new Todo(id, title, description);
                todos.add(todo);
            }
            System.out.println("data has been retrieved 😀😀");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all todos", e);
        }
        return todos;
    }

    public void addTodo(Todo todo) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TODO, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, todo.getTitle());
            preparedStatement.setString(2, todo.getDescription());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                LOGGER.log(Level.SEVERE, "Creating todo failed, no rows affected.");
                throw new SQLException("Creating todo failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    todo.setId(generatedKeys.getInt(1));
                } else {
                    LOGGER.log(Level.SEVERE, "Creating todo failed, no ID obtained.");
                    throw new SQLException("Creating todo failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding todo to database", e);
        }
          System.out.println("adding new todolist 🌷✅");

    }



    public void deleteTodo(int todoId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TODO)) {

            preparedStatement.setInt(1, todoId);

            preparedStatement.executeUpdate();
            System.out.println("to do list has been deleted 💥💛");


        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting todo from database", e);
        }
    }
}
