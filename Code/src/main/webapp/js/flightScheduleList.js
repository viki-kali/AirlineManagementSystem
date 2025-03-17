function myFunction() {
    var input = document.getElementById("myInput");
    var filter = input.value.toUpperCase();
    var table = document.getElementById("myTable");
    var tr = table.getElementsByTagName("tr");
    
    // Loop through all table rows, skipping the header row (index 0)
    for (var i = 1; i < tr.length; i++) {
        var tds = tr[i].getElementsByTagName("td");
        var rowMatches = false;
        // Loop through all cells in the current row
        for (var j = 0; j < tds.length; j++) {
            var cellText = tds[j].textContent || tds[j].innerText;
            if (cellText.toUpperCase().indexOf(filter) > -1) {
                rowMatches = true;
                break;
            }
        }
        // Show the row if a match is found; otherwise, hide it.
        tr[i].style.display = rowMatches ? "" : "none";
    }
}