package br.com.pizzaria.model.form;

import java.math.BigDecimal;
import java.math.MathContext;

import br.com.pizzaria.model.Pizza;
import br.com.pizzaria.model.TipoSabor;

public class PizzaForm {
	
	private Integer id;
	private String titulo;
	private String descricao;
	private BigDecimal preco;
	private TipoSabor tipoSabor;
	
	public PizzaForm() {
		
	}
	
	public PizzaForm(Pizza pizza) {
		setId(pizza.getId());
		setTitulo(pizza.getTitulo());
		setDescricao(pizza.getDescricao());
		setPreco(pizza.getPreco());
		setTipoSabor(pizza.getTipoSabor());
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

	public Pizza createPizza() {
		Pizza pizza = new Pizza();
		pizza.setId(id);
		pizza.setTitulo(titulo);
		pizza.setDescricao(descricao);
		pizza.setPreco(preco);
		pizza.setTipoSabor(tipoSabor);
		return pizza;
	}

	public TipoSabor getTipoSabor() {
		return tipoSabor;
	}

	public void setTipoSabor(TipoSabor tipoSabor) {
		this.tipoSabor = tipoSabor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
