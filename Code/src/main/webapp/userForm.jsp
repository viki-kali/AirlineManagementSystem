<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.model.User" %>
<html>
<head>
    <title>Customer Form</title>
        <style>

html, body {
    margin: 0;
    padding: 0;
}


body {
    background-color: teal;
    padding-top: 80px; 


.register-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 30%;
    margin: 0 auto; 
    padding: 20px;
    background: rgb(220, 220, 220);
    border-radius: 10px;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
}


.register-container input,
.register-container textarea,
.register-container select {
    width: 100%;
    padding: 10px;
    margin-bottom: 20px; 
    border: 1px solid black;
    border-radius: 5px;
    
}


.register-btn {
    display: block; 
    margin-top: 10px;
    text-align: center;
    padding: 10px;
    background-color: dodgerblue;
    color: white;
    font-size: 18px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    width: 100%;
    margin-bottom: 20px; 
}

.register-btn:hover {
    background-color: cobalt;
}

    </style>
    
        <script>
        function validateForm() {
           
            let firstName = document.getElementById("firstName").value.trim();
            let lastName = document.getElementById("lastName").value.trim();
            let password = document.getElementById("password").value;
            let confirmPassword = document.getElementById("cpassword").value;
            let dob = document.getElementById("date").value;
            let email = document.getElementById("email").value.trim();
            let phoneNumber = document.getElementById("phone").value.trim();
            let address = document.getElementById("address").value.trim();

            let errorMessage = "";
            let invalidNames = ["nan", "na", "null"];
            let enteredDate = new Date(dob);
            let minDate = new Date("1924-01-01");
            let maxDate = new Date();

            if (invalidNames.includes(firstName.toLowerCase())) {
                errorMessage = "First name cannot be empty, 'NaN', 'NA', or 'null'.";
            }
            if (firstName.length < 3) {
                errorMessage = "First Name should not be less than three characters";
            }
            if (lastName.length < 3) {
                errorMessage = "Last Name should not be less than three characters";
            }
            if (invalidNames.includes(lastName.toLowerCase())) {
                errorMessage = "Last name cannot be empty, 'NaN', 'NA', or 'null'.";
            }

            let passwordPattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
            if (!passwordPattern.test(password)) {
                errorMessage = "Password must be at least 6 characters long and contain at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character (!@#$%^&*).";
            }
            if (enteredDate < minDate) {
                errorMessage = "Choose a date greater than 01/01/1924.";
            }
            if (enteredDate > maxDate) {
                errorMessage = "Date of birth cannot be in the future.";
            }

            let phonePattern = /^[6-9][0-9]{9}$/;
            if (!phonePattern.test(phoneNumber)) {
                errorMessage = "Enter a valid contact number (10 digits only).";
            }

            let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailPattern.test(email)) {
                errorMessage = "Enter a valid email ID.";
            }

            if (password !== confirmPassword) {
                errorMessage = "Passwords do not match.";
            }
            if (address.length < 5) {
                errorMessage = "Address must be at least 5 characters long.";
            }

            if (errorMessage) {
                // Update the element with id "errorMessage" instead of "p1"
                document.getElementById("errorMessage").innerHTML = errorMessage;
                return false; 
            } 
        }

    </script>
</head>

<body style="background-color: teal;">
<jsp:include page="adminheader.jsp"></jsp:include>    

<%
    
    User user = (User) request.getAttribute("user");
    boolean isEdit = (user != null);
%>
<div class="register-container">
<h2><%= isEdit ? "Edit Customer" : "Add New Customer" %></h2>

<form name="userForm" action="UserServlet" method="get" onsubmit="return validateForm();">
<%
    if (isEdit) {
%>
        <input type="hidden" name="action" value="update" />
        <input type="hidden" name="id" value="<%= user.getUserId() %>" />
<%
    } else {
%>
        <input type="hidden" name="action" value="insert" />
<%
    }
%>

            <input type="text" id="firstName" name="firstName" placeholder="First Name" maxlength="50" value="<%= isEdit ? user.getFirstName() : "" %>" required>
            <input type="text" id="lastName" name="lastName" placeholder="Last Name" maxlength="50" value="<%= isEdit ? user.getLastName() : "" %>" required>
            
            <input type="password" id="password" name="password" placeholder="Password" maxlength="30" value="<%= isEdit ? user.getPassword() : "" %>" required>
            <input type="password" id="cpassword" placeholder="Confirm Password" maxlength="30" value="<%= isEdit ? user.getPassword() : "" %>" required>
            
           
 <select id="role" name="role" required>
    <option value="">Select Role</option>
    <option value="Admin" <%= isEdit && "Admin".equals(user.getRole()) ? "selected" : "" %>>Admin</option>
    <option value="Customer" <%= (!isEdit || "Customer".equals(user.getRole())) ? "selected" : "" %>>Customer</option>
</select>

            
           
<select id="customertype" name="customertype" required>
    <option value="">Select Customer Type</option>
    <option value="Silver" <%= isEdit && "Silver".equals(user.getCustomerType()) ? "selected" : "" %>>Silver</option>
    <option value="Gold" <%= isEdit && "Gold".equals(user.getCustomerType()) ? "selected" : "" %>>Gold</option>
    <option value="Platinum" <%= isEdit && "Platinum".equals(user.getCustomerType()) ? "selected" : "" %>>Platinum</option>
</select>

            
            <input type="text" id="date" name="date" placeholder="DOB" value="<%= isEdit ? user.getDateOfBirth() : "" %>" required onfocus="(this.type='date')">
            <input type="email" id="email" name="email" placeholder="Email ID" value="<%= isEdit ? user.getEmailId() : "" %>" required>
            <textarea id="address" name="address" placeholder="Address" maxlength="100" required><%= isEdit ? user.getAddress() : "" %></textarea>
            <input type="text" id="phone" name="phone" placeholder="Contact Number" value="<%= isEdit ? user.getPhone() : "" %>"  maxlength="10" required>
            
            <p id="errorMessage" style="color: red;">
                <% if (request.getAttribute("message") != null) { %>
                    <%= request.getAttribute("message") %>
                <% } %>
            </p>
    <input class="register-btn" type="submit" value="<%= isEdit ? "Update Customer" : "Submit" %>" />
            <br>
        <a href="UserServlet?action=list" class="register-btn">Back to Carrier List</a>
    
</form>
    </div>
</body>
<jsp:include page="footer.jsp"></jsp:include>
</html>
