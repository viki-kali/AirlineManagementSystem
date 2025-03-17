<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.FlightBooking" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Booking Confirmation - Airline Management System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 20px;
        }
        .container {
            width: 60%;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.3);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #343a40;
            color: #fff;
            text-transform: uppercase;
        }
        .summary {
            margin-top: 20px;
            text-align: center;
            font-size: 18px;
            color: #333;
        }
        
        .confirmation-container {
            margin-top: 30px;
            text-align: center;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #e9ecef;
        }
        .button-container {
            margin-top: 20px;
        }
        .btn {
            padding: 10px 20px;
            margin: 0 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn-confirm {
            background-color: #28a745;
            color: #fff;
        }
        .btn-cancel {
            background-color: #dc3545;
            color: #fff;
        }
    </style>
    <script>
    function confirmBooking() {
        alert("Tickets confirmed!");
        window.location.href = "ProfileServlet";
    }

    function cancelBooking() {
        if (confirm("Do you want to cancel the booking?")) {
            window.location.href = "bookFlight.jsp";
        }
    }
    </script>
</head>
<body>
    <div class="container">
        <%
           
            FlightBooking booking = (FlightBooking) request.getAttribute("booking");
            Double totalAmount = (Double) request.getAttribute("totalAmount");
            Double discountPercentage = (Double) request.getAttribute("discountPercentage");
            Double discountAmount = (Double) request.getAttribute("discountAmount");
            Double finalAmount = (Double) request.getAttribute("finalAmount");
        %>
        <% if (booking == null) { %>
            <p>No booking details available.</p>
        <% } else { %>
            <h2>Booking Confirmation</h2>
            <table>
                <tr>
                    <th>Booking ID</th>
                    <td><%= booking.getBookingID() %></td>
                </tr>
                <tr>
                    <th>Flight ID</th>
                    <td><%= booking.getFlightID() %></td>
                </tr>
                <tr>
                    <th>Customer ID</th>
                    <td><%= booking.getCustomerID() %></td>
                </tr>
                <tr>
                    <th>Number of Seats</th>
                    <td><%= booking.getNoOfSeats() %></td>
                </tr>
                <tr>
                    <th>Seat Category</th>
                    <td><%= booking.getSeatCategory() %></td>
                </tr>
                <tr>
                    <th>Date of Booking</th>
                    <td><%= booking.getDateOfBooking() %></td>
                </tr>
                <tr>
                    <th>Date of Travel</th>
                    <td><%= booking.getDateOfTravel() %></td>
                </tr>
                <tr>
                    <th>Booking Status</th>
                    <td><%= booking.getBookingStatus() %></td>
                </tr>
                <tr>
                    <th>Booking Amount</th>
                    <td>$<%= booking.getBookingAmount() %></td>
                </tr>
                <% if (totalAmount != null && discountPercentage != null && discountAmount != null && finalAmount != null) { %>
                <tr>
                    <th>Total Ticket Price (Before Discount)</th>
                    <td>$<%= totalAmount %></td>
                </tr>
                <tr>
                    <th>Discount Applied</th>
                    <td><%= discountPercentage %>% (-$<%= discountAmount %>)</td>
                </tr>
                <tr>
                    <th>Final Amount to Pay</th>
                    <td>$<%= finalAmount %></td>
                </tr>
                <% } %>
            </table>
            <div class="confirmation-container">
                <h2>Booking Confirmation</h2>
                <p>Your flight has been successfully booked!</p>
                <div class="button-container">
                    <button class="btn btn-confirm" onclick="confirmBooking()">Confirm</button>
                    <button class="btn btn-cancel" onclick="cancelBooking()">Cancel</button>
                </div>
            </div>
        <% } %>
    </div>
</body>
</html>
