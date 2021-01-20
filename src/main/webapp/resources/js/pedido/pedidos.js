var formEnderecoHTML = document.getElementById("form-endereco").innerHTML
var formClienteHTML = document.getElementById("form-cliente").innerHTML
document.getElementById("form-endereco").innerHTML = "";
document.getElementById("form-cliente").innerHTML = "";

if(document.getElementById("show-endereco-form").value === "true"){
	showEnderecoForm(document.getElementById("form-endereco"))
}

if(document.getElementById("show-cliente-form").value === "true"){
	showClienteForm(document.getElementById("form-cliente"))
} else {
	hideClienteForm(document.getElementById("form-cliente"))
}

function toggleEnderecoForm(){
	var formEndereco = document.getElementById("form-endereco")
	if(formEndereco.style.display === "block"){
		hideEnderecoForm(formEndereco);
	} else {
		showEnderecoForm(formEndereco);
	}
}

function toggleClienteForm(){
	var formCliente = document.getElementById("form-cliente")
	if(formCliente.style.display === "block"){
		hideClienteForm(formCliente);
	} else {
		showClienteForm(formCliente);
	}
}

function showClienteForm(formCliente){
	document.getElementById("form-cliente").innerHTML = formClienteHTML;
	document.querySelector(".toggle-cliente-form").textContent = "Esconder opções de busca pelo cliente."
	maskInputsTelefone()
	maskInputsCelular()
	formCliente.style.display = "block"
}

function hideClienteForm(formCliente){
	document.getElementById("form-cliente").innerHTML = "";
	document.querySelector(".toggle-cliente-form").textContent = "Mostrar opções de busca pelo cliente."
	formCliente.style.display = "none"
}

function hideEnderecoForm(formEndereco){
	document.getElementById("form-endereco").innerHTML = "";
	document.querySelector(".toggle-endereco-form").textContent = "Mostrar opções de busca pelo endereço."
	formEndereco.style.display = "none"
}

function showEnderecoForm(formEndereco){
	document.getElementById("form-endereco").innerHTML = formEnderecoHTML;
	document.querySelector(".toggle-endereco-form").textContent = "Esconder opções de busca pelo endereço."
	maskInputsCEP()
	adicionaEventoDeBuscaAoCEP(preencheCampos)
	formEndereco.style.display = "block"
}