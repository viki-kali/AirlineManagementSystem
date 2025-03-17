<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.FlightBooking" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Flight Booking Form</title>
    <style>
        .register-container {
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            padding: 5px;
            height: 80%;
            width: 30%;
            overflow-y: auto;
            padding-right: 20px;
            background: rgb(220, 220, 220);
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
            margin: auto;
            text-align: center;
        }

        h2 {
            color: black;
        }

        input, textarea, select {
            width: 90%;
            padding: 10px;
            margin: 10px;
            border: 1px solid black;
            border-radius: 5px;
        }

        .register-btn {
            margin-top: 10px;
            width: 50%;
            padding: 10px;
            background-color: dodgerblue;
            color: white;
            font-size: 18px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .register-btn:hover {
            background-color: cobalt;
        }

        .reset-btn {
            width: 40%;
            padding: 10px;
            background-color: gray;
            color: white;
            font-size: 18px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        #acknowledgment {
            margin-top: 15px;
            color: green;
            display: none;
        }

        a {
            text-decoration: none;
            color: white;
        }
    </style>
    <script>
        function validateFlightBookingForm() {
            let flightId = document.forms["flightBookingForm"]["flightId"].value;
            let customerId = document.forms["flightBookingForm"]["customerId"].value;
            let noOfSeats = document.forms["flightBookingForm"]["noOfSeats"].value;
            let seatCategory = document.forms["flightBookingForm"]["seatCategory"].value;
            let dateOfBooking = document.forms["flightBookingForm"]["dateOfBooking"].value;
            let dateOfTravel = document.forms["flightBookingForm"]["dateOfTravel"].value;
            let bookingStatus = document.forms["flightBookingForm"]["bookingStatus"].value;
            let bookingAmount = document.forms["flightBookingForm"]["bookingAmount"].value;
            let refundAmount = document.forms["flightBookingForm"]["refundAmount"].value;

            
            if (flightId <= 0 || isNaN(flightId)) {
                alert("Flight ID must be a positive integer.");
                return false;
            }

          
            if (customerId <= 0 || isNaN(customerId)) {
                alert("Customer ID must be a positive integer.");
                return false;
            }

           
            if (noOfSeats <= 0 || isNaN(noOfSeats)) {
                alert("Number of Seats must be greater than 0.");
                return false;
            }

           
            if (!["Economy", "Executive", "Business"].includes(seatCategory)) {
                alert("Seat Category must be one of Economy, Executive, or Business.");
                return false;
            }

          
            if (!dateOfBooking || !dateOfTravel) {
                alert("Date of Booking and Date of Travel are required.");
                return false;
            }

           
            if (!["Booked", "Travel Completed", "Cancelled"].includes(bookingStatus)) {
                alert("Booking Status must be one of Booked, Travel Completed, or Cancelled.");
                return false;
            }

          
            if (bookingAmount < 0 || isNaN(bookingAmount)) {
                alert("Booking Amount must be greater than or equal to 0.");
                return false;
            }

            
            if (refundAmount < 0 || isNaN(refundAmount)) {
                alert("Refund Amount must be greater than or equal to 0.");
                return false;
            }

            return true;
        }
    </script>
</head>
<body style="background-color:teal">
    <%
        FlightBooking booking = (FlightBooking) request.getAttribute("booking");
        boolean isEdit = (booking != null);
    %>
    <jsp:include page="adminheader.jsp"></jsp:include>
    <div class="register-container">
        <h2><%= isEdit ? "Edit Flight Booking" : "Add New Flight Booking" %></h2>
        <form name="flightBookingForm" action="BookingServlet" method="post" onsubmit="return validateFlightBookingForm()">
            <% if (isEdit) { %>
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="<%= booking.getBookingID() %>">
            <% } else { %>
                <input type="hidden" name="action" value="insert">
            <% } %>
            
            <label>Flight ID:</label>
            <input type="number" name="flightID" value="<%= isEdit ? booking.getFlightID() : "" %>" required><br>
            
            <label>Customer ID:</label>
            <input type="number" name="customerID" value="<%= isEdit ? booking.getCustomerID() : "" %>" required><br>
            
            <label>No. of Seats:</label>
            <input type="number" name="noOfSeats" value="<%= isEdit ? booking.getNoOfSeats() : "" %>" required><br>
            
            <label>Seat Category:</label>
            <select name="seatCategory" required>
                <option value="Economy" <%= isEdit && booking.getSeatCategory().equals("Economy") ? "selected" : "" %>>Economy</option>
                <option value="Executive" <%= isEdit && booking.getSeatCategory().equals("Executive") ? "selected" : "" %>>Executive</option>
                <option value="Business" <%= isEdit && booking.getSeatCategory().equals("Business") ? "selected" : "" %>>Business</option>
            </select><br>
            
            <label>Date of Booking (YYYY-MM-DD):</label>
            <input type="date" name="dateOfBooking" value="<%= isEdit ? booking.getDateOfBooking() : "" %>" required><br>
            
            <label>Date of Travel (YYYY-MM-DD):</label>
            <input type="date" name="dateOfTravel" value="<%= isEdit ? booking.getDateOfTravel() : "" %>" required><br>
            
            <label>Booking Status:</label>
            <select name="bookingStatus" required>
                <option value="Booked" <%= isEdit && booking.getBookingStatus().equals("Booked") ? "selected" : "" %>>Booked</option>
                <option value="Travel Completed" <%= isEdit && booking.getBookingStatus().equals("Travel Completed") ? "selected" : "" %>>Travel Completed</option>
                <option value="Cancelled" <%= isEdit && booking.getBookingStatus().equals("Cancelled") ? "selected" : "" %>>Cancelled</option>
            </select><br>
            
            <label>Booking Amount:</label>
            <input type="number" step="0.01" name="bookingAmount" value="<%= isEdit ? booking.getBookingAmount() : "" %>" required><br>

            <label>Refund Amount:</label>
            <input type="number" step="0.01" name="refundAmount" value="<%= isEdit ? booking.getRefundAmount() : "" %>" required><br>        
            <input type="submit" value="Submit" class="register-btn">
        </form>
        <br>
        <button class="btn-edit" style="width:250px;" onclick="window.location.href='FlightBookingServlet?action=list'">Back to Flight Booking List</button>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>