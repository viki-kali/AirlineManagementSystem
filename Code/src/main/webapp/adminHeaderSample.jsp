<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/adminHeader.css">
<script src="js/adminHeader.js"></script>

<header class="admin-header">
    <div class="header-container">
        <div class="logo">
            <h1>Admin Dashboard</h1>
        </div>
        <nav>
            <ul class="nav-list">
                <li class="nav-item">
                    <a class="nav-link" href="CarrierServlet?action=list">Carrier</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="FlightServlet?action=list">Flight</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="FlightBookingServlet?action=list">FlightBooking</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="FlightScheduleServlet?action=list">Flight Schedule</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="UserServlet?action=list">Customer Details</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="RefundServlet">Refund</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="LogoutServlet">Logout</a>
                </li>
            </ul>
        </nav>
    </div>
</header>
