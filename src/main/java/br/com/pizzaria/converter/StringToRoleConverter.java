package br.com.pizzaria.converter;

import org.springframework.core.convert.converter.Converter;

import br.com.pizzaria.model.Role;

public class StringToRoleConverter implements Converter<String, Role>{

	@Override
	public Role convert(String source) {
		Role role = new Role();
		role.setAuthority(source);
		return role;
	}

}
