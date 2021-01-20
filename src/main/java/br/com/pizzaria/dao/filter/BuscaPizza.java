package br.com.pizzaria.dao.filter;

import java.math.BigDecimal;

import br.com.pizzaria.dao.util.annotation.FilterParameter;
import br.com.pizzaria.dao.util.annotation.QueryFilter;
import br.com.pizzaria.model.TipoSabor;

@QueryFilter
public class BuscaPizza {

	@FilterParameter
	private Integer id;
	@FilterParameter
	private String titulo;
	@FilterParameter
	private String descricao;
	@FilterParameter
	private BigDecimal preco;
	@FilterParameter
	private TipoSabor tipoSabor;
	@FilterParameter
	private Boolean visivel = true;
	
	
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
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public TipoSabor getTipoSabor() {
		return tipoSabor;
	}
	public void setTipoSabor(TipoSabor tipoSabor) {
		this.tipoSabor = tipoSabor;
	}
	public Boolean getVisivel() {
		return visivel;
	}
	public void setVisivel(Boolean visivel) {
		this.visivel = visivel;
	}
}
