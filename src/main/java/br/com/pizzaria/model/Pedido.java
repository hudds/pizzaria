package br.com.pizzaria.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TB_PEDIDOS")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="CLIENTE_ID")
	@NotNull
	private Usuario cliente;
	
	@ManyToOne
	@JoinColumn(name="ENDERECO_ID")
	@NotNull
	private Endereco endereco;
	
	@NotEmpty
	@OneToMany(cascade = {CascadeType.PERSIST})
	@JoinTable(name = "TB_PEDIDO_PEDIDOS_DE_PIZZAS", 
	joinColumns = @JoinColumn(name = "PEDIDO_ID"),
	inverseJoinColumns = @JoinColumn(name= "PIZZA_ID"))
	private List<PedidoPizza> pizzas;
	
	@OneToMany(cascade = {CascadeType.PERSIST})
	@JoinTable(name = "TB_PEDIDO_PEDIDOS_DE_BEBIDAS", 
	joinColumns = @JoinColumn(name = "PEDIDO_ID"),
	inverseJoinColumns = @JoinColumn(name= "BEBIDA_ID"))
	private List<PedidoBebida> bebidas;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ESTADO_DO_PEDIDO")
	@NotNull
	private EstadoPedido estado;
	
	@Column(name="HORARIO_DO_PEDIDO")
	@NotNull
	private LocalDateTime horaPedido;
	
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="PAGAMENTO")
	@NotNull
	private Pagamento pagamento;

	public Pedido() {
		this.bebidas = new ArrayList<>();
	}
	
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

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}

	public LocalDateTime getHoraPedido() {
		return horaPedido;
	}

	public void setHoraPedido(LocalDateTime horaPedido) {
		this.horaPedido = horaPedido;
	}

	public List<PedidoBebida> getBebidas() {
		return bebidas;
	}

	public void setBebidas(List<PedidoBebida> bebidas) {
		this.bebidas = bebidas;
	}

	public List<PedidoPizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<PedidoPizza> pizzas) {
		this.pizzas = pizzas;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
		
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	
	public Pagamento getPagamento() {
		return pagamento;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	
	
}
