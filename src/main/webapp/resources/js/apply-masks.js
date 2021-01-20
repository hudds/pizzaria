
var dateTimeInputs = document.querySelectorAll(".date-time-input")

maskCEPs()

maskCelulares()

maskTelefones()

if(!(dateTimeInputs === null || dateTimeInputs === undefined)){
	for(let i = 0; i < dateTimeInputs.length; i++){
		var dateTimeInput = dateTimeInputs[i]
		dateTimeInput.placeholder = applyDateTimeMask("19/08/2020 23h:35")
		dateTimeInput.addEventListener("focusout", function(e){
			this.value = applyDateTimeMask(this.value)
		})
	}
}

function maskInputsCEP() {
	const cepPattern = "99999-999";
    document.querySelectorAll(".mask-cep").forEach((cepInput) => {
        VMasker(cepInput).maskPattern(cepPattern);
    })
}

function maskInputsTelefone() {
	var telInputs = document.querySelectorAll(".mask-tel")
	const telPattern = "(99) 9999-9999"
	if(!(telInputs === null || telInputs === undefined)){
		telInputs.forEach((telInput) => {
			VMasker(telInput).maskPattern(telPattern)
		})
	}
}

function maskInputsCelular(){
	var celInputs = document.querySelectorAll(".mask-cel")
	const celPattern = "(99) 99999-9999"
	celInputs.forEach((celInput) => {
		VMasker(celInput).maskPattern(celPattern)
	})
}

function maskTelefones() {
	var telefones= document.querySelectorAll(".telefone")
	if(!(telefones === null || telefones === undefined)){
		for (let i = 0; i < telefones.length; i++) {
			var telefone = telefones[i]
			telefone.innerText = VMasker.toPattern(telefone.innerText, "(99) 9999-9999")
		}
	}
}

function maskCelulares() {
	var celulares = document.querySelectorAll(".celular")
	if(!(celulares === null || celulares === undefined)){
		for (let i = 0; i < celulares.length; i++) {
			var celular = celulares[i]
			celular.innerText = VMasker.toPattern(celular.innerText, "(99) 99999-9999")
		}
	}
}

function maskCEPs() {
	var ceps = document.querySelectorAll(".cep")
	if(!(ceps === null || ceps === undefined)){
		for (let i = 0; i < ceps.length; i++) {
			var cep = ceps[i]
			cep.innerText = VMasker.toPattern(cep.innerText, "99999-999")
		}
	}
}

function applyDateTimeMask(s){
	s = s.replace(/[^\d]/g, "")
	return VMasker.toPattern(s, "99/99/9999 99h:99")
}
