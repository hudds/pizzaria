package br.com.pizzaria.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.pizzaria.controller.contracts.ErrorDTO;
import br.com.pizzaria.model.Pizza;
import br.com.pizzaria.model.TipoSabor;
import br.com.pizzaria.model.dto.PizzaFormDTO;
import br.com.pizzaria.service.PizzaService;
import br.com.pizzaria.validation.PizzaValidation;

@Controller
@RequestMapping(path = "/pizza")
public class PizzaController {

	@Autowired
	private PizzaService pizzaService;

	@InitBinder(value = { "novaPizza", "pizzaEdit" })
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new PizzaValidation());
	}

	@RequestMapping(method = RequestMethod.GET, path = { "/cadastro" })
	public ModelAndView formPizza(@ModelAttribute("novaPizza") PizzaFormDTO novaPizza) {
		ModelAndView modelAndView = new ModelAndView("pizza/formNovaPizza");
		modelAndView.addObject("novaPizza", novaPizza);
		modelAndView.addObject("tipos", TipoSabor.values());
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, path = { "/cadastro" })
	public ModelAndView cadastra(@ModelAttribute("novaPizza") @Valid PizzaFormDTO novaPizza, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return formPizza(novaPizza);
		}
		pizzaService.grava(novaPizza.createPizza());
		ModelAndView modelAndView = new ModelAndView("redirect:/pizza");
		redirectAttributes.addFlashAttribute("statusCadastroPizza", "success");
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, path = { "/delete/{id}" })
	public ModelAndView confirmarDelete(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("pizza/confirmarDelete");
		modelAndView.addObject("pizzaParaDeletar", pizzaService.busca(id));
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, path = { "/delete/{id}" })
	public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:/pizza/");
		pizzaService.deletaPeloId(id);
		redirectAttributes.addFlashAttribute("pizzaStatusDelete", "success");
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, path = { "/edit/{pId}" })
	public ModelAndView formEdit(PizzaFormDTO pizzaForm, @PathVariable("pId") Integer id) {
		ModelAndView modelAndView = new ModelAndView("pizza/formEditarPizza");
		pizzaForm = pizzaForm.getId() == null ? new PizzaFormDTO(pizzaService.busca(id)) : pizzaForm;
		modelAndView.addObject("pizzaEdit", pizzaForm);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, path = { "/edit" })
	public ModelAndView edit(@ModelAttribute("pizzaEdit") @Valid PizzaFormDTO pizzaForm, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return formEdit(pizzaForm, pizzaForm.getId());
		}
		pizzaService.edita(pizzaForm.createPizza());
		ModelAndView modelAndView = new ModelAndView("redirect:/pizza/edit/" + pizzaForm.getId());
		redirectAttributes.addFlashAttribute("statusEditPizza", "success");
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView lista() {
		ModelAndView modelAndView = new ModelAndView("pizza/listaPizzas");
		modelAndView.addObject("pizzas", pizzaService.buscaPizzasOrdenadasPeloTipoSabor(TipoSabor.SALGADA));
		return modelAndView;
	}

	@GetMapping(path = "/json")
	@ResponseBody
	public List<Pizza> listaJson(Authentication authentication) {
		return this.pizzaService.buscaPizzas(authentication);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = { "/json/delete/{id}" })
	public ResponseEntity<Object> deleteJSON(@PathVariable("id") Integer id) {
		try {
			pizzaService.deletaPeloId(id);
		} catch (DataIntegrityViolationException e) {
			ErrorDTO errorDTO = new ErrorDTO();
			errorDTO.setErrorName("DataIntegrity");
			errorDTO.setMessage("Não é possível deletar esta entidade porque existe outra dependente dela.");
			return new ResponseEntity<Object>(errorDTO, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(path = { "/visivel/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<HttpStatus> setVisibilidade(@PathVariable(name = "id") Integer id,
			@RequestBody boolean visivel) {
		pizzaService.setVisivel(id, visivel);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
