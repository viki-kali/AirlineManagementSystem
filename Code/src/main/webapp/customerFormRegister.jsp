
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Registration</title>
</head>
<body>
    <h2>Register as a New Customer</h2>
    <form action="UserServlet" method="post">
        
        <input type="hidden" name="action" value="register" />

        <label for="firstName">First Name:</label>
        <input type="text" name="firstName" id="firstName" required /><br/>

        <label for="lastName">Last Name:</label>
        <input type="text" name="lastName" id="lastName" required /><br/>

        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required /><br/>

        
        <label for="customerType">Customer Type:</label>
        <select name="customerType" id="customerType">
            <option value="">--Select--</option>
            <option value="Silver">Silver</option>
            <option value="Gold">Gold</option>
            <option value="Platinum">Platinum</option>
        </select><br/>

        <label for="phone">Phone:</label>
        <input type="text" name="phone" id="phone" required /><br/>

        <label for="emailId">Email:</label>
        <input type="email" name="emailId" id="emailId" required /><br/>

        <label for="address">Address:</label>
        <input type="text" name="address" id="address" required /><br/>

        <label for="dateOfBirth">Date of Birth:</label>
        <input type="date" name="dateOfBirth" id="dateOfBirth" required /><br/>

        <input type="submit" value="Register" />
    </form>
        <br>
        <a href="login.jsp">Back to Login</a>
</body>
</html>
