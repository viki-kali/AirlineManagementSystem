<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.FlightSchedule" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Flight Schedule Form</title>
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

        .error-message {
            color: red;
            font-size: 14px;
            margin-top: 5px;
        }
    </style>
    <script>
        function validateFlightScheduleForm() {
            let flightId = document.forms["flightScheduleForm"]["flightId"].value;
            let dateOfTravel = document.forms["flightScheduleForm"]["dateOfTravel"].value;
            let businessBooked = document.forms["flightScheduleForm"]["businessBooked"].value;
            let economyBooked = document.forms["flightScheduleForm"]["economyBooked"].value;
            let executiveBooked = document.forms["flightScheduleForm"]["executiveBooked"].value;

            document.getElementById("flightIdError").textContent = "";
            document.getElementById("dateOfTravelError").textContent = "";
            document.getElementById("businessBookedError").textContent = "";
            document.getElementById("economyBookedError").textContent = "";
            document.getElementById("executiveBookedError").textContent = "";

            let isValid = true;

            if (flightId <= 0 || isNaN(flightId)) {
                document.getElementById("flightIdError").textContent = "Flight ID must be a positive integer.";
                isValid = false;
            }

          
            let today = new Date().toISOString().split('T')[0]; // Get today's date in YYYY-MM-DD format
            if (!dateOfTravel || dateOfTravel < today) {
                document.getElementById("dateOfTravelError").textContent = "Date of Travel must be today or a future date.";
                isValid = false;
            }

            
            if (businessBooked < 0 || isNaN(businessBooked)) {
                document.getElementById("businessBookedError").textContent = "Business Booked Count must be a non-negative integer.";
                isValid = false;
            }

          
            if (economyBooked < 0 || isNaN(economyBooked)) {
                document.getElementById("economyBookedError").textContent = "Economy Booked Count must be a non-negative integer.";
                isValid = false;
            }

            
            if (executiveBooked < 0 || isNaN(executiveBooked)) {
                document.getElementById("executiveBookedError").textContent = "Executive Booked Count must be a non-negative integer.";
                isValid = false;
            }

            return isValid;
        }
    </script>
</head>
<body style="background-color:teal;">
    <%
        FlightSchedule schedule = (FlightSchedule) request.getAttribute("schedule");
        boolean isEdit = (schedule != null);
    %>
    <jsp:include page="adminheader.jsp"></jsp:include>
    <div class="register-container">
        <h2><%= isEdit ? "Edit Flight Schedule" : "Add New Flight Schedule" %></h2>
        <form name="flightScheduleForm" action="FlightScheduleServlet" method="post" onsubmit="return validateFlightScheduleForm()">
            <% if(isEdit) { %>
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="<%= schedule.getFlightScheduleID() %>">
            <% } else { %>
                <input type="hidden" name="action" value="insert">
            <% } %>
            
            <label>Flight ID:</label>
            <input type="number" name="flightId" value="<%= isEdit ? schedule.getFlightID() : "" %>" required>
            <div id="flightIdError" class="error-message"></div><br>
            
            <label>Date of Travel (YYYY-MM-DD):</label>
            <input type="date" name="dateOfTravel" value="<%= isEdit ? schedule.getDateOfTravel() : "" %>" required>
            <div id="dateOfTravelError" class="error-message"></div><br>
            
            <label>Business Booked Count:</label>
            <input type="number" name="businessBooked" value="<%= isEdit ? schedule.getBusinessClassBookedCount() : "0" %>" required>
            <div id="businessBookedError" class="error-message"></div><br>
            
            <label>Economy Booked Count:</label>
            <input type="number" name="economyBooked" value="<%= isEdit ? schedule.getEconomyClassBookedCount() : "0" %>" required>
            <div id="economyBookedError" class="error-message"></div><br>
            
            <label>Executive Booked Count:</label>
            <input type="number" name="executiveBooked" value="<%= isEdit ? schedule.getExecutiveClassBookedCount() : "0" %>" required>
            <div id="executiveBookedError" class="error-message"></div><br>
            
            <input type="submit" value="Submit" class="register-btn">
        </form>
        <br>
        <button class="btn-edit" style="width:250px" onclick="window.location.href='FlightScheduleServlet?action=list'">Back to Flight Schedule List</button>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>