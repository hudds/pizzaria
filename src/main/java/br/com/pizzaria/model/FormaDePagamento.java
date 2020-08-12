package br.com.pizzaria.model;

public enum FormaDePagamento {
	CARTAO("Cartão"), DINHEIRO("Dinheiro");
	
	private final String value; 
	
	FormaDePagamento(String string) {
		this.value = string;
	}
	
	public String getValue() {
		return value;
	}
}
