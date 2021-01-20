package br.com.pizzaria.controller.contracts;

import java.util.Set;

import br.com.pizzaria.model.Role;

public class UsuarioEditRoleDTO {
	
	private Integer id;
	private Set<Role> roles;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
