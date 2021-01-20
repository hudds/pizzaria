class View{
    constructor(domElement){
        this._domElement = domElement
    }

    update(model){
        this._domElement.innerHTML = this._template(model)
    }

    _template(model){
        throw new Error("This method must be implementated")
    }

}