package com.controller;

import com.dao.UserDAO;
import com.model.User;
import java.sql.SQLException;
import java.util.List;

public class UserController {

    private UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    // Add a new user by delegating to the DAO.
    public int addUser(User user) {
        try {
           return userDAO.createUser(user);
        } catch (SQLException e) {
            // Handle exception as needed (e.g., logging)
            e.printStackTrace();
        }
        return 0;
    }

    // Retrieve a user by their ID.
    public User getUser(int userId) {
        try {
            return userDAO.getUserById(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all users.
    public List<User> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update an existing user's details.
    public boolean updateUser(User user) {
        try {
            return userDAO.updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a user by ID.
    public boolean deleteUser(int userId) {
        try {
            return userDAO.deleteUser(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
