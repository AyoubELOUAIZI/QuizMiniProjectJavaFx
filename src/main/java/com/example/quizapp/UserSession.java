package com.example.quizapp;

import com.example.quizapp.model.User;

public class UserSession {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void clearSession() {
        currentUser = null;
    }
}
