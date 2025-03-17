<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.model.FlightBooking" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirm Flight Deletion</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" >
    <style>
        body {
            font-family: Arial, sans-serif;
            color: #333;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        .content {
            flex: 1;
            padding: 20px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: left;
        }
        .btn-container {
            margin-top: 20px;
            text-align: center;
        }
        .btn {
            margin: 5px;
        }
        footer {
            background-color: #343a40;
            color: white;
            text-align: center;
            padding: 10px;
            position: relative;
            width: 100%;
        }
    </style>
</head>
<body>
    <jsp:include page="adminheader.jsp" />
    <div class="content">
        <h2 class="text-center">Confirm Flight Deletion</h2>
        <p class="text-center">The following bookings are associated with this flight and will be cancelled:</p>
        
        <table class="table table-bordered">
            <tr>
                <th>Booking ID</th>
                <th>Customer ID</th>
                <th>Booking Amount</th>
                <th>Status</th>
            </tr>
            <%
                List<FlightBooking> bookings = (List<FlightBooking>) request.getAttribute("bookings");
                if (bookings != null && !bookings.isEmpty()) {
                    for (FlightBooking booking : bookings) {
            %>
            <tr>
                <td><%= booking.getBookingID() %></td>
                <td><%= booking.getCustomerID() %></td>
                <td>$<%= booking.getBookingAmount() %></td>
                <td><%= booking.getBookingStatus() %></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        
        <p class="text-center"><strong>Total Refund Amount (if cancelled):</strong> $<%= request.getAttribute("totalRefund") %></p>
        
        <p class="text-center">Are you sure you want to cancel all these bookings and delete this flight?</p>
        
        <div class="btn-container">
            <form action="FlightServlet" method="get" style="display:inline;">
                <input type="hidden" name="action" value="confirmDelete">
                <input type="hidden" name="flightId" value="<%= request.getAttribute("flightId") %>">
                <input type="submit" class="btn btn-danger" value="Yes, Delete Flight">
            </form>
            <a href="FlightServlet?action=list" class="btn btn-secondary">No, Return to Flight List</a>
        </div>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>