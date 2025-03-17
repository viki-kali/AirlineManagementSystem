<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*,com.model.*,com.usercontroller.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css">
 <style>
       
table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
    background: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    overflow: hidden;
}

th {
    background-color: #007BFF;
    color: white;
    padding: 12px;
    text-align: left;
    font-size: 16px;
}


td {
    padding: 10px;
    border-bottom: 1px solid #ddd;
    text-align: left;
}


tr:nth-child(even) {
    background-color: #f9f9f9;
}


tr:hover {
    background-color: #f1f1f1;
}


td a {
    display: inline-block;
    padding: 6px 10px;
    background-color: #28a745;
    color: white;
    text-decoration: none;
    border-radius: 5px;
    font-weight: bold;
}

td a:hover {
    background-color: #218838;
}
        
        
        
        ////////////////////////////////
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

       
        .con1 {
            width: 40%;
            margin: 100px auto;
            padding: 20px;
            background: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            text-align: center;
        }

        
        h2 {
            margin-bottom: 20px;
            color: #333;
        }

        label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
            text-align: left;
        }

        input {
            width: 95%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        
        button {
            width: 100%;
            padding: 10px;
            background-color: #007BFF;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
	<jsp:include page="userheader.jsp"/>
	<div class="con1">
        <h2>Search Flights</h2>
        <form action="FlightSearchServlet" method="GET">
            <label for="origin">Origin:</label>
            <input type="text" id="origin" name="origin" placeholder="Enter departure city" required>
            
            <label for="destination">Destination:</label>
            <input type="text" id="destination" name="destination" placeholder="Enter arrival city" required>
            
            <label for="dateOfTravel">Date of Travel:</label>
            <input type="date" id="dateOfTravel" name="dateOfTravel" required>
            <br>
            <br>
            
            <button type="submit" name="action" value="search">Search Flights</button>
        </form>
       
    </div>
     <div class="new">
        	<%List<FlightSchedule> fs = (List<FlightSchedule>)request.getAttribute("scheduledFlights");
        	FlightScheduleService fss = new FlightScheduleService();
        	FlightService fservice = new FlightService();
        	CarrierService cs = new CarrierService();
        	String message =(String) request.getAttribute("message");
        	if(fs!=null && fs.size()>0){
        	%>
        	<table>
        		<tr>
        		<th>Flight Id</th>
        		<th>Origin</th>
        		<th>Destination</th>
        		<th>DateOfTravel</th>
        		<th>Basic AirFare</th>
        		<th>Economy Seats Available</th>
        		<th>Business Seats Available</th>
        		<th>Executive Seats Available</th>
        		<th>Action</th>
        		</tr>
        		<%for(FlightSchedule f:fs){ %>
        		<tr>
        		<% FlightDetails flight = fservice.getFlight(f.getFlightID());%>
        			<td><%= f.getFlightID()%></td>
        			<td><%= flight.getOrigin()%></td>
        			<td><%= flight.getDestination()%></td>
        			<td><%= f.getDateOfTravel()%></td>
        			<td><%= flight.getAirfare()%></td>
        			<td><%= flight.getSeatCapacityEconomyClass()-f.getEconomyClassBookedCount()%></td>
        			<td><%= flight.getSeatCapacityBusinessClass()-f.getBusinessClassBookedCount()%></td>
        			<td><%= flight.getSeatCapacityExecutiveClass()-f.getExecutiveClassBookedCount()%></td>
        			<td><a href="FlightSearchServlet?action=preBooking&fsid=<%=f.getFlightScheduleID()%>">Book</a></td>
        		</tr>
        		<%} %>
        	</table>
        	<%}else if(message!=null){ %>
        		<p style="text-align:center;color:red;"><%=message %></p>
        	<%} %>
        </div>
        <jsp:include page="footer.jsp"/>
  
		<script>
		    document.addEventListener("DOMContentLoaded", function() {
		        let today = new Date().toISOString().split('T')[0]; 
		        document.getElementById("dateOfTravel").setAttribute("min", today);
		    });
		</script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>        
    
</body>
</html>