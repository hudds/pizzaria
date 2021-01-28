class MessageView extends View {

    constructor(domElement){
        super(domElement)
        this._levelCssClasses = {
            "error" : "alert-danger",
            "success" : "alert-success",
            "info" : "alert-info"
        }
    }

    _template(model){
        return model.empty ? '' : `
            <div class="alert ${this._levelCssClasses[model.level]} mt-1 mb-5">
                ${model.text}
            </div>
        `
    }
}