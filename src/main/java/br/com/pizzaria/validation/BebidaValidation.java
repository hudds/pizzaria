package br.com.pizzaria.validation;

import java.math.BigDecimal;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.pizzaria.model.dto.BebidaFormDTO;

public class BebidaValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return BebidaFormDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "valor", "field.required");
		
		if(errors.hasErrors()) {
			return;
		}
		
		BebidaFormDTO bebida = (BebidaFormDTO) target;
		
		if(bebida.getValor().compareTo(BigDecimal.ZERO) <= 0) {
			errors.rejectValue("valor", "field.invalid");
		}
		
	}

}
