<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <title>Refund Calculation</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script>
        function validateForm() {
             let flightId = document.getElementById("flightId").value;
             let travelDate = document.getElementById("travelDate").value;
             
              if (!flightId || isNaN(flightId)) {
                  alert("Please enter a valid Flight ID.");
                return false;
               }
             
            let selectedDate = new Date(travelDate);
            let today = new Date();
            today.setHours(0, 0, 0, 0);
           

            if (selectedDate < today) {
                alert("Please select a date from today or in the future.");
                return false;
            }
			alert("Do you want to cancle the flight");
            return true;
        }
    </script>
     <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>


    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            
        }
       
        .container {
             max-width: 600px;
             
            margin: 50px auto;
            background-color: #fff;
            padding: 55px;
            border:1px solid #ccc;
            
            border-radius: 8px;
           
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="number"],
        input[type="date"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
       
       
        .error {
            color: red;
            margin-bottom: 10px;
        }
        .back-link{
            text-align: center;
            display: block;
            margin-top: 20px;
        }

    </style>
</head>


<body>

	<jsp:include page="adminheader.jsp"></jsp:include>
    <div class="container" style="width:500px;">
        <h2>Flight Cancellation Refund Calculation</h2>
        <% if (request.getAttribute("errorMessage") != null) { %>
            <p class="error"><%= request.getAttribute("errorMessage") %></p>
        <% } %>
        <form action="RefundServlet" method="post" onsubmit="return validateForm()">
            <div>
                <label for="flightId">Flight ID:</label>
                <input type="number" id="flightId" name="flightId" required min="1" >
            </div>
            <div>
                <label for="travelDate">Date of Travel (YYYY-MM-DD):</label>
                <input type="date" id="travelDate" name="travelDate" required>
            </div>
            <input type="submit" value="Calculate Refund">
        </form>
    </div>
    <jsp:include page="footer.jsp" />
   
</body>
</html>