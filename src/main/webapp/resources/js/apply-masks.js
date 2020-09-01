var ceps = document.querySelectorAll(".cep")
var celulares = document.querySelectorAll(".celular")
var telefones= document.querySelectorAll(".telefone")
var dateTimeInputs = document.querySelectorAll(".date-time-input")

for(let i = 0; i < ceps.length; i++){
	var cep = ceps[i]
	cep.innerText = VMasker.toPattern(cep.innerText, "99999-999")	
}

for(let i = 0; i < celulares.length; i++){
	var celular = celulares[i]
	celular.innerText = VMasker.toPattern(celular.innerText, "(99) 99999-9999")
}

for(let i = 0; i < telefones.length; i++){
	var telefone = telefones[i]
	telefone.innerText = VMasker.toPattern(telefone.innerText, "(99) 9999-9999")
}

for(let i = 0; i < dateTimeInputs.length; i++){
	var dateTimeInput = dateTimeInputs[i]
	dateTimeInput.placeholder = applyDateTimeMask("19/08/2020 23h:35")
	dateTimeInput.addEventListener("focusout", function(e){
		this.value = applyDateTimeMask(this.value)
	})
}

function applyDateTimeMask(s){
	s = s.replace(/[^\d]/g, "")
	return VMasker.toPattern(s, "99/99/9999 99h:99")
}
