package br.com.pizzaria.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.pizzaria.dao.UsuarioDAO;
import br.com.pizzaria.model.Endereco;
import br.com.pizzaria.model.Usuario;
import br.com.pizzaria.model.form.EnderecoForm;

@Service
@Transactional
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired
	private EnderecoService enderecoService;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		return usuarioDAO.loadUserByUsername(usernameOrEmail);
	}

	public Usuario getUsuario(Integer id) {
		return usuarioDAO.getUsuario(id);
	}

	public void grava(Usuario usuario) {
		usuarioDAO.grava(usuario);
	}

	public void edita(Usuario usuario) {
		usuarioDAO.edita(usuario);
	}

	public List<Usuario> getUsuarios(boolean comPedidos) {
		return usuarioDAO.getUsuarios(comPedidos);
	}
	
	public void atualizaEndereco(Endereco endereco, Usuario usuario) {
		System.out.println("estou atualizando endereco");
		if(endereco.getId() == null) {
			enderecoService.grava(endereco);
		} 
		usuario.setEndereco(endereco);
		edita(usuario);
	}

}
