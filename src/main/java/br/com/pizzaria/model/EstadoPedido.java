package br.com.pizzaria.model;

public enum EstadoPedido {
	CONCLUIDO("Concluído"), PREPARANDO("Preparando"), ENVIADO("Enviado"), REGISTRADO("Registrado");

	private final String value;
	
	EstadoPedido(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
