package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.helper.DBHelper;
import com.model.User;

public class UserDAO {

    // Create a new user record in the database.
    public int createUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (firstName, lastName, password, role, customerType, phone, emailId, address, dateOfBirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getCustomerType());
            ps.setString(6, user.getPhone());
            ps.setString(7, user.getEmailId());
            ps.setString(8, user.getAddress());
            ps.setDate(9, user.getDateOfBirth());

            int rowsAffected = ps.executeUpdate();

            // Retrieve the auto-generated userId
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    user.setUserId(generatedId);
                    return generatedId;
                }
            }
        }
        return 0;
    }

    // Retrieve a user by their unique ID.
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM Users WHERE userId = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("userId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("customerType"),
                        rs.getString("phone"),
                        rs.getString("emailId"),
                        rs.getString("address"),
                        rs.getDate("dateOfBirth")
                    );
                }
            }
        }
        return null;
    }

    // Retrieve all users.
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                    rs.getInt("userId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("customerType"),
                    rs.getString("phone"),
                    rs.getString("emailId"),
                    rs.getString("address"),
                    rs.getDate("dateOfBirth")
                );
                users.add(user);
            }
        }
        return users;
    }

    // Update an existing user.
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET firstName = ?, lastName = ?, password = ?, role = ?, customerType = ?, phone = ?, emailId = ?, address = ?, dateOfBirth = ? WHERE userId = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getCustomerType());
            ps.setString(6, user.getPhone());
            ps.setString(7, user.getEmailId());
            ps.setString(8, user.getAddress());
            ps.setDate(9, user.getDateOfBirth());
            ps.setInt(10, user.getUserId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Delete a user by ID.
    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM Users WHERE userId = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
