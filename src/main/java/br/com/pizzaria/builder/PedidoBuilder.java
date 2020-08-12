package br.com.pizzaria.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.pizzaria.model.Carrinho;
import br.com.pizzaria.model.EstadoPedido;
import br.com.pizzaria.model.Pedido;
import br.com.pizzaria.model.PedidoBebida;
import br.com.pizzaria.model.PedidoPizza;
import br.com.pizzaria.model.Usuario;

public class PedidoBuilder {
	
	public static Pedido build(Carrinho carrinho, Usuario usuario) {
		List<PedidoPizza> pizzas = new ArrayList<>(carrinho.getPizzas());
		List<PedidoBebida> bebidas = new ArrayList<>(carrinho.getBebidas());
		Pedido pedido = new Pedido();
		pedido.setBebidas(bebidas);
		pedido.setPizzas(pizzas);
		pedido.setCliente(usuario);
		pedido.setEstado(EstadoPedido.EM_PREPARO);
		pedido.setHoraPedido(LocalDateTime.now());
		pedido.setEndereco(usuario.getEndereco());
		pedido.setPagamento(carrinho.getPagamento());
		
		return pedido;
	}

}
