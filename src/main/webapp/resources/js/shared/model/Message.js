class Message {
    constructor(){
        this._text = ""
        this._level = "info"
    }

    get empty(){
        return this._text.trim() === ""
    }

    get text(){
        return this._text
    }

    set text(txt){
        this._text = txt.trim()
    }

    set level(lvl){
        this._level = lvl
    }

    get level(){
        return this._level
    }
}