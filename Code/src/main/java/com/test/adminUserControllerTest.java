package com.test;
import com.controller.UserController;
import com.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

public class adminUserControllerTest {
    private UserController userController;
    private User testUser;

    @Before
    public void setUp() {
        userController = new UserController();
        testUser = new User(0, "John", "Doe", "password123", "Customer", "Silver", "1234567890", "john3@example.com", "123 Main St", Date.valueOf("1990-01-01"));
        int userId = userController.addUser(testUser);
        testUser.setUserId(userId);
    }

    @After
    public void tearDown() {
        if (testUser.getUserId() > 0) {
            userController.deleteUser(testUser.getUserId());
        }
    }

    @Test
    public void testAddUser() {
        assertTrue(testUser.getUserId() > 0);
    }

    @Test
    public void testGetUser() {
        User retrievedUser = userController.getUser(testUser.getUserId());
        assertNotNull(retrievedUser);
        assertEquals(testUser.getEmailId(), retrievedUser.getEmailId());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = userController.getAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testUpdateUser() {
        testUser.setFirstName("Jane");
        boolean updated = userController.updateUser(testUser);
        assertTrue(updated);
        User updatedUser = userController.getUser(testUser.getUserId());
        assertEquals("Jane", updatedUser.getFirstName());
    }

    @Test
    public void testDeleteUser() {
        boolean deleted = userController.deleteUser(testUser.getUserId());
        assertTrue(deleted);
        User deletedUser = userController.getUser(testUser.getUserId());
        assertNull(deletedUser);
    }
}
