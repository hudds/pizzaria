package br.com.pizzaria.model.form;

import java.math.BigDecimal;

import br.com.pizzaria.model.Bebida;

public class BebidaForm {

	private Integer id;
	private String titulo;

	private BigDecimal valor;
	
	public BebidaForm() {
		
	}
	
	public BebidaForm(Bebida bebida) {
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
	
}
