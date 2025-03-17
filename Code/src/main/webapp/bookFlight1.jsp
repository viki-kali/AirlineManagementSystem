<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="com.model.FlightSearchResult" %>
<%@ page import="com.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Flight - Airline Management System</title>
    <link rel="stylesheet" href="css/bookFlight.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css">
    <script src="js/bookFlight.js"></script>
</head>
<body>
   
        <jsp:include page="userheader.jsp" />

    
    <% 
        HttpSession sessions = request.getSession(false);
        if (sessions == null || sessions.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
    %>
    
   
    <main class="main-content">
       
        <div class="search-container">
            <h2>Search Flights</h2>
            <form id="searchForm" action="FlightServlet?action=search" method="get">
                <input type="hidden" name="action" value="search">
                <input type="text" id="origin" name="origin" placeholder="Origin">
                <input type="text" id="destination" name="destination" placeholder="Destination">
                <input type="date" id="travelDate" name="travelDate" required>
                <button type="submit" class="search-btn">Search</button>
            </form>
        </div>
        
       
        <div class="table-container">
            <table class="table" id="flightTable">
                <thead>
                    <tr>
                        <th>Flight ID</th>
                        <th>Carrier ID</th>
                        <th>Flight Schedule ID</th>
                        <th>Origin</th>
                        <th>Destination</th>
                        <th>Date Of Travel</th>
                        <th>Airfare</th>
                        <th>Available Economy Seats</th>
                        <th>Available Executive Seats</th>
                        <th>Available Business Seats</th>
                        <th>Select Flight</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    List<FlightSearchResult> flightList = (List<FlightSearchResult>) request.getAttribute("flightList");
                    if (flightList != null) {
                        for (FlightSearchResult result : flightList) {
                %>
                    <tr>
                        <td><%= result.getFlightId() %></td>
                        <td><%= result.getCarrierId() %></td>
                        <td><%= result.getFlightScheduleId() %></td>
                        <td><%= result.getOrigin() %></td>
                        <td><%= result.getDestination() %></td>
                        <td><%= result.getDateOfTravel() %></td>
                        <td>$<%= result.getAirfare() %></td>
                        <td><%= result.getAvailableEconomySeats() %></td>
                        <td><%= result.getAvailableExecutiveSeats() %></td>
                        <td><%= result.getAvailableBusinessSeats() %></td>
                        <td>
                            <button type="button" class="select-btn"
                                onclick="selectFlight('<%= result.getFlightId() %>', '<%= result.getAvailableEconomySeats() %>', '<%= result.getAvailableExecutiveSeats() %>', '<%= result.getAvailableBusinessSeats() %>', '<%= result.getDateOfTravel() %>')">
                                Select Flight
                            </button>
                        </td>
                    </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
        </div>
        
        
        <div class="booking-container">
            <h2>Book Flight</h2>
            <form id="bookingForm" action="FlightBookingServlet?action=book" method="post" onsubmit="return validateBookingForm();">
                <!-- Hidden fields to store selected flight details -->
                <input type="hidden" id="selectedFlightId" name="flightId" value="">
                <input type="hidden" id="selectedTravelDate" name="dateOfTravel" value="">
                
                <label for="seatCategory">Seat Category:</label>
                <select id="seatCategory" name="seatCategory" required>
                    <option value="">Select Seat Category</option>
                    <option value="Economy">Economy</option>
                    <option value="Executive">Executive</option>
                    <option value="Business">Business</option>
                </select>
                
                <label for="numberOfSeats">Number of Seats:</label>
                <input type="number" id="numberOfSeats" name="numberOfSeats" min="1" required>
                <span id="seatError" style="color:red;"></span>
                
                <button type="submit" class="book-btn">Book</button>
            </form>
        </div>
    </main>
    
    
    <footer class="site-footer">
        <jsp:include page="footer.jsp" />
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
