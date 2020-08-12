package br.com.pizzaria.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.model.PedidoPizza;

@Repository
public class PedidoPizzaDAO {
	
	@Autowired
	private EntityManager entityManager;
	
	
	public void grava(PedidoPizza pedidoPizza) {
		entityManager.persist(pedidoPizza);
	}
	
}
