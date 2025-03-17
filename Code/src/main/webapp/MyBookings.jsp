<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="com.model.*,java.util.*,com.usercontroller.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>My Booked Trips</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/datastyles.css">
    <link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css">
</head>
<body>
	<jsp:include page="userheader.jsp"/>
	<% List<FlightBooking> fb= (List<FlightBooking>)request.getAttribute("bookingList");
		User user = (User)session.getAttribute("user");
	%>
	<%!	FlightService fs = new FlightService();%>
	<%! CarrierService cs = new CarrierService();%>
	<%!UserController us = new UserController();%>
	
	<% %>

	
	<%if(fb!=null && fb.size()>0){ %>
    <div class="con1">
    <%String status =(String) request.getParameter("status");
     String status1 = (String) request.getParameter("bookStatus");
     
	 if(status!=null){%>
		 <h4 align="center" style="color:red"><%=status%></h4>
	 <%}
	 if(status1!=null){%>
	  <h4 align="center" style="color:red"><%=status1%></h4>
	 <%}%>
      <table border="1">
        <tr>
        	<th>Flight Booking Id</th>
        	<th>Flight Name</th>
        	<th>Customer Name</th>
        	<th>Origin</th>
        	<th>Destination</th>
        	<th>Date of Booking</th>
        	<th>Date of Travel</th>
        	<th>No of seats</th>
        	<th>Seat Category</th>
        	<th>Booking Status</th>
        	<th>Amount</th>  
        	<th>Refunded Amount</th>
        	<th>Action</th>      	
        </tr>
        
        <%for(FlightBooking f:fb){
        %>
        <tr>
        	
        	<td><%= f.getBookingID() %></td>
        	<td><%= (f.getFlightID()==0)?"NULL":cs.getCarrier(fs.getFlight(f.getFlightID()).getCarrierId()).getCarrierName()%></td>
        	<td><%= (f.getCustomerID()==0)?"NULL":us.getUser(f.getCustomerID()).getFirstName()%></td>
        	<td><%= (f.getFlightID()==0)?"NULL":fs.getFlight(f.getFlightID()).getOrigin()%></td>
        	<td><%= (f.getFlightID()==0)?"NULL":fs.getFlight(f.getFlightID()).getDestination()%></td>
        	<td><%= f.getDateOfBooking() %></td>
        	<td><%= f.getDateOfTravel()%></td>
        	<td><%= f.getNoOfSeats()%></td>
        	<td><%= f.getSeatCategory()%></td>
        	<td><%= f.getBookingStatus()%></td>
        	<td><%= f.getBookingAmount()%></td>
        	<td><%= f.getRefundAmount() %></td>
        	<%if(f.getBookingStatus().equals("Booked")){%>
        	<td><a class="btn btn-danger" href="UserBookingsServlet?action=deleteConfirmation&flightBookingId=<%=f.getBookingID()%>">Cancel Tickets</a></td>
        	<%} else{%>
        	<td>No action required</td>
        	<%} %>
        </tr>
        <%} %>
       
      </table>
</div>
<%}
else{
String message =(String) request.getAttribute("message"); %>
<div class="con1">
<%= message %><%} %></div>


	<jsp:include page="footer.jsp"/>
	

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	
</body>
</html>