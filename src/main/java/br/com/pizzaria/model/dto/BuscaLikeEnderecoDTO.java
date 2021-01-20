package br.com.pizzaria.model.dto;

import br.com.caelum.stella.type.Estado;
import br.com.pizzaria.dao.util.annotation.FilterParameter;
import br.com.pizzaria.dao.util.annotation.Like;
import br.com.pizzaria.dao.util.annotation.QueryFilter;

@QueryFilter
public class BuscaLikeEnderecoDTO {
	
	@FilterParameter
	private Integer id;
	@FilterParameter
	private Estado estado;
	@FilterParameter
	@Like
	private String cidade;
	@FilterParameter
	@Like
	private String bairro;
	@FilterParameter
	@Like
	private String cep;
	@FilterParameter
	@Like
	private String numero;
	@FilterParameter
	@Like
	private String logradouro;
	@FilterParameter
	@Like
	private String complemento;

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
	public void setCep(String cep) {
		this.cep = cep;
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

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuscaLikeEnderecoDTO other = (BuscaLikeEnderecoDTO) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (estado != other.estado)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}
	public Boolean vazio() {
		return 	id == null && estado == null 							 &&
				(cidade == null 		|| cidade.trim().isEmpty()     ) &&
				(bairro == null 		|| bairro.trim().isEmpty()     ) &&
				(cep == null 			|| cep.trim().isEmpty()        ) &&
				(numero == null 		|| numero.trim().isEmpty()     ) &&
				(logradouro == null  	|| logradouro.trim().isEmpty() ) &&
				(complemento == null 	|| complemento.trim().isEmpty());
	}
}
