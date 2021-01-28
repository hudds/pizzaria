class SaborView extends View{
    constructor(domElement, contextPath, isAdmin){
        super(domElement)
        this._contextPath = contextPath
        console.log("isAdmin: " + isAdmin) 
        this._isAdmin = isAdmin
        this._pathImages = Paths.images(contextPath)
    }

    _template(model){
        console.log("Sabor view recebeu sabores " + model)
        return model.map((sabor) => `
            <div class="card m-2 card-sabor ${sabor.visivel ? '' : 'oculto'}">
               <div class="card-body" >
                    ${this._isAdmin ? this._getVisibilityToggleButton(sabor) : ''}                    
                    <h5 class="card-title card-sabor-titulo">${sabor.titulo}</h5>
                    <p class="card-text card-sabor-descricao">${sabor.descricao}</p>
                    <a href="/pedido/fazerPedido" class="btn btn-danger mb-1 card-sabor-btn-fazer-pedido">Fazer pedido.</a>
                    ${this._getAdminButtons(sabor)}
                </div>
            </div>`
        ).join("")
    }

    _getVisibilityToggleButton(sabor){
        let imgName = sabor.visivel ?  "visivel.png" : "oculto.png"
        let imgTitle = sabor.visivel ?  "Tornar invisível" : "Tornar visível"
        let alt = sabor.visivel ?  "Olho" : "Olho riscado"
        return `
            <button class="btn-toggle-visibility" type="button" onclick="saborController.setVisivel(${sabor.id}, '${!sabor.visivel}')"><img title="${imgTitle}" alt="${alt}" src="${this._pathImages}${imgName}"/></button>
        `  
    }

    _getAdminButtons(sabor){
        return this._isAdmin ? `
            <div>
                <a href="/sabor/edit/${sabor.id}" class="btn btn-danger mb-1 card-sabor-btn-editar">
                    Editar Sabor
                </a>
            </div>
            <div>
                <button type="button" onclick="saborController.updateModalDelete(${sabor.id}, '${sabor.titulo}')" class="btn btn-danger btn-delete-sabor" data-toggle="modal" data-target="#${ModalConfirmDeleteView.getHtmlId()}">
                    Deletar sabor
                </button>
            </div>
        ` : ""
    }

}