package br.com.pizzaria.model.dto;

import java.math.BigDecimal;

import br.com.pizzaria.model.Bebida;

public class BebidaFormDTO {

	private Integer id;
	private String titulo;
	private BigDecimal valor;
	
	public BebidaFormDTO() {
		
	}
	
	public BebidaFormDTO(Bebida bebida) {
		this.id = bebida.getId();
		this.titulo = bebida.getTitulo();
		this.valor = bebida.getValor();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public Bebida createBebida() {
		Bebida bebida = new Bebida();
		bebida.setId(id);
		bebida.setTitulo(titulo);
		bebida.setValor(valor);
		return bebida;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		BebidaFormDTO other = (BebidaFormDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
}
