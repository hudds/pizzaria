package br.com.pizzaria.validation.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;

public class NameValidationUtil {
	
	public static void validate(String name, Errors errors, String fieldName) {
		rejectAll(validate(name), errors, fieldName);
	}
	
	public static void validate(String name, Errors errors) {
		rejectAll(validate(name), errors);
	}
	
	private static Set<String> validate(String name) {
		Set<String> errorCodes = new HashSet<>();
		if(name.trim().isEmpty()) {
			errorCodes.add("field.required");
		}
		if(!isComplete(name)) {
			errorCodes.add("field.nome.incompleto");
		}
		Pattern invalidCharacter =  Pattern.compile("[^A-zÀ-ÿ\\s]|[\\[\\]^`_\\\\]");
		if (invalidCharacter.matcher(name).find()) {
			errorCodes.add("field.nome.invalido");
		}
		return errorCodes;
	}
	
	private static void rejectAll(Collection<String> errorCodes, Errors errors, String fieldName) {
		errorCodes.forEach(e -> errors.rejectValue(fieldName, e));
	}
	
	private static void rejectAll(Collection<String> errorCodes, Errors errors) {
		errorCodes.forEach(errors::reject);
	}
	
	public static boolean isComplete(String name) {
		return name.split(" ").length >= 2;
	}

}
