<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Airline Management System</title>
    <link rel="stylesheet" href="css/register.css">
</head>
<body>
    
    <div class="register-container">
        <h2>Register</h2>
        <form id="registrationForm" onsubmit="return validateForm();" method="post" action="RegisterServlet"> 
            
            <input type="text" id="firstName" name="firstName" placeholder="First Name" maxlength="50" required>
            <input type="text" id="lastName" name="lastName" placeholder="Last Name" maxlength="50" required>
            <input type="password" placeholder="Password" maxlength="30" required id="password" name="password">
            <input type="password" placeholder="Confirm Password" maxlength="30" required id="cpassword">
            <select name="customertype" disabled="disabled">
            	<option value="Silver">Silver</option>
            	<option value="Gold">Gold</option>
            	<option value="Platinum">Platinum</option>
            </select>
            <input type="type" id="date" name="date" required placeholder="DOB" onfocus="(this.type='date')">
            <input type="email" id="email" name="email" placeholder="Email ID" required>
            <textarea placeholder="Address" name="address" maxlength="100" id="address" required></textarea>
            <input type="text" id="phoneNumber" name="phoneNumber" placeholder="Contact Number" maxlength="10" required>
            
            <p id="p1" style="color: red;">
            <%if (request.getAttribute("message")!=null) {%>
            <%=request.getAttribute("message")%>
            <%} %>
            </p>
            
            <button type="submit"  class="register-btn"  name="action" value="register">Register</button>
            <button type="reset" class="reset-btn" onclick="return confirmReset();">Reset</button>
        </form>
        <p class="login-link">
            Already have an account? <a href="login.jsp">Login</a>
        </p>
    </div>
<script src="js/register.js"></script>
</body>
</html>