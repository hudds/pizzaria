class SaborService {
    constructor(ajax){
        this._ajax = ajax;
    }

    getTipos(callback){
        this._ajax.get(`/sabor/tipos`, null, tipos => callback(JSON.parse(tipos)))
    }

    getSabores(callback) {
        this._ajax.get(`/sabor/json`, null, sabor => callback(JSON.parse(sabor)));
    }

    getSaboresLike(sabor, callback){
        this._ajax.get(`/sabor/json/like`, sabor, sabores => callback(JSON.parse(sabores)))
    }

    setVisivel(id, visivel, callback){
        this._ajax.put(`/sabor/visivel/${id}`, visivel, callback)
    }

    deleteSabor(id, callback, errorCallback = null){
        this._ajax.delete(`/sabor/json/delete/${id}`, null, callback, (status, error) => {
            if(errorCallback != null){
                console.log(error)
                errorCallback(status, JSON.parse(error))
            }
        });
    }
}