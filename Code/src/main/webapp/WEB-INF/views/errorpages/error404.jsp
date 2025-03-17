  
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>

<% response.setStatus(404); %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>404 - Page Not Found</title>
<!--  <link rel="stylesheet" href="./error.css"> -->
<style>
@charset "ISO-8859-1";

/* General Styling */
body {

color: #ffffff;
font-family: Arial, sans-serif;
text-align: center;
overflow: hidden;
margin: 0;
height: 100vh;
display: flex;
flex-direction: column;
align-items: center;
justify-content: center;
}

/* 404 Text */
h1 {
font-size: 100px;
margin: 10px 0;
color:black;
}

p {
font-size: 20px;
color:black;
}

.home-button {
display: inline-block;
margin-top: 20px;
padding: 10px 20px;
font-size: 18px;
background: #ff4757;
color: #ffffff;
text-decoration: none;
border-radius: 5px;
}
.home-button:hover{
	background-color:red;
}

.container{
 padding: 40px;
  background-color: white;
  border:2px;
  color: white;
  backdrop-filter: blur(30px);
  border-radius:10px ;
  width:420px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, .7);
}</style>
</head>
<body>
<div class="container">
    <h1>404</h1>
    <p>Oops!!! Page Not Found!</p>
    <a href="/Airline_Management_System/login.jsp" class="home-button">Login Again</a>

</div>
</body>
</html>