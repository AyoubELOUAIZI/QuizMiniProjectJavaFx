package com.example.quizapp.dao;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Teacher;
import com.example.quizapp.model.User;
import com.example.quizapp.database.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String TABLE_NAME = "User";

        public boolean updateStudentUserName(int userId, String firstName, String lastName) {
            try (Connection connection = DatabaseConnector.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET firstname=?, lastname=? WHERE userId=?")) {

                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setInt(3, userId);

                int rowsUpdated = preparedStatement.executeUpdate();

                // If at least one row is updated, return true; otherwise, return false
                return rowsUpdated > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false; // or you could throw an exception
            }
        }



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

public User createUser(User user) {
    ResultSet generatedKeys = null;
    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
                 "INSERT INTO " + TABLE_NAME + " (firstname, lastname, email, password, role, sexe) VALUES (?, ?, ?, ?, ?, ?)",
                 Statement.RETURN_GENERATED_KEYS)) {

        // Note: Removed the userId from the INSERT statement, as it's auto-generated
        preparedStatement.setString(1, user.getFirstname());
        preparedStatement.setString(2, user.getLastname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword()); // Ensure this is hashed and secure
        preparedStatement.setString(5, user.getRole());
        preparedStatement.setString(6, user.getSexe());

        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            // Set the userId back to the user object
            user.setUserId(generatedKeys.getInt(1));
        } else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return null; // or you could throw an exception
    } finally {
        if (generatedKeys != null) {
            try {
                generatedKeys.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return user;
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
        String sex = resultSet.getString("sexe");

        // Fetch associated modules
        List<Quiz> quizzes = getQuizzesForUser(userId);

        User user = new User(userId, firstname, lastname, email, password, role,sex, quizzes);
        return user;
    }

    public List<Quiz> getQuizzesForUser(int userId) {
        List<Quiz> quizzes = new ArrayList<>();
        // Implement logic to retrieve modules associated with the user from the database
        // Modify this part according to your database schema

        // Example: Assume you have a table named "user_modules" with columns userId and moduleId
        String query = "SELECT quizId FROM Quiz WHERE teacherId=?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int quizId = resultSet.getInt("quizId");
                    // Fetch module details based on moduleId
                    Quiz quiz = getQuizById(quizId);
                    if (quiz != null) {
                        quizzes.add(quiz);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    private Quiz getQuizById(int quizId) {
        // Implement logic to retrieve a module by its ID from the database
        // Modify this part according to your database schema
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.getQuizById(quizId);
    }

    private void updateModulesForUser(User user) {
        // Implement logic to update modules associated with the user in the database
        // Modify this part according to your database schema
    }

    private void deleteModulesForUser(int userId) {
        // Implement logic to delete modules associated with the user in the database
        // Modify this part according to your database schema
    }

//    public User loginUser(String email, String password) throws SQLException {
//        String query = "SELECT * FROM Student WHERE email = ? AND password = ? UNION SELECT * FROM Teacher WHERE email = ? AND password = ?";
//        try (Connection connection = DatabaseConnector.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, email);
//            statement.setString(2, password);
//            statement.setString(3, email);
//            statement.setString(4, password);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    User user = new User();
//                    user.setUserId(resultSet.getInt("userId"));
//                    user.setFirstname(resultSet.getString("firstname"));
//                    user.setLastname(resultSet.getString("lastname"));
//                    user.setEmail(resultSet.getString("email"));
//                    user.setPassword(resultSet.getString("password"));
//                    user.setRole(resultSet.getString("role"));
//                    return user;
//                }
//            }
//        }
//        return null; // Login failed
//    }

    public User loginUser(String email, String password) throws SQLException {
        String query = "SELECT * FROM User WHERE email = ? AND password = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUserId(resultSet.getInt("userId"));
                    user.setFirstname(resultSet.getString("firstname"));
                    user.setLastname(resultSet.getString("lastname"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    user.setSexe(resultSet.getString("sexe"));
                    return user;
                }
            }
        }
        return null; // Login failed
    }

    public void insertUser(User newUser) {
    }

    public Teacher getQuizTeacherByTeacherId(int teacherId) {
        Teacher teacher = null;

        // Modify the query based on your database schema
        String query = "SELECT * FROM User WHERE userId = ? AND role = 'teacher'";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, teacherId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    teacher = createTeacherFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacher;
    }


    private Teacher createTeacherFromResultSet(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setUserId(resultSet.getInt("UserId"));
        teacher.setFirstname(resultSet.getString("firstname"));
        teacher.setLastname(resultSet.getString("lastname"));
        teacher.setEmail(resultSet.getString("email"));
        teacher.setSexe(resultSet.getString("sexe"));
        // Add more properties if needed

        return teacher;
    }

    public boolean updateStudentUserEmail(int userId, String newEmail) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE User SET email=? WHERE userId=?")) {

            preparedStatement.setString(1, newEmail);
            preparedStatement.setInt(2, userId);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStudentUserPassword(int userId, String newPassword) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET password=? WHERE userId=?")) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setInt(2, userId);

                int affectedRows = preparedStatement.executeUpdate();
                return affectedRows > 0; // Returns true if at least one row was affected (update successful)
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Update failed
        }
    }

}
