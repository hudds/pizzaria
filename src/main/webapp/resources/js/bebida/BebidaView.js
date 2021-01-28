class BebidaView extends View{
    constructor(domElement, contextPath, isAdmin){
        super(domElement)
        console.log("bebida isAdmin: " + isAdmin)
        this._isAdmin = isAdmin
        this._pathImages = Paths.images(contextPath)
    }

    _template(model){
        return`
            <table class="table">
				<thead>
					<tr>
						<th scope="col">Título</th>
						<th scope="col">Valor</th>
					</tr>
				</thead>
                <tbody>
                    ${model.map((bebida) => `
                        <tr class="${bebida.visivel ? '' : 'oculto'}">
                            <td>${bebida.titulo}</td>
                            <td>R$ ${bebida.valor}</td>
                            ${this._isAdmin ? `
                                <td>
                                    <a href="/bebida/edit/${bebida.id}" class="btn btn-danger text-white">Editar</a>
                                </td>
                                <td>
                                    <button onclick="bebidaController.updateModalDelete(${bebida.id}, '${bebida.titulo}')" class="btn btn-danger text-white" data-toggle="modal" data-target="#${ModalConfirmDeleteView.getHtmlId()}">Deletar</button>
                                </td>
                                <td>
                                    ${this._getVisibilityToggleButton(bebida)}
                                </td>
                            ` : ''}
                        </tr>
                    `).join("")}
                </tbody>
            </table>
            `
    }

    _getVisibilityToggleButton(bebida){
        let imgName = bebida.visivel ?  "visivel.png" : "oculto.png"
        let imgTitle = bebida.visivel ?  "Tornar invisível" : "Tornar visível"
        let alt = bebida.visivel ?  "Olho" : "Olho riscado"
        return `
            <button class="btn-toggle-visibility" type="button" onclick="bebidaController.setVisivel(${bebida.id}, '${!bebida.visivel}')"><img title="${imgTitle}" alt="${alt}" src="${this._pathImages}${imgName}"/></button>
        `  
    }
}