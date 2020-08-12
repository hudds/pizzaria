package br.com.pizzaria.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import br.com.caelum.stella.type.Estado;
import br.com.pizzaria.model.Endereco;
import br.com.pizzaria.model.form.EnderecoEContatoForm;

@SpringBootTest
public class EnderecoEContatoValidationTest {

	@Test
	public void todasInformacoesValidas() {
		EnderecoEContatoForm enderecoEContato = buildEnderecoEContatoForm(1);
		Errors errors = validate(enderecoEContato);
		assertThat(errors.hasErrors()).isFalse();

	}

	@Test
	public void testaEnderecoCamposVazios() {
		Field[] campos = Endereco.class.getDeclaredFields();

		for (Field campo : campos) {
			if (campo.getName().equals("id") || campo.getName().equals("complemento")) {
				continue;
			}

			Endereco endereco = buildEndereco(1);
			setEmptyValue(campo, endereco);

			EnderecoEContatoForm enderecoEContato = buildEnderecoEContatoForm(1);
			enderecoEContato.setEndereco(endereco);

			Errors errors = validate(enderecoEContato);

			assertThat(errors.hasErrors()).isTrue();
			assertThat(errors.getFieldError("endereco." + campo.getName())).isNotNull();

		}
	}

	@Test
	public void testaCepInvalido() {
		int n = 1;
		List<String> cepsInvalidos = Arrays.asList("", "egageegagege", "00", "153246", "583214565", "2aaaa",
				"2a3s3d5f");

		for (String cepInvalido : cepsInvalidos) {

			Endereco endereco = buildEndereco(n);
			endereco.setCep(cepInvalido);

			EnderecoEContatoForm enderecoEContato = buildEnderecoEContatoForm(n);
			enderecoEContato.setEndereco(endereco);

			Errors errors = validate(enderecoEContato);

			assertThat(errors.hasErrors()).isTrue();
			assertThat(errors.getFieldError("endereco.cep")).isNotNull();
		}

	}

	@Test
	public void testaTelefoneInvalido() {
		int n = 1;
		List<String> telefonesInvalidos = Arrays.asList(
				"", "egageegagege", "00", "153246", "583214565", "2aaaa",
				"2a3s3d5f", "123564489", "0024563321", "2198756423", "21923545621", "21823545621", "23546652"
			);

		for (String telefoneInvalido : telefonesInvalidos) {
			EnderecoEContatoForm enderecoEContato = buildEnderecoEContatoForm(n);
			enderecoEContato.setCelular("");
			enderecoEContato.setTelefone(telefoneInvalido);

			Errors errors = validate(enderecoEContato);

			assertThat(errors.hasErrors()).isTrue();
			assertThat(errors.getFieldError("telefone")).isNotNull();
		}

	}
	
	@Test
	public void testaCelularInvalido() {
		int n = 1;
		List<String> celularesInvalidos = Arrays.asList(
				"", "egageegagege", "00", "153246", "583214565", "2aaaa",
				"2a3s3d5f", "123564489", "00924563321", "21823545621", "23599871", "987543325", "875423354"
			);

		for (String celularInvalido : celularesInvalidos) {
			EnderecoEContatoForm enderecoEContato = buildEnderecoEContatoForm(n);
			enderecoEContato.setTelefone("");
			enderecoEContato.setCelular(celularInvalido);

			Errors errors = validate(enderecoEContato);

			assertThat(errors.hasErrors()).isTrue();
			assertThat(errors.getFieldError("celular")).isNotNull();
		}

	}
	
	@Test
	public void testaTelefoneECelularVazios() {
		EnderecoEContatoForm enderecoEContato = buildEnderecoEContatoForm(1);
		
		// somente telefone null
		enderecoEContato.setTelefone(null);
		assertThat(validate(enderecoEContato).hasErrors()).isFalse();
		
		// somente telefone vazio
		enderecoEContato.setTelefone("");
		assertThat(validate(enderecoEContato).hasErrors()).isFalse();
		
		// somente celular null
		enderecoEContato = buildEnderecoEContatoForm(1);
		enderecoEContato.setCelular(null);
		assertThat(validate(enderecoEContato).hasErrors()).isFalse();
		
		// somente celular vazio
		enderecoEContato.setCelular("");
		assertThat(validate(enderecoEContato).hasErrors()).isFalse();
		
		// celular e telefone null
		enderecoEContato = buildEnderecoEContatoForm(1);
		enderecoEContato.setCelular(null);
		enderecoEContato.setTelefone(null);
		Errors errors = validate(enderecoEContato);
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("celular")).isNotNull();
		assertThat(errors.getFieldError("telefone")).isNotNull();
		
		// celular e telefone vazios
		enderecoEContato = buildEnderecoEContatoForm(1);
		enderecoEContato.setCelular("");
		enderecoEContato.setTelefone("");
		errors = validate(enderecoEContato);
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("celular")).isNotNull();
		assertThat(errors.getFieldError("telefone")).isNotNull();
		
	}
	
	@Test
	public void testaEnderecoNumeroInvalido() {
		EnderecoEContatoForm enderecoEContato = buildEnderecoEContatoForm(1);
		enderecoEContato.getEndereco().setNumero(-1);
		Errors errors = validate(enderecoEContato);
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("endereco.numero")).isNotNull();
	}

	private void setEmptyValue(Field field, Object object) {
		try {
			String fieldName = field.getName();
			String setterName = "set" + Character.valueOf(fieldName.charAt(0)).toString().toUpperCase()
					+ fieldName.substring(1);
			Method setter = Endereco.class.getMethod(setterName, new Class<?>[] { field.getType() });
			setter.invoke(object, new Object[] { field.getType().equals(String.class) ? "" : null });
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private Endereco buildEndereco(Integer n) {
		Endereco endereco = new Endereco();
		endereco.setBairro("Bairro " + n);
		endereco.setCep(String.format("%08d", n));
		endereco.setCidade("Cidade " + n);
		endereco.setComplemento("Complemento " + n);
		endereco.setEstado(Estado.RJ);
		endereco.setLogradouro("Logradouro " + n);
		endereco.setNumero(n);
		return endereco;
	}

	private EnderecoEContatoForm buildEnderecoEContatoForm(Integer n) {
		EnderecoEContatoForm enderecoEContato = new EnderecoEContatoForm();
		enderecoEContato.setEndereco(buildEndereco(n));
		enderecoEContato.setCelular("219" + String.format("%08d", n));
		enderecoEContato.setTelefone("212" + String.format("%07d", n));
		return enderecoEContato;
	}

	private Errors buildErrors(Object target) {
		return new BeanPropertyBindingResult(target, "enderecoEContato");
	}

	private Errors validate(EnderecoEContatoForm enderecoEContatoForm) {
		Errors errors = buildErrors(enderecoEContatoForm);
		new EnderecoEContatoValidation().validate(enderecoEContatoForm, errors);
		return errors;

	}

}
