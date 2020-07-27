package br.com.pizzaria.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.pizzaria.model.Carrinho;
import br.com.pizzaria.model.Pizza;
import br.com.pizzaria.model.TipoSabor;
import br.com.pizzaria.model.form.PedidoPizzaForm;
import br.com.pizzaria.service.PizzaService;
import br.com.pizzaria.service.SaborService;
import br.com.pizzaria.util.ConversoesParaString;
import br.com.pizzaria.validation.PedidoPizzaValidation;

@Controller
@RequestMapping(path = { "/pedido" })
public class PedidoController {

	@Autowired
	private PizzaService pizzaService;
	@Autowired
	private SaborService saborService;
	@Autowired
	private Carrinho carrinho;
	

	@InitBinder(value = {"novoPedidoPizza"})
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new PedidoPizzaValidation(pizzaService, saborService));
	}
	
	@RequestMapping(path = { "/fazerPedido" }, method = RequestMethod.GET)
	public ModelAndView formsDePedido(@RequestParam(name = "pizza", required = false) Integer idPizza,
			@RequestParam(name = "sabores", required = false) List<Integer> idsSabores, PedidoPizzaForm pedidoPizzaForm) {
		ModelAndView modelAndView = decideQualFormRetornar(idPizza, idsSabores);
		modelAndView.addObject("novoPedidoPizza", pedidoPizzaForm);
		return modelAndView;
	}

	@RequestMapping(path = { "/addPizza" }, method = RequestMethod.POST)
	public ModelAndView adicionaPizzaAoCarrinho(@ModelAttribute("novoPedidoPizza") @Valid PedidoPizzaForm pedidoPizzaForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		if(bindingResult.hasErrors()) {
			return formsDePedido(pedidoPizzaForm.getIdPizza(), pedidoPizzaForm.getIdsSabores(), pedidoPizzaForm);
		}
		
		this.carrinho.adicionaItem(pedidoPizzaForm.createPedidoPizza(pizzaService, saborService));
		
		return null;
	}
	
	private ModelAndView decideQualFormRetornar(Integer idPizza, List<Integer> idsSabores) {

		boolean idsSaboresEhNulo = idsSabores == null || idsSabores.isEmpty();
		boolean osDoisParametrosSaoNulos = idPizza == null && idsSaboresEhNulo;

		ModelAndView modelAndView = new ModelAndView();

		if (osDoisParametrosSaoNulos || idPizza == null) {
			modelAndView.setViewName("pedido/formEscolhaPizza");
			TipoSabor tipoSabor = idsSaboresEhNulo ? null : saborService.buscaTipoSaborPeloId(idsSabores.get(0));
			List<Pizza> pizzas = idsSaboresEhNulo ? pizzaService.buscaPizzas() : pizzaService.buscaPizzasPeloTipoSabor(tipoSabor);
			modelAndView.addObject("pizzas", pizzas);
		} else {
			modelAndView.setViewName(("pedido/formEscolhaSabores"));
			Pizza pizzaSelecionada = pizzaService.buscaPizza(idPizza);
			modelAndView.addObject("pizzaSelecionada", pizzaSelecionada);
			
		}
		modelAndView.addObject("tipos", TipoSabor.values());
		return modelAndView;
	}
}
