const details = document.querySelector("#details");
const detailButton = document.querySelector("#showdetails");
function show(){
    details.classList.toggle("show");
}
detailButton.addEventListener("click", show);