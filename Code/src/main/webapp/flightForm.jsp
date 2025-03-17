<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.FlightDetails" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Flight Form</title>
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
        input, textarea {
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
            background-color: royalblue;
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
        /* Style for error messages */
        .error-message {
            color: red;
            font-size: 14px;
            margin-top: -5px;
            margin-bottom: 10px;
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
        function validateFlightForm() {
            
            document.getElementById("carrierIdError").innerHTML = "";
            document.getElementById("originError").innerHTML = "";
            document.getElementById("destinationError").innerHTML = "";
            document.getElementById("airfareError").innerHTML = "";
            document.getElementById("economySeatsError").innerHTML = "";
            document.getElementById("businessSeatsError").innerHTML = "";
            document.getElementById("executiveSeatsError").innerHTML = "";

            let valid = true;
            
            let carrierId = parseInt(document.forms["flightForm"]["carrierId"].value);
            let origin = document.forms["flightForm"]["origin"].value.trim();
            let destination = document.forms["flightForm"]["destination"].value.trim();
            let airfare = parseFloat(document.forms["flightForm"]["airfare"].value);
            let economySeats = parseInt(document.forms["flightForm"]["economySeats"].value);
            let businessSeats = parseInt(document.forms["flightForm"]["businessSeats"].value);
            let executiveSeats = parseInt(document.forms["flightForm"]["executiveSeats"].value);

            if (isNaN(carrierId) || carrierId <= 0) {
                document.getElementById("carrierIdError").innerHTML = "Carrier ID must be a positive integer.</br>";
                valid = false;
            }
            if (origin === "") {
                document.getElementById("originError").innerHTML = "Origin cannot be empty.</br>";
                valid = false;
            }
            if (destination === "") {
                document.getElementById("destinationError").innerHTML = "Destination cannot be empty.</br>";
                valid = false;
            }
            if (origin === destination && origin !== "") {
                document.getElementById("destinationError").innerHTML = "Origin and Destination cannot be the same.</br>";
                valid = false;
            }
            if (isNaN(airfare) || airfare <= 0) {
                document.getElementById("airfareError").innerHTML = "Airfare must be greater than 0.</br>";
                valid = false;
            }
            if (isNaN(economySeats) || economySeats < 20) {
                document.getElementById("economySeatsError").innerHTML = "Economy Seat Capacity must be at least 20.</br>";
                valid = false;
            }
            if (isNaN(businessSeats) || businessSeats < 10) {
                document.getElementById("businessSeatsError").innerHTML = "Business Seat Capacity must be at least 10.</br>";
                valid = false;
            }
            if (isNaN(executiveSeats) || executiveSeats < 10) {
                document.getElementById("executiveSeatsError").innerHTML = "Executive Seat Capacity must be at least 10.</br>";
                valid = false;
            }
            return valid;
        }
    </script>
</head>
<body style="background-color: teal;">
    <jsp:include page="adminheader.jsp"></jsp:include>

    <%
        FlightDetails flight = (FlightDetails) request.getAttribute("flight");
        boolean isEdit = (flight != null);
    %>
    
    <div class="register-container">
        <h2><%= isEdit ? "Edit Flight" : "Add New Flight" %></h2>
        <form name="flightForm" action="FlightServlet" method="post" onsubmit="return validateFlightForm()">
            <% if(isEdit) { %>
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="<%= flight.getFlightID() %>">
            <% } else { %>
                <input type="hidden" name="action" value="insert">
            <% } %>
            
            <label>Carrier ID:</label>
            <input type="number" name="carrierId" value="<%= isEdit ? flight.getCarrierId() : "" %>" required><br>
            <span class="error-message" id="carrierIdError"></span>
            
            <label>Origin:</label>
            <input type="text" name="origin" value="<%= isEdit ? flight.getOrigin() : "" %>" required><br>
            <span class="error-message" id="originError"></span>
            
            <label>Destination:</label>
            <input type="text" name="destination" value="<%= isEdit ? flight.getDestination() : "" %>" required><br>
            <span class="error-message" id="destinationError"></span>
            
            <label>Airfare:</label>
            <input type="number" name="airfare" step="0.01" value="<%= isEdit ? flight.getAirfare() : "" %>" required><br>
            <span class="error-message" id="airfareError"></span>
            
            <label>Economy Seat Capacity:</label>
            <input type="number" name="economySeats" value="<%= isEdit ? flight.getSeatCapacityEconomyClass() : "" %>" required><br>
            <span class="error-message" id="economySeatsError"></span>
            
            <label>Business Seat Capacity:</label>
            <input type="number" name="businessSeats" value="<%= isEdit ? flight.getSeatCapacityBusinessClass() : "" %>" required><br>
            <span class="error-message" id="businessSeatsError"></span>
            
            <label>Executive Seat Capacity:</label>
            <input type="number" name="executiveSeats" value="<%= isEdit ? flight.getSeatCapacityExecutiveClass() : "" %>" required><br>
            <span class="error-message" id="executiveSeatsError"></span>
            
            <input type="submit" value="Submit" class="register-btn">
        </form>
        <br>
        <button onclick="window.location.href='FlightServlet?action=list'" class="btn-edit" style="width:200px">Back to Flight List</button>
    </div>

    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
