<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.model.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">

<style>
        body {
            font-family: Arial, sans-serif;
            color: #333;
            margin: 0;
            padding: 0;
             background-color: #f8f9fa;
             }
         
        
        .table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
              box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .table th {
           background-color: #343a40; /* Dark background for header */
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
       
        a{
        color:white;
        }
        a:hover{
        color:white;
        text-decoration:none;
        }
		button{
		margin:7px;
		width:100px;
		}
		.add-new
		{
		width:150px;
		}
		
    </style>

</head>
<body>
<%User user = (User) session.getAttribute("admin");
user.getFirstName();
User user1 = (User) session.getAttribute("user");
if(user1!=null)
	session.invalidate();
%>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="CarrierServlet?action=list">Airline Management System</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" 
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto"> 
                <li class="nav-item"><a class="nav-link" href="CarrierServlet?action=list">Carrier</a></li>
                <li class="nav-item"><a class="nav-link" href="FlightServlet?action=list">Flight</a></li>
                <li class="nav-item"><a class="nav-link" href="BookingServlet?action=list">FlightBooking</a></li>
                <li class="nav-item"><a class="nav-link" href="FlightScheduleServlet?action=list">Flight Schedule</a></li>
                <li class="nav-item"><a class="nav-link" href="UserServlet?action=list">Customer Details</a></li>
                <li class="nav-item"><a class="nav-link" href="RefundServlet">Refund</a></li>
                <li class="nav-item"><a class="nav-link" href="LogoutServlet">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

</body>
</html>