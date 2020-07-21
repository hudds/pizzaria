package br.com.pizzaria.validation;

import java.util.regex.Pattern;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.pizzaria.dao.UsuarioDAO;
import br.com.pizzaria.model.form.UsuarioForm;

public class UsuarioValidation implements Validator {

	private static final String REGEX_EMAIL_VALIDO = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
	private UsuarioDAO usuarioDAO;
	
	public UsuarioValidation(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
		
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return UsuarioForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomeDeUsuario", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "senha", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmacaoSenha", "field.required");

		UsuarioForm usuario = (UsuarioForm) target;
		
		if (usuario.getNome().split(" ").length < 2) {
			errors.rejectValue("nome", "field.nome.incompleto");
		}
		
		Pattern regexNaoEhLetra = Pattern.compile("[^A-zÀ-ÿ\\s]|[\\[\\]^`_\\\\]");
		if (regexNaoEhLetra.matcher(usuario.getNome()).find()) {
			errors.rejectValue("nome", "field.nome.invalido");
		}
		
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
		
		if(usuario.getSenha().length() < 8) {
			errors.rejectValue("senha", "field.senha.fraca");
		}
		
		if(!usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
			errors.rejectValue("confirmacaoSenha", "field.senha.confirmacao.diferente");
		}

	}
	
	private boolean nomeDeUsuarioOuEmailExiste(String nomeDeUsuarioOuEmail) {
		try {
			usuarioDAO.loadUserByUsername(nomeDeUsuarioOuEmail);
			return true;
		} catch(UsernameNotFoundException e) {
			return false;
		}
		
	}

}
