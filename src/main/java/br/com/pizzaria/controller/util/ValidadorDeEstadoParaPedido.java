package br.com.pizzaria.controller.util;

import br.com.pizzaria.model.Carrinho;
import br.com.pizzaria.model.Usuario;

public final class ValidadorDeEstadoParaPedido {
	
	public static final String ENDERECO_VAZIO = "redirect:/usuarios/info/endereco-telefone?redirectAfter=%2Fpedido%2Fresumo&intention="+FormIntention.CONFIRM;
	public static final String CARRINHO_NAO_CONTEM_PIZZAS = "redirect:/pedido/carrinho";
	public static final String PAGAMENTO_NAO_ATUALIZADO = "redirect:/pedido/pagamento";
	

	public static String getURIParaRedirecionar(Carrinho carrinho, Usuario usuario, String uriPadrao) {
		
		if(!carrinho.contemPizzas()) {
			return CARRINHO_NAO_CONTEM_PIZZAS;
		}
		
		if(!carrinho.pagamentoAtualizado()) {
			return PAGAMENTO_NAO_ATUALIZADO;
		}
		
		if(usuario.getEndereco() == null) {
			return ENDERECO_VAZIO;
		}
		
		return uriPadrao;
	}
	
	

}
