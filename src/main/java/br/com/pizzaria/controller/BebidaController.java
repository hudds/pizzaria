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
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.pizzaria.controller.contracts.ErrorDTO;
import br.com.pizzaria.model.Bebida;
import br.com.pizzaria.model.dto.BebidaFormDTO;
import br.com.pizzaria.service.BebidaService;
import br.com.pizzaria.validation.BebidaValidation;

@Controller
@RequestMapping(path="/bebida")
public class BebidaController {

	@Autowired
	private BebidaService bebidaService;
	
	@InitBinder(value ={"novaBebida", "editBebida"})
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.addValidators(new BebidaValidation());
	}
	
	@RequestMapping(path = {"/cadastro"}, method = RequestMethod.GET)
	public ModelAndView formCadastroBebida(BebidaFormDTO novaBebida) {
		ModelAndView modelAndView = new ModelAndView("bebida/formCadastroBebida");
		modelAndView.addObject("novaBebida", novaBebida);
		return modelAndView;
	}
	
	@RequestMapping(path = {"/cadastro"}, method = RequestMethod.POST)
	public ModelAndView cadastraNovaBebida(@ModelAttribute("novaBebida") @Valid BebidaFormDTO novaBebida, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return formCadastroBebida(novaBebida);
		}
		bebidaService.grava(novaBebida.createBebida());
		ModelAndView modelAndView = new ModelAndView("redirect:lista");
		return modelAndView;
	}
	
	@RequestMapping(path="/lista", method=RequestMethod.GET)
	public ModelAndView listaBebidas() {
		ModelAndView modelAndView = new ModelAndView("bebida/listaBebidas");
		modelAndView.addObject("bebidas", bebidaService.buscaBebidas());
		return modelAndView;
	}
	
	@RequestMapping(path= {"/edit/{bId}"})
	public ModelAndView formEditBebida(@PathVariable("bId") Integer bId, BebidaFormDTO bebida) {
		ModelAndView modelAndView = new ModelAndView("bebida/formEditBebida");
		if(bebida.getId() == null) {
			bebida = new BebidaFormDTO(bebidaService.buscaBebida(bId));
		}
		modelAndView.addObject("editBebida", bebida);
		
		return modelAndView;
	}
	
	@RequestMapping(path= {"/edit"}, method = RequestMethod.POST)
	public ModelAndView editBebida(@ModelAttribute("editBebida") @Valid BebidaFormDTO editBebida, BindingResult bindingResult, RedirectAttributes redirectAttributes ) {
		if(bindingResult.hasErrors()) {
			return formEditBebida(editBebida.getId(), editBebida);
		}
		bebidaService.edita(editBebida.createBebida());
		ModelAndView modelAndView = new ModelAndView("redirect:/bebida/lista");
		redirectAttributes.addFlashAttribute("bebida_edit_status", "success");
		return modelAndView;
	}
	
	@RequestMapping(path={"/delete/{id}"}, method = RequestMethod.GET)
	public ModelAndView formConfirmarDelete(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("bebida/confirmarDelete");
		modelAndView.addObject("bebida", bebidaService.buscaBebida(id));
		return modelAndView;
	}
	
	@RequestMapping(path= {"/delete/{id}"}, method = RequestMethod.POST)
	public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:/bebida/lista");
		bebidaService.deletaPeloId(id);
		redirectAttributes.addFlashAttribute("bebida_delete_status", "success");
		return modelAndView;
	}
	
	@RequestMapping(path = {"/json"}, method = RequestMethod.GET)
	@ResponseBody
	public List<Bebida> getJsonBebidas(Authentication authentication){
		return bebidaService.buscaBebidas();
	}
	
	@RequestMapping(path = {"/json/delete/{id}"}, method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Object> jsonDeleteBebidas(@PathVariable("id") Integer id){
		try {
			bebidaService.deletaPeloId(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(DataIntegrityViolationException e) {
			ErrorDTO errorDTO = new ErrorDTO();
			errorDTO.setErrorName("DataIntegrity");
			errorDTO.setMessage("Não é possível deletar esta entidade porque existe outra dependente dela.");
			return new ResponseEntity<Object>(errorDTO, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/visivel/{id}")
	public ResponseEntity<HttpStatus> setVisivel(@PathVariable("id") Integer id, @RequestBody boolean visivel){
		try {
			bebidaService.setVisivel(id, visivel);
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}
	}
	
}
