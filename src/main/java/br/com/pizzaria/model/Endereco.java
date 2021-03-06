package br.com.pizzaria.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import br.com.caelum.stella.type.Estado;

@Entity
@Table(name="TB_ENDERECOS")
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ESTADO")
	private Estado estado;
	
	@Column(name="CIDADE")
	private String cidade;
	
	@Column(name="BAIRRO")
	private String bairro;
	
	@Column(name="CEP")
	private String cep;
	
	@Column(name="NUMERO")
	private String numero;
	
	@Column(name="LOGRADOURO")
	private String logradouro;
	
	@Column(name="COMPLEMENTO")
	private String complemento;

	public Endereco() {
		
	}
	
	public Endereco(Endereco endereco) {
		BeanUtils.copyProperties(endereco, this);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	/**
	 * Remove tudo o que não é dígito e atribui a propriedade cep.
	 * @param cep - Cep a ser atribuído.
	 */
	public void setCep(String cep) {
		this.cep = cep.replaceAll("[^\\d]", "");
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

}
