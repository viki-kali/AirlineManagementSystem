<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration Successful</title>
    <link rel="stylesheet" href="css/register.css">
    <script>
    alert(<%= request.getAttribute("userId") %>);
        setTimeout(function() {
            window.location.href = "login.jsp"; 
        }, 5000);
    </script>
</head>
<body>
    <div class="register-container">
    	<h3><%=request.getAttribute("userId") %></h3>
        <h2>Registration Successful!</h2>
       <!--  <p>Redirecting to login page in 5 seconds...</p> -->
        <a href="login.jsp">Click here if not redirected</a>
    </div>
</body>
</html>
