package br.com.pizzaria.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.dao.filter.Filter;
import br.com.pizzaria.dao.util.CriteriaQueryGeneratorByFilter;
import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.TipoSabor;

@Repository
public class SaborDAO {
	
	@Autowired
	private EntityManager em;
	
	public Sabor buscaSabor(Integer id) {
		return em.find(Sabor.class, id);
	}
	
	public Integer gravaSabor(Sabor sabor) {
		em.persist(sabor);
		return sabor.getId();
	}

	public List<Sabor> buscaSabores() {
		String jpql = "select s from Sabor s";
		TypedQuery<Sabor> query = em.createQuery(jpql, Sabor.class);
		return query.getResultList();
	}
	
	
	
	public List<Sabor> buscaSabores(Filter busca){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQueryGeneratorByFilter<Sabor> criteriaGenerator = new CriteriaQueryGeneratorByFilter<>(Sabor.class, cb);
		CriteriaQuery<Sabor> cq = criteriaGenerator.generate(busca);
		TypedQuery<Sabor> query = em.createQuery(cq);
		return query.getResultList();
		
	}
	
	public void edita(Sabor sabor) {
		em.merge(sabor);
	}
	
	public void remove(Integer id) {
		String jpql = "delete from Sabor s where s.id =:pId";
		Query query = em.createQuery(jpql);
		query.setParameter("pId", id);
		query.executeUpdate();
	}

	public TipoSabor buscaTipoSaborPeloId(Integer id) {
		String jpql = "select s.tipo from Sabor s where s.id = :pId";
		TypedQuery<TipoSabor> query = em.createQuery(jpql, TipoSabor.class);
		query.setParameter("pId", id);
		return query.getSingleResult();
	}

	public List<Sabor> buscaSabores(Collection<Integer> idsSabores) {
		String jpql = "select s from Sabor s where s.id in (:pIds)";
		TypedQuery<Sabor> query = em.createQuery(jpql, Sabor.class);
		query.setParameter("pIds", idsSabores);
		return query.getResultList();
	}

}
