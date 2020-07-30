package br.com.pizzaria.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.model.Endereco;

@Repository
public class EnderecoDAO {
	
	@Autowired
	private EntityManager em;
	
	public void grava(Endereco endereco) {
		em.persist(endereco);
	}
	
	public void edita(Endereco endereco) {
		em.merge(endereco);
	}
	
}
