<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile - Airline Management System</title>
    <link rel="stylesheet" href="css/profile.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css">
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
    <div class="register-container">
        <h2>User Profile</h2>

       
        <form id="profileForm" action="ProfileServlet" method="post" onsubmit="return validateForm()">
         
            <input type="hidden" id="userId" name="userId" value="<%= user.getUserId() %>" readonly>
            
            <label>First Name:</label>
            <input type="text" id="firstName" name="firstName" value="<%= user.getFirstName() %>" disabled required>
            
            <label>Last Name:</label>
            <input type="text" id="lastName" name="lastName" value="<%= user.getLastName() %>" disabled required>
            
            <label>Password:</label>
            <input type="password" id="password" name="password" value="<%= user.getPassword() %>" disabled required>
            
            <label>Confirm Password:</label>
            <input type="password" id="cpassword" value="<%= user.getPassword() %>" disabled required>
            
            <label>Role:</label>
            <input type="text" id="role" name="role" value="<%= user.getRole() %>" disabled required>
            
            <label>Customer Type:</label>
            <input type="text" id="customertype" name="customertype" value="<%= user.getCustomerType() %>" disabled required>
            
            <label>Date of Birth:</label>
            <input type="text" id="date" name="date" value="<%= user.getDateOfBirth() %>" disabled required onfocus="(this.type='date')">
            
            <label>Email:</label>
            <input type="email" id="email" name="email" value="<%= user.getEmailId() %>" disabled required>
            
            <label>Address:</label>
            <textarea id="address" name="address" disabled required><%= user.getAddress() %></textarea>
            
            <label>Phone Number:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" value="<%= user.getPhone() %>" disabled required maxlength="10">
            
            
            <p id="p1" style="color:red;"></p>
            
            <div class="btn-container">
                <button type="submit" id="saveBtn" class="register-btn" style="display:none;">Save</button>
                <button type="button" id="editBtn" class="register-btn" onclick="enableEdit();">Edit</button>
                <button type="button" id="deleteBtn" class="btn-delete" onclick="confirmDeletion();">Delete</button>
            </div>
        </form>
    </div>
    <jsp:include page="footer.jsp" />
    <script src="js/profile.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
