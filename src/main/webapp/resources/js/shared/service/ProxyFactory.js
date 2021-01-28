class ProxyFactory{

    static build(object, properties, action){
        return new Proxy(object, {
            get(target, prop, receiver){
                if(properties.includes(prop) && ProxyFactory._isFunction(target[prop])){
                    return function(){
                        let returnValue = Reflect.apply(target[prop], target, arguments)
                        action(target)
                        return returnValue;
                    }
                }

                return Reflect.get(target, prop, receiver)
            },

            set(target, prop, value, receiver){
                let returnValue = Reflect.set(target, prop, value, receiver);
                if(properties.includes(prop)){
                    console.log(prop+" interceptado")
                    action(target)
                }
                return returnValue
            }
        });
    }

    static _isFunction(object){
        return typeof(object) == typeof(Function);
    }

}