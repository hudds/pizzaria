package br.com.pizzaria.controller.contracts;

public class ErrorDTO {
	
	private String errorName;
	private String message;
	
	public String getErrorName() {
		return errorName;
	}
	public void setErrorName(String errorId) {
		this.errorName = errorId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static final String DATA_INTEGRITY_ERROR_NAME = "DataIntegrity";
	public static final String DATA_INTEGRITY_ERROR_MESSAGE = "Não é possível deletar esta entidade porque existe outra dependente dela.";

}
