class BebidaService {
    constructor(ajax){
        this._ajax = ajax;
    }

    getBebidas(callback) {
        this._ajax.get(`/bebida/json`, null, bebidas => callback(JSON.parse(bebidas)));
    }

    setVisivel(id, visivel, callback){
        this._ajax.put(`/bebida/visivel/${id}`, visivel, callback)
    }

    deleteBebida(id, callback, errorCallback = null){
        this._ajax.delete(`/bebida/json/delete/${id}`, null, callback, (status, error) => {
            if(errorCallback != null){
                console.log(error)
                errorCallback(status, JSON.parse(error))
            }
        });
    }
}