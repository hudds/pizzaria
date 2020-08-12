package br.com.pizzaria.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.TipoSabor;
import br.com.pizzaria.model.form.SaborForm;
import br.com.pizzaria.service.SaborService;
import br.com.pizzaria.validation.SaborValidation;

@Controller
@RequestMapping("/sabor")
public class SaboresController {

	@Autowired
	private SaborService saborService;

	@InitBinder(value = { "novoSabor", "sabor" })
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new SaborValidation());
	}

	@RequestMapping(method = RequestMethod.GET, path = { "/cadastro" })
	public ModelAndView formNovoSabor(@ModelAttribute("novoSabor") SaborForm novoSabor) {
		ModelAndView modelAndView = new ModelAndView("sabor/formNovoSabor");
		modelAndView.addObject("novoSabor", novoSabor);
		modelAndView.addObject("tipos", TipoSabor.values());
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, path = { "/cadastro" })
	public ModelAndView cadastra(@ModelAttribute("novoSabor") @Valid SaborForm novoSabor, BindingResult result,
			RedirectAttributes attributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:/sabor");
		if (result.hasErrors()) {
			return formNovoSabor(novoSabor);
		}
		saborService.gravaSabor(novoSabor.createSabor());
		attributes.addFlashAttribute("statusCadastro", "success");
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView sabores(@RequestParam(name = "tipo", required = false) TipoSabor tipoSelecionado,
			@RequestParam(name = "busca", required = false) String busca) {
		ModelAndView modelAndView = new ModelAndView("sabor/listaSaboresComJavaScript");
		modelAndView.addObject("tipos", TipoSabor.values());
		modelAndView.addObject("tipoSelecionado", tipoSelecionado);
		modelAndView.addObject("sabores", saborService.buscaSaboresPorTipoETituloOuDescricao(tipoSelecionado, busca));
		modelAndView.addObject("busca", busca);
		return modelAndView;
	}
	

	@RequestMapping(method = RequestMethod.GET, path = {"/json"})
	@ResponseBody
	public List<Sabor> saboresJson(@RequestParam(name = "tipo", required = false) TipoSabor tipoSelecionado,
			@RequestParam(name = "busca", required = false) String busca) {
		List<Sabor> sabores = saborService.buscaSaboresPorTipoETituloOuDescricao(tipoSelecionado, busca);
		return sabores;
	}

	@RequestMapping(path = { "/edit/{pId}" }, method = RequestMethod.GET)
	public ModelAndView formEdit(SaborForm sabor, @PathVariable("pId") Integer pId) {
		ModelAndView modelAndView = new ModelAndView("sabor/formEditarSabor");
		if (sabor.getId() == null) {
			sabor = new SaborForm(saborService.buscaSabor(pId));
		}
		modelAndView.addObject("sabor", sabor);
		modelAndView.addObject("tipos", TipoSabor.values());
		return modelAndView;
	}

	@RequestMapping(path = { "/edit" }, method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute("sabor") @Valid SaborForm saborForm, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return formEdit(saborForm, saborForm.getId());
		}

		saborService.edita(saborForm.createSabor());
		attributes.addFlashAttribute("statusCadastro", "success");
		return new ModelAndView("redirect:/sabor/edit/" + saborForm.getId());
	}

	@RequestMapping(path = { "/delete/{id}" }, method = RequestMethod.GET)
	public ModelAndView confirmDelete(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("sabor/confirmarDelete");
		modelAndView.addObject("saborParaDeletar", saborService.buscaSabor(id));
		return modelAndView;
	}

	@RequestMapping(path = { "/delete/{id}" }, method = RequestMethod.POST)
	public ModelAndView delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:/sabor");
		saborService.remove(id);
		redirectAttributes.addFlashAttribute("statusDelete", "success");
		return modelAndView;
	}

}
