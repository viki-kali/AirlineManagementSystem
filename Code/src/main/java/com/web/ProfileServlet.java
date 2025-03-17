package com.web;

import com.controller.UserController;
import com.model.User;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserController userController;

    @Override
    public void init() throws ServletException {
        userController = new UserController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
       
            RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
            dispatcher.forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle profile update.
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        // Get the current user from session.
        User user = (User) session.getAttribute("user");
        
        // Retrieve editable fields from the form.
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String dobStr = request.getParameter("date"); // Expected format: YYYY-MM-DD
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phone = request.getParameter("phoneNumber");

        // Update the user details. Fields like userId, role, and customerType remain unchanged.
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setDateOfBirth(Date.valueOf(dobStr));
        user.setEmailId(email);
        user.setAddress(address);
        user.setPhone(phone);
        
        // Update the user in the database.
        boolean updated = userController.updateUser(user);
        if (updated) {
            // Update the user object in session.
            session.setAttribute("user", user);
            request.setAttribute("message", "Profile updated successfully.");
        } else {
            request.setAttribute("message", "Failed to update profile. Please try again.");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
        dispatcher.forward(request, response);
    }
}
