package br.com.pizzaria.validation;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.pizzaria.service.UsuarioService;

public class EmailValidation implements Validator{
	
	private UsuarioService usuarioService;

	public EmailValidation(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
		
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return String.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		String email = (String) target;
		if(usuarioService.emailExiste(email)) {
			errors.reject("field.email.existe");
		}
		if(!new EmailValidator().isValid(email, null)) {
			errors.reject("field.email.invalido");
		}
		
	}

}
