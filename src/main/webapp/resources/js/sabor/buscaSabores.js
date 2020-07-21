const contextPath = document.querySelector("#context-path").value;

function buscaSabores(tipo="", busca=""){
	var xmlHttp = new XMLHttpRequest()
	xmlHttp.open("GET", contextPath +"/sabor/json?tipo="+tipo+"&busca="+busca, false)
	xmlHttp.send(null)
	return JSON.parse(xmlHttp.responseText)
}