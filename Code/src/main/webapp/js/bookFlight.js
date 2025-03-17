// js/bookFlight.js

var availableEconomy = 0;
var availableExecutive = 0;
var availableBusiness = 0;


function selectFlight(flightId, economySeats, executiveSeats, businessSeats, travelDate) {
    document.getElementById("selectedFlightId").value = flightId;
    document.getElementById("selectedTravelDate").value = travelDate; // Ensure travel date is set
    
    // Store available seats correctly
    availableEconomy = parseInt(economySeats); 
    availableExecutive = parseInt(executiveSeats);
    availableBusiness = parseInt(businessSeats);
    
    alert("Flight " + flightId + " selected.");
}


function myFunction() {
    var input = document.getElementById("myInput");
    var filter = input.value.toUpperCase();
    var table = document.getElementById("flightTable");
    var tr = table.getElementsByTagName("tr");
    // Loop through all rows except header (index 0)
    for (var i = 1; i < tr.length; i++) {
        var tds = tr[i].getElementsByTagName("td");
        var rowMatches = false;
        for (var j = 0; j < tds.length; j++) {
            var cellText = tds[j].textContent || tds[j].innerText;
            if (cellText.toUpperCase().indexOf(filter) > -1) {
                rowMatches = true;
                break;
            }
        }
        tr[i].style.display = rowMatches ? "" : "none";
    }
}

function validateBookingForm() {
    var seatCategory = document.getElementById("seatCategory").value;
    var numberOfSeats = parseInt(document.getElementById("numberOfSeats").value);
    var errorSpan = document.getElementById("seatError");
    errorSpan.innerHTML = "";
    
    if (seatCategory === "") {
        errorSpan.innerHTML = "Please select a seat category.";
        return false;
    }
    if (isNaN(numberOfSeats) || numberOfSeats <= 0) {
        errorSpan.innerHTML = "Please enter a valid number of seats.";
        return false;
    }
    if (seatCategory === "Economy" && numberOfSeats > availableEconomy) {
        errorSpan.innerHTML = "Number of seats exceeds available economy seats (" + availableEconomy + ").";
        return false;
    }
    if (seatCategory === "Executive" && numberOfSeats > availableExecutive) {
        errorSpan.innerHTML = "Number of seats exceeds available executive seats (" + availableExecutive + ").";
        return false;
    }
    if (seatCategory === "Business" && numberOfSeats > availableBusiness) {
        errorSpan.innerHTML = "Number of seats exceeds available business seats (" + availableBusiness + ").";
        return false;
    }
    return true;
}
