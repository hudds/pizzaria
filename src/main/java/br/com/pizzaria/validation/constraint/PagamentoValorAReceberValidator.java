package br.com.pizzaria.validation.constraint;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.pizzaria.model.Pagamento;

public class PagamentoValorAReceberValidator implements ConstraintValidator<ValorAReceberValido, Pagamento> {

	@Override
	public boolean isValid(Pagamento pagamento, ConstraintValidatorContext context) {
		return pagamento.getTroco().compareTo(BigDecimal.ZERO) >= 0;
	}
	

}
