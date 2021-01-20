package br.com.pizzaria.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.pizzaria.model.dto.SaborFormDTO;

public class SaborValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return SaborFormDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descricao", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tipo", "field.required");

	}

}
