package br.com.pizzaria.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.pizzaria.model.form.PizzaForm;

public class PizzaValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PizzaForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descricao", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "preco", "field.required");

	}

}
