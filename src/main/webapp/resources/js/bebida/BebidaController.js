class BebidaController{
    constructor(contextPath, csrfToken, csrfHeader, isAdmin){
        let $ = document.querySelector.bind(document)
        this._contextPath = contextPath
        this._ajax = new Ajax(csrfToken, csrfHeader)
        this._bebidaService = new BebidaService(this._ajax);
        console.log($("#bebidas"))
        this._bebidaView = new BebidaView($("#bebidas"), contextPath, isAdmin)
        this._modalDeleteView = new ModalConfirmDeleteView($("#containerModalConfirmDelete"), isAdmin)
        this._message = Binder.bind(new Message(), new MessageView($("#messageContainer")), "text", "level")
        this.updateModalDelete()
        this._loadBebidas();
    }

    setVisivel(id, visivel){
        this._bebidaService.setVisivel(id, visivel, () => {
            this._loadBebidas()
        })
    }

    updateModalDelete(id="", title=""){
        this._modalDeleteView.update({title:title, action: `bebidaController.deleteBebida(${id})`})
    }

    deleteBebida(id){
        this._bebidaService.deleteBebida(id, () => {
            this._loadBebidas()
            this._message.text = "Bebida foi deletada."
            this._message.level = "success"
        }, (status, error) => {
            console.log(error.errorName)
            if(error.errorName === "DataIntegrity"){
                this._message.level = "error"
                this._message.text = "Não foi possível deletar esta bebida porque já existem pedidos registrados com ela. É possível torná-la invisível para que não seja possível fazer mais pedidos com ela."
            }
        })
    }

    _loadBebidas() {
        this._bebidaService.getBebidas(bebidas => this._bebidaView.update(bebidas));
    }
}