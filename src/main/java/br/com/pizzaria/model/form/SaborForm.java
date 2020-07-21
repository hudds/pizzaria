package br.com.pizzaria.model.form;

import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.TipoSabor;

public class SaborForm {
	
	private Integer id;
	private String titulo;
	private String descricao;
	private TipoSabor tipo;
	
	public SaborForm() {
		
	}
	
	public SaborForm(Integer id, String sabor, String descricao, TipoSabor tipo) {
		this.id = id;
		this.setTitulo(sabor);
		this.descricao = descricao;
		this.tipo = tipo;
	}
	
	public SaborForm(Sabor sabor) {
		this.id = sabor.getId();
		this.setTitulo(sabor.getTitulo());
		this.descricao = sabor.getDescricao();
		this.tipo = sabor.getTipo();
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Sabor createSabor() {
		Sabor sabor = new Sabor();
		sabor.setId(id);
		sabor.setTitulo(titulo);
		sabor.setDescricao(descricao);
		sabor.setTipo(tipo);
		return sabor;
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

	public TipoSabor getTipo() {
		return tipo;
	}

	public void setTipo(TipoSabor tipoSabor) {
		this.tipo = tipoSabor;
	}
	
}
