package br.com.pizzaria.model.form;

import org.springframework.beans.BeanUtils;

import br.com.pizzaria.model.Endereco;

public class EnderecoEContatoForm {
	
	private Endereco endereco;
	private String telefone;
	private String celular;
	private boolean rejected = false;
	
	
	public EnderecoEContatoForm() {
		this.endereco = new Endereco();
	}
	
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	/**
	 * Atribui uma nova instância de Endereco à propriedade endereco.
	 * A nova instância terá as mesmas propriedades do Endereco passado como parâmetro, mas o id sempre será null.
	 * @param endereco - Endereco a ser atribuido.
	 */
	public void setEndereco(Endereco endereco) {
		if(endereco != null) {
			Endereco formEndereco = new Endereco();
			BeanUtils.copyProperties(endereco, formEndereco);
			formEndereco.setId(null);
			this.endereco = formEndereco;
		}
	}
	public String getTelefone() {
		return telefone;
	}
	
	/**
	 * Remove tudo o que não é dígito e atribui a propriedade telefone. 
	 * @param telefone -  Telefone a ser atribuído.
	 */
	
	public void setTelefone(String telefone) {
		this.telefone = telefone != null ? telefone.replaceAll("[^\\d]", "") : telefone;
	}
	public String getCelular() {
		return celular;
	}
	
	/**
	 * Remove tudo o que não é dígito e atribui a propriedade celular.
	 * @param celular - Celular a ser atribuído.
	 */
	public void setCelular(String celular) {
		this.celular = celular == null ? celular : celular.replaceAll("[^\\d]", "");
	}


	public boolean isRejected() {
		return rejected;
	}


	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}
	
}
