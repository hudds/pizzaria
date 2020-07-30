package br.com.pizzaria.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.pizzaria.model.TipoSabor;
import br.com.pizzaria.model.form.PizzaForm;
import br.com.pizzaria.service.PizzaService;
import br.com.pizzaria.validation.PizzaValidation;

@Controller
@RequestMapping(path = "/pizza")
public class PizzaController {
	
	@Autowired
	private PizzaService pizzaService;
	
	@InitBinder(value = {"novaPizza", "pizzaEdit"})
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new PizzaValidation());
	}
	
	@RequestMapping(method = RequestMethod.GET, path = {"/cadastro"})
	public ModelAndView formPizza(@ModelAttribute("novaPizza") PizzaForm novaPizza) {
		ModelAndView modelAndView = new ModelAndView("pizza/formNovaPizza");
		modelAndView.addObject("novaPizza", novaPizza);
		modelAndView.addObject("tipos", TipoSabor.values());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = {"/cadastro"})
	public ModelAndView cadastra(@ModelAttribute("novaPizza") @Valid PizzaForm novaPizza, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return formPizza(novaPizza);
		}
		pizzaService.cadastra(novaPizza.createPizza());
		ModelAndView modelAndView = new ModelAndView("redirect:/pizza/");
		redirectAttributes.addFlashAttribute("statusCadastroPizza", "success");
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, path={"/delete/{id}"})
	public ModelAndView confirmarDelete(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("pizza/confirmarDelete");
		modelAndView.addObject("pizzaParaDeletar", pizzaService.buscaPizza(id));
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST, path={"/delete/{id}"})
	public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:/pizza/");
		pizzaService.deletaPeloId(id);
		redirectAttributes.addFlashAttribute("pizzaStatusDelete", "success");
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, path={"/edit/{pId}"})
	public ModelAndView formEdit(PizzaForm pizzaForm, @PathVariable("pId") Integer id) {
		ModelAndView modelAndView = new ModelAndView("pizza/formEditarPizza");
		pizzaForm = pizzaForm.getId() == null ? new PizzaForm(pizzaService.buscaPizza(id)) : pizzaForm;
		modelAndView.addObject("pizzaEdit", pizzaForm);
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST, path={"/edit"})
	public ModelAndView edit(@ModelAttribute("pizzaEdit") @Valid PizzaForm pizzaForm, BindingResult result, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			return formEdit(pizzaForm, pizzaForm.getId());
		}
		pizzaService.editaPizza(pizzaForm.createPizza());
		ModelAndView modelAndView = new ModelAndView("redirect:/pizza/edit/"+pizzaForm.getId());
		redirectAttributes.addFlashAttribute("statusEditPizza", "success");
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView lista() {
		ModelAndView modelAndView = new ModelAndView("pizza/listaPizzas");
		modelAndView.addObject("pizzas", pizzaService.buscaPizzasOrdenadasPeloTipoSabor(TipoSabor.SALGADA));
		return modelAndView;
	}

}
