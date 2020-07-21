package br.com.pizzaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.model.Pizza;
import br.com.pizzaria.model.TipoSabor;

@Repository
public class PizzaDAO {
	
	@Autowired
	private EntityManager em;

	public void cadastra(Pizza pizza) {
		em.persist(pizza);
	}
	
	public Pizza buscaPizza(Integer id) {
		return em.find(Pizza.class, id);
	}
	
	public List<Pizza> buscaPizzas(){
		return em.createQuery("select p from Pizza p", Pizza.class).getResultList();
	}

	public void deletaPeloId(Integer id) {
		String jpql = "delete from Pizza p where p.id = :pId";
		Query query = em.createQuery(jpql);
		query.setParameter("pId", id);
		query.executeUpdate();
	}

	public Pizza editaPizza(Pizza pizza) {
		return em.merge(pizza);
	}

	public TipoSabor buscaTipoSaborPeloId(Integer idPizza) {
		String jpql = "select p.tipoSabor from Pizza p where p.id = :pId";
		TypedQuery<TipoSabor> query = em.createQuery(jpql, TipoSabor.class);
		query.setParameter("pId", idPizza);
		return query.getSingleResult();
	}

	public List<Pizza> buscaPizzasPeloTipoSabor(TipoSabor tipoSabor) {
		String jpql = "select p from Pizza p where p.tipoSabor = :pTipoSabor";
		TypedQuery<Pizza> query = em.createQuery(jpql, Pizza.class);
		query.setParameter("pTipoSabor", tipoSabor);
		return query.getResultList();
	}
}
