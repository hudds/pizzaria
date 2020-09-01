var formEnderecoHTML = document.getElementById("form-endereco").innerHTML
document.getElementById("form-endereco").innerHTML = "";

if(document.getElementById("show-endereco-form").value === "true"){
	showEnderecoForm(document.getElementById("form-endereco"));
}

function toggleEnderecoForm(){
	var formEndereco = document.getElementById("form-endereco")
	if(formEndereco.style.display === "block"){
		hideEnderecoForm(formEndereco);
	} else {
		showEnderecoForm(formEndereco);
	}
}

function hideEnderecoForm(formEndereco){
	document.getElementById("form-endereco").innerHTML = "";
	document.querySelector(".toggle-endereco-form").textContent = "Mostrar opções de busca pelo endereço."
	formEndereco.style.display = "none"
}

function showEnderecoForm(formEndereco){
	document.getElementById("form-endereco").innerHTML = formEnderecoHTML;
	document.querySelector(".toggle-endereco-form").textContent = "Esconder opções de busca pelo endereço."
	formEndereco.style.display = "block"
}