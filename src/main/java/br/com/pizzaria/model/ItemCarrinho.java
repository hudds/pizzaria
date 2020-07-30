package br.com.pizzaria.model;

import java.math.BigDecimal;

public interface ItemCarrinho {
	
	public String getDescricao();
	public BigDecimal getValor();
	public Integer getQuantidade();
	public void setQuantidade(Integer qnt);
	public Boolean isEmpty();
}
