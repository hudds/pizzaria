package br.com.pizzaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.model.Usuario;

@Repository
public class UsuarioDAO implements UserDetailsService{

	@Autowired
	private EntityManager em;
	
	public Usuario getUsuario(Integer id) {
		return em.find(Usuario.class, id);
	}
	
	public List<Usuario> getUsuarios(boolean comPedidos){
		String joinFetchPedidos = comPedidos ? " join fetch u.pedidos" : ""; 
		String jpql = String.format("select distinct u from Usuario u%s", joinFetchPedidos);
		return em.createQuery(jpql, Usuario.class).getResultList();
	}
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		String jpql = "select u from Usuario u where u.nomeDeUsuario = :pUsernameOrEmail or u.email = :pUsernameOrEmail";
		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		query.setParameter("pUsernameOrEmail", usernameOrEmail);
		Usuario usuario;
		try {
			usuario = query.getSingleResult();
		}catch(NoResultException e) {
			throw new UsernameNotFoundException(usernameOrEmail);
		}
		
		return usuario;
	}

	public void grava(Usuario usuario) {
		em.persist(usuario);
	}

}
