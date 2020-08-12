package br.com.pizzaria.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.pizzaria.model.Usuario;
import br.com.pizzaria.service.PedidoService;
import br.com.pizzaria.service.UsuarioService;

@Component
public class CheckPedidoUserId {
	
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PedidoService pedidoService;
	
	
	public boolean check(Authentication authentication, Integer pedidoId) {
		Usuario usuario = (Usuario) usuarioService.buscaPeloEmailOuNome(authentication.getName());
		return pedidoService.pedidoEhDoUsuario(pedidoId, usuario.getId());
	}
	
}
