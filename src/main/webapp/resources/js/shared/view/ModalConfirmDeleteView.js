class ModalConfirmDeleteView extends View{
    constructor(domElement, isAdmin){
        super(domElement)
        this._isAdmin = isAdmin
    }

    update(model = {title: "", action : ""}){
        super.update(model)
    }

    static getHtmlId(){
        return "modalConfirmarDelete"
    }

    _template(model){
        console.log("isAdmin " + this._isAdmin)
        if(this._isAdmin){
            return `
            <div class="modal fade" id="${ModalConfirmDeleteView.getHtmlId()}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <dialog class="modal-content">
                        <header class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Tem certeza que deseja deletar ${model.title}?</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </header>
                        <footer class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Não</button>
                            <button type="button" onclick="${model.action}" data-dismiss="modal" class="btn btn-primary">Sim</button>
                        </footer>
                    </dialog>
                </div>
            </div>
            `
        }
        return 'nao é admim'
    }
}