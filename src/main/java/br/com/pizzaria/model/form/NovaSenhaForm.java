package br.com.pizzaria.model.form;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class NovaSenhaForm {
	
	private String senhaAntiga;
	private String senhaNova;
	private String senhaNovaConfirmacao;
	
	public String getSenhaAntiga() {
		return senhaAntiga;
	}
	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}
	public String getSenhaNova() {
		return senhaNova;
	}
	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}
	
	public String getSenhaNovaEncoded() {
		return BCrypt.hashpw(senhaNova, BCrypt.gensalt());
	}
	public String getSenhaNovaConfirmacao() {
		return senhaNovaConfirmacao;
	}
	public void setSenhaNovaConfirmacao(String senhaNovaConfirmacao) {
		this.senhaNovaConfirmacao = senhaNovaConfirmacao;
	}
	
	
	

}
