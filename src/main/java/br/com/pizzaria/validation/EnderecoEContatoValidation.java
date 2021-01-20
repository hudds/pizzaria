package br.com.pizzaria.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.pizzaria.model.dto.EnderecoEContatoFormDTO;

public class EnderecoEContatoValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return EnderecoEContatoFormDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.estado", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.cidade", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.bairro", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.cep", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.numero", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.logradouro", "field.required");

		if(errors.hasErrors()) {
			return;
		}
		
		EnderecoEContatoFormDTO enderecoContato = (EnderecoEContatoFormDTO) target;

		validaNumerosDeContato(errors, enderecoContato);
		validaCep(enderecoContato, errors);
		validaNumero(enderecoContato, errors);
		

	}

	private void validaNumerosDeContato(Errors errors, EnderecoEContatoFormDTO enderecoContato) {
		boolean campoCelularVazio = enderecoContato.getCelular() == null || enderecoContato.getCelular().trim().isEmpty();
		boolean campoTelefoneVazio = enderecoContato.getTelefone() == null || enderecoContato.getTelefone().trim().isEmpty();
		
		if (campoCelularVazio && campoTelefoneVazio) {
			errors.rejectValue("telefone", "contato.required");
			errors.rejectValue("celular", "contato.required");
		} else {
			if (!campoCelularVazio) {
				validaCelular(enderecoContato.getCelular(), errors);
			}
			if (!campoTelefoneVazio) {
				validaTelefone(enderecoContato.getTelefone(), errors);
			}
		}
	}

	private void validaCelular(String celular, Errors errors) {
		celular = celular.replaceAll("[^\\d]", "").trim();
		if (!celular.matches("[1-9]{2}9[\\d]{8}")) {
			errors.rejectValue("celular", "celular.invalid");
		}
	}

	private void validaTelefone(String telefone, Errors errors) {
		telefone = telefone.replaceAll("[^\\d]", "");
		if (!telefone.matches("[1-9]{2}[1-8][\\d]{7}")) {
			errors.rejectValue("telefone", "telefone.invalid");
		}
	}
	
	private void validaCep(EnderecoEContatoFormDTO enderecoEContatoForm, Errors errors) {
		String cep = enderecoEContatoForm.getEndereco().getCep();
		if(!cep.matches("^\\d{8}$|^\\d{5}-\\d{3}$")) {
			errors.rejectValue("endereco.cep", "endereco.cep.invalid");
		}
	}
	
	private void validaNumero(EnderecoEContatoFormDTO enderecoEContatoForm, Errors errors) {
		String numero = enderecoEContatoForm.getEndereco().getNumero();
		if(numero.length() > 10 || numero.replaceAll("[^\\d]", "").isEmpty()) {
			errors.rejectValue("endereco.numero", "endereco.numero.invalid");
		}
	}

}
