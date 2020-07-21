package br.com.pizzaria.controller;

import javax.transaction.Transactional;
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

import br.com.pizzaria.dao.RoleDAO;
import br.com.pizzaria.dao.UsuarioDAO;
import br.com.pizzaria.model.Role;
import br.com.pizzaria.model.Usuario;
import br.com.pizzaria.model.form.UsuarioForm;
import br.com.pizzaria.validation.UsuarioValidation;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired
	private RoleDAO roleDAO;

	@InitBinder("novoUsuario")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UsuarioValidation(usuarioDAO));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView usuarios() {
		ModelAndView modelAndView = new ModelAndView("usuario/usuarios");

		modelAndView.addObject("usuarios", usuarioDAO.getUsuarios(false));
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, path = { "/roles/{id}" })
	public ModelAndView usuarioRoles(@PathVariable(name = "id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("usuario/roles");
		modelAndView.addObject("usuario", usuarioDAO.getUsuario(id));
		modelAndView.addObject("roles", roleDAO.getRoles());

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, path = { "/roles" })
	@Transactional
	public String editaRoles(Usuario usuario) {
		usuarioDAO.getUsuario(usuario.getId()).setRoles(usuario.getRoles());
		return "redirect:roles/" + usuario.getId();
	}

	@RequestMapping(method = RequestMethod.GET, path = { "/cadastro" })
	public ModelAndView cadastroForm(@ModelAttribute("novoUsuario") UsuarioForm novoUsuario) {
		ModelAndView modelAndView = new ModelAndView("usuario/formCadastro");
		modelAndView.addObject("usuario", novoUsuario);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, path = { "/cadastro" })
	@Transactional
	public ModelAndView cadastra(@ModelAttribute("novoUsuario") @Valid UsuarioForm novoUsuario,
			BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return cadastroForm(novoUsuario);
		}
		
		Usuario usuario = novoUsuario.createUsuario();
		usuario.addRole(new Role("ROLE_CLIENTE"));
		usuarioDAO.grava(usuario);
		attributes.addFlashAttribute("usuario", novoUsuario);
		return new ModelAndView("redirect:/login");
	}
	
	

}
