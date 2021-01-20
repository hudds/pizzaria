package br.com.pizzaria.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.web.servlet.MockMvc;

import br.com.pizzaria.model.Bebida;
import br.com.pizzaria.model.Carrinho;
import br.com.pizzaria.model.PedidoPizza;
import br.com.pizzaria.model.Pizza;
import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.TipoSabor;
import br.com.pizzaria.service.BebidaService;
import br.com.pizzaria.service.PizzaService;
import br.com.pizzaria.service.SaborService;
import br.com.pizzaria.test.util.TestUtils;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class PedidoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private SaborService saborService;
	
	@Autowired
	private BebidaService bebidaService;
	
	private Carrinho carrinho = new Carrinho();
	
	
	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void deveRetornarEscolhaDePizza() throws Exception {
		
		List<Pizza> pizzas = TestUtils.criaPizzas(10, TipoSabor.SALGADA);
		pizzas.forEach( p -> p.setId(pizzaService.grava(p)));
		
		mockMvc.perform(get("/pedido/fazerPedido"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/pedido/formEscolhaPizza.jsp"))
		.andExpect(model().attribute("pizzas", pizzas));
		
	}
	
	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void dadoUmaPizzaInexistenteDeveRetornarEscolhaDePizza() throws Exception {
		mockMvc.perform(get("/pedido/fazerPedido?pizza=1"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/pedido/formEscolhaPizza.jsp"));
		
	}
	
	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void deveRetornarEscolhaDeSabores() throws Exception {
		
		List<Pizza> pizzas = TestUtils.criaPizzas(10, TipoSabor.SALGADA);
		pizzas.forEach(p -> p.setId(pizzaService.grava(p)));
		
		Pizza pizzaSelecionada = pizzas.get(0);
		mockMvc.perform(get("/pedido/fazerPedido?pizza=" + pizzaSelecionada.getId()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/pedido/formEscolhaSabores.jsp"))
		.andExpect(model().attribute("pizzaSelecionada", pizzaSelecionada));
		
	}
	
	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void deveGravarPizzaComMultiplosSaboresNoCarrinhoERetornarPaginaDoCarrinho() throws Exception {
		List<Pizza> pizzas = TestUtils.criaPizzas(10, TipoSabor.SALGADA);
		List<Sabor> sabores = TestUtils.criaSabores(PedidoPizza.SABORES_QUANTIDADE_MAXIMA, TipoSabor.SALGADA);
		pizzas.forEach(p -> p.setId(pizzaService.grava(p)));
		sabores.forEach(s -> s.setId(saborService.grava(s)));
		
		List<String> idsSabores = sabores.stream()
				.map(s -> s.getId().toString())
				.collect(Collectors.toList());
		
		carrinho.limpa();
		for(int i = 1; i <= PedidoPizza.SABORES_QUANTIDADE_MAXIMA; i++) {
			String paramIdsSabores = String.join(",", idsSabores.subList(0, i));
			mockMvc.perform(post("/pedido/addPizza")
					.param("idPizza", pizzas.get(0).getId().toString())
					.param("idsSabores", paramIdsSabores)
					.with(csrf())
					.sessionAttr("scopedTarget.carrinho", carrinho))
			.andExpect(redirectedUrl("carrinho"));
			assertThat(carrinho.getPizzas().size()).isEqualTo(i);
		}
		
	}
	
	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void dadoPizzaInexistenteNaoDeveGravarNoCarrinho() throws Exception {
		carrinho.limpa();
		mockMvc.perform(post("/pedido/addPizza")
				.param("idPizza", "0")
				.param("idsSabores", "1,2,3,4")
				.with(csrf())
				.sessionAttr("scopedTarget.carrinho", carrinho))
		.andExpect(model().attributeHasFieldErrors("novoPedidoPizza", "idPizza"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/pedido/formEscolhaPizza.jsp"));
		
		assertThat(carrinho.isEmpty()).isTrue();
		
	}
	
	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void dadoSaborInexistenteNaoDeveGravarNoCarrinho() throws Exception {
		Integer idPizza = pizzaService.grava(TestUtils.criaPizzas(1, TipoSabor.SALGADA).get(0));
		
		carrinho.limpa();
		for(int i = 1; i <= PedidoPizza.SABORES_QUANTIDADE_MAXIMA; i++) {
			
			mockMvc.perform(post("/pedido/addPizza")
					.param("idPizza", idPizza.toString())
					.param("idsSabores", "1,2,3,4")
					.with(csrf())
					.sessionAttr("scopedTarget.carrinho", carrinho))
			.andExpect(model().attributeHasFieldErrors("novoPedidoPizza", "idsSabores"))
			.andExpect(forwardedUrl("/WEB-INF/jsp/pedido/formEscolhaSabores.jsp"));
			
			assertThat(carrinho.isEmpty()).isTrue();
		}
	}
	
	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void dadoSaborSalgadoComPizzaDoceNaoDeveGravarNoCarrinho() throws Exception {
		carrinho.limpa();
		
		List<Pizza> pizzasDoces = TestUtils.criaPizzas(1, TipoSabor.DOCE);
		List<Sabor> saboresSalgados = TestUtils.criaSabores(2, TipoSabor.SALGADA);
		pizzasDoces.forEach(p -> p.setId(pizzaService.grava(p)));
		saboresSalgados.forEach(s -> s.setId(saborService.grava(s)));
		
		Integer idPizza = pizzasDoces.get(0).getId();
		
		mockMvc.perform(post("/pedido/addPizza")
				.param("idPizza", idPizza.toString())
				.param("idsSabores", saboresSalgados.get(0).getId().toString())
				.with(csrf())
				.sessionAttr("scopedTarget.carrinho", carrinho))
		.andExpect(model().attributeHasFieldErrors("novoPedidoPizza", "idsSabores"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/pedido/formEscolhaSabores.jsp"));
		
		assertThat(carrinho.isEmpty()).isTrue();
	}
	
	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void dadoSaborDoceComPizzaSalgadaNaoDeveGravarNoCarrinho() throws Exception {
		carrinho.limpa();
		
		List<Pizza> pizzasDoces = TestUtils.criaPizzas(1, TipoSabor.SALGADA);
		List<Sabor> saboresSalgados = TestUtils.criaSabores(2, TipoSabor.DOCE);
		pizzasDoces.forEach(p -> p.setId(pizzaService.grava(p)));
		saboresSalgados.forEach(s -> s.setId(saborService.grava(s)));
		
		Integer idPizza = pizzasDoces.get(0).getId();
		
		mockMvc.perform(post("/pedido/addPizza")
				.param("idPizza", idPizza.toString())
				.param("idsSabores", saboresSalgados.get(0).getId().toString())
				.with(csrf())
				.sessionAttr("scopedTarget.carrinho", carrinho))
		.andExpect(model().attributeHasFieldErrors("novoPedidoPizza", "idsSabores"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/pedido/formEscolhaSabores.jsp"));
		
		assertThat(carrinho.isEmpty()).isTrue();
	}
	
	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void deveGravarBebidaNoCarrinhoERedirecionar() throws Exception {
		
		Bebida bebida = TestUtils.criaBebida();
		Integer idBebida = bebidaService.grava(bebida);
		
		carrinho.limpa();
		mockMvc.perform(post("/pedido/addBebida")
				.param("id", idBebida.toString())
				.with(csrf())
				.sessionAttr("scopedTarget.carrinho", carrinho))
		.andExpect(redirectedUrl("carrinho"));
		
		assertThat(carrinho.getBebidas().size()).isEqualTo(1);
	}

	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void deveAlterarQuantidadeDoItemNoCarrinhoERedirecionar() throws Exception {
		
		int quantidadeDoPedido = 1;
		Pizza pizza = TestUtils.criaPizzas(1, TipoSabor.SALGADA).get(0);
		List<Sabor> sabores = TestUtils.criaSabores(PedidoPizza.SABORES_QUANTIDADE_MAXIMA, TipoSabor.SALGADA);
		pizza.setId(pizzaService.grava(pizza));
		sabores.forEach(s -> s.setId(saborService.grava(s)));
		
		carrinho.limpa();
		PedidoPizza pedidoPizza = TestUtils.criaPedidoPizza(quantidadeDoPedido, quantidadeDoPedido, pizza, sabores).get(0);

		Integer idItemCarrinho = carrinho.adicionaItem(pedidoPizza);
		mockMvc.perform(post("/pedido/item/quantidade")
				.param("id", idItemCarrinho.toString())
				.param("quantidade", String.valueOf(++quantidadeDoPedido))
				.with(csrf())
				.sessionAttr("scopedTarget.carrinho", carrinho))
		.andExpect(redirectedUrl("/pedido/carrinho"));
		
		assertThat(carrinho.getIdsItens().get(idItemCarrinho).getQuantidade()).isEqualTo(quantidadeDoPedido);
	}
	
	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void deveRemoverItemDoCarrinhoERedirecionar() throws Exception {
		
		int quantidadeDoPedido = 1;
		Pizza pizza = TestUtils.criaPizzas(1, TipoSabor.SALGADA).get(0);
		List<Sabor> sabores = TestUtils.criaSabores(PedidoPizza.SABORES_QUANTIDADE_MAXIMA, TipoSabor.SALGADA);
		pizza.setId(pizzaService.grava(pizza));
		sabores.forEach(s -> s.setId(saborService.grava(s)));
		
		carrinho.limpa();
		PedidoPizza pedidoPizza = TestUtils.criaPedidoPizza(quantidadeDoPedido, quantidadeDoPedido, pizza, sabores).get(0);
		Integer idItemCarrinho = carrinho.adicionaItem(pedidoPizza);
		mockMvc.perform(post("/pedido/item/remove")
				.param("id", idItemCarrinho.toString())
				.with(csrf())
				.sessionAttr("scopedTarget.carrinho", carrinho))
		.andExpect(redirectedUrl("/pedido/carrinho"));
		
		assertThat(carrinho.isEmpty()).isTrue();
	}
	
	
	
	
	
}
