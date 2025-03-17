<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.User" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    HttpSession sessions = request.getSession(false);
    User customer = (User) sessions.getAttribute("customer");
    if (customer == null || !customer.getRole().equalsIgnoreCase("Customer")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h2 {
            color: #333;
        }
        ul.menu {
            list-style-type: none;
            padding: 0;
        }
        ul.menu li {
            margin: 10px 0;
        }
        ul.menu li a {
            text-decoration: none;
            font-size: 1.1em;
            color: #0066cc;
        }
        ul.menu li a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h2>Welcome, <%= customer.getFirstName()+" "+customer.getLastName() %>!</h2>
    <ul class="menu">
        <li><a href="User?action=view&id=<%= customer.getUserId() %>">View Profile</a></li>
        <li><a href="User?action=edit&id=<%= customer.getUserId() %>">Edit Profile</a></li>
        <li><a href="FlightBookingServlet?action=new">Book a Flight</a></li>
        <li><a href="FlightBookingServlet?action=listmybooking">View My Bookings</a></li>
        <li><a href="LogoutServlet">Logout</a></li>
    </ul>
</body>
</html>
