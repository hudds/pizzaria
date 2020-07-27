package br.com.pizzaria.validation;

import java.math.BigDecimal;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.pizzaria.model.form.BebidaForm;

public class BebidaValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return BebidaForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "valor", "field.required");
		
		if(errors.hasErrors()) {
			return;
		}
		
		BebidaForm bebida = (BebidaForm) target;
		
		if(bebida.getValor().compareTo(BigDecimal.ZERO) <= 0) {
			errors.rejectValue("valor", "field.invalid");
		}
		
	}

}
