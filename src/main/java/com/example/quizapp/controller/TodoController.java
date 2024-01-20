package com.example.quizapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import com.example.quizapp.dao.TodoDAO;
import com.example.quizapp.model.Todo;

import java.util.List;

public class TodoController {

    @FXML
    private ListView<Todo> todoListView;

    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private Label statusLabel;

    private final ObservableList<Todo> todoList = FXCollections.observableArrayList();
    private final TodoDAO todoDAO = new TodoDAO();

    @FXML
    private void initialize() {
        // Initialize the todoListView with the todoList
        todoListView.setItems(todoList);
        loadTodosFromDatabase();
    }

    private void loadTodosFromDatabase() {
        // Load todos from the database and populate the list
        List<Todo> todos = todoDAO.getAllTodos();
        todoList.setAll(todos);
    }

    @FXML
    private void addTodo() {
        // Get values from text fields
        String title = titleField.getText();
        String description = descriptionField.getText();

        // Validate input
        if (title.isEmpty() || description.isEmpty()) {
            statusLabel.setText("Please enter both title and description.");
            return;
        }

        // Create a new Todo object
        Todo newTodo = new Todo(0, title, description);

        // Add the todo to the database
        todoDAO.addTodo(newTodo);

        // Refresh the list view by reloading data from the database
        loadTodosFromDatabase();

        // Clear text fields
        titleField.clear();
        descriptionField.clear();
        statusLabel.setText("Todo added successfully.");
    }

    @FXML
    private void deleteTodo() {
        // Get the selected todo from the list
        Todo selectedTodo = todoListView.getSelectionModel().getSelectedItem();

        // Check if a todo is selected
        if (selectedTodo != null) {
            // Delete the selected todo from the database
            todoDAO.deleteTodo(selectedTodo.getId());

            // Refresh the list view by reloading data from the database
            loadTodosFromDatabase();

            statusLabel.setText("Todo deleted successfully.");
        } else {
            statusLabel.setText("Please select a todo to delete.");
        }
    }
}
