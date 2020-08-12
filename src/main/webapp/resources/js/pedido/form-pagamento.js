const brlInput = document.querySelector(".brl-input")
const formPagamento = document.querySelector(".form-pagamento")
const selectFormaPagamento = document.querySelector(".select-forma-pagamento")

atualizaVisibilidadeTrocoFormGroup();

VMasker(brlInput).maskMoney({unit:"R$"})


selectFormaPagamento.addEventListener("click", atualizaVisibilidadeTrocoFormGroup)

function atualizaVisibilidadeTrocoFormGroup(){
    if(getFormaDePagamentoSelecionada() === "DINHEIRO"){
        mostraTrocoFormGroup();
    } else {
        escondeTrocoFormGroup();
    }
}

formPagamento.addEventListener("submit", function(e){
    brlInput.value = brlInput.value.replace(/[^,\d]/g, "")
})

function getFormaDePagamentoSelecionada(){
    return selectFormaPagamento.options[selectFormaPagamento.selectedIndex].value
}

function escondeTrocoFormGroup(){
    formPagamento.querySelector(".valor-troco-form-group").style.display = "none";
}

function mostraTrocoFormGroup(){
    formPagamento.querySelector(".valor-troco-form-group").style.display = "block";
}