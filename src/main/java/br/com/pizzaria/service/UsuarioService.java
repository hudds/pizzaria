package br.com.pizzaria.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.pizzaria.dao.UsuarioDAO;
import br.com.pizzaria.model.Endereco;
import br.com.pizzaria.model.Pedido;
import br.com.pizzaria.model.Usuario;
import br.com.pizzaria.model.form.EnderecoEContatoForm;

@Service
@Transactional
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired
	private EnderecoService enderecoService;

	public Usuario buscaPeloEmailOuNome(String usernameOrEmail) throws UsernameNotFoundException {
		return (Usuario) usuarioDAO.buscaPeloEmailOuNome(usernameOrEmail);
	}
	
	public Integer buscaIdPeloEmailOuNome(String usernameOrEmail) {
		return usuarioDAO.buscaIdPeloEmailOuNome(usernameOrEmail);
	}

	public Usuario buscaUsuario(Integer id) {
		return usuarioDAO.getUsuario(id, false);
	}
	
	public Usuario getUsuarioComRoles(Integer id) {
		return usuarioDAO.getUsuario(id, true);
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
		enderecoService.grava(endereco);
		usuario.setEndereco(endereco);
		edita(usuario);
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		return usuarioDAO.getUserDetails(usernameOrEmail);
	}

	public void atualizaEnderecoEContato(EnderecoEContatoForm enderecoEContato, Usuario usuario) {
		enderecoService.grava(enderecoEContato.getEndereco());
		usuario.setEndereco(enderecoEContato.getEndereco());
		usuario.setTelefone(enderecoEContato.getTelefone());
		usuario.setCelular(enderecoEContato.getCelular());
		edita(usuario);
		
	}
	
	public boolean emailExiste(String email) {
		return usuarioDAO.buscaIdPeloEmail(email) != null;
	}

	public void addPedido(Usuario usuario, Pedido pedido) {
		try {
			usuario.addPedido(pedido);
		}catch (LazyInitializationException e) {
			usuario = buscaUsuario(usuario.getId());
			usuario.addPedido(pedido);
		}
		edita(usuario);
		
	}

}
