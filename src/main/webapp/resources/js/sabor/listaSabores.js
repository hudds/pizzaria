const listaSabores = document.querySelector("#lista-sabores");
const templateCardSabor = document.querySelector("#template-card-sabor");
const campoBusca = document.querySelector(".campo-busca");

var contextPath = document.querySelector("#context-path").value;
var tipoSelecionado;
function criaCardSabor(id, titulo, descricao, visivel){
	var cardBody = document.importNode(templateCardSabor.content, true);
	cardBody.querySelector(".card-sabor-titulo").textContent = titulo;
	cardBody.querySelector(".card-sabor-descricao").textContent = descricao;
	var btnEditar = cardBody.querySelector(".card-sabor-btn-editar");
	if(btnEditar != null){
		btnEditar.setAttribute("href", contextPath+"/sabor/edit/"+id)
	}

	var modal = document.querySelector("#modalConfirmarDelete")

	if(modal != null && modal != undefined){
		var btnDelete = cardBody.querySelector(".btn-delete-sabor")
		btnDelete.addEventListener("click", () => {
			modal.querySelector(".modal-nome-sabor").textContent = titulo
			modal.querySelector(".form-deletar-sabor").action=contextPath+"/sabor/delete/"+id
			console.log(modal.querySelector(".form-deletar-sabor").action)
		})
	}
	console.log("visivel: " + visivel)
	if(!visivel){
		cardBody.querySelector(".card-sabor").classList.add("sabor-oculto")
	}

	var btnMostrarSabor = cardBody.querySelector(".btn-mostrar-sabor")
	var btnOcultarSabor = cardBody.querySelector(".btn-ocultar-sabor")
	if(btnMostrarSabor != null && btnMostrarSabor != undefined){
		btnMostrarSabor.addEventListener("click", function() {ajax.changeVisibility("sabor", id, true, acaoBuscar)})
		if(visivel){
			btnMostrarSabor.remove()
		}
	}
	if(btnOcultarSabor != null && btnOcultarSabor != undefined){
		btnOcultarSabor.addEventListener("click", function() {ajax.changeVisibility("sabor", id, false, acaoBuscar)})
		if(!visivel){
			btnOcultarSabor.remove()
		}
	}

	return cardBody
}

function modificaVisibilidade(id, visivel){
	var xhr = new XMLHttpRequest()
	var url
	if(visivel){
		url = contextPath+"/sabor/mostra/"+id
	} else{
		url = contextPath+"/sabor/esconde/"+id
	}
	xhr.open("PUT", url)
	xhr.onreadystatechange = () => {
		if(xhr.readyState === XMLHttpRequest.DONE && (xhr.status === 200 || xhr.status === 204)){
			acaoBuscar()
		}
	}

	var elementToken = document.querySelector('#_csrf');
	var token = elementToken.value
	var elementHeader = document.querySelector('#_csrf_header')
	var header = elementHeader.value

	xhr.setRequestHeader(header, token)

	xhr.send(null)
}

function renderizaSabores(sabores){
	listaSabores.innerHTML = ""
	for (let i = 0; i < sabores.length; i++){
		var id = sabores[i]["id"]
		var titulo = sabores[i]["titulo"]
		var descricao = sabores[i]["descricao"]
		var visivel = sabores[i]["visivel"]
		listaSabores.appendChild(criaCardSabor(id, titulo, descricao, visivel))
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
	buscaSabores(tipoBusca, busca, (sabores) => {
		var alert = document.querySelector(".alert-busca")
		if(busca.trim().length > 0){
			alert.innerText = 'Exibindo resultados para a busca "'+ busca +'".'
			alert.style.display = "block";
		} else {
			alert.style.display = "none";
		}
		renderizaSabores(sabores);
	})
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
