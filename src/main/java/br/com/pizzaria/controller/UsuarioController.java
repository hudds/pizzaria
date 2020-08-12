package br.com.pizzaria.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.util.UriUtils;

import br.com.caelum.stella.type.Estado;
import br.com.pizzaria.controller.util.EnderecoEContatoFormParams;
import br.com.pizzaria.controller.util.FormIntention;
import br.com.pizzaria.dao.RoleDAO;
import br.com.pizzaria.model.Role;
import br.com.pizzaria.model.Usuario;
import br.com.pizzaria.model.form.EnderecoEContatoForm;
import br.com.pizzaria.model.form.NovaSenhaForm;
import br.com.pizzaria.model.form.UsuarioForm;
import br.com.pizzaria.service.UsuarioService;
import br.com.pizzaria.validation.EmailValidation;
import br.com.pizzaria.validation.EnderecoEContatoValidation;
import br.com.pizzaria.validation.NovaSenhaValidation;
import br.com.pizzaria.validation.UsuarioValidation;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RoleDAO roleDAO;

	@InitBinder(value = { "novoUsuario" })
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UsuarioValidation(usuarioService));
	}

	@InitBinder(value = { "enderecoEContato" })
	public void initBinderEndereco(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new EnderecoEContatoValidation());
	}

	@InitBinder(value = { "novoEmail" })
	public void initBinderEmail(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new EmailValidation(usuarioService));
	}
	
	@InitBinder(value = { "novaSenha" })
	public void initBinderSenha(WebDataBinder webDataBinder, Authentication authentication) {
		webDataBinder.addValidators(new NovaSenhaValidation(usuarioService, authentication));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView usuarios() {
		ModelAndView modelAndView = new ModelAndView("usuario/usuarios");

		modelAndView.addObject("usuarios", usuarioService.getUsuarios(false));
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, path = { "/roles/{id}" })
	public ModelAndView usuarioRoles(@PathVariable(name = "id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("usuario/roles");
		modelAndView.addObject("usuario", usuarioService.getUsuarioComRoles(id));
		modelAndView.addObject("roles", roleDAO.getRoles());

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, path = { "/roles" })
	@Transactional
	public String editaRoles(Usuario usuario) {
		usuarioService.getUsuario(usuario.getId()).setRoles(usuario.getRoles());
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
	public ModelAndView cadastra(@ModelAttribute("novoUsuario") @Valid UsuarioForm novoUsuario, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return cadastroForm(novoUsuario);
		}

		Usuario usuario = novoUsuario.createUsuario();
		usuario.addRole(new Role("ROLE_CLIENTE"));
		usuarioService.grava(usuario);
		attributes.addFlashAttribute("usuario", novoUsuario);
		return new ModelAndView("redirect:/login");
	}

	@RequestMapping(method = RequestMethod.GET, path = { "/menu/{option}" })
	public ModelAndView menu(@PathVariable("option") String option, Authentication authentication) {
		ModelAndView modelAndView = new ModelAndView("usuario/menuDoUsuario");
		Usuario usuario = usuarioService.buscaPeloEmailOuNome(authentication.getName());
		modelAndView.addObject("usuario", usuario);
		return modelAndView;
	}

	@RequestMapping(path = { "/info/endereco-telefone" }, method = RequestMethod.GET)
	public ModelAndView enderecoForm(Authentication authentication, EnderecoEContatoForm enderecoEContato,
			EnderecoEContatoFormParams params) {
		ModelAndView modelAndView = new ModelAndView("usuario/enderecoForm");
		if (!enderecoEContato.isRejected()) {
			Usuario usuario = (Usuario) usuarioService.buscaPeloEmailOuNome(authentication.getName());
			enderecoEContato.setEndereco(usuario.getEndereco());
			enderecoEContato.setTelefone(usuario.getTelefone());
			enderecoEContato.setCelular(usuario.getCelular());
		}

		params.setIntention(params.getIntention() == null ? FormIntention.EDIT : params.getIntention());

		modelAndView.addObject("intention", params.getIntention());
		modelAndView.addObject("enderecoEContato", enderecoEContato);
		modelAndView.addObject("estados", Estado.values());
		modelAndView.addObject("redirectAfter", UriUtils.encode(params.getRedirectAfter(), "UTF-8"));
		return modelAndView;
	}

	@RequestMapping(path = { "/info/endereco-telefone" }, method = RequestMethod.POST)
	public ModelAndView confirmarEndereco(
			@ModelAttribute("enderecoEContato") @Valid EnderecoEContatoForm enderecoEContato,
			BindingResult bindingResult, Authentication authentication, EnderecoEContatoFormParams params) {
		ModelAndView modelAndView = new ModelAndView(
				"redirect:" + (params.getRedirectAfter() == null ? "/usuarios/endereco" : params.getRedirectAfter()));
		if (bindingResult.hasErrors()) {
			enderecoEContato.setRejected(true);
			return enderecoForm(authentication, enderecoEContato, params);
		}
		Usuario usuario = (Usuario) usuarioService.buscaPeloEmailOuNome(authentication.getName());
		usuarioService.atualizaEnderecoEContato(enderecoEContato, usuario);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/info/email")
	private ModelAndView formNovoEmail(String email) {
		ModelAndView modelAndView = new ModelAndView("usuario/formEmail");
		modelAndView.addObject("novoEmail", email);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, path = { "/info/email" })
	public ModelAndView editarEmail(@ModelAttribute("novoEmail") @Valid  String email, BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			return formNovoEmail(email);
		}
		Usuario usuario = usuarioService.buscaPeloEmailOuNome(authentication.getName());
		usuario.setEmail(email);
		usuarioService.edita(usuario);
		return new ModelAndView("redirect:/usuarios/menu/info");
	}

	@RequestMapping(method = RequestMethod.GET, path= {"info/senha"})
	public ModelAndView editarSenhaForm(NovaSenhaForm novaSenha) {
		ModelAndView modelAndView = new ModelAndView("usuario/formNovaSenha");
		modelAndView.addObject("novaSenha", novaSenha);
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST, path= {"info/senha"})
	public ModelAndView editarSenha(@ModelAttribute("novaSenha") @Valid NovaSenhaForm novaSenha, BindingResult bindingResult, Authentication authentication) {
		if(bindingResult.hasErrors()) {
			return editarSenhaForm(novaSenha);
		}
		Usuario usuario = usuarioService.buscaPeloEmailOuNome(authentication.getName());
		usuario.setSenha(novaSenha.getSenhaNovaEncoded());
		usuarioService.edita(usuario);
		return new ModelAndView("redirect:/logout");
	}

}
