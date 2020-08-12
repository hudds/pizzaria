package br.com.pizzaria.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.model.PedidoBebida;

@Repository
public class PedidoBebidaDAO {
	
	@Autowired
	private EntityManager entityManager;
	
	
	public void grava(PedidoBebida pedidoBebida) {
		entityManager.persist(pedidoBebida);
	}
	
}
