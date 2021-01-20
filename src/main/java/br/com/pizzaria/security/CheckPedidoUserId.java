package br.com.pizzaria.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.pizzaria.model.Role;
import br.com.pizzaria.model.Usuario;
import br.com.pizzaria.security.util.CurrentUserGetter;
import br.com.pizzaria.service.PedidoService;
import br.com.pizzaria.service.UsuarioService;

@Component
public class CheckPedidoUserId {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	public boolean check(Authentication authentication, Integer pedidoId) {
		Usuario usuario;
		try {
			usuario = CurrentUserGetter.get(authentication, usuarioService, true);
		} catch (UsernameNotFoundException | ClassCastException e) {
			return false;
		}
		return pedidoService.pedidoEhDoUsuario(pedidoId, usuario.getId()) || usuario.getRoles().contains(new Role("ROLE_ADMIN"));
	}
	
}
