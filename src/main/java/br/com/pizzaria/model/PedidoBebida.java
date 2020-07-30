package br.com.pizzaria.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_PEDIDOS_DE_BEBIDAS")
public class PedidoBebida extends ItemPedido implements ItemCarrinho{
	
	
	@ManyToOne
	@JoinColumn(name="BEBIDA_ID")
	private Bebida bebida;
	
	public PedidoBebida() {
		
	}
	
	public PedidoBebida(Bebida bebida, Integer quantidade) {
		this.bebida = bebida;
		super.setQuantidade(quantidade);
		super.setDescricao(this.geraDescricao());
	}
	
	public Bebida getBebida() {
		return bebida;
	}
	
	public void setBebida(Bebida bebida) {
		this.bebida = bebida;
	}
	
	@Override
	public BigDecimal calculaValor() {
		return this.bebida.getValor().multiply(new BigDecimal(getQuantidade()));
	}
	public String getTitulo() {
		return this.bebida.getTitulo();
	}
	
	@Override
	public String geraDescricao() {
		return this.bebida.getTitulo();
	}

	@Override
	public Boolean isEmpty() {
		return (getQuantidade() == null ? true : getQuantidade() <= 0) || this.bebida == null;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bebida, getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoBebida other = (PedidoBebida) obj;
		return Objects.equals(bebida, other.bebida) && Objects.equals(super.getId(), other.getId());
	}

	@Override
	public boolean vazio() {
		return (getQuantidade() == null ? true : getQuantidade() <= 0) || this.bebida == null;
	}

}
