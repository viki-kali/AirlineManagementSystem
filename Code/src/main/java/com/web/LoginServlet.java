package com.web;

import com.controller.UserController;
import com.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserController userController;

    @Override
    public void init() throws ServletException {
        userController = new UserController();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        // Retrieve parameters from the login form
        String useridStr = request.getParameter("userid");
        String password = request.getParameter("password");

        // Basic server-side validations
        if (useridStr == null || useridStr.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "User ID and password are required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        int userId = 0;
        try {
            userId = Integer.parseInt(useridStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "User ID must be numeric.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        // Fetch the user using the UserController
        User user = userController.getUser(userId);
        
        // Validate user existence and password match
        if (user == null || !user.getPassword().equals(password)) {
            request.setAttribute("error", "Invalid User ID or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            // Successful login - set user in session
        	HttpSession session = request.getSession();
        	 // Set for both Admin and Customer
        	if (user.getRole().equalsIgnoreCase("Admin")) {
        		session.setAttribute("admin", user); 
        	    response.sendRedirect("CarrierServlet?action=list");
        	    System.out.println("Admin login success");
        	} else if (user.getRole().equalsIgnoreCase("Customer")) {
        		session.setAttribute("user", user); 
        		response.sendRedirect("home.jsp");
        	    System.out.println("User login success");
        	} else {
        	    // Unknown role handling
        	    request.setAttribute("error", "Invalid user role.");
        	    request.getRequestDispatcher("login.jsp").forward(request, response);
        	}
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
            // Prefer customer attribute if available, otherwise use admin's
            User user = (User) (session.getAttribute("user") != null ? session.getAttribute("user") : session.getAttribute("currentUser"));
            // For ProfileServlet, you want to show profile.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
            dispatcher.forward(request, response);
        }
    


}
