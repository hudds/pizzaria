const listaSabores = document.querySelector("#lista-sabores");
const listaSaboresSelecionados = document.querySelector("#lista-sabores-selecionados");
const templateCardSabor = document.querySelector("#template-card-sabor");
const templateSaborSelecionado = document.querySelector("#template-sabor-selecionado");
const campoBusca = document.querySelector(".campo-busca");
const contextPath = document.querySelector("#context-path").value;
const tipoSelecionado = document.querySelector("#input-tipo-selecionado").value;

const saboresSelecionados = new Map();

function buscaSabores(tipo="", busca=""){
	var xmlHttp = new XMLHttpRequest()
	xmlHttp.open("GET", contextPath +"/sabor/json?tipo="+tipo+"&busca="+busca, false)
	xmlHttp.send(null)
	return JSON.parse(xmlHttp.responseText)
}

function selecionaSabor(sabor) {
	if (saboresSelecionados.size >= 4){
		return
	}
    if(saboresSelecionados.get(sabor["id"]) == undefined){
        listaSaboresSelecionados.appendChild(criaElementoSaborSelecionado(sabor))
        saboresSelecionados.set(sabor["id"], sabor)
    }
}

function deselecionaSabor(id, elementoSaborSelecionado){
    saboresSelecionados.delete(id);
    //elementoSaborSelecionado.innerHTML=""
    elementoSaborSelecionado.outerHTML=""
}

function criaElementoSaborSelecionado(sabor){
    var elementoSaborSelecionado = document.importNode(templateSaborSelecionado.content, true);
    elementoSaborSelecionado.querySelector(".sabor-selecionado-titulo").innerText = sabor["titulo"]
    elementoSaborSelecionado.id = "sabor-selecionado-"+sabor["id"]
    console.log(elementoSaborSelecionado.id);
    var btnRemover = elementoSaborSelecionado.querySelector(".btn-remover-sabor-selecionado")
    btnRemover.addEventListener("click", function (e){
        deselecionaSabor(sabor["id"], this.parentNode);
    })
    console.log(elementoSaborSelecionado.className)
    return elementoSaborSelecionado;
}

function criaCardSabor(sabor){
	var cardBody = document.importNode(templateCardSabor.content, true);
	cardBody.querySelector(".card-sabor-titulo").textContent = sabor["titulo"];
	cardBody.querySelector(".card-sabor-descricao").textContent = sabor["descricao"];
    var btnSelecionar = cardBody.querySelector(".card-sabor-btn-selecionar");
    btnSelecionar.addEventListener("click", function(e) {
        selecionaSabor(sabor)
    })
	return cardBody
}

function renderizaSabores(sabores){
	listaSabores.innerHTML = ""
	for (let i = 0; i < sabores.length; i++){
		var id = sabores[i]["id"]
		var titulo = sabores[i]["titulo"]
		var descricao = sabores[i]["descricao"]
		listaSabores.appendChild(criaCardSabor(sabores[i]))
	}
}

// function setTipoSelecionado(tipo){
// 	tipoSelecionado = tipo;
// 	document.querySelector(".dropdown-tipo-selecionado").innerText = "Exibindo somente pizzas do tipo: "+tipoSelecionado;
	
// }

function acaoBuscar(){
	console.log(tipoSelecionado)
	var busca = campoBusca.value.trim();
	campoBusca.value = busca
	var tipoBusca = tipoSelecionado
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

// function adicionaEventosAoDropdownTipos(){
// 	var itensDropDown = document.querySelectorAll(".dropdown-item-tipo")
// 	for(let i = 0; i < itensDropDown.length; i++){
// 		var itemDropdown = itensDropDown[i]
// 		itemDropdown.addEventListener("click", function(e){
// 			setTipoSelecionado(e.target.innerText)
// 			acaoBuscar()
// 		})
// 	}
// }

function adicionaEventoAoFormBusca(){
	document.querySelector(".form-busca").addEventListener("keypress", function(e) {
		if (e.which == 13 || e.keyCode == 13) {
			e.preventDefault()
			acaoBuscar()
		}
	})
}


//setTipoSelecionado("SALGADA")
renderizaSabores(buscaSabores(tipoSelecionado))
//adicionaEventosAoDropdownTipos()
adicionaEventoAoFormBusca()
console.log(contextPath)