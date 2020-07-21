package br.com.pizzaria.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TB_PEDIDOS")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="CLIENTE_ID")
	private Usuario cliente;
	
	@OneToMany
	@JoinTable(name = "TB_PEDIDO_PEDIDOS_DE_PIZZAS", 
	joinColumns = @JoinColumn(name = "PEDIDO_ID"),
	inverseJoinColumns = @JoinColumn(name= "PIZZA_ID"))
	private List<PedidoPizza> pizzas;
	
	@OneToMany
	@JoinTable(name = "TB_PEDIDO_PEDIDOS_DE_BEBIDAS", 
	joinColumns = @JoinColumn(name = "PEDIDO_ID"),
	inverseJoinColumns = @JoinColumn(name= "BEBIDA_ID"))
	private List<PedidoBebida> bebidas;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ESTAGIO_DO_PEDIDO")
	private EstagioPedido estagio;
	
	@Column(name="HORARIO_DO_PEDIDO")
	private LocalDateTime horaPedido;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public EstagioPedido getEstagio() {
		return estagio;
	}

	public void setEstagio(EstagioPedido estagio) {
		this.estagio = estagio;
	}

	public LocalDateTime getHoraPedido() {
		return horaPedido;
	}

	public void setHoraPedido(LocalDateTime horaPedido) {
		this.horaPedido = horaPedido;
	}
	
	
	
}
