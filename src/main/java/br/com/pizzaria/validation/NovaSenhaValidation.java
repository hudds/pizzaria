package br.com.pizzaria.validation;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.pizzaria.model.dto.NovaSenhaFormDTO;
import br.com.pizzaria.service.UsuarioService;
import br.com.pizzaria.validation.util.SenhaUtil;

public class NovaSenhaValidation implements Validator{
	
	private UsuarioService usuarioService;
	private Authentication authentication;

	public NovaSenhaValidation(UsuarioService usuarioService, Authentication authentication) {
		this.usuarioService = usuarioService;
		this.authentication = authentication;
		
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return NovaSenhaFormDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "senhaAntiga", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "senhaNova", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "senhaNovaConfirmacao", "field.required");
		
		if(errors.hasErrors()) {
			return;
		}
		
		NovaSenhaFormDTO novaSenha = (NovaSenhaFormDTO) target;
		
		
		UserDetails userDetails = usuarioService.loadUserByUsername(authentication.getName());
		if(!BCrypt.checkpw(novaSenha.getSenhaAntiga(), userDetails.getPassword())) {
			errors.rejectValue("senhaAntiga", "field.senha.incorreta");
		}
		if(!SenhaUtil.senhaForte(novaSenha.getSenhaNova())) {
			errors.rejectValue("senhaNova", "field.senha.fraca");
		}
		if(!novaSenha.getSenhaNova().equals(novaSenha.getSenhaNovaConfirmacao())) {
			errors.rejectValue("senhaNovaConfirmacao", "field.senha.confirmacao.diferente");
		}
		
	}

}
