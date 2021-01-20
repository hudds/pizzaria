package br.com.pizzaria.model.dto;

import br.com.pizzaria.dao.util.annotation.FilterParameter;
import br.com.pizzaria.dao.util.annotation.Like;
import br.com.pizzaria.dao.util.annotation.QueryFilter;

@QueryFilter
public class BuscaLikeUsuarioDTO {

	@FilterParameter
	@Like
	private String email;
	@FilterParameter
	@Like
	private String nomeDeUsuario;
	@FilterParameter
	@Like
	private String nome;
	@FilterParameter
	@Like
	private String telefone;
	@FilterParameter
	@Like
	private String celular;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNomeDeUsuario() {
		return nomeDeUsuario;
	}
	public void setNomeDeUsuario(String nomeDeUsuario) {
		this.nomeDeUsuario = nomeDeUsuario;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public Boolean vazio() {
		System.out.println("BuscaLikeUsuarioDTO.vazio(): " + ((email == null || email.trim().isEmpty()) &&
			   (nomeDeUsuario == null || nomeDeUsuario.trim().isEmpty()) &&
			   (nome == null || nome.trim().isEmpty()) &&
			   (telefone == null || telefone.trim().isEmpty()) &&
			   (celular == null || celular.trim().isEmpty())));
		
		return (email == null || email.trim().isEmpty()) &&
			   (nomeDeUsuario == null || nomeDeUsuario.trim().isEmpty()) &&
			   (nome == null || nome.trim().isEmpty()) &&
			   (telefone == null || telefone.trim().isEmpty()) &&
			   (celular == null || celular.trim().isEmpty());
	}
	
}
