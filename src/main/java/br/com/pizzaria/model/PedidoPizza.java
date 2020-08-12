package br.com.pizzaria.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_PEDIDOS_DE_PIZZAS")
public class PedidoPizza extends ItemPedido implements ItemCarrinho{
	
	public static final Integer SABORES_QUANTIDADE_MAXIMA = 4;

	@ManyToMany()
	@JoinTable(name = "TB_PEDIDOS_DE_PIZZAS_SABORES", joinColumns = @JoinColumn(name = "PEDIDO_ID"), inverseJoinColumns = @JoinColumn(name = "SABOR_ID"))
	private List<Sabor> sabores;
	@ManyToOne()
	@JoinColumn(name="PIZZA_ID")
	private Pizza pizza;
	
	public PedidoPizza() {
		
	}
	
	public PedidoPizza(Pizza pizza, List<Sabor> sabores, Integer quantidade) {
		this.pizza = pizza;
		this.sabores = sabores;
		super.setQuantidade(quantidade);
		super.setDescricao(this.geraDescricao());
	}

	public String getTitulo() {
		return getPizza().getTitulo();
	}


	@Override
	public String toString() {
		return getDescricao();
	}

	public BigDecimal calculaValor() {
		return this.pizza.getPreco().multiply(new BigDecimal(super.getQuantidade()));
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

	@Override
	public Boolean isEmpty() {
		return (super.getQuantidade() == null ? true : super.getQuantidade() <= 0) || this.pizza == null ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.getId(), pizza, sabores);
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
		return Objects.equals(super.getId(), other.getId()) && Objects.equals(pizza, other.pizza)
				&& Objects.equals(sabores, other.sabores);
	}

	@Override
	public String geraDescricao() {
		StringBuilder descricao = new StringBuilder(getTitulo() + " ");
		descricao.append(pizza.getTipoSabor().toString() + " ");
		descricao.append(getSabores().size() > 1 ? "sabores: " : "sabor ");
		if (getSabores().size() > 1) {
			for (int i = 0; i < getSabores().size(); i++) {
				descricao.append(String.format("%d/%d ", 1, getSabores().size()));
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

	@Override
	public boolean vazio() {
		return (super.getQuantidade() == null ? true : super.getQuantidade() <= 0) || this.pizza == null ;
	}
	
}
