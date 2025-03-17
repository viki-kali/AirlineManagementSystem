<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.model.*,com.usercontroller.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css">
 <style>
 
div1 {
    font-family: Arial, sans-serif;
    background-color: #f4f4f4;
    
    height: 100vh;
}


.f1 {
	
	
    height: 80vh;
    background: white;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    width: 350px;
    text-align: center;
}


label {
    display: block;
    margin: 10px 0 5px;
    font-weight: bold;
    text-align: left;
}

input, select {
    width: 100%;
    padding: 10px;
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 14px;
}
select {
    background: white;
    cursor: pointer;
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


input[disabled] {
    background-color: #e9ecef;
    cursor: not-allowed;
}
   
</style>
</head>
<body>
<jsp:include page="userheader.jsp"/>
<div class="div1 d-flex flex-row justify-content-center">
<div>
<%FlightSchedule fs = (FlightSchedule)request.getAttribute("selectedSchedule"); 
FlightService fss = new FlightService();
FlightDetails f = fss.getFlight(fs.getFlightID());%>
	<form method="get" action="FlightSearchServlet" class="f1">
	<label>Flight ID</label>
	<input type="number" value=<%=fs.getFlightID() %> id="flightId" disabled/>
	<input type="hidden" value=<%=fs.getFlightID() %> name="flightId"/>
	<br>
	<label>Origin</label>
	<input type="text" value=<%=f.getOrigin() %> id="origin" disabled/>
	<input type="hidden" value=<%=f.getOrigin() %> name="origin"/>
	<br>
	<label>Destination</label>
	<input type="text" value=<%=f.getDestination() %> id="destination" disabled/>
	<input type="hidden" value=<%=f.getDestination() %> name="destination"/>
	<br>
	<label>Travel Date</label>
	<input type="text" value=<%=fs.getDateOfTravel() %> id="date" disabled/>
	<input type="hidden" value=<%=fs.getDateOfTravel() %> name="date"/>
	<br>
	<label>No of Seats</label>
	<input type="number" name="noOfSeats" min="1" required/>
	<br>
	<label>Seat Category</label>
	<select name="category">
	<option value="Economy" name ="category">Economy</option>
	<option value="Executive" name ="category">Executive</option>
	<option value="Business" name ="category">Business</option>
	</select>
	<br><br>
	<button type="submit" name="action" value="checkAvailability">Book</button>
	
	</form>
</div>
</div>
	<jsp:include page="footer.jsp"/>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>