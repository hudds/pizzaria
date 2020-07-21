package br.com.pizzaria.model.form;

import org.springframework.security.crypto.bcrypt.BCrypt;

import br.com.pizzaria.model.Usuario;

public class UsuarioForm {
	
	private String nome;
	private String nomeDeUsuario;
	private String email;
	private String senha;
	private String confirmacaoSenha;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome.trim();
	}
	public String getNomeDeUsuario() {
		return nomeDeUsuario;
	}
	public void setNomeDeUsuario(String nomeDeUsuario) {
		this.nomeDeUsuario = nomeDeUsuario.trim();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email.trim();
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}
	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}
	
	public Usuario createUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setNomeDeUsuario(nomeDeUsuario);
		usuario.setEmail(email);
		usuario.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
		return usuario;
	}

}
