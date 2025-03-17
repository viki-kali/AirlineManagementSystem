<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.model.FlightDetails" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Flight List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" 
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" 
          crossorigin="anonymous">
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
        }
        .table-container {
            max-height: 500px;
            overflow-y: auto;
            margin-top: 20px;
        }
        .table {
            width: 100%;
            border-collapse: collapse;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .table th {
            background-color: #343a40;
            color: white;
            text-transform: uppercase;
            border: 1px solid #dee2e6;
            padding: 12px;
            text-align: left;
        }
        .table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        .table tbody tr {
            background-color: #fff;
            transition: background-color 0.3s ease;
        }
        .table tbody tr:hover {
            background-color: #f2f2f2;
        }
        .btn-edit, .btn-delete {
            padding: 8px 12px;
            border: none;
            cursor: pointer;
            border-radius: 4px;
            color: white;
        }
        .btn-edit {
            background-color: #5cb85c;
        }
        .btn-delete {
            background-color: #d9534f;
        }
        .add-carrier-button {
            text-align: center;
        }
        button {
            margin: 7px;
            width: 100px;
        }
        .add-new {
            width: 150px;
        }
        .lesswidth {
            width: 100px;
        }
        #myInput {
            width: 20%;
            font-size: 16px;
            padding: 12px 20px 12px 40px;
            border: 1px solid #ddd;
            margin-bottom: 12px;
        }
        footer {
            background-color: #343a40;
            color: white;
            text-align: center;
            padding: 10px 0;
            margin-top: auto;
        }
    </style>
</head>
<body>
    <jsp:include page="adminheader.jsp"></jsp:include>
    <div class="content">
        <h2 class="text-center mt-3">List of Flights</h2>
        <div class="add-carrier-button">
            <button class="btn btn-primary add-new" onclick="window.location.href='FlightServlet?action=new'">Add New Flight</button>
        </div>
        <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for Flight Id.." title="Type in a name">
        <div class="table-container">
            <table class="table" id="myTable">
                <tr>
                    <th>Flight ID</th>
                    <th>Carrier ID</th>
                    <th>Origin</th>
                    <th>Destination</th>
                    <th>Airfare</th>
                    <th>Economy Seats</th>
                    <th>Business Seats</th>
                    <th>Executive Seats</th>
                    <th class="lesswidth">Actions</th>
                </tr>
                <%
                    List<FlightDetails> listFlights = (List<FlightDetails>) request.getAttribute("listFlights");
                    if(listFlights != null) {
                        for(FlightDetails flight : listFlights) {
                %>
                <tr>
                    <td><%= flight.getFlightID() %></td>
                    <td><%= flight.getCarrierId() %></td>
                    <td><%= flight.getOrigin() %></td>
                    <td><%= flight.getDestination() %></td>
                    <td>$<%= flight.getAirfare() %></td>
                    <td><%= flight.getSeatCapacityEconomyClass() %></td>
                    <td><%= flight.getSeatCapacityBusinessClass() %></td>
                    <td><%= flight.getSeatCapacityExecutiveClass() %></td>
                    <td>
                        <button class="btn-edit" onclick="window.location.href='FlightServlet?action=edit&id=<%= flight.getFlightID() %>'">Edit</button>
                        <button class="btn-delete" onclick="if(confirm('Are you sure?')) window.location.href='FlightServlet?action=delete&id=<%= flight.getFlightID() %>'">Delete</button>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
    <!-- Reference the external flightList.js file -->
    <script src="js/flightList.js"></script>
</body>
</html>
