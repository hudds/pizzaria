package br.com.pizzaria.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.pizzaria.validation.util.NameValidationUtil;

public class NomeValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return String.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		NameValidationUtil.validate((String) target, errors);
	}

}
