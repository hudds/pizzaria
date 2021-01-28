package br.com.pizzaria.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;
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
import br.com.pizzaria.dao.filter.BuscaLikeSabor;
import br.com.pizzaria.dao.filter.BuscaSabor;
import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.TipoSabor;
import br.com.pizzaria.model.dto.SaborFormDTO;
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
	public ModelAndView formNovoSabor(@ModelAttribute("novoSabor") SaborFormDTO novoSabor) {
		ModelAndView modelAndView = new ModelAndView("sabor/formNovoSabor");
		modelAndView.addObject("novoSabor", novoSabor);
		modelAndView.addObject("tipos", TipoSabor.values());
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, path = { "/cadastro" })
	public ModelAndView cadastra(@ModelAttribute("novoSabor") @Valid SaborFormDTO novoSabor, BindingResult result,
			RedirectAttributes attributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:/sabor");
		if (result.hasErrors()) {
			return formNovoSabor(novoSabor);
		}
		saborService.grava(novoSabor.createSabor());
		attributes.addFlashAttribute("statusCadastro", "success");
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView sabores(BuscaLikeSabor busca, Authentication authentication) {
		ModelAndView modelAndView = new ModelAndView("sabor/listaSaboresComJavaScript");
		modelAndView.addObject("tipos", TipoSabor.values());
		modelAndView.addObject("tipoSelecionado", busca.getTipo());
		modelAndView.addObject("sabores", saborService.buscaSabores(busca, authentication));
		modelAndView.addObject("busca", busca);
		return modelAndView;
	}
	

	@RequestMapping(method = RequestMethod.GET, path = {"/json"})
	@ResponseBody
	public List<Sabor> saboresJson(BuscaSabor busca, Authentication authentication) {
		System.out.println("Tipo sabor: " + busca.getTipo());
		List<Sabor> sabores = saborService.buscaSabores(busca, authentication);
		return sabores;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = {"/json/like"})
	@ResponseBody
	public List<Sabor> buscaSaboresLike(BuscaLikeSabor busca, Authentication authentication) {
		System.out.println("Busca apenas visiveis: " + busca.getVisivel());
		System.out.println("Tipo sabor: " + busca.getTipo());
		List<Sabor> sabores = saborService.buscaSabores(busca, authentication);
		return sabores;
	}

	@RequestMapping(path = { "/edit/{pId}" }, method = RequestMethod.GET)
	public ModelAndView formEdit(SaborFormDTO sabor, @PathVariable("pId") Integer pId) {
		ModelAndView modelAndView = new ModelAndView("sabor/formEditarSabor");
		if (sabor.getId() == null) {
			sabor = new SaborFormDTO(saborService.buscaSabor(pId));
		}
		modelAndView.addObject("sabor", sabor);
		modelAndView.addObject("tipos", TipoSabor.values());
		return modelAndView;
	}
	
	@GetMapping("/tipos")
	@ResponseBody
	public TipoSabor[] tipos() {
		return TipoSabor.values();
	}

	@RequestMapping(path = { "/edit" }, method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute("sabor") @Valid SaborFormDTO saborForm, BindingResult result,
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
		try {
			saborService.remove(id);
		} catch (DataIntegrityViolationException e) {
			redirectAttributes.addFlashAttribute("statusDelete", "error");
			redirectAttributes.addFlashAttribute("errorType", "dataIntegrity");
			return modelAndView;
		}
		redirectAttributes.addFlashAttribute("statusDelete", "success");
		return modelAndView;
	}
	
	@RequestMapping(path = { "/json/delete/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<Object> jsonDelete(@PathVariable Integer id) {
		try {
			saborService.remove(id);
		} catch (DataIntegrityViolationException e) {
			ErrorDTO errorDTO = new ErrorDTO();
			errorDTO.setErrorName(ErrorDTO.DATA_INTEGRITY_ERROR_NAME);
			errorDTO.setMessage(ErrorDTO.DATA_INTEGRITY_ERROR_MESSAGE);
			return new ResponseEntity<Object>(errorDTO, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	
	@RequestMapping(path={"/visivel/{id}"}, method=RequestMethod.PUT)
	public ResponseEntity<HttpStatus> setVisivel(@PathVariable(name = "id") Integer id, @RequestBody Boolean visivel) {
		try {
			saborService.setVisivel(id, visivel);
		}catch(EntityNotFoundException e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
