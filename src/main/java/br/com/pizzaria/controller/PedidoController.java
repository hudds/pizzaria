package br.com.pizzaria.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.pizzaria.builder.PedidoBuilder;
import br.com.pizzaria.controller.util.ValidadorDeEstadoParaPedido;
import br.com.pizzaria.model.Carrinho;
import br.com.pizzaria.model.FormaDePagamento;
import br.com.pizzaria.model.ItemPedido;
import br.com.pizzaria.model.Pagamento;
import br.com.pizzaria.model.Pedido;
import br.com.pizzaria.model.PedidoBebida;
import br.com.pizzaria.model.Pizza;
import br.com.pizzaria.model.TipoSabor;
import br.com.pizzaria.model.Usuario;
import br.com.pizzaria.model.form.PedidoPizzaForm;
import br.com.pizzaria.service.BebidaService;
import br.com.pizzaria.service.PedidoService;
import br.com.pizzaria.service.PizzaService;
import br.com.pizzaria.service.SaborService;
import br.com.pizzaria.service.UsuarioService;
import br.com.pizzaria.validation.PagamentoValidation;
import br.com.pizzaria.validation.PedidoPizzaValidation;

@Controller
@RequestMapping(path = { "/pedido" })
public class PedidoController {

	@Autowired
	private PizzaService pizzaService;
	@Autowired
	private SaborService saborService;
	@Autowired
	private BebidaService bebidaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private Carrinho carrinho;

	@InitBinder(value = { "novoPedidoPizza" })
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new PedidoPizzaValidation(pizzaService, saborService));
	}

	@InitBinder(value = { "pagamento" })
	public void initBinderPagamento(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new PagamentoValidation());
	}

	@RequestMapping(path = { "/fazerPedido" }, method = RequestMethod.GET)
	public ModelAndView formsDePedido(@RequestParam(name = "pizza", required = false) Integer idPizza,
			@RequestParam(name = "sabores", required = false) List<Integer> idsSabores,
			PedidoPizzaForm pedidoPizzaForm) {
		ModelAndView modelAndView = decideQualFormRetornar(idPizza, idsSabores);
		modelAndView.addObject("novoPedidoPizza", pedidoPizzaForm);
		return modelAndView;
	}

	@RequestMapping(path = { "/addPizza" }, method = RequestMethod.POST)
	public ModelAndView adicionaPizzaAoCarrinho(
			@ModelAttribute("novoPedidoPizza") @Valid PedidoPizzaForm pedidoPizzaForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("redirect:carrinho");
		if (bindingResult.hasErrors()) {
			return formsDePedido(pedidoPizzaForm.getIdPizza(), pedidoPizzaForm.getIdsSabores(), pedidoPizzaForm);
		}

		this.carrinho.adicionaItem(pedidoPizzaForm.createPedidoPizza(pizzaService, saborService));

		return modelAndView;
	}

	@RequestMapping(path = { "/item/quantidade" }, method = RequestMethod.POST)
	public ModelAndView alteraQuantidadeDoItem(@RequestParam(name = "id") Integer itemId,
			@RequestParam(name = "quantidade") Integer quantidade) {
		ModelAndView modelAndView = new ModelAndView("redirect:/pedido/carrinho");
		carrinho.setQuantidade(itemId, quantidade);
		return modelAndView;
	}

	@RequestMapping(path = { "/item/remove" }, method = RequestMethod.POST)
	public ModelAndView removeItem(@RequestParam(name = "id") Integer itemId) {
		ModelAndView modelAndView = new ModelAndView("redirect:/pedido/carrinho");
		carrinho.removeItem(itemId);
		return modelAndView;
	}

	@RequestMapping(path = { "/addBebida" }, method = RequestMethod.POST)
	public ModelAndView adicionaBebidaAoCarrinho(@RequestParam("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("redirect:carrinho");
		PedidoBebida pedidoBebida = new PedidoBebida(bebidaService.buscaBebida(id), 1);
		carrinho.adicionaItem(pedidoBebida);
		return modelAndView;
	}

	@RequestMapping(path = { "/carrinho" }, method = RequestMethod.GET)
	public ModelAndView itensCarrinho() {
		ModelAndView modelAndView = new ModelAndView("pedido/carrinho");
		Map<Integer, ItemPedido> itens = carrinho.getIdsItens();
		modelAndView.addObject("ids", itens.keySet());
		modelAndView.addObject("itens", itens);
		modelAndView.addObject("valorTotal", carrinho.getValorTotal());
		modelAndView.addObject("bebidas", bebidaService.buscaBebidas());
		modelAndView.addObject("carrinhoContemPizzas", carrinho.contemPizzas());
		modelAndView.addObject("carrinhoVazio", carrinho.isEmpty());
		return modelAndView;
	}

	@RequestMapping(path = { "/pagamento" }, method = RequestMethod.GET)
	public ModelAndView formPagamento(Pagamento pagamento) {
		ModelAndView modelAndView = new ModelAndView("pedido/formPagamento");
		BigDecimal valorTotal = carrinho.getValorTotal();
		pagamento.setValor(valorTotal);
		pagamento.setValorAReceber(valorTotal);
		modelAndView.addObject("pagamento", pagamento);
		modelAndView.addObject("formasDePagamento", FormaDePagamento.values());
		modelAndView.addObject("valorTotal", valorTotal);
		return modelAndView;
	}

	@RequestMapping(path = { "/pagamento" }, method = RequestMethod.POST)
	public ModelAndView setPagamento(@ModelAttribute("pagamento") @Valid Pagamento pagamento,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return formPagamento(pagamento);
		}
		carrinho.atualizaPagamento(pagamento);
		return new ModelAndView("redirect:/pedido/resumo");
	}

	@RequestMapping(path = { "/resumo" })
	public ModelAndView resumoDoPedido(Authentication authentication) {
		
		ModelAndView modelAndView = new ModelAndView();
		Usuario usuario = (Usuario) usuarioService.buscaPeloEmailOuNome(authentication.getName());
		String uriValido = "pedido/resumo";
		String uriParaRedirecionar = ValidadorDeEstadoParaPedido.getURIParaRedirecionar(carrinho, usuario, uriValido);
		
		if(uriParaRedirecionar.equals(uriValido)) {
			modelAndView.addObject("itens", carrinho.getItens());
			modelAndView.addObject("pagamento", carrinho.getPagamento());
			modelAndView.addObject("endereco", usuario.getEndereco());
		}
		
		modelAndView.setViewName(uriParaRedirecionar);
		return modelAndView;
		
	}

	@RequestMapping(method = RequestMethod.POST, path = { "/confirmacao" })
	public ModelAndView registraPedido(Authentication authentication, RedirectAttributes redirectAttributes) {
		
		Usuario usuario = (Usuario) usuarioService.buscaPeloEmailOuNome(authentication.getName());
		String uriSucesso = "redirect:/pedido/detalhes";
		String uriParaRedirecionar = ValidadorDeEstadoParaPedido.getURIParaRedirecionar(carrinho, usuario,
				uriSucesso);
		
		if(uriParaRedirecionar.equals(uriSucesso)) {
			Pedido pedido = PedidoBuilder.build(carrinho, usuario);
			pedidoService.grava(pedido);
			carrinho.limpa();
			uriParaRedirecionar = uriSucesso + "/" + pedido.getId();
			redirectAttributes.addFlashAttribute("status_registro_pedido", "success");
		} else {
			redirectAttributes.addFlashAttribute("status_registro_pedido", "error");
		}
		
		ModelAndView modelAndView = new ModelAndView(uriParaRedirecionar);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, path = { "/detalhes/{id}" })
	public ModelAndView paginaDeConclusao(@PathVariable("id") Integer id, Authentication authentication) {
		ModelAndView modelAndView = new ModelAndView("pedido/detalhes");
		modelAndView.addObject("pedido", pedidoService.buscaComItens(id));
		return modelAndView;
	}

	private ModelAndView decideQualFormRetornar(Integer idPizza, List<Integer> idsSabores) {

		boolean idsSaboresEhNulo = idsSabores == null || idsSabores.isEmpty();
		boolean osDoisParametrosSaoNulos = idPizza == null && idsSaboresEhNulo;

		ModelAndView modelAndView = new ModelAndView();

		if (osDoisParametrosSaoNulos || idPizza == null) {
			formEscolhaPizza(modelAndView);
		} else {
			formEscolhaSabores(idPizza, modelAndView);
		}
		modelAndView.addObject("tipos", TipoSabor.values());
		return modelAndView;
	}

	private void formEscolhaPizza(ModelAndView modelAndView) {
		modelAndView.setViewName("pedido/formEscolhaPizza");
		List<Pizza> pizzas = pizzaService.buscaPizzasOrdenadasPeloTipoSabor(TipoSabor.SALGADA);
		modelAndView.addObject("pizzas", pizzas);
		modelAndView.addObject("method", "GET");
	}

	private void formEscolhaSabores(Integer idPizza, ModelAndView modelAndView) {
		modelAndView.setViewName(("pedido/formEscolhaSabores"));
		Pizza pizzaSelecionada = pizzaService.buscaPizza(idPizza);
		modelAndView.addObject("pizzaSelecionada", pizzaSelecionada);
		modelAndView.addObject("method", "POST");
	}

}
