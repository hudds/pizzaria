package br.com.pizzaria.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.stella.type.Estado;
import br.com.pizzaria.model.Carrinho;
import br.com.pizzaria.model.ItemPedido;
import br.com.pizzaria.model.PedidoBebida;
import br.com.pizzaria.model.Pizza;
import br.com.pizzaria.model.TipoSabor;
import br.com.pizzaria.model.Usuario;
import br.com.pizzaria.model.form.EnderecoForm;
import br.com.pizzaria.model.form.PedidoPizzaForm;
import br.com.pizzaria.service.BebidaService;
import br.com.pizzaria.service.EnderecoService;
import br.com.pizzaria.service.PizzaService;
import br.com.pizzaria.service.SaborService;
import br.com.pizzaria.service.UsuarioService;
import br.com.pizzaria.validation.EnderecoValidation;
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
	private Carrinho carrinho;

	@InitBinder(value = { "novoPedidoPizza" })
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new PedidoPizzaValidation(pizzaService, saborService));
	}

	@InitBinder(value = { "enderecoPedido" })
	public void initBinderEndereco(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new EnderecoValidation());
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
		return modelAndView;
	}

	@RequestMapping(path = { "/endereco" }, method = RequestMethod.GET)
	public ModelAndView enderecoForm(Authentication authentication, EnderecoForm endereco) {
		ModelAndView modelAndView = new ModelAndView("pedido/enderecoForm");
		if (endereco.getId() == null) {
			Usuario usuario = (Usuario) usuarioService.loadUserByUsername(authentication.getName());
			endereco = usuario.getEndereco() == null ? endereco : new EnderecoForm(usuario.getEndereco());
		}
		
		modelAndView.addObject("enderecoPedido", endereco);
		modelAndView.addObject("estados", Estado.values());
		return modelAndView;
	}

	@RequestMapping(path = { "/endereco" }, method = RequestMethod.POST)
	public ModelAndView confirmarEndereco(@ModelAttribute("enderecoPedido") @Valid EnderecoForm endereco,
			BindingResult bindingResult, Authentication authentication) {
		ModelAndView modelAndView = new ModelAndView("redirect:/pedido/endereco");
		if (bindingResult.hasErrors()) {
			return enderecoForm(authentication, endereco);
		}
		Usuario usuario = (Usuario) usuarioService.loadUserByUsername(authentication.getName());
		usuarioService.atualizaEndereco(endereco.createEndereco(), usuario);
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
