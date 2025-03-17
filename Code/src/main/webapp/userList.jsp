<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer</title>
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
    <jsp:include page="adminheader.jsp" />
    <div class="content">
        <h2 class="text-center mt-3">List of Customers</h2>
        <div class="add-carrier-button">
            <button class="btn btn-primary add-new" onclick="window.location.href='UserServlet?action=new'" style="width:250px">Add New Customer</button>
        </div>
        <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for Customer by Name.." title="Type in a name">
        <div class="table-container">
            <table class="table" id="myTable">
                <tr>
                    <th>Customer ID</th>
                    <th>User Name</th>
                    <th>Category</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th class="lesswidth">Actions</th>
                </tr>
                <% List<User> customers = (List<User>) request.getAttribute("userList");
                   if (customers != null) {
                       for (User customer : customers) { %>
                <tr>
                    <td><%= customer.getUserId() %></td>
                    <td><%= customer.getFirstName() %></td>
                    <td><%= customer.getRole() %></td>
                    <td><%= customer.getEmailId() %></td>
                    <td><%= customer.getPhone() %></td>
                    <td>
                        <button class="btn-edit" onclick="window.location.href='UserServlet?action=edit&id=<%= customer.getUserId() %>'">Edit</button>
                        <button class="btn-delete" onclick="if(confirm('Are you sure?')) window.location.href='UserServlet?action=delete&id=<%= customer.getUserId() %>'">Delete</button>
                    </td>
                </tr>
                <% } } %>
            </table>
        </div>
    </div>
    <jsp:include page="footer.jsp" />
    <script>
        function myFunction() {
            var input, filter, table, tr, td, i, j, txtValue;
            input = document.getElementById("myInput");
            filter = input.value.toUpperCase();
            table = document.getElementById("myTable");
            tr = table.getElementsByTagName("tr");
            for (i = 1; i < tr.length; i++) {
                var rowMatches = false;
                td = tr[i].getElementsByTagName("td");
                for (j = 0; j < td.length; j++) {
                    if (td[j]) {
                        txtValue = td[j].textContent || td[j].innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            rowMatches = true;
                            break;
                        }
                    }
                }
                tr[i].style.display = rowMatches ? "" : "none";
            }
        }
    </script>
</body>
</html>
