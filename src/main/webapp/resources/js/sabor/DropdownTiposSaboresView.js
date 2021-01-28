class DropDownTiposSaboresView extends View{

    _template(model){
        return `
        <a class="btn btn-danger dropdown-toggle dropdown-tipo-selecionado" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">        	
              ${model.tipoSelecionado.trim() === "" ? `
                Exibir somente pizzas do tipo:` : 
                `Exibindo somente pizzas do tipo ${model.tipoSelecionado}`
              }
        </a>
        <div class="dropdown-menu dropdown-tipos" aria-labelledby="navbarDropdown">

            <button class="dropdown-item dropdown-item-tipo" type = "button" onclick="saborController.selectTipo('')">TODAS</button>
            ${model.tipos.map((tipo) => `
                <button onclick="saborController.selectTipo('${tipo}')" class="dropdown-item dropdown-item-tipo" type="button" value="">${tipo}</button>`
            ).join("")}
        </div>
        `

    }

}