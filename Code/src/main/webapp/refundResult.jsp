<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.model.FlightBooking" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Refund Result</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
    </style>
</head>
<body>
 <div style="height:90vh;">
   <jsp:include page="adminheader.jsp"></jsp:include>
    <h2>Refund Calculation Result</h2>
    <p><strong>Flight ID:</strong> <%= request.getAttribute("flightId") %></p>
    <p><strong>Date of Travel:</strong> <%= request.getAttribute("travelDate") %></p>
    <p><strong>Total Booking Amount (All affected bookings):</strong> $<%= request.getAttribute("totalRefundAmount") %></p>
    <p><strong>Refund Percentage:</strong> <%= request.getAttribute("refundPercentage") %>%</p>
    <p><strong>Refund Amount:</strong> $<%= request.getAttribute("refundAmount") %></p>
    <p><strong>Extra Compensation (if applicable):</strong> $<%= request.getAttribute("compensationAmount") %></p>
    <p><strong>Total Loss Incurred by Carrier:</strong> $<%= request.getAttribute("totalLoss") %></p>
     
    <h3>Affected Bookings</h3>
    <table>
        <tr>
            <th>Booking ID</th>
            <th>Customer ID</th>
            <th>Booking Amount</th>
            <th>Status</th>
        </tr>
        <%
            List<FlightBooking> bookings = (List<FlightBooking>) request.getAttribute("affectedBookings");
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
            } else {
        %>
        <tr>
            <td colspan="4">No bookings found for this flight on the given date.</td>
        </tr>
        <% } %>
    </table>
    <br>
    <a href="RefundServlet">Back to Refund Calculation</a> | <a href="CarrierServlet?action=list">Back to Dashboard</a>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
