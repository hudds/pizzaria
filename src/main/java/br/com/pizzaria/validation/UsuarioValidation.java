package br.com.pizzaria.validation;

import java.util.regex.Pattern;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.pizzaria.model.form.UsuarioForm;
import br.com.pizzaria.service.UsuarioService;
import br.com.pizzaria.validation.util.NameValidationUtil;
import br.com.pizzaria.validation.util.SenhaUtil;

public class UsuarioValidation implements Validator {

	public static final String REGEX_EMAIL_VALIDO = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
	private UsuarioService usuarioService;
	
	public UsuarioValidation(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
		
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UsuarioForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UsuarioForm usuario = (UsuarioForm) target;
		
		NameValidationUtil.validate(usuario.getNome(), errors, "nome");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomeDeUsuario", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "senha", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmacaoSenha", "field.required");
				
		Pattern regexNaoEhLetraOuNumero = Pattern.compile("[^A-z\\d]");
		if (regexNaoEhLetraOuNumero.matcher(usuario.getNomeDeUsuario()).find()) {
			errors.rejectValue("nomeDeUsuario", "field.nomeDeUsuario.invalido");
		}
		
		Pattern regexEmailValido = Pattern.compile(REGEX_EMAIL_VALIDO);
		if(!regexEmailValido.matcher(usuario.getEmail()).find()) {
			errors.rejectValue("email", "field.email.invalido");
		}
		
		if(nomeDeUsuarioOuEmailExiste(usuario.getNomeDeUsuario())) {
			errors.rejectValue("nomeDeUsuario", "field.nomeDeUsuario.existe");
		}
		
		if(nomeDeUsuarioOuEmailExiste(usuario.getEmail())) {
			errors.rejectValue("email", "field.email.existe");
		}
		
		if(!SenhaUtil.senhaForte(usuario.getSenha())) {
			errors.rejectValue("senha", "field.senha.fraca");
		}
		
		if(!usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
			errors.rejectValue("confirmacaoSenha", "field.senha.confirmacao.diferente");
		}

	}
	
	private boolean nomeDeUsuarioOuEmailExiste(String nomeDeUsuarioOuEmail) {
		try {
			usuarioService.buscaPeloEmailOuNome(nomeDeUsuarioOuEmail);
			return true;
		} catch(UsernameNotFoundException e) {
			return false;
		}
		
	}

}
