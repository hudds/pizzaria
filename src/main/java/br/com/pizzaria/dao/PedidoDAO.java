package br.com.pizzaria.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.dao.util.CriteriaQueryGeneratorByFilter;
import br.com.pizzaria.model.EstadoPedido;
import br.com.pizzaria.model.Pedido;
import br.com.pizzaria.model.dto.BuscaLikePedidoDTO;

@Repository
public class PedidoDAO {

	@Autowired
	private EntityManager em;

	public void grava(Pedido pedido) {
		em.persist(pedido);
	}

	public Pedido buscaComItens(Integer id) {
		/**
		 * hibernate nao permite buscar duas collections na mesma query portanto, serao
		 * necessarias duas queries para buscar todos os itens do pedido
		 */

		// buscando pizzas
		String jpql = "select distinct p from Pedido p " + "left join fetch p.pizzas as pPizza "
				+ "left join fetch p.pagamento " + "left join fetch p.cliente " + "left join fetch p.endereco "
				+ "left join fetch pPizza.pizza " + "where p.id = :pId";
		TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
		query.setParameter("pId", id);
		query.setHint(QueryHints.PASS_DISTINCT_THROUGH, false);
		query.setHint("org.hibernate.cacheable", true);
		Pedido pedidoComPizza = query.getSingleResult();

		// buscando bebidas
		jpql = "select distinct p from Pedido p left join fetch p.bebidas where p.id = :pId";
		Query query2 = em.createQuery(jpql);
		query2.setParameter("pId", id);
		query2.setHint(QueryHints.PASS_DISTINCT_THROUGH, false);
		query2.setHint("org.hibernate.cacheable", true);
		query2.getResultList();

		return pedidoComPizza;
	}

	public Pedido buscaSemItens(Integer id) {
		String jpql = "select p from Pedido p" + " left join fetch p.endereco" + " left join fetch p.cliente"
				+ " left join fetch p.endereco" + " where p.id = :pId";
		TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
		query.setParameter("pId", id);
		query.setHint("org.hibernate.cacheable", true);
		return query.getSingleResult();
	}

	public Pedido buscaPedidoDoUsuario(Integer pedidoId, Integer usuarioId) {
		String jpql = "select distinct p from Pedido p where p.id = :pId and p.cliente.id = :pUId";
		TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
		query.setParameter("pId", pedidoId);
		query.setParameter("pUId", usuarioId);
		query.setHint("org.hibernate.cacheable", true);
		return query.getSingleResult();
	}

	public Integer buscaIdPedidoDoUsuario(Integer pedidoId, Integer usuarioId) {
		String jpql = "select p.id from Pedido p where p.id = :pId and p.cliente.id = :pUId";
		TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);
		query.setParameter("pId", pedidoId);
		query.setParameter("pUId", usuarioId);
		query.setHint("org.hibernate.cacheable", true);
		return query.getSingleResult();
	}

	public List<Pedido> buscaPedidosPeloUsuario(Integer usuarioId) {
		String jpql = "select p from Pedido p where p.cliente.id = :pUId";
		TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
		query.setParameter("pUId", usuarioId);
		query.setHint("org.hibernate.cacheable", true);
		return query.getResultList();
	}

	public List<Pedido> buscaPedidos() {
		TypedQuery<Pedido> query = em.createQuery("select p from Pedido p", Pedido.class);
		query.setHint("org.hibernate.cacheable", true);
		return query.getResultList();
	}

	public List<Pedido> buscaPedidosPorEstado(EstadoPedido estado) {
		TypedQuery<Pedido> query = em
				.createQuery("select p from Pedido p join fetch p.pizzas where p.estado = :pEstado ", Pedido.class);
		query.setParameter("pEstado", estado);
		query.setHint("org.hibernate.cacheable", true);
		return query.getResultList();
	}

	public List<Pedido> buscaPedidos(BuscaLikePedidoDTO pedidoQuery) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQueryGeneratorByFilter<Pedido> generator = new CriteriaQueryGeneratorByFilter<>(Pedido.class,
				criteriaBuilder);

		
		Root<Pedido> pedido = generator.getRoot();
		List<Predicate> datePredicates = createDatePredicates(pedidoQuery, criteriaBuilder, pedido);
		CriteriaQuery<Pedido> criteriaQuery = generator.generate(pedidoQuery, datePredicates);
		
		pedido.fetch("pagamento");
		pedido.fetch("cliente");
		pedido.fetch("cliente").fetch("endereco");
		pedido.fetch("endereco");
		System.out.println("fetch pizzas: " + pedidoQuery.getFetchPizzas());
		if (pedidoQuery.getFetchPizzas()) {
			pedido.fetch("pizzas");
		}
		
		TypedQuery<Pedido> query = em.createQuery(criteriaQuery);
		
		if (pedidoQuery.isFetchBebidas()) {
			query.getResultList();
			pedido = generator.getRoot();
			datePredicates = createDatePredicates(pedidoQuery, criteriaBuilder, pedido);
			criteriaQuery = generator.generate(pedidoQuery, datePredicates);
			pedido.fetch("pagamento");
			pedido.fetch("cliente");
			pedido.fetch("cliente").fetch("endereco");
			pedido.fetch("endereco");
			pedido.fetch("bebidas");
			query = em.createQuery(criteriaQuery);

		}
		List<Pedido> resultList = query.getResultList();		
		return resultList;
	}

	private List<Predicate> createDatePredicates(BuscaLikePedidoDTO pedidoQuery, CriteriaBuilder criteriaBuilder, Root<Pedido> pedido) {
		List<Predicate> predicates = new ArrayList<>();
		if (pedidoQuery.getFirstDate() != null && pedidoQuery.getSecondDate() != null) {
			Predicate betweenPredicate = criteriaBuilder.between(pedido.get("horaPedido"), pedidoQuery.getFirstDate(),
					pedidoQuery.getSecondDate());
			predicates.add(betweenPredicate);
		} else if (pedidoQuery.getFirstDate() != null) {
			pedidoQuery.setSecondDate(pedidoQuery.getFirstDate().plusMinutes(1));
			Predicate betweenPredicate = criteriaBuilder.between(pedido.get("horaPedido"), pedidoQuery.getFirstDate(),
					pedidoQuery.getSecondDate());
			predicates.add(betweenPredicate);
		} else if (pedidoQuery.getSecondDate() != null) {
			pedidoQuery.setFirstDate(pedidoQuery.getSecondDate());
			pedidoQuery.setSecondDate(pedidoQuery.getFirstDate().plusMinutes(1));
			Predicate betweenPredicate = criteriaBuilder.between(pedido.get("horaPedido"), pedidoQuery.getFirstDate(),
					pedidoQuery.getSecondDate());
			predicates.add(betweenPredicate);
		}
		return predicates;
	}

	public void edita(Pedido pedido) {
		em.merge(pedido);

	}

}
