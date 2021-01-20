const contextPath = document.querySelector("#context-path").value

function mudaEstado(id, estado, callback) {
	var elementToken = document.querySelector('#_csrf');
	var token = elementToken.value
	var elementHeader = document.querySelector('#_csrf_header')
	var header = elementHeader.value
	
	console.log(elementToken.content)
	console.log(header)
	
	var xmlHttp = new XMLHttpRequest()
	xmlHttp.onreadystatechange = function() {
		console.log(xmlHttp.responseText)
		if (xmlHttp.status == 200) {
			callback()
		}
	}
	xmlHttp.open("PUT", contextPath + "/pedido/estado/" + id, true)
	xmlHttp.setRequestHeader(header, token)
	xmlHttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	console.log('{"estado":"' + estado +'"}')
	xmlHttp.send('{"estado":"' + estado +'"}')
}