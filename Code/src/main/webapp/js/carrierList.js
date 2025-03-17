// Function to filter table rows based on Carrier ID
function myFunction() {
    // Get the search input and convert it to uppercase for case-insensitive comparison
    var input = document.getElementById("myInput");
    var filter = input.value.toUpperCase();
    // Get the table and all its rows
    var table = document.getElementById("myTable");
    var tr = table.getElementsByTagName("tr");

    // Loop through all table rows (skipping the header row at index 0)
    for (var i = 1; i < tr.length; i++) {
        // Get only the first cell (Carrier ID column) of the row
        var td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            var txtValue = td.textContent || td.innerText;
            // Only display the row if the Carrier ID matches the search filter
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}


// Function to confirm deletion of a carrier
function confirmDelete(carrierId) {
    if (confirm("Are you sure you want to delete Carrier ID " + carrierId + "?")) {
        window.location.href = "CarrierServlet?action=delete&id=" + carrierId;
    }
}
