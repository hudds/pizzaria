class PizzaView extends View{

    constructor(domElement, contextPath, isAdmin){
        super(domElement)
        this._contextPath = contextPath;
        this._isAdmin = isAdmin
        this._pathImages = Paths.images(this._contextPath)
    }

    _template(model){
        return model.map((pizza)=>{
            return `
            <div class="card d-inline-block m-2 ${pizza.visivel ? '' : 'oculto'}" style="width: 18rem;">
                <div class="card-body">
                    ${this._isAdmin ? `${this._getVisibilityToggleButton(pizza)}` : ''}
                    <h5 class="card-title">${pizza.titulo}</h5>
                    <h5 class="card-title">${pizza.tipoSabor}</h5>
                    <h5 class="card-title"><fmt:formatNumber value="${pizza.preco}" type="currency" /></h5>
                    <a href="${this._contextPath}/pedido/fazerPedido?pizza=${pizza.id}" class="btn btn-danger mb-1">Fazer pedido.</a>
                    ${this._isAdmin ? this._getAdminButtons(pizza): ''}
                </div>
            </div>
            `
        }).join("")
    }

    _getAdminButtons(pizza) {
        return `
            <div>
                <a href="/pizza/edit/${pizza.id}" class="btn btn-danger mb-1">Editar Pizza</a>
            </div>
            <div>
                <a href="/pizza/delete/${pizza.id}" class="btn btn-danger">Deletar Pizza</a>
            </div>
        `
    }

    _getVisibilityToggleButton(pizza){
        let imgName = pizza.visivel ?  "visivel.png" : "oculto.png"
        let imgTitle = pizza.visivel ?  "Tornar invisível" : "Tornar visível"
        let alt = pizza.visivel ?  "Olho" : "Olho riscado"
        return `
            <button class="btn-toggle-visibility" type="button" onclick="pizzaController.setVisivel(${pizza.id}, ${!pizza.visivel})"><img title="${imgTitle}" alt="${alt}" src="${this._pathImages}${imgName}"/></button>
        `  
    }
    
}