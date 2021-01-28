class SaborController{
    constructor(contextPath, csrfToken, csrfHeader, isAdmin){
        let $ = document.querySelector.bind(document)

        this._inputSearch = $('#inputSearchSabor')

        this._contextPath = contextPath
        this._ajax = new Ajax(csrfToken, csrfHeader)
        this._saborService = new SaborService(this._ajax);
        console.log("controler received isAdmin " + isAdmin)
        this._saborView = new SaborView($("#sabores"), contextPath, isAdmin)
        this._modalDeleteView = new ModalConfirmDeleteView($("#containerModalConfirmDelete"), isAdmin)

        this._tipos
        this._dropDownTiposSaboresView = new DropDownTiposSaboresView($('#dropdownTiposSabores'))
        this._initDropDownTipos()

        this._message = Binder.bind(new Message(), new MessageView($("#messageContainer")), "text", "level")
        this._searchMessage = Binder.bind(new Message(), new MessageView($("#searchMessageContainer")), "text", "level")

        this._tipoSaborSelected = ""



        this.updateModalDelete()
        this.fetchSabores();
    }


    updateModalDelete(id, titulo){
        this._modalDeleteView.update({title: titulo, action: `saborController.deleteSabor(${id})`})
    }

    deleteSabor(id){
        this._saborService.deleteSabor(id, () => {
            this._message.level = "success"
            this._message.text = "Sabor foi deletado!"
        }, (status, error) => {
            if(error.errorName === "DataIntegrity"){
                this._message.level = "error"
                this._message.text = "Não foi possível deletar este sabor porque já existem pedidos registrados com ele. É possível torná-lo invisível para que não seja possível fazer mais pedidos com ele."
            } else {
                this._message.level = "error"
                this._message.text = "Ocorreu um erro ao deletar o sabor."
            }
        })
    }

    setVisivel(id, visivel){
        console.log("settin visivel" + visivel)
        this._saborService.setVisivel(id, visivel, () =>{
            this.fetchSabores()
        })
    }

    fetchSabores(e){
        if(e != undefined && e != null){
            e.preventDefault()
        }
        let busca = this._inputSearch.value;
        let sabor = {
            titulo:busca,
            descricao:busca,
            tipo: this._tipoSaborSelected
        }
        this._saborService.getSaboresLike(sabor, (sabores) => {
            console.log("Controller recebeu sabores: " + sabores)
            this._saborView.update(sabores)
            this._searchMessage.text = busca.trim() === "" ? "" : `Exibindo sabores para a busca "${busca}".`
        })
    }

    selectTipo(tipo){
        if(tipo === this._tipoSaborSelected){
            return
        }
        this._tipoSaborSelected = tipo
        this._dropDownTiposSaboresView.update({tipos:this._tipos, tipoSelecionado:this._tipoSaborSelected})
        this.fetchSabores()
    }

    _initDropDownTipos(){
        this._saborService.getTipos((tipos) => {
            this._tipos = tipos
            this._dropDownTiposSaboresView.update({tipos:tipos, tipoSelecionado:this._tipoSaborSelected})
        })
    }
}