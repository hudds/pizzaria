package br.com.pizzaria.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TB_SABORES")
public class Sabor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name= "TITULO")
	private String titulo;
	
	@Column(name= "DESCRICAO")
	private String descricao;
	
	@Column(name="TIPO")
	@Enumerated(EnumType.STRING)
	private TipoSabor tipo;
	
	public Sabor() {
		
	}
	
	public Sabor(String titulo, String descricao, TipoSabor tipo) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.tipo = tipo;
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

	public String getDescricao() {
		return descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Sabor other = (Sabor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoSabor getTipo() {
		return tipo;
	}

	public void setTipo(TipoSabor tipo) {
		this.tipo = tipo;
	}

}
