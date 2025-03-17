<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.model.FlightSchedule" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Flight Schedule List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
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
            width: 200px;
        }
        .lesswidth {
            width: 150px;
        }
        #myInput {
            width: 20%;
            font-size: 16px;
            padding: 12px 20px 12px 40px;
            border: 1px solid #ddd;
            margin-bottom: 12px;
        }
    </style>
</head>
<body>
    <jsp:include page="adminheader.jsp"></jsp:include>
    <div class="content">
        <h2 class="text-center mt-3" >Flight Schedule List</h2>
        <div class="add-carrier-button">
            <button class="btn btn-primary add-new" onclick="window.location.href='FlightScheduleServlet?action=new'" style="width:250px;">Add New Schedule</button>
        </div>
        <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for Schedule Id.." title="Type in a name">
        <div class="table-container">
            <table class="table" id="myTable">
                <tr>
                    <th>Schedule ID</th>
                    <th>Flight ID</th>
                    <th>Date of Travel</th>
                    <th>Business Booked</th>
                    <th>Economy Booked</th>
                    <th>Executive Booked</th>
                    <th class="lesswidth">Actions</th>
                </tr>
                <%
                    List<FlightSchedule> listSchedules = (List<FlightSchedule>) request.getAttribute("listSchedules");
                    if (listSchedules != null) {
                        for (FlightSchedule schedule : listSchedules) {
                %>
                <tr>
                    <td><%= schedule.getFlightScheduleID() %></td>
                    <td><%= schedule.getFlightID() %></td>
                    <td><%= schedule.getDateOfTravel() %></td>
                    <td><%= schedule.getBusinessClassBookedCount() %></td>
                    <td><%= schedule.getEconomyClassBookedCount() %></td>
                    <td><%= schedule.getExecutiveClassBookedCount() %></td>
                    <td>
                        <button class="btn-edit" onclick="window.location.href='FlightScheduleServlet?action=edit&id=<%= schedule.getFlightScheduleID() %>'">Edit</button>
                        <button class="btn-delete" onclick="if(confirm('Are you sure?')) window.location.href='FlightScheduleServlet?action=delete&id=<%= schedule.getFlightScheduleID() %>'">Delete</button>
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
	<script src="js/flightScheduleList.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
</body>
</html>