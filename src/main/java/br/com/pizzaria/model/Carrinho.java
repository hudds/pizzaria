package br.com.pizzaria.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Carrinho {
	
	private Map<Integer, ItemCarrinho> itens = new HashMap<Integer, ItemCarrinho>();
	private Integer id = 1;
	
	public ItemCarrinho adicionaItem(ItemCarrinho item) {
		if(item.getQuantidade() <= 0) {
			return null;
		}
		return this.itens.put(id++, item);
	}
	
	public void removeItem(Integer id) {
		this.itens.remove(id);
	}
	
	public void aumentaQuantidade(Integer id, Integer qnt) {
		ItemCarrinho item = this.itens.get(id);
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
		ItemCarrinho item = this.itens.get(id);
		item.setQuantidade(qnt);
		
	}
	
	public BigDecimal getValorTotal() {
		return this.itens.values().stream()
				.map(ItemCarrinho::getValor)
				.reduce(BigDecimal.ZERO, (v1, v2) -> v1.add(v2));
	}
	
	public Map<Integer, ItemCarrinho> getItens(){
		return new HashMap<>(this.itens);
	}
	
}
