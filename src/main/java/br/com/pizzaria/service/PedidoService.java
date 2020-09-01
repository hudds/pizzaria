package br.com.pizzaria.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.pizzaria.dao.PedidoDAO;
import br.com.pizzaria.model.Pedido;
import br.com.pizzaria.query.PedidoQuery;

@Service
@Transactional
public class PedidoService {

	@Autowired
	private PedidoDAO pedidoDAO;
	@Autowired
	private UsuarioService usuarioService;
	
	
	
	public void pedido(Pedido pedido) {
		pedidoDAO.grava(pedido);
	}

	public void grava(Pedido pedido) {
		pedidoDAO.grava(pedido);
		usuarioService.addPedido(pedido.getCliente(), pedido);
		
	}

	public Pedido buscaComItens(Integer id) {
		return pedidoDAO.buscaComItens(id);
	}
	
	public Pedido buscaSemItens(Integer id) {
		return pedidoDAO.buscaSemItens(id);
	} 
	
	public boolean pedidoEhDoUsuario(Integer pedidoId, Integer usuarioId) {
		try {
			pedidoDAO.buscaIdPedidoDoUsuario(pedidoId, usuarioId);
			return true;
		} catch (EmptyResultDataAccessException | NoResultException e) {
			return false;
		}
		
	}

	public List<Pedido> buscaPedidosPeloUsuario(Integer usuarioId) {

		return pedidoDAO.buscaPedidosPeloUsuario(usuarioId);
	}

	public List<Pedido> buscaPedidos() {
		return pedidoDAO.buscaPedidos();
	}
	
	public List<Pedido> buscaPedidos(PedidoQuery pedidoQuery) {
		return pedidoDAO.buscaPedidos(pedidoQuery);
	}
}
