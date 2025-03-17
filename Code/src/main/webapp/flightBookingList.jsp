<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.model.FlightBooking" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Flight Booking List</title>
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
        <h2 class="text-center mt-3 ">List of Flight Bookings</h2>
        <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for Booking Id.." title="Type in a name">
        <%String status = (String) request.getParameter("status");%>
        		
        <div class="table-container">
        	<%if(status!=null){%>
        		<h3 style="text-align:center;color:red;"><%=status %></h3>
        		<%} %>
            <table class="table" id="myTable">
                <tr>
                    <th>Booking ID</th>
                    <th>Flight ID</th>
                    <th>Customer ID</th>
                    <th>No. of Seats</th>
                    <th>Seat Category</th>
                    <th>Date of Booking</th>
                    <th>Date of Travel</th>
                    <th>Booking Status</th>
                    <th>Booking Amount</th>
                    <th>Refund Amount</th>
                    <th class="lesswidth">Actions</th>
                </tr>
                <%
                    List<FlightBooking> listBookings = (List<FlightBooking>) request.getAttribute("listBookings");
                    if (listBookings != null) {
                        for (FlightBooking booking : listBookings) {
                %>
                <tr>
                    <td><%= booking.getBookingID() %></td>
                    <td><%= booking.getFlightID() %></td>
                    <td><%= booking.getCustomerID() %></td>
                    <td><%= booking.getNoOfSeats() %></td>
                    <td><%= booking.getSeatCategory() %></td>
                    <td><%= booking.getDateOfBooking() %></td>
                    <td><%= booking.getDateOfTravel() %></td>
                    <td><%= booking.getBookingStatus() %></td>
                    <td>$<%= booking.getBookingAmount() %></td>
                    <td>$<%= booking.getRefundAmount() %></td>
                    <td>
                        <button class="btn-edit" onclick="window.location.href='BookingServlet?action=edit&id=<%= booking.getBookingID() %>'">Edit</button>
                        <%if(booking.getBookingStatus().equalsIgnoreCase("Booked")){ %>
                        <button class="btn-delete" onclick="if(confirm('Are you sure?')) window.location.href='BookingServlet?action=delete&id=<%= booking.getBookingID() %>&userId=<%=booking.getCustomerID()%>'">Cancel</button>
                        <%} %>
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
    <script>
    function myFunction() {
        var input, filter, table, tr, td, i, j, txtValue, rowMatch;
        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("myTable");
        tr = table.getElementsByTagName("tr");
        
        for (i = 1; i < tr.length; i++) { 
            rowMatch = false;
            td = tr[i].getElementsByTagName("td");
            
            for (j = 0; j < td.length; j++) {
                if (td[j]) {
                    txtValue = td[j].textContent || td[j].innerText;
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                        rowMatch = true;
                        break;
                    }
                }
            }
            
            tr[i].style.display = rowMatch ? "" : "none";
        }
    }

    </script>
</body>
</html>