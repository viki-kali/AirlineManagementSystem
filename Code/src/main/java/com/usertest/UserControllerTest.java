package com.usertest;

import com.usercontroller.UserController;
import com.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;

import java.util.List;

public class UserControllerTest {

    private UserController userController;
    private User testUser;

    @Before
    public void setUp() {
        userController = new UserController();
        testUser = new User(0, "John","Doe","password123","Customer","Silver","9876543210","john123@example.com","abc",Date.valueOf("2000-05-12"));
    }

    @After
    public void tearDown() {
        testUser = null;
    }



    @Test
    public void testGetUser() {
        User retrievedUser = userController.getUser(10003);
        System.out.println(retrievedUser);
        assertNotNull(retrievedUser); // Since no actual DB connection exists
    }

//    @Test
//    public void testInsertUser() {
//        int result = userController.insertUser(testUser);
//        assertEquals(1, result); // Expected default return
//    }

//    @Test
//    public void testGetUserId() {
//        int userId = userController.getUserId();
//        assertEquals(10103, userId); // Expected default return
//    }

//    @Test
//    public void testGetUsername() {
//        User user = userController.getUsername(10000, "admin123");
//        assertNotNull(user); // Since no actual DB connection exists
//    }

//    @Test
//    public void testDeleteUser() {
//        boolean deleted = userController.deleteUser(10002);
//        assertTrue(deleted); // Since no actual DB connection exists
//    }

//    @Test
//    public void testUpdateUser() {
//    	User testuser1=userController.getUser(10003);
//    	testuser1.setFirstName("HelloWorld");
//        User updatedUser = userController.updateUser(testuser1);
//        assertNotNull(updatedUser); // Since no actual DB connection exists
//    }
}