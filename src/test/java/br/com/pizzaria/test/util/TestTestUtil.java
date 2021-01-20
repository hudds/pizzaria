package br.com.pizzaria.test.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.pizzaria.model.Bebida;
import br.com.pizzaria.model.PedidoPizza;
import br.com.pizzaria.model.Pizza;
import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.TipoSabor;
import br.com.pizzaria.model.dto.BebidaFormDTO;

public class TestTestUtil {
	
	@Test
	public void deveCriarBebida() {
		Bebida bebida = TestUtils.criaBebida();
		assertThat(bebida).isNotNull();
	}
	
	@Test
	public void deveCriarDTODeBebidaComValoresCorretos() {
		Bebida bebida = TestUtils.criaBebida();
		BebidaFormDTO dto = TestUtils.criaBebidaDTO(bebida);
		assertThat(bebida.getId()).isEqualTo(dto.getId());
		assertThat(bebida.getTitulo()).isEqualTo(dto.getTitulo());
		assertThat(bebida.getValor()).isEqualTo(dto.getValor());
	}
	
	@Test
	public void deveCriarQuantidadeDePizzasCorretasEComTipoSaborCorreto(){
		int quantidadeDePizzas = 10;
		List<Pizza> pizzas = TestUtils.criaPizzas(quantidadeDePizzas, TipoSabor.SALGADA);
		assertThat(pizzas.size()).isEqualTo(quantidadeDePizzas);
		assertThat(pizzas).allMatch(p -> p.getTipoSabor().equals(TipoSabor.SALGADA));
		
		quantidadeDePizzas = 20;
		pizzas = TestUtils.criaPizzas(quantidadeDePizzas, TipoSabor.DOCE);
		assertThat(pizzas.size()).isEqualTo(quantidadeDePizzas);
		assertThat(pizzas).allMatch(p -> p.getTipoSabor().equals(TipoSabor.DOCE));
	}
	
	@Test
	public void deveCriarQuantidadeDeSaboresCorretaEComTipoSaborCorreto(){
		int quantidadeDeSabores = 10;
		List<Sabor> sabores = TestUtils.criaSabores(quantidadeDeSabores, TipoSabor.SALGADA);
		assertThat(sabores.size()).isEqualTo(quantidadeDeSabores);
		assertThat(sabores).allMatch(p -> p.getTipo().equals(TipoSabor.SALGADA));
		
		quantidadeDeSabores = 20;
		sabores = TestUtils.criaSabores(quantidadeDeSabores, TipoSabor.DOCE);
		assertThat(sabores.size()).isEqualTo(quantidadeDeSabores);
		assertThat(sabores).allMatch(p -> p.getTipo().equals(TipoSabor.DOCE));
	}
	
	@Test
	public void deveCriarQuantidadeDePedidosDePizzasCorretaEComValoresCorretos() {
		int quantidadeDeSabores = PedidoPizza.SABORES_QUANTIDADE_MAXIMA;
		int quantidadeDoPedido = 1;
		int quantidadeDePizzas = quantidadeDoPedido;
		int quantidadeDePedidosDePizzas = 10;
		List<Sabor> sabores = TestUtils.criaSabores(quantidadeDeSabores, TipoSabor.SALGADA);
		List<Pizza> pizzas = TestUtils.criaPizzas(quantidadeDePizzas, TipoSabor.SALGADA);
		List<PedidoPizza> pedidosPizza = TestUtils.criaPedidoPizza(quantidadeDePedidosDePizzas, quantidadeDoPedido, pizzas.get(0), sabores);
		
		assertThat(pedidosPizza).hasSize(quantidadeDePedidosDePizzas);
		assertThat(pedidosPizza).allMatch(p -> p.getPizza().equals(pizzas.get(0)));
		assertThat(pedidosPizza).allMatch(p -> p.getSabores().equals(sabores));
		assertThat(pedidosPizza).allMatch(p -> p.getQuantidade().equals(quantidadeDoPedido));
		
	}

}
