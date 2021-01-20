class PizzaController{
    constructor(contextPath, csrfToken, csrfHeader, isAdmin){
        let $ = document.querySelector.bind(document)
        this._contextPath = contextPath
        this._ajax = new Ajax(csrfToken, csrfHeader)
        this._pizzaView = new PizzaView($("#pizzas"), contextPath, isAdmin)
        this._loadPizzas();
    }

    setVisivel(id, visivel){
        this._ajax.put(`${this._contextPath}/pizza/visivel/${id}`, visivel, () => this._loadPizzas())
    }


    _loadPizzas() {
        this._ajax.get(`${this._contextPath}/pizza/json`, null, pizzas => this._pizzaView.update(JSON.parse(pizzas)));
    }
}