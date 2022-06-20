// code to Show the navbar for mobile
// Targeting the icon
const errorButton = document.getElementById('view');
// Targets the ul in the nav
const errText = document.getElementById('errDetails');
// Showing the navbar ul and changing icon

displayErr = () => {
    errText.classList.toggle('show');
}
// create event listener for icon
errorButton.addEventListener('click', displayErr);
