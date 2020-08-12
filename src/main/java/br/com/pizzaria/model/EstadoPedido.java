package br.com.pizzaria.model;

public enum EstadoPedido {
	CONCLUIDO("Concluído"), EM_PREPARO("Em preparo"), SAIU_PARA_ENTREGA("Saiu para a entrega");

	private final String value;
	
	EstadoPedido(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
