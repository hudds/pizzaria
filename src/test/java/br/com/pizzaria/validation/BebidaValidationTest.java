package br.com.pizzaria.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import br.com.pizzaria.model.form.BebidaForm;

@SpringBootTest
public class BebidaValidationTest {

	
	@Test
	public void validate() {
		BebidaForm bebidaForm = createBebidaForm();
		BebidaValidation bebidaValidation = new BebidaValidation();
		Errors errors = createErrors(bebidaForm, "novaBebida");
		
		bebidaValidation.validate(bebidaForm, errors);
		assertThat(errors.hasErrors()).isFalse();
		
	}
	
	@Test
	public void validateTituloVazioTest() {
		BebidaForm bebidaForm = createBebidaForm();
		BebidaValidation bebidaValidation = new BebidaValidation();
		Errors errors = createErrors(bebidaForm, "novaBebida");
		
		bebidaForm.setTitulo(null);
		bebidaValidation.validate(bebidaForm, errors);
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("titulo")).isNotNull();
		
		bebidaForm = createBebidaForm();
		errors = createErrors(bebidaForm, "novaBebida");

		bebidaForm.setTitulo("");
		bebidaValidation.validate(bebidaForm, errors);
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("titulo")).isNotNull();
		
	}
	
	
	
	@Test
	public void validateValorVazioTest() {
		BebidaForm bebidaForm = createBebidaForm();
		BebidaValidation bebidaValidation = new BebidaValidation();
		Errors errors = createErrors(bebidaForm, "novaBebida");
		
		bebidaForm.setValor(null);
		bebidaValidation.validate(bebidaForm, errors);
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("valor")).isNotNull();
		
	}
	
	@Test
	public void validateValorInvalidoTest() {
		BebidaForm bebidaForm = createBebidaForm();
		BebidaValidation bebidaValidation = new BebidaValidation();
		Errors errors = createErrors(bebidaForm, "novaBebida");
		
		bebidaForm.setValor(BigDecimal.ZERO);
		bebidaValidation.validate(bebidaForm, errors);
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("valor")).isNotNull();
		
		bebidaForm = createBebidaForm();
		errors = createErrors(bebidaForm, "novaBebida");

		bebidaForm.setValor(BigDecimal.valueOf(-1));
		bebidaValidation.validate(bebidaForm, errors);
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("valor")).isNotNull();
		
	}	
	
	public BebidaForm createBebidaForm() {
		BebidaForm bebidaForm = new BebidaForm();
		bebidaForm.setTitulo("Teste");
		bebidaForm.setValor(BigDecimal.TEN);
		return bebidaForm;
	}
	
	public Errors createErrors(Object target, String objectName) {
		return new BeanPropertyBindingResult(target, objectName);
	}
	
}
