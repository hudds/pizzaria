const maskedForm = document.querySelector(".masks-form");

const cepInput = document.querySelector(".mask-cep");
const estadoInput = document.querySelector(".input-estado");
const cidadeInput = document.querySelector(".input-cidade");
const bairroInput = document.querySelector(".input-bairro");
const logradouroInput = document.querySelector(".input-logradouro");
const complementoInput = document.querySelector(".input-complemento");
const telInput = document.querySelector(".mask-tel");
const celInput = document.querySelector(".mask-cel");

const cepPattern = "99999-999";
const telPattern = "(99) 9999-9999";
const celPattern = "(99) 99999-9999";

VMasker(cepInput).maskPattern(cepPattern)
VMasker(telInput).maskPattern(telPattern)
VMasker(celInput).maskPattern(celPattern);

cepInput.addEventListener("focusout", function(e) {
    var cep = cepInput.value.replace(/[^\d]/g, "")
    buscaEndereco(cep)
})

maskedForm.addEventListener("submit", function(e){
    maskedForm.querySelectorAll(".masked").forEach((input, i, inputs) => VMasker(input).unMask())
})

function buscaEndereco(cep){
    if(cep.replace(/[^\d]/g, "").length != 8){
        return
    }
    const http = new XMLHttpRequest()
    const url="https://viacep.com.br/ws/"+cep+"/json/unicode"

    http.onreadystatechange = function(){
        if(http.status == 200){
            var response  = JSON.parse(http.responseText)
            preencheCampos(response)
        }
    }

    http.open("GET", url, true)
    http.send()
}

function preencheCampos(endereco){

    if(endereco["erro"] === true){
        return
    }

    cidadeInput.value = endereco["localidade"] != undefined ?  endereco["localidade"] : ""
    bairroInput.value = endereco["bairro"] != undefined ? endereco["bairro"] : ""
    logradouroInput.value = endereco["logradouro"] != undefined ? endereco["logradouro"] : ""
    complementoInput.value = endereco["complemento"] != undefined ? endereco["complemento"] : ""
    if(endereco["uf"] != undefined){
        var index;
        for (let i = 0; i < estadoInput.options.length; i++) {
            const option = estadoInput.options[i];
            if(option.value == endereco["uf"]){
                index = i
                break
            }
            
        }

        estadoInput.selectedIndex = index

    }
}
