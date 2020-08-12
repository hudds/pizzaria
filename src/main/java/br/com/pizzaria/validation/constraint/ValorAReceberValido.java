package br.com.pizzaria.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PagamentoValorAReceberValidator.class)
@Documented
public @interface ValorAReceberValido {

	String message() default "br.com.pizzaria.model.Pagamento: valorAReceber must be bigger than valor";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
	
}
