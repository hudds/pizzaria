package br.com.pizzaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.model.Bebida;

@Repository
public class BebidaDAO {
	
	@Autowired
	private EntityManager em;

	public Integer cadastra(Bebida bebida) {
		em.persist(bebida);
		return bebida.getId();
	}

	public List<Bebida> buscaBebidas() {
		String jpql = "select b from Bebida b";
		TypedQuery<Bebida> query = em.createQuery(jpql, Bebida.class);
		return query.getResultList();
	}

	public Bebida buscaBebida(Integer bId) {
		return em.find(Bebida.class, bId);
	}

	public void edita(Bebida bebida) {
		em.merge(bebida);
	}

	public int deletaPeloId(Integer id) {
		String jpql = "delete from Bebida b where b.id = :pId";
		Query query = em.createQuery(jpql);
		query.setParameter("pId", id);
		return query.executeUpdate();
	}

}
