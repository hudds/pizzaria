package br.com.pizzaria.dao.filter;

import br.com.pizzaria.dao.util.annotation.FilterParameter;
import br.com.pizzaria.dao.util.annotation.QueryFilter;
import br.com.pizzaria.model.TipoSabor;

@QueryFilter
public class BuscaSabor implements SaborFilter {

	@FilterParameter
	private Integer id;

	@FilterParameter
	private String titulo;
	
	@FilterParameter
	private String descricao;
	
	@FilterParameter
	private TipoSabor tipo;
	
	@FilterParameter
	private Boolean visivel = null;
	
	private String orderBy = "id";
	
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

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoSabor getTipo() {
		return tipo;
	}

	public void setTipo(TipoSabor tipo) {
		this.tipo = tipo;
	}

	public Boolean getVisivel() {
		return visivel;
	}

	public void setVisivel(Boolean visivel) {
		this.visivel = visivel;
	}

	@Override
	public String getOrderBy() {
		return this.orderBy;
	}

	@Override
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}
