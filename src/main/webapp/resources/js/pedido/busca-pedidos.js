const templatePedido = document.querySelector("#template-pedido")
const templateItemPedido = document.querySelector("#template-item-pedido")
const listaPedidosRecebidos = document.querySelector("#lista-pedidos-recebidos")
const mudarEstadoPara = document.querySelector("#change-state-to").value
const estadoABuscar = document.querySelector("#estado-pedido").value
const intervaloDeBusca = 5000;

const stringFormaPagamento = { "CARTAO": "Cartão", "DINHEIRO": "Dinheiro" }

function mantemAtualizado(estado, locationCaseFailure){
	let busca = {
		estado: estado,
		fetchPizzas: "true",
		fetchBebidas: "true"
	}
	let callbackError = (status) =>{
		console.log("erro " + status)
		if(status === 403 || status === 301 || status === 302){
			window.location.href=locationCaseFailure;
		}
	}
	buscaPedidos(busca, callbackError)
	setInterval(() => buscaPedidos(busca, callbackError), intervaloDeBusca)
}

// function buscaPedidos(estado) {
// 	var xmlHttp = new XMLHttpRequest()
// 	xmlHttp.responseType = "json"
// 	xmlHttp.onreadystatechange = function() {
// 		if (xmlHttp.status == 200) {
// 			renderizaListaPedidos(listaPedidosRecebidos, xmlHttp.response)
// 		}
// 	}
// 	xmlHttp.open("GET", contextPath + "/pedido/json/lista?estado=" + estado + "&fetchPizzas=true&fetchBebidas=true", true)
// 	xmlHttp.send(null)
// }


function buscaPedidos(busca, callbackError=null) {
	var xmlHttp = new XMLHttpRequest()
	xmlHttp.responseType = "json"
	xmlHttp.onreadystatechange = function() {
		if(xmlHttp.readyState == XMLHttpRequest.DONE){
			if (xmlHttp.status == 200) {
				renderizaListaPedidos(listaPedidosRecebidos, xmlHttp.response)
			} else if(callbackError != null) {
				console.log("xmlHttp erro " + xmlHttp.status)
				callbackError(xmlHttp.status)
			}
		}
	}
	let requestParameters = "?";
	Object.keys(busca).forEach((k) => {
		requestParameters += `${k}=${busca[k]}&`
	})
	console.log(requestParameters)
	xmlHttp.open("GET", contextPath + "/pedido/json/lista"+requestParameters, true)
	console.log("Busca: " + JSON.stringify(busca))
	xmlHttp.send(null)
}

function renderizaListaPedidos(lista, pedidos) {
	lista.innerHTML = ""
	for (var i in pedidos) {
		adicionaPedidoALista(lista, pedidos[i])
	}
}

function adicionaPedidoALista(lista, dadosPedido) {
	var pedido = document.importNode(templatePedido.content, true)
	preencheDadosDoPedido(pedido, dadosPedido)
	lista.appendChild(pedido)

}

function preencheDadosDoPedido(pedido, dados) {
	pedido.querySelector(".numero-pedido").innerHTML = "Pedido número " + dados["id"];
	preencheDadosDataHora(pedido, dados["horaPedido"])
	preencheDadosPagamento(pedido, dados["pagamento"])
	preencheDadosCliente(pedido, dados["cliente"])
	preencheDadosEndereco(pedido, dados["endereco"])
	adicionaPizzasAoPedido(pedido, dados["pizzas"])
	adicionaBebidasAoPedido(pedido, dados["bebidas"])
	configuraBotao(pedido, dados["id"], mudarEstadoPara, pedido["estado"])
}

function configuraBotao(pedido, id, novoEstado, estadoAtual){
	var btn = pedido.querySelector(".button-muda-estado")
	btn.onclick = function() {
		mudaEstado(id, novoEstado, function (){buscaPedidos(estadoABuscar)})
		console.log(estadoABuscar)
	}
}

function preencheDadosDataHora(pedido, dataHora){
	var strData = VMasker.toPattern(dataHora[2],"99") + "/" + VMasker.toPattern(dataHora[1],"99") + "/" + VMasker.toPattern(dataHora[0],"9999")
	pedido.querySelector(".data-pedido").innerHTML = strData
	var strHora = VMasker.toPattern(dataHora[3],"99") + ":" + VMasker.toPattern(dataHora[4],"99")
	pedido.querySelector(".hora-pedido").innerHTML = strHora
}

function preencheDadosEndereco(pedido, dadosEndereco){
	pedido.querySelector(".logradouro").innerHTML = dadosEndereco["logradouro"]
	pedido.querySelector(".numero-endereco").innerHTML = dadosEndereco["numero"]
	if(dadosEndereco["complemento"] && !(dadosEndereco["complemento"] === "")){
		pedido.querySelector(".complemento-endereco").innerHTML = dadosEndereco["complemento"]
		exibeElemento(pedido.querySelector(".container-complemento-endereco"))
	}
	pedido.querySelector(".bairro").innerHTML = dadosEndereco["bairro"]
	pedido.querySelector(".cidade").innerHTML = dadosEndereco["cidade"]
	pedido.querySelector(".estado-endereco").innerHTML = dadosEndereco["estado"]
	pedido.querySelector(".cep").innerHTML = VMasker.toPattern(dadosEndereco["cep"], "99999-999")
}

function preencheDadosCliente(pedido, dadosCliente){
	pedido.querySelector(".nome-cliente").innerHTML = dadosCliente["nome"]
	pedido.querySelector(".email-cliente").innerHTML = dadosCliente["email"]
	if (dadosCliente["telefone"] != undefined  && dadosCliente["telefone"] != "") {
		pedido.querySelector(".telefone-cliente").innerHTML = VMasker.toPattern(dadosCliente["telefone"], "(99) 9999-9999")
		exibeElemento(pedido.querySelector(".container-telefone-cliente"))
	}
	if (dadosCliente["celular"] != undefined && !(dadosCliente["celular"] === "")) {
		pedido.querySelector(".celular-cliente").innerHTML = VMasker.toPattern(dadosCliente["celular"], "(99) 99999-9999")
		exibeElemento(pedido.querySelector(".container-celular-cliente"))
	}
	
}

function preencheDadosPagamento(pedido, dadosPagamento) {
	var formaPagamento = dadosPagamento["formaDePagamento"];
	pedido.querySelector(".forma-pagamento").innerHTML = stringFormaPagamento[formaPagamento];
	var campoValor = pedido.querySelector(".valor-pedido")
	campoValor.innerHTML = maskMoney(dadosPagamento["valor"]);
	if (formaPagamento === "DINHEIRO") {
		
		var campoValorReceber = pedido.querySelector(".valor-receber");
		campoValorReceber.innerHTML = maskMoney(dadosPagamento["valorAReceber"])
		var containerValorReceber = pedido.querySelector(".container-valor-receber")
		exibeElemento(containerValorReceber)
		
		var campoTroco = pedido.querySelector(".valor-troco");
		var valorTroco = dadosPagamento["valorAReceber"] - dadosPagamento["valor"]
		if(valorTroco > 0){
			campoTroco.innerHTML = maskMoney(valorTroco)
			var containerTroco = pedido.querySelector(".container-troco")
			exibeElemento(containerTroco);
		}
	}
}

function adicionaPizzasAoPedido(pedido, pizzas) {
	for (let i = 0; i < pizzas.length; i++) {
		var pizza = pizzas[i]
		var itemPizza = document.importNode(templateItemPedido.content, true)
		itemPizza.querySelector(".titulo").innerHTML = pizza["descricao"]
		itemPizza.querySelector(".quantidade").innerHTML = pizza["quantidade"]
		pedido.querySelector(".tabela-itens").appendChild(itemPizza)
	}
}

function adicionaBebidasAoPedido(pedido, bebidas) {
	for (let i = 0; i < bebidas.length; i++) {
		var bebida = bebidas[i]
		var itemBebida = document.importNode(templateItemPedido.content, true)
		itemBebida.querySelector(".titulo").innerHTML = bebida["descricao"]
		itemBebida.querySelector(".quantidade").innerHTML = bebida["quantidade"]
		pedido.querySelector(".tabela-itens").appendChild(itemBebida)
	}
}

function exibeElemento(elemento){
	elemento.classList.remove("d-none");
	elemento.classList.add("d-block");
}

function maskMoney(s) {
	s = s.toString()
	s = s.replace(/\./, 'dot')
	s = s.replace(/,/, 'comma')
	s = s.replace(/dot/, ',')
	s = s.replace(/comma/, '.')
	sParts = s.split(",")
	if(sParts.length > 1){
		if(sParts[1].length > 2){
			s = sParts[0] + "," + sParts[1].slice(0, 2);
		} else if (sParts[1].length < 2) {
			s = s + "0"
		}
	} else {
		s = s + ",00"
	}
	s = "R$ " + s
	return s
}
