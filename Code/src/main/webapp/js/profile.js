function enableEdit() {
    // Enable fields for editing except userId, role, and customer type.
    document.getElementById("firstName").disabled = false;
    document.getElementById("lastName").disabled = false;
    document.getElementById("password").disabled = false;
    document.getElementById("cpassword").disabled = false;
    document.getElementById("date").disabled = false;
    document.getElementById("email").disabled = false;
    document.getElementById("address").disabled = false;
    document.getElementById("phoneNumber").disabled = false;
    
    // Ensure that userId, role, and customertype remain disabled.
    document.getElementById("userId").disabled = true;
    document.getElementById("role").disabled = true;
    document.getElementById("customertype").disabled = true;
    
    // Hide the "Edit" button and show the "Save" button.
    document.getElementById("editBtn").style.display = "none";
    document.getElementById("saveBtn").style.display = "inline-block";
}

function confirmDeletion() {
    if (confirm("Do you want to delete your account?")) {
        // If confirmed, redirect to login.jsp (or a servlet that handles deletion).
        window.location.href = "login.jsp";
    }
}

function validateForm() {
    // Clear any previous error message.
    document.getElementById("p1").innerHTML = "";
    
    // Get form values.
    let firstName = document.getElementById("firstName").value.trim();
    let lastName = document.getElementById("lastName").value.trim();
    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("cpassword").value;
    let dob = document.getElementById("date").value;
    let email = document.getElementById("email").value.trim();
    let phoneNumber = document.getElementById("phoneNumber").value.trim();
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
        errorMessage = "First Name should not be less than three characters.";
    }
    if (lastName.length < 3) {
        errorMessage = "Last Name should not be less than three characters.";
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
        document.getElementById("p1").innerHTML = errorMessage;
        return false; 
    } else {
        // Optionally, remove the following redirection if you want to submit the form instead.
        // window.location.href = "login.jsp";
        return true; 
    }
}
