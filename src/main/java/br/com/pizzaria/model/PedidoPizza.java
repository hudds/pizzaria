package br.com.pizzaria.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_PEDIDOS_DE_PIZZAS")
public class PedidoPizza implements ItemCarrinho{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@ManyToMany
	@JoinTable(name = "TB_PEDIDOS_DE_PIZZAS_SABORES", joinColumns = @JoinColumn(name = "PEDIDO_ID"), inverseJoinColumns = @JoinColumn(name = "SABOR_ID"))
	private List<Sabor> sabores;
	@ManyToOne
	@JoinColumn(name="PIZZA_ID")
	private Pizza pizza;
	@Column(name="QUANTIDADE")
	private Integer quantidade;

	public String getDescricao() {
		StringBuilder descricao = new StringBuilder("Pizza " + getTitulo() + " sabor ");
		if (getSabores().size() > 1) {
			for (int i = 0; i < getSabores().size(); i++) {
				descricao.append(String.format("%d/%d", 1, getSabores().size()));
				descricao.append(getSabores().get(i));
				if (i == getSabores().size() - 2) {
					descricao.append(" e ");
				} else if (i < getSabores().size() - 1) {
					descricao.append(", ");
				}
			}
		} else {
			descricao.append(getSabores().get(0));
		}

		return descricao.toString();
	}

	public String getTitulo() {
		return "Pizza " + getPizza().getTitulo();
	}


	@Override
	public String toString() {
		return getDescricao();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getQuantidade() {
		return quantidade;
	}

	@Override
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValor() {
		return calculaValor();
	}

	private BigDecimal calculaValor() {
		return this.pizza.getPreco().multiply(new BigDecimal(this.quantidade));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((getPizza() == null) ? 0 : getPizza().hashCode());
		result = prime * result + ((getSabores() == null) ? 0 : getSabores().hashCode());
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
		PedidoPizza other = (PedidoPizza) obj;
		if (getPizza() == null) {
			if (other.getPizza() != null)
				return false;
		} else if (!getPizza().equals(other.getPizza()))
			return false;
		if (getSabores() == null) {
			if (other.getSabores() != null)
				return false;
		} else if (!getSabores().equals(other.getSabores()))
			return false;
		return true;
	}

	public List<Sabor> getSabores() {
		return sabores;
	}

	public void addSabor(Sabor sabor) {
		sabores = sabores == null ? new ArrayList<Sabor>() : sabores;
		sabores.add(sabor);
	}
	
	public void setSabores(List<Sabor> sabores) {
		this.sabores = sabores;
	}

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

}
