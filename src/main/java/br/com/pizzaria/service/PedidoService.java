package br.com.pizzaria.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.pizzaria.dao.PedidoDAO;
import br.com.pizzaria.model.EstadoPedido;
import br.com.pizzaria.model.Pedido;
import br.com.pizzaria.model.dto.BuscaLikePedidoDTO;

@Service
@Transactional
@CacheConfig(cacheNames = {"pedidos"})
public class PedidoService {

	@Autowired
	private PedidoDAO pedidoDAO;
	@Autowired
	private UsuarioService usuarioService;
	
	@CacheEvict(value = "pedidos", allEntries = true)
	public void grava(Pedido pedido) {
		pedidoDAO.grava(pedido);
		usuarioService.addPedido(pedido.getCliente(), pedido);
		
	}
	
	@CacheEvict(value = "pedidos", allEntries = true)
	public void edita(Pedido pedido) {
		if(pedido.getId() == null) {
			throw new NullPointerException("Id: " + pedido.getId());
		}
		if(pedidoDAO.buscaSemItens(pedido.getId()) == null) {
			throw new NoResultException();
		}
		pedidoDAO.edita(pedido);
	}

	@Cacheable
	public Pedido buscaComItens(Integer id) {
		return pedidoDAO.buscaComItens(id);
	}
	
	@Cacheable
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

	@Cacheable
	public List<Pedido> buscaPedidosPeloUsuario(Integer usuarioId) {

		return pedidoDAO.buscaPedidosPeloUsuario(usuarioId);
	}

	@Cacheable
	public List<Pedido> buscaPedidos() {
		return pedidoDAO.buscaPedidos();
	}
	
	@Cacheable
	public List<Pedido> buscaPedidos(BuscaLikePedidoDTO pedidoQuery) {
		return pedidoDAO.buscaPedidos(pedidoQuery);
	}
	
	@Cacheable
	public List<Pedido> buscaPedidosPorEstado(EstadoPedido estado) {
		return pedidoDAO.buscaPedidosPorEstado(estado);
	}
}
