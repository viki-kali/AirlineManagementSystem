<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <script src="js/login.js"></script>
    
    <link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css">
</head>
<body>
    <div class="login-container">
          	<p id="p2">
          	<%session.invalidate(); %>
      	<%if(request.getAttribute("error")!=null){ %>
			<%=request.getAttribute("error") %>
			<%} %>
      	</p>
        <h2>Login</h2>
        <form id="form1" onsubmit="return validateLogin()" method="post" action="LoginServlet">
            <p id="p1"></p>
            <p id="p2"></p>  
            <div class="input-group">
                <i class="bx bxs-user"></i>
          
                <input type="text" name="userid" id="userid" placeholder="User ID" required>
            </div>
            <div class="input-group">
                <i class="bx bxs-lock"></i>
             
                <input type="password" name="password" id="password" placeholder="Password" maxlength="30" required>
            </div>
            <input class="login-btn" type="submit" value="Login">
            
        	<button class="login-btn" onclick="redirectRegister()">Register</button>
        </form>
        
    </div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
