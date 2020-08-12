package br.com.pizzaria.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Carrinho {

	private final Map<Integer, PedidoPizza> idsPizzas = new HashMap<>();
	private final Map<Integer, PedidoBebida> idsBebidas = new HashMap<>();
	private final Set<ItemPedido> itens = new HashSet<>();
	private Pagamento pagamento = new Pagamento();
	private boolean pagamentoAtualizado = false;
	private static int id = 1;

	public Integer adicionaItem(PedidoPizza item) {
		if (item.vazio() || itens.contains(item)) {
			return null;
		}
		int id = Carrinho.id++;
		idsPizzas.put(id, item);
		adicionaItemPedido(item);
		return id;
	}

	public Integer adicionaItem(PedidoBebida item) {
		if (item.vazio() || itens.contains(item)) {
			return null;
		}
		int id = Carrinho.id++;
		idsBebidas.put(id, item);
		adicionaItemPedido(item);
		return id;
	}

	private void adicionaItemPedido(ItemPedido item) {
		item.setValor(item.calculaValor());
		item.setDescricao(item.geraDescricao());
		itens.add(item);
		desatualizaPagamento();
	}

	public void removeItem(Integer id) {
		ItemPedido item = getItem(id);
		Map<Integer, ?> map = item.getClass().equals(PedidoPizza.class) ? idsPizzas : idsBebidas;
		itens.remove(item);
		map.remove(id);
		desatualizaPagamento();
	}

	public void aumentaQuantidade(Integer id, Integer qnt) {
		ItemPedido item = getItem(id);
		if (item.getQuantidade() + qnt <= 0) {
			removeItem(id);
			return;
		}
		item.setQuantidade(item.getQuantidade() + qnt);
		desatualizaPagamento();
	}

	public void aumentaQuantidade(Integer id) {
		aumentaQuantidade(id, 1);
	}

	public void setQuantidade(Integer id, Integer qnt) {
		if (qnt <= 0) {
			removeItem(id);
			return;
		}
		ItemPedido item = getItem(id);
		item.setQuantidade(qnt);
		item.setValor(item.calculaValor());
		desatualizaPagamento();
	}

	private ItemPedido getItem(Integer id) {
		if (idsPizzas.containsKey(id)) {
			return idsPizzas.get(id);
		}
		if (idsBebidas.containsKey(id)) {
			return idsBebidas.get(id);
		}
		return null;
	}

	public BigDecimal getValorTotal() {
		return this.itens.stream().map(ItemPedido::calculaValor).reduce(BigDecimal.ZERO, (v1, v2) -> v1.add(v2));
	}

	public Collection<PedidoPizza> getPizzas(){
		return this.idsPizzas.values();
	}
	
	public Collection<PedidoBebida> getBebidas(){
		return this.idsBebidas.values();
	}
	
	public Map<Integer, ItemPedido> getIdsItens() {
		HashMap<Integer, ItemPedido> idsItens = new HashMap<>();
		idsItens.putAll(idsPizzas);
		idsItens.putAll(idsBebidas);
		return idsItens;
	}

	public Set<ItemPedido> getItens() {
		return new HashSet<ItemPedido>(this.itens);
	}

	public boolean contemPizzas() {
		return !idsPizzas.isEmpty();
	}

	public boolean isEmpty() {
		return itens.isEmpty();
	}

	public void atualizaPagamento(Pagamento pagamento) {
		this.pagamento.setValor(getValorTotal());
		this.pagamento.setFormaDePagamento(pagamento.getFormaDePagamento());
		this.pagamento.setValorAReceber(pagamento.getValorAReceber());
		this.pagamentoAtualizado = true;
	}

	public Pagamento getPagamento() {
		Pagamento pagamento = new Pagamento();
		pagamento.setId(this.pagamento.getId());
		pagamento.setFormaDePagamento(this.pagamento.getFormaDePagamento());
		pagamento.setValor(getValorTotal());
		pagamento.setValorAReceber(this.pagamentoAtualizado ? this.pagamento.getValorAReceber() : getValorTotal());
		return pagamento;
	}

	private void desatualizaPagamento() {
		this.pagamento = new Pagamento();
		this.pagamentoAtualizado = false;
	}

	public boolean pagamentoAtualizado() {
		return pagamentoAtualizado;
	}

	public void limpa() {
		this.idsBebidas.clear();
		this.idsPizzas.clear();
		this.itens.clear();
		desatualizaPagamento();
		Carrinho.id = 1;
	}

}
