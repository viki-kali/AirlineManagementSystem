package com.userdao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.model.User;
import com.helper.DBHelper;

public class UserDao {
    
    // Admin side
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Connection conn = DBHelper.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {
            while (rs.next()) {
                User u = new User(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getDate(10)
                );
                users.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public User getUser(int userId) {
        User user = null;
        String query = "SELECT * FROM Users WHERE userId=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getDate(10)
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    // Insert user
    public int insertUser(User u) {
        String query = "INSERT INTO USERS (firstName, lastName, password, role, customerType, phone, emailId, address, dateOfBirth) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, u.getFirstName());
            ps.setString(2, u.getLastName());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRole());
            ps.setString(5, u.getCustomerType());
            ps.setString(6, u.getPhone());
            ps.setString(7, u.getEmailId());
            ps.setString(8, u.getAddress());
            ps.setDate(9, u.getDateOfBirth());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    // Retrieve user id (maximum userId)
    public int getUserId() {
        String query = "SELECT max(USERID) FROM USERS";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Credentials validation
    public User getUsername(int userid, String password) {
        String query = "SELECT * FROM USERS WHERE USERID=? AND PASSWORD=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userid);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    String fname = rs.getString(2);
                    String lname = rs.getString(3);
                    String pwd = rs.getString(4);
                    String role = rs.getString(5);
                    String customerType = rs.getString(6);
                    String phone = rs.getString(7);
                    String emailid = rs.getString(8);
                    String address = rs.getString(9);
                    Date dob = rs.getDate(10);
                    return new User(userId, fname, lname, pwd, role, customerType, phone, emailid, address, dob);
                } else {
                    System.out.println("No matching user found.");
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
    
    // Update user details
    public User updateUser(User u) {
        String query = "UPDATE Users SET firstName = ?, lastName = ?, password = ?, role = ?, customerType = ?, phone = ?, emailId = ?, address = ?, dateOfBirth = ? WHERE userId = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, u.getFirstName());
            ps.setString(2, u.getLastName());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRole());
            ps.setString(5, u.getCustomerType());
            ps.setString(6, u.getPhone());
            ps.setString(7, u.getEmailId());
            ps.setString(8, u.getAddress());
            ps.setDate(9, u.getDateOfBirth());
            ps.setInt(10, u.getUserId());
            int r = ps.executeUpdate();
            System.out.println(r);
            return u;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
    
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE userId=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
