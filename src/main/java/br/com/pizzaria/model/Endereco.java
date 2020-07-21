package br.com.pizzaria.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private Integer numero;
	
	@Column(name="LOGRADOURO")
	private String logradouro;
	
	@Column(name="COMPLEMENTO")
	private String complemento;

}
