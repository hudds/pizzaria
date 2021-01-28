class PizzaController{
    constructor(contextPath, csrfToken, csrfHeader, isAdmin){
        let $ = document.querySelector.bind(document)
        this._contextPath = contextPath
        this._ajax = new Ajax(csrfToken, csrfHeader)
        this._pizzaService = new PizzaService(this._ajax);
        this._pizzaView = new PizzaView($("#pizzas"), contextPath, isAdmin)
        this._modalDeleteView = new ModalConfirmDeleteView($("#containerModalConfirmDelete"), isAdmin)
        this._message = Binder.bind(new Message(), new MessageView($("#messageContainer")), "text", "level")
        this.updateModalDelete()
        this._loadPizzas();
    }

    setVisivel(id, visivel){
        this._pizzaService.setVisivel(id, visivel, () => {
            this._loadPizzas()
        })
    }

    updateModalDelete(id="", title=""){
        this._modalDeleteView.update({title:title, action: `pizzaController.deletePizza(${id})`})
    }

    deletePizza(id){
        this._pizzaService.deletePizza(id, () => {
            this._loadPizzas()
            this._message.text = "Pizza foi deletada."
            this._message.level = "success"
        }, (status, error) => {
            console.log(error.errorName)
            if(error.errorName === "DataIntegrity"){
                this._message.level = "error"
                this._message.text = "Não foi possível deletar esta pizza porque já existem pedidos registrados com ela. É possível torná-la invisível para que não seja possível fazer mais pedidos com ela."
            }
        })
    }

    _loadPizzas() {
        this._pizzaService.getPizzas(pizzas => this._pizzaView.update(pizzas));
    }
}