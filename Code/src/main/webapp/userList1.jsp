<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.model.User" %>
<html>
<head>
    <title>Customer List</title>

    <link rel="stylesheet" type="text/css" href="customerList.css">
    <script src="customerList.js"></script>
</head>
<body>
    <h2>Customer List</h2>
    
    <a href="UserServlet?action=new">Add New Customer</a>
    <table border="1" cellspacing="0" cellpadding="8">
        <tr>
            <th>User ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Actions</th>
        </tr>
        <%
            
            List<User> userList = (List<User>) request.getAttribute("userList");
            if(userList != null){
                for(com.model.User user : userList){
        %>
        <tr>
            <td><%= user.getUserId() %></td>
            <td><%= user.getFirstName() %></td>
            <td><%= user.getLastName() %></td>
            <td><%= user.getEmailId() %></td>
            <td><%= user.getPhone() %></td>
            <td>
             
                <a href="UserServlet?action=edit&id=<%= user.getUserId() %>">Edit</a>
                <a href="UserServlet?action=delete&id=<%= user.getUserId() %>" onclick="return confirm('Are you sure you want to delete this customer?');">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
