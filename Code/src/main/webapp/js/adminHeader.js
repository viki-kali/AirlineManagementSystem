document.addEventListener("DOMContentLoaded", function() {
    // Example: If you add a mobile toggle button with id "navToggle", this code toggles the nav-list visibility.
    var navToggle = document.getElementById("navToggle");
    if (navToggle) {
        navToggle.addEventListener("click", function() {
            var navList = document.querySelector(".nav-list");
            if (navList.style.display === "block") {
                navList.style.display = "none";
            } else {
                navList.style.display = "block";
            }
        });
    }
});
