const listaSabores = document.querySelector("#lista-sabores");
const templateCardSabor = document.querySelector("#template-card-sabor");
const campoBusca = document.querySelector(".campo-busca");
const contextPath = document.querySelector("#context-path").value;

var tipoSelecionado;

function buscaSabores(tipo="", busca=""){
	var xmlHttp = new XMLHttpRequest()
	xmlHttp.open("GET", contextPath +"/sabor/json?tipo="+tipo+"&busca="+busca, false)
	xmlHttp.send(null)
	return JSON.parse(xmlHttp.responseText)
}

function criaCardSabor(id, titulo, descricao){
	var cardBody = document.importNode(templateCardSabor.content, true);
	cardBody.querySelector(".card-sabor-titulo").textContent = titulo;
	cardBody.querySelector(".card-sabor-descricao").textContent = descricao;
	var btnEditar = cardBody.querySelector(".card-sabor-btn-editar");
	var btnDeletar = cardBody.querySelector(".card-sabor-btn-deletar");
	if(btnEditar != null){
		btnEditar.setAttribute("href", contextPath+"/sabor/edit/"+id)
	}
	if(btnDeletar != null){
		btnDeletar.setAttribute("href", contextPath+"/sabor/delete/"+id)
	}
	return cardBody
}

function renderizaSabores(sabores){
	listaSabores.innerHTML = ""
	for (let i = 0; i < sabores.length; i++){
		var id = sabores[i]["id"]
		var titulo = sabores[i]["titulo"]
		var descricao = sabores[i]["descricao"]
		listaSabores.appendChild(criaCardSabor(id, titulo, descricao))
	}
}

function setTipoSelecionado(tipo){
	tipoSelecionado = tipo;
	document.querySelector(".dropdown-tipo-selecionado").innerText = "Exibindo somente pizzas do tipo: "+tipoSelecionado;
	
}

function acaoBuscar(){
	var busca = campoBusca.value.trim();
	campoBusca.value = busca
	var tipoBusca = tipoSelecionado === "TODAS" ? "" : tipoSelecionado
	var sabores = buscaSabores(tipoBusca, busca)
	var alert = document.querySelector(".alert-busca")
	if(busca.trim().length > 0){
		alert.innerText = 'Exibindo resultados para a busca "'+ busca +'".'
		alert.style.display = "block";
	} else {
		alert.style.display = "none";
	}
	renderizaSabores(sabores);
}

function adicionaEventosAoDropdownTipos(){
	var itensDropDown = document.querySelectorAll(".dropdown-item-tipo")
	for(let i = 0; i < itensDropDown.length; i++){
		var itemDropdown = itensDropDown[i]
		itemDropdown.addEventListener("click", function(e){
			setTipoSelecionado(e.target.innerText)
			acaoBuscar()
		})
	}
}

function adicionaEventoAoFormBusca(){
	document.querySelector(".form-busca").addEventListener("keypress", function(e) {
		if (e.which == 13 || e.keyCode == 13) {
			e.preventDefault()
			acaoBuscar()
		}
	})
}


setTipoSelecionado("SALGADA")
renderizaSabores(buscaSabores(tipoSelecionado))
adicionaEventosAoDropdownTipos()
adicionaEventoAoFormBusca()
console.log(contextPath)
