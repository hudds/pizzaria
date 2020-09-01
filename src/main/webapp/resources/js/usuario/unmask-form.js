const maskedForm = document.querySelector(".masks-form");

maskedForm.addEventListener("submit", function(e){
    maskedForm.querySelectorAll(".masked").forEach((input, i, inputs) => VMasker(input).unMask())
})