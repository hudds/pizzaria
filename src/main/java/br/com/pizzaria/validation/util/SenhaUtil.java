package br.com.pizzaria.validation.util;

public class SenhaUtil {

	
	public static boolean senhaForte(String senha) {
		return senha.length() >= 8;
	}
}
