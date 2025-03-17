<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="java.util.List, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= request.getAttribute("pageTitle") %></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }
        .navbar {
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
        }
        .footer {
            position: fixed;
            bottom: 0;
            width: 100%;
            background-color: #f1f1f1;
            padding: 10px;
            text-align: center;
        }
        .content {
            margin-top: 80px;
            margin-bottom: 80px;
            padding: 20px;
        }
        .table-container {
            max-height: 400px;
            overflow-y: auto;
            margin-top: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: white;
        }
    </style>
</head>
<body>
    
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.html">Airline Management System</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link" href="CarrierServlet?action=list">Carrier</a></li>
                    <li class="nav-item"><a class="nav-link" href="FlightServlet?action=list">Flight</a></li>
                    <li class="nav-item"><a class="nav-link" href="FlightBookingServlet?action=list">FlightBooking</a></li>
                    <li class="nav-item"><a class="nav-link" href="FlightScheduleServlet?action=list">Flight Schedule</a></li>
                    <li class="nav-item"><a class="nav-link" href="UserServlet?action=list">Customer Details</a></li>
                    <li class="nav-item"><a class="nav-link" href="RefundServlet">Refund</a></li>
                    <li class="nav-item"><a class="nav-link" href="LogoutServlet">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
    
    <div class="container content">
       
        <h2><%= request.getAttribute("heading") %></h2>
        <div class="add-carrier-button">
            <button class="btn btn-primary add-new" onclick="window.location.href='<%= request.getAttribute("buttonUrl") %>'">
                <%= request.getAttribute("buttonText") %>
            </button>
        </div>
        <input type="text" id="searchInput" onkeyup="searchFunction()" placeholder="<%= request.getAttribute("searchText") %>" title="Type in a name">
    
        <!-- Dynamic Table -->
        <div class="table-container">
            <table class="table" id="dataTable">
                <tr>
                    <% List<String> columns = (List<String>) request.getAttribute("columns");
                       for (String column : columns) { %>
                        <th><%= column %></th>
                    <% } %>
                    <th>Actions</th>
                </tr>
                <%
                    List<Map<String, String>> rowData = (List<Map<String, String>>) request.getAttribute("rowData");
                    List<Map<String, String>> actionButtons = (List<Map<String, String>>) request.getAttribute("actionButtons");
                    if (rowData != null) {
                        for (Map<String, String> row : rowData) {
                %>
                <tr>
                    <% for (String column : columns) { %>
                        <td><%= row.get(column) %></td>
                    <% } %>
                    <td>
                        <% for (Map<String, String> button : actionButtons) { %>
                            <button class="btn btn-primary" onclick="window.location.href='<%= button.get("buttonUrl") + "?id=" + row.get(columns.get(0)) %>'">
                                <%= button.get("buttonText") %>
                            </button>
                        <% } %>
                    </td>
                </tr>
                <%      }
                    } %>
            </table>
        </div>
    </div>
    
   
    <div class="footer">
        <hr>
        <h5>Contact Us</h5>
        <p>+91 9999999999 | AMSMailer@help.com</p>
        <p>&copy; Airline Management System</p>
    </div>
    
    <script>
        function searchFunction() {
            let input = document.getElementById("searchInput").value.toLowerCase();
            let table = document.getElementById("dataTable");
            let tr = table.getElementsByTagName("tr");
            for (let i = 1; i < tr.length; i++) {
                let td = tr[i].getElementsByTagName("td")[0];
                if (td) {
                    tr[i].style.display = td.textContent.toLowerCase().includes(input) ? "" : "none";
                }
            }
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>