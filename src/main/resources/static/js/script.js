// code to Show the navbar for mobile
// Targeting the icon
const navButton = document.getElementById('dropdown');
// Targets the ul in the nav
const navUl = document.getElementById('links');
// Showing the navbar ul and changing icon
showMe = () => {
    navUl.classList.toggle('show');
    navButton.classList.toggle('fa-arrow-up')
}
// create event listener for icon
navButton.addEventListener('click', showMe);
