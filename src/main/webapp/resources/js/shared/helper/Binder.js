class Binder {
    static bind(model, view, ...props){
        let proxy = ProxyFactory.build(model, props, (model) =>{
            view.update(model);
        })
        view.update(model)
        return proxy;
    }
}