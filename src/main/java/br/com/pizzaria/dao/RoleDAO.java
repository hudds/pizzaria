package br.com.pizzaria.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.model.Role;

@Repository
public class RoleDAO {

	@Autowired
	private EntityManager em;
	
	public List<Role> getRoles(){
		return em.createQuery("select r from Role r", Role.class).getResultList();
	}
	
}
