package br.com.pizzaria.model.form;

import java.util.List;

import br.com.pizzaria.model.PedidoPizza;
import br.com.pizzaria.service.PizzaService;
import br.com.pizzaria.service.SaborService;

public class PedidoPizzaForm {
	
	private Integer idPizza;
	private List<Integer> idsSabores;
	private Integer quantidade = 1;
	
	public Integer getIdPizza() {
		return idPizza;
	}
	public void setIdPizza(Integer idPizza) {
		this.idPizza = idPizza;
	}
	public List<Integer> getIdsSabores() {
		return idsSabores;
	}
	public void setIdsSabores(List<Integer> idsSabores) {
		this.idsSabores = idsSabores;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public PedidoPizza createPedidoPizza(PizzaService pizzaService, SaborService saborService) {
		PedidoPizza pedidoPizza = new PedidoPizza();
		pedidoPizza.setQuantidade(this.quantidade);
		pedidoPizza.setPizza(pizzaService.buscaPizza(this.idPizza));
		pedidoPizza.setSabores(saborService.buscaSabores(this.idsSabores));
		return pedidoPizza;
	}
	

}
