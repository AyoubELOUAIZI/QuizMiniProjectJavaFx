package com.example.quizapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String JDBC_URL = "jdbc:mysql://aws.connect.psdb.cloud/ayoubdb1?sslMode=VERIFY_IDENTITY";
    private static final String USERNAME = "i5sdrm8rlplnu3q2glpn";
    private static final String PASSWORD = "pscale_pw_t4V8cKpSKkPex4toYFIIS7geqJTRMTpbQn16JJfVb00";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    // Ensure thread safety for connection handling
    private static final Object lock = new Object();
    private static Connection connection;

    static {
        // Load the JDBC driver only once using static block
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load the JDBC driver.", e);
        }
    }

    public static Connection getConnection() {
        synchronized (lock) {
            try {
                connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                System.out.println("connected successfully to database online");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to connect to the database.", e);
            }
            return connection;
        }
    }

    public static void closeConnection() {
        synchronized (lock) {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    connection = null;
                }
            }
        }
    }
}