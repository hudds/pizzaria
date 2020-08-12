package br.com.pizzaria.validation;

import java.math.BigDecimal;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.pizzaria.model.Pagamento;

public class PagamentoValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Pagamento.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "valor", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "valorAReceber", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "formaDePagamento", "field.required");
		
		if(errors.hasErrors()) {
			return;
		}
		
		Pagamento pagamento = (Pagamento) target;
		
		if(pagamento.getValor().compareTo(BigDecimal.ZERO) < 0) {
			errors.rejectValue("valor", "field.invalid");
		}
		
		if(pagamento.getValorAReceber().subtract(pagamento.getValor()).compareTo(BigDecimal.ZERO) < 0) {
			errors.rejectValue("valorAReceber", "field.invalid");
		}
		
		
	}

}
