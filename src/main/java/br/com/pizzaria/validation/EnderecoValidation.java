package br.com.pizzaria.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.pizzaria.model.form.EnderecoForm;

public class EnderecoValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return EnderecoForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "estado", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cidade", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bairro", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cep", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numero", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "logradouro", "field.required");
		
		EnderecoForm enderecoForm = (EnderecoForm) target;
		if(enderecoForm.getCep().length() != 8) {
			errors.rejectValue("cep", "field.invalid");
		}

	}

}
