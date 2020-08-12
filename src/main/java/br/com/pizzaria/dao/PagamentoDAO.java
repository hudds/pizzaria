package br.com.pizzaria.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.model.Pagamento;

@Repository
public class PagamentoDAO {

	@Autowired
	private EntityManager entityManager;
	
	public void grava(Pagamento pagamento) {
		entityManager.persist(pagamento);
	}
	
}
