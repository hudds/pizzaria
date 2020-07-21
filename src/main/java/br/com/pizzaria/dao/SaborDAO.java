package br.com.pizzaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.TipoSabor;

@Repository
public class SaborDAO {
	
	@Autowired
	private EntityManager em;
	
	public Sabor buscaSabor(Integer id) {
		return em.find(Sabor.class, id);
	}
	
	public void gravaSabor(Sabor sabor) {
		em.persist(sabor);
	}
	
	public List<Sabor> buscaSaboresPorTipo(TipoSabor tipo){
		String jpql = "select s from Sabor s where s.tipo = :pTipo";
		TypedQuery<Sabor> query = em.createQuery(jpql, Sabor.class);
		query.setParameter("pTipo", tipo);
		return query.getResultList();
	}

	public List<Sabor> buscaSabores() {
		String jpql = "select s from Sabor s";
		return em.createQuery(jpql, Sabor.class).getResultList();
		
	}
	
	public List<Sabor> buscaSaboresPorTituloOuDescricao(String tituloOuDescricao) {
		String jpql = "select s from Sabor s where upper(s.titulo) like upper(:pBusca) or upper(s.descricao) like upper(:pBusca)";
		TypedQuery<Sabor> query = em.createQuery(jpql, Sabor.class);
		query.setParameter("pBusca", "%" + tituloOuDescricao + "%");
		return query.getResultList();
		
	}
	
	public List<Sabor> buscaSaboresPorTipoETituloOuDescricao(TipoSabor tipo, String tituloOuDescricao) {
		String jpql = "select s from Sabor s where s.tipo = :pTipo and (upper(s.titulo) like upper(:pBusca) or upper(s.descricao) like upper(:pBusca))";
		TypedQuery<Sabor> query = em.createQuery(jpql, Sabor.class);
		query.setParameter("pBusca", "%" + tituloOuDescricao + "%");
		query.setParameter("pTipo", tipo);
		return query.getResultList();
		
	}

	public void edita(Sabor sabor) {
		em.merge(sabor);
	}
	
	public void remove(Integer id) {
		String jpql = "delete from Sabor s where s.id =:pId";
		Query query = em.createQuery(jpql);
		query.setParameter("pId", id);
	}

	public TipoSabor buscaTipoSaborPeloId(Integer id) {
		String jpql = "select s.tipo from Sabor s where s.id = :pId";
		TypedQuery<TipoSabor> query = em.createQuery(jpql, TipoSabor.class);
		query.setParameter("pId", id);
		return query.getSingleResult();
	}
}
