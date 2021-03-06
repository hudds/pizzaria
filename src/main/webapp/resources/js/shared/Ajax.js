class Ajax{

    constructor(csrfToken, csrfHeader){
	    this._csrfToken = csrfToken
	    this._csrfHeader = csrfHeader
    }

    put(url, requestBody = null, callback = null, errorCallback = null){
        var xhr = new XMLHttpRequest()
        xhr.open("PUT", url, true)
        xhr.onreadystatechange = () => {
            if(xhr.readyState === XMLHttpRequest.DONE){
                if((xhr.status === 200 || xhr.status === 204) && callback != null){
                    callback(xhr.responseText)
                } else if(errorCallback != null){
                    errorCallback(xhr.status, xhr.responseText)
                }
            }
        }
        xhr.setRequestHeader(this._csrfHeader, this._csrfToken)
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhr.send(requestBody == null ? null : JSON.stringify(requestBody))
    }


    delete(url, requestBody = null, callback = null, errorCallback = null){
        var xhr = new XMLHttpRequest()
        xhr.open("DELETE", url, true)

        xhr.onreadystatechange = () => {
            if(xhr.readyState === XMLHttpRequest.DONE){
                if((xhr.status === 200 || xhr.status === 204) && callback != null){
                    callback(xhr.responseText)
                } else if(errorCallback != null){
                    errorCallback(xhr.status, xhr.responseText)
                }
            }
        }
        xhr.setRequestHeader(this._csrfHeader, this._csrfToken)
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhr.send(requestBody == null ? null : JSON.stringify(requestBody))
    }


    get(url, requestParameters = null, callback = null, errorCallback = null){
        var xhr = new XMLHttpRequest()
        if(requestParameters != null){
            let firstSeparator = url.includes("?") ? "&" : "?" 
            url += firstSeparator + Object.keys(requestParameters).map(p => `${p}=${requestParameters[p]}`).join("&")
        }
        xhr.open("GET", url, true)
        xhr.onreadystatechange = () => {
            if(xhr.readyState === XMLHttpRequest.DONE){
                if((xhr.status === 200 || xhr.status === 204) && callback != null){
                    callback(xhr.responseText)
                } else if(errorCallback != null){
                    errorCallback(xhr.status, xhr.responseText)
                }
            }
        }
        xhr.send(null)
    }
}