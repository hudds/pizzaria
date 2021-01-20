var contextPath = document.querySelector("#context-path").value;

function buscaSabores(tipo="", busca="", callback=null){
	console.log("Tipo sabor: " + tipo);
	if(callback == null){
		console.log("buscando sincrono")
		return buscaSaboresSemCallback(tipo, busca)
	} else {
		console.log("buscando Assincrono")
		var xmlHttp = new XMLHttpRequest()
		xmlHttp.open("GET", contextPath +"/sabor/json/like?tipo="+tipo+"&titulo="+busca+"&descricao="+busca, true)
		xmlHttp.onreadystatechange = () => {
			if(xmlHttp.readyState === XMLHttpRequest.DONE && xmlHttp.status === 200){
				callback(JSON.parse(xmlHttp.responseText))
			}
		}
		xmlHttp.send(null)
	}
	
}

function buscaSaboresSemCallback(tipo="", busca=""){
	var xmlHttp = new XMLHttpRequest()
	xmlHttp.open("GET", contextPath +"/sabor/json?tipo="+tipo+"&busca="+busca, false)
	xmlHttp.send(null)
	return JSON.parse(xmlHttp.responseText)
}