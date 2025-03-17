<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Register - Airline Management System</title>
    <link rel="stylesheet" href="css/register.css">
</head>
<body>
    <div class="register-container">
        <h2>Admin Register</h2>
        <form id="adminRegistrationForm" onsubmit="return validateAdminForm();" method="get" action="UserServlet?action=insert">
            <input type="text" id="firstName" name="firstName" placeholder="First Name" maxlength="50" required>
            <input type="text" id="lastName" name="lastName" placeholder="Last Name" maxlength="50" required>
            
            <input type="password" id="password" name="password" placeholder="Password" maxlength="30" required>
            <input type="password" id="cpassword" placeholder="Confirm Password" maxlength="30" required>
            
           
            <select id="role" name="role" required>
                <option value="">Select Role</option>
                <option value="Admin">Admin</option>
                <option value="Customer">Customer</option>
            </select>
            
           
            <select id="customertype" name="customertype" required>
                <option value="">Select Customer Type</option>
                <option value="Silver">Silver</option>
                <option value="Gold">Gold</option>
                <option value="Platinum">Platinum</option>
            </select>
            
            <input type="text" id="date" name="date" placeholder="DOB" required onfocus="(this.type='date')">
            <input type="email" id="email" name="email" placeholder="Email ID" required>
            <textarea id="address" name="address" placeholder="Address" maxlength="100" required></textarea>
            <input type="text" id="phoneNumber" name="phone" placeholder="Contact Number" maxlength="10" required>
            
            <p id="errorMessage" style="color: red;">
                <% if (request.getAttribute("message") != null) { %>
                    <%= request.getAttribute("message") %>
                <% } %>
            </p>
            
            <button type="submit" class="register-btn" name="action" value=insert>Register</button>
            <button type="reset" class="reset-btn" onclick="return confirmReset();">Reset</button>
        </form>
    </div>
    <script src="js/register.js"></script>
</body>
</html>
