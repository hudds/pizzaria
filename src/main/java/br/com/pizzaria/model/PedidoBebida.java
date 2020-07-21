package br.com.pizzaria.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_PEDIDOS_DE_BEBIDAS")
public class PedidoBebida implements ItemCarrinho{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="BEBIDA_ID")
	private Bebida bebida;
	
	@Column(name="QUANTIDADE")
	private Integer quantidade;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Bebida getBebida() {
		return bebida;
	}
	
	public void setBebida(Bebida bebida) {
		this.bebida = bebida;
	}
	
	@Override
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	private BigDecimal calculaValor() {
		return this.bebida.getValor().multiply(new BigDecimal(this.quantidade));
	}
	@Override
	public String getTitulo() {
		return this.bebida.getTitulo();
	}
	@Override
	public String getDescricao() {
		return this.bebida.getDescricao();
	}
	@Override
	public BigDecimal getValor() {
		return this.calculaValor();
	}

}
