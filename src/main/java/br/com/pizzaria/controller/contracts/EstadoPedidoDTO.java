package br.com.pizzaria.controller.contracts;

import br.com.pizzaria.model.EstadoPedido;

public class EstadoPedidoDTO {
	
	private EstadoPedido estado;

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}

}
