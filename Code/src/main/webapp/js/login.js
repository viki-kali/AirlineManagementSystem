function validateLogin() {
    // Clear any previous error messages
    document.getElementById("p1").innerHTML = "";
    document.getElementById("p2").innerHTML = "";
    
    var userid = document.getElementById("userid").value;
    var password = document.getElementById("password").value;
    let useridpattern = /^\d{5,}$/;
    
    if (!(useridpattern.test(userid)) && (password.length > 30 || password.length < 6)) {
        document.getElementById("p2").innerHTML = "Both ID and password are not valid";
        return false;
    } 
    else if (password.length > 30 || password.length < 6) {
        document.getElementById("p2").innerHTML = "Password not valid";
        return false;
    } 
    else if (!(useridpattern.test(userid))) {
        document.getElementById("p1").innerHTML = "ID is not valid";
        return false;
    } 
    else {
        return true;
    }
}

function redirectRegister(){
	setTimeout(function() {
            window.location.href = "register.jsp"; // Redirect to login page after 5 seconds
        }, 100);
}