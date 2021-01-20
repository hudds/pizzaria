package br.com.pizzaria.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.pizzaria.model.Usuario;
import br.com.pizzaria.service.UsuarioService;

public class CurrentUserGetter {
	
	public static Usuario get(Authentication authentication, UsuarioService usuarioService, boolean fetchRoles) {

			UserDetails userDetails =  (UserDetails) authentication.getPrincipal();
			Usuario usuario = usuarioService.buscaPeloEmailOuNome(userDetails.getUsername(), fetchRoles);
			return usuario;
	}

}
