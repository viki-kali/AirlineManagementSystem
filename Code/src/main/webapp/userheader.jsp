<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.User" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - Airline Management System</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css">
</head>
<body>
<%User user1 = (User) session.getAttribute("admin");
if(user1!=null)
	session.invalidate();
%>
	   <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="home.jsp">Airline Management System</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="home.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="profile.jsp">My Profile</a>
                    </li>
                    
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="tripsDropdown" role="button" data-bs-toggle="dropdown">
                            My Trips
                        </a>
                        <ul class="dropdown-menu">
                        	<li><a class="dropdown-item" href="UserBookingsServlet?action=allBookings">All Trips</a></li>
                            <li><a class="dropdown-item" href="UserBookingsServlet?action=upcomingBookings">Upcoming</a></li>
                            <li><a class="dropdown-item" href="UserBookingsServlet?action=cancelledBookings">Cancelled</a></li>
                            <li><a class="dropdown-item" href="UserBookingsServlet?action=travelledBookings">Completed</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                           <div class="d-flex flex-row nav-link dropdown-toggle" href="#" id="tripsDropdown" role="button" data-bs-toggle="dropdown">
                           		<i class="bx bxs-user bx-sm"></i><p ><%User u=(User)session.getAttribute("user"); %><%=u.getFirstName() %></p>
                   
                           </div>
         
                        <ul class="dropdown-menu">
                        	<li class="dropdown-item"><p>Welcome</p>
                        	 <p ><%=u.getFirstName() %></p>
                        	</li>
                            <li> <a class="nav-link text-danger" href="LogoutServlet">Logout</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                    <pre>        </pre>
                    </li>
                    
                </ul>
            </div>
        </div>
    </nav>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>