package br.com.pizzaria.model.dto;

import br.com.pizzaria.model.Endereco;

public class EnderecoFormDTO  extends Endereco{
	
	public EnderecoFormDTO() {
		
	}
	
	public EnderecoFormDTO(Endereco endereco) {
		setEstado(endereco.getEstado());
		setBairro(endereco.getBairro());
		setCep(endereco.getCep());
		setCidade(endereco.getCidade());
		setComplemento(endereco.getComplemento());
		setId(endereco.getId());
		setNumero(endereco.getNumero());
		setLogradouro(endereco.getLogradouro());
	}
	
	public Endereco createEndereco() {
		Endereco endereco = new Endereco();
		endereco.setEstado(getEstado());
		endereco.setBairro(getBairro());
		endereco.setCep(getCep());
		endereco.setCidade(getCidade());
		endereco.setComplemento(getComplemento());
		endereco.setId(getId());
		endereco.setNumero(getNumero());
		endereco.setLogradouro(getLogradouro());
		return endereco;
	}
	
}
