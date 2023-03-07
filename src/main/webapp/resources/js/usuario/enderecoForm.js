
maskInputsCEP();

adicionaEventoDeBuscaAoCEP(preencheCampos);

function adicionaEventoDeBuscaAoCEP(callback) {
    var cepInputs = document.querySelectorAll(".mask-cep");
    cepInputs.forEach((cepInput) => {
        cepInput.addEventListener("focusout", function (e) {
            var cep = cepInput.value.replace(/[^\d]/g, "");
            buscaEndereco(cep, callback);
        });
    });
}

function buscaEndereco(cep, callback){
    if(cep.replace(/[^\d]/g, "").length != 8){
        return
    }
    const http = new XMLHttpRequest()
    const url="https://viacep.com.br/ws/"+cep+"/json"

    http.onreadystatechange = function(){
        if(http.status == 200){
            var response  = JSON.parse(http.responseText)
            callback(response)
        }
    }

    http.open("GET", url, true)
    http.send()
}

function preencheCampos(endereco){

    if(endereco["erro"] === true){
        return
    }
    
    var estadoInputs = document.querySelectorAll(".input-estado");
    var cidadeInputs = document.querySelectorAll(".input-cidade");
    var bairroInputs = document.querySelectorAll(".input-bairro");
    var logradouroInputs = document.querySelectorAll(".input-logradouro");
    var complementoInputs = document.querySelectorAll(".input-complemento");

    estadoInputs.forEach((estadoInput) => {
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
    });

    cidadeInputs.forEach((cidadeInput) => {
        cidadeInput.value = endereco["localidade"] != undefined ?  endereco["localidade"] : cidadeInput.value
    });

    bairroInputs.forEach((bairroInput) => {
        bairroInput.value = endereco["bairro"] != undefined ? endereco["bairro"] : bairroInput.value
    });

    logradouroInputs.forEach((logradouroInput) => {
        logradouroInput.value = endereco["logradouro"] != undefined ? endereco["logradouro"] : logradouroInput.value
    });

    complementoInputs.forEach((complementoInput) => {
        if(endereco["complemento"] != undefined){
            complementoInput.value = endereco["complemento"]
        }
    })

}

