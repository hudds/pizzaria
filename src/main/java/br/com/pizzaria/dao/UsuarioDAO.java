package br.com.pizzaria.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.model.Usuario;

@Repository
public class UsuarioDAO{

	@Autowired
	private EntityManager em;
	
	public Usuario getUsuario(Integer id, boolean buscarRoles) {
		if(buscarRoles) {
			String jpql = "select u from Usuario u left join fetch u.roles where u.id = :pId";
			TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
			query.setParameter("pId", id);
			return query.getSingleResult();
		}
		return em.find(Usuario.class, id);
	}
	
	
	
	public List<Usuario> getUsuarios(boolean comPedidos){
		String joinFetchPedidos = comPedidos ? " join fetch u.pedidos" : ""; 
		String jpql = String.format("select distinct u from Usuario u%s join fetch u.roles join fetch u.endereco", joinFetchPedidos);
		return em.createQuery(jpql, Usuario.class).getResultList();
	}
	
	public Usuario buscaPeloEmailOuNome(String usernameOrEmail, boolean fetchRoles) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
		Root<Usuario> root = cq.from(Usuario.class);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.equal(root.get("nomeDeUsuario"), usernameOrEmail));
		predicates.add(cb.equal(root.get("email"), usernameOrEmail));
		if(fetchRoles) {
			root.fetch("roles");
		}
		cq.select(root).where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		TypedQuery<Usuario> query = em.createQuery(cq);
		Usuario usuario;
		try {
			usuario = query.getSingleResult();
		}catch(NoResultException e) {
			throw new UsernameNotFoundException(usernameOrEmail);
		}
		
		return usuario;
	}
	
	public UserDetails getUserDetails(String usernameOrEmail) throws UsernameNotFoundException {
		String jpql = "select distinct u from Usuario u join fetch u.roles where u.nomeDeUsuario = :pUsernameOrEmail or u.senha = :pUsernameOrEmail";
		Usuario usuario;
		try {
			usuario = em.createQuery(jpql, Usuario.class).setParameter("pUsernameOrEmail", usernameOrEmail).getSingleResult();
		}catch(NoResultException e) {
			throw new UsernameNotFoundException(usernameOrEmail);
		}
		
		return usuario;
	}

	public void grava(Usuario usuario) {
		em.persist(usuario);
	}

	public void edita(Usuario usuario) {
		em.merge(usuario);
		
	}

	public Integer buscaIdPeloEmailOuNome(String usernameOrEmail) {
		String jpql = "select u.id from Usuario u where u.nomeDeUsuario = :pUsernameOrEmail or u.email = :pUsernameOrEmail";
		return em.createQuery(jpql, Integer.class).setParameter("pUsernameOrEmail", usernameOrEmail).getSingleResult();
	}



	public Integer buscaIdPeloEmail(String email) {
		String jpql = "select u.id from Usuario u where u.email = :pEmail";
		TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);
		query.setParameter("pEmail", email);
		List<Integer> resultList = query.getResultList();
		if(resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

}
