package br.com.pizzaria.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.pizzaria.model.Role;
import br.com.pizzaria.model.Usuario;
import br.com.pizzaria.service.PedidoService;

@Component
public class CheckPedidoUserId {
	
	@Autowired
	private PedidoService pedidoService;
	
	
	public boolean check(Authentication authentication, Integer pedidoId) {
		Usuario usuario;
		try {
			usuario = (Usuario) authentication.getPrincipal();
		} catch (ClassCastException e) {
			return false;
		}
		return pedidoService.pedidoEhDoUsuario(pedidoId, usuario.getId()) || usuario.getRoles().contains(new Role("ROLE_ADMIN"));
	}
	
}
