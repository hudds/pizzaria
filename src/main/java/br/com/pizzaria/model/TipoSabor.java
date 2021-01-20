package br.com.pizzaria.model;

public enum TipoSabor {
	SALGADA("Salgada"), DOCE("Doce");
	
	private String value;
	
	TipoSabor(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
