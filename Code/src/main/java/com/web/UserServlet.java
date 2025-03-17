package com.web;

import com.controller.UserController;
import com.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserController userController;

    @Override
    public void init() {
        userController = new UserController();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        String action = request.getParameter("action");
        if (action == null) {
            action = "view";
        }

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "insert":
                insertUser(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                updateUser(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            case "list":
                listUsers(request, response);
                break;
            default:
            	listUsers(request, response);
                break;
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	
        List<User> userList = userController.getAllUsers();
        request.setAttribute("userList", userList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("userList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminRegister.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userController.getUser(id);
        request.setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("userForm.jsp");
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	
    	User user = extractUserFromRequest(request);
        userController.addUser(user);
        response.sendRedirect("UserServlet?action=list");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	
        User user = extractUserFromRequest(request);
        user.setUserId(Integer.parseInt(request.getParameter("id")));
        userController.updateUser(user);
        response.sendRedirect("UserServlet?action=list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	
        int id = Integer.parseInt(request.getParameter("id"));
        userController.deleteUser(id);
        response.sendRedirect("UserServlet?action=list");
    }

    private User extractUserFromRequest(HttpServletRequest request) {
        return new User(        		
            0, // User ID (auto-generated for new users)
            request.getParameter("firstName"),
            request.getParameter("lastName"),
            request.getParameter("password"),
            request.getParameter("role"),
            request.getParameter("customerType"),
            request.getParameter("phone"),
            request.getParameter("email"),
            request.getParameter("address"),
            Date.valueOf(request.getParameter("date"))
        );
    }
}
