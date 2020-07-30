package br.com.pizzaria.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Carrinho {
	
	private Map<Integer, PedidoPizza> idsPizzas = new HashMap<>();
	private Map<Integer, PedidoBebida> idsBebidas = new HashMap<>();
	private Set<ItemPedido> itens = new HashSet<>();
	public static int id = 1; 
	
	public Integer adicionaItem(PedidoPizza item) {
		if(item.vazio() || itens.contains(item)) {
			return null;
		}
		int id = Carrinho.id++;
		idsPizzas.put(id, item);
		adicionaItemPedido(item);
		return id;
	}
	
	public Integer adicionaItem(PedidoBebida item) {
		if(item.vazio() || itens.contains(item)) {
			return null;
		}
		int id = Carrinho.id++;
		idsBebidas.put(id, item);
		adicionaItemPedido(item);
		return id;
	}

	private void adicionaItemPedido(ItemPedido item) {
		item.setValor(item.calculaValor());
		item.setDescricao(item.geraDescricao());
		itens.add(item);
	}

	public void removeItem(Integer id) {
		ItemPedido item = getItem(id);
		Map<Integer, ?> map = item.getClass().equals(PedidoPizza.class) ? idsPizzas : idsBebidas;
		itens.remove(item);
		map.remove(id);
	}
	
	public void aumentaQuantidade(Integer id, Integer qnt) {
		ItemPedido item = getItem(id);
		if(item.getQuantidade() + qnt <= 0) {
			removeItem(id);
			return;
		}
		item.setQuantidade(item.getQuantidade() + qnt);
	}
	
	public void aumentaQuantidade(Integer id) {
		aumentaQuantidade(id, 1);
	}
	
	public void setQuantidade(Integer id, Integer qnt) {
		if(qnt <= 0) {
			removeItem(id);
			return;
		}
		ItemPedido item = getItem(id);
		item.setQuantidade(qnt);
		item.setValor(item.calculaValor());
		
	}
	
	private ItemPedido getItem(Integer id) {
		if(idsPizzas.containsKey(id)) {
			return idsPizzas.get(id);
		}
		if(idsBebidas.containsKey(id)) {
			return idsBebidas.get(id);
		}
		return null;
	}
	
	public BigDecimal getValorTotal() {
		return this.itens.stream()
				.map(ItemPedido::calculaValor)
				.reduce(BigDecimal.ZERO, (v1, v2) -> v1.add(v2));
	}
	
	public Map<Integer, ItemPedido> getIdsItens(){
		HashMap<Integer, ItemPedido> idsItens = new HashMap<>();
		idsItens.putAll(idsPizzas);
		idsItens.putAll(idsBebidas);
		return idsItens;
	}
	
	public Set<ItemPedido> getItens(){
		return new HashSet<ItemPedido>(this.itens);
	}
	
}
