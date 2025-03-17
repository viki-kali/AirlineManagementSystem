<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.User" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    HttpSession sessions = request.getSession(false);
    User admin = (User) sessions.getAttribute("customer");
    if (admin == null || !admin.getRole().equalsIgnoreCase("Admin")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h2 { color: #333; }
        .menu { list-style-type: none; padding: 0; }
        .menu li { margin: 10px 0; }
        .menu a { text-decoration: none; font-size: 1.1em; color: #0066cc; }
        .menu a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h2>Welcome, <%= admin.getFirstName() %> (Admin)</h2>
    <ul class="menu">
        <li><a href="CarrierServlet?action=list">Manage Carriers</a></li>
        <li><a href="FlightServlet?action=list">Manage Flights</a></li>
        <li><a href="FlightScheduleServlet?action=list">Manage Flight Schedules</a></li>
        <li><a href="FlightBookingServlet?action=list">Manage Flight Bookings</a></li>
        <li><a href="UserServlet?action=list">Manage Customers</a></li>
        <li><a href="RefundServlet">Flight Cancellation Refund Calculation</a></li>
        <li><a href="LogoutServlet">Logout</a></li>
    </ul>
</body>
</html>
