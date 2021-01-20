package br.com.pizzaria.test.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.pizzaria.model.Bebida;
import br.com.pizzaria.model.PedidoPizza;
import br.com.pizzaria.model.Pizza;
import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.TipoSabor;
import br.com.pizzaria.model.dto.BebidaFormDTO;

public class TestUtils {
	
	public static Bebida criaBebida() {
		Bebida bebida = new Bebida();
		bebida.setTitulo("bebida");
		bebida.setValor(new BigDecimal("1.99"));
		return bebida;
	}
	
	public static BebidaFormDTO criaBebidaDTO(Bebida bebida) {
		return new BebidaFormDTO(bebida);
	}

	public static List<PedidoPizza> criaPedidoPizza(Integer quantidade, Integer quantidadeDoPedido, Pizza pizza, List<Sabor> sabores) {
		List<PedidoPizza> pedidos = new ArrayList<PedidoPizza>();
		
		for(int i = 0; i < quantidade; i++) {
			PedidoPizza pedidoPizza = new PedidoPizza();
			pedidoPizza.setSabores(sabores);
			pedidoPizza.setQuantidade(quantidadeDoPedido);
			pedidoPizza.setPizza(pizza);
			pedidoPizza.geraDescricao();
			pedidos.add(pedidoPizza);
		}
		
		return pedidos;
	}
	
	public static List<Pizza> criaPizzas(int quantidade, TipoSabor tipoSabor){
		List<Pizza> pizzas = new ArrayList<>();
		for(int i  = 0; i < quantidade; i++) {
			Pizza pizza = new Pizza();
			pizza.setDescricao("descricao pizza" + i);
			pizza.setPreco(BigDecimal.valueOf(i));
			pizza.setTipoSabor(tipoSabor);
			pizza.setTitulo("Pizza " + tipoSabor + " " + i);
			pizzas.add(pizza);
		}
		return pizzas;
	}
	
	public static List<Sabor> criaSabores(int quantidade, TipoSabor tipoSabor){
		List<Sabor> sabores = new ArrayList<>();
		for(int i  = 0; i < quantidade; i++) {
			Sabor sabor = new Sabor();
			sabor.setDescricao("descricao sabor " + i);
			sabor.setTipo(tipoSabor);
			sabor.setTitulo("sabor " + tipoSabor + " " + i);
			sabores.add(sabor);
		}
		return sabores;
	}
}
