const telInput = document.querySelector(".mask-tel");
const celInput = document.querySelector(".mask-cel");

const telPattern = "(99) 9999-9999";
const celPattern = "(99) 99999-9999";

VMasker(telInput).maskPattern(telPattern)
VMasker(celInput).maskPattern(celPattern);
