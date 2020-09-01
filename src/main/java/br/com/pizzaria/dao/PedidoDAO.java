package br.com.pizzaria.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.model.Pedido;
import br.com.pizzaria.query.PedidoQuery;

@Repository
public class PedidoDAO {

	@Autowired
	private EntityManager em;
	
	public void grava(Pedido pedido) {
		em.persist(pedido);
	}

	public Pedido buscaComItens(Integer id) {
		/**
		 * hibernate nao permite buscar duas collections na mesma query portanto,
		 * serao necessarias duas queries para buscar todos os itens do pedido
		 */
		
		// buscando pizzas
		String jpql = "select distinct p from Pedido p "
				+ "left join fetch p.pizzas as pPizza "
				+ "left join fetch p.pagamento "
				+ "left join fetch p.cliente "
				+ "left join fetch p.endereco "
				+ "left join fetch pPizza.pizza "
				+ "where p.id = :pId";
		TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
		query.setParameter("pId", id);
		query.setHint(QueryHints.PASS_DISTINCT_THROUGH, false);
		Pedido pedidoComPizza = query.getSingleResult();
		
		// buscando bebidas
		jpql = "select distinct p from Pedido p left join fetch p.bebidas where p.id = :pId";
		Query query2 = em.createQuery(jpql);
		query2.setParameter("pId", id);
		query2.setHint(QueryHints.PASS_DISTINCT_THROUGH, false);
		query2.getResultList();
		
		return pedidoComPizza;
	}
	
	public Pedido buscaSemItens(Integer id) {
		String jpql = "select p from Pedido p"
				+ " left join fetch p.endereco"
				+ " left join fetch p.cliente"
				+ " left join fetch p.endereco"
				+ " where p.id = :pId";
		TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
		query.setParameter("pId", id);
		return query.getSingleResult();
	}
	
	public Pedido buscaPedidoDoUsuario(Integer pedidoId, Integer usuarioId) {
		String jpql = "select distinct p from Pedido p where p.id = :pId and p.cliente.id = :pUId";
		TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
		query.setParameter("pId", pedidoId);
		query.setParameter("pUId", usuarioId);
		return query.getSingleResult();
	}
	
	public Integer buscaIdPedidoDoUsuario(Integer pedidoId, Integer usuarioId) {
		String jpql = "select p.id from Pedido p where p.id = :pId and p.cliente.id = :pUId";
		TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);
		query.setParameter("pId", pedidoId);
		query.setParameter("pUId", usuarioId);
		return query.getSingleResult();
	}

	public List<Pedido> buscaPedidosPeloUsuario(Integer usuarioId) {
		String jpql = "select p from Pedido p where p.cliente.id = :pUId";
		TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
		query.setParameter("pUId", usuarioId);
		return query.getResultList();
	}

	public List<Pedido> buscaPedidos() {
		return em.createQuery("select p from Pedido p", Pedido.class).getResultList();
	}

	public List<Pedido> buscaPedidos(PedidoQuery pedidoQuery) {
		TypedQuery<Pedido> query = em.createQuery(pedidoQuery.createJPQL(), Pedido.class);
		Map<String, Object> parameters = pedidoQuery.getNamedParameters();
		parameters.keySet().forEach(k -> query.setParameter(k, parameters.get(k)));
		return query.getResultList();
	}
	
}
