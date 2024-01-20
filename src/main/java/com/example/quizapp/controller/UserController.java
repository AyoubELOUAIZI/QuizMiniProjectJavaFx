// UserController.java
package com.example.quizapp.controller;

import com.example.quizapp.dao.UserDAO;
import com.example.quizapp.model.User;

import java.util.List;

public class UserController {
    private UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public void createUser(User user) {
        userDAO.createUser(user);
    }

    public void updateUser(User updatedUser) {
        userDAO.updateUser(updatedUser);
    }

    public void deleteUser(int userId) {
        userDAO.deleteUser(userId);
    }

//    public List<User> getAllUsers() {
//        return userDAO.getAllUsers();
//    }
}
