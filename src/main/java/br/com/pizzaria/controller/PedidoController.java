package br.com.pizzaria.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

@Controller
@RequestMapping(path = { "/pedido" })
public class PedidoController {

	@Autowired
	private PizzaService pizzaService;
	@Autowired
	private SaborService saborService;
	@Autowired
	private Carrinho carrinho;
	

	@RequestMapping(path = { "/fazerPedido" }, method = RequestMethod.GET)
	public ModelAndView formsDePedido(@RequestParam(name = "pizza", required = false) Integer idPizza,
			@RequestParam(name = "sabores", required = false) List<Integer> idsSabores) {
		ModelAndView modelAndView = decideQualFormRetornar(idPizza, idsSabores);
		return modelAndView;
	}

	@RequestMapping(path = { "/addPizza" }, method = RequestMethod.POST)
	public ModelAndView adicionaPizzaAoCarrinho(@ModelAttribute("novoPedidoPizza") @Valid PedidoPizzaForm pedidoPizza) {
		return null;
	}
	
	private ModelAndView decideQualFormRetornar(Integer idPizza, List<Integer> idsSabores) {

		boolean idsSaboresEhNulo = idsSabores == null || idsSabores.isEmpty();
		boolean osDoisParametrosSaoNulos = idPizza == null && idsSaboresEhNulo;

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("method", osDoisParametrosSaoNulos ? "GET" : "POST");

		if (osDoisParametrosSaoNulos || idPizza == null) {
			modelAndView.setViewName("pedido/formEscolhaPizza");
			TipoSabor tipoSabor = idsSaboresEhNulo ? null : saborService.buscaTipoSaborPeloId(idsSabores.get(0));
			List<Pizza> pizzas = idsSaboresEhNulo ? pizzaService.buscaPizzas() : pizzaService.buscaPizzasPeloTipoSabor(tipoSabor);
			modelAndView.addObject("pizzas", pizzas);
			modelAndView.addObject("idsSaboresSelecionados", ConversoesParaString.listaSemColchetes(idsSabores));
		} else {
			modelAndView.setViewName(("pedido/formEscolhaSabores"));
			Pizza pizzaSelecionada = pizzaService.buscaPizza(idPizza);
			modelAndView.addObject("pizzaSelecionada", pizzaSelecionada);
			
		}
		modelAndView.addObject("tipos", TipoSabor.values());
		return modelAndView;
	}
}
