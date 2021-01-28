class PizzaService {
    constructor(ajax){
        this._ajax = ajax;
    }

    getPizzas(callback) {
        this._ajax.get(`pizza/json`, null, pizzas => callback(JSON.parse(pizzas)));
    }

    setVisivel(id, visivel, callback){
        this._ajax.put(`pizza/visivel/${id}`, visivel, callback)
    }

    deletePizza(id, callback, errorCallback = null){
        this._ajax.delete(`pizza/json/delete/${id}`, null, callback, (status, error) => {
            if(errorCallback != null){
                console.log(error)
                errorCallback(status, JSON.parse(error))
            }
        });
    }
}