package br.com.pizzaria.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.dao.PedidoPizzaDAO;
import br.com.pizzaria.model.PedidoPizza;

@Repository
@Transactional
public class PedidoPizzaService {
	
	@Autowired
	private PedidoPizzaDAO pedidoPizzaDAO;
	
	public void grava(PedidoPizza pedidoPizza) {
		pedidoPizzaDAO.grava(pedidoPizza);
	}

}
