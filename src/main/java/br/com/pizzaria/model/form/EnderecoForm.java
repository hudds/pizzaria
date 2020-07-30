package br.com.pizzaria.model.form;

import br.com.pizzaria.model.Endereco;

public class EnderecoForm  extends Endereco{
	
	public EnderecoForm() {
		
	}
	
	public EnderecoForm(Endereco endereco) {
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
