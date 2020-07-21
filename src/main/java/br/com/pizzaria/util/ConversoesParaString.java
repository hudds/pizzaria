package br.com.pizzaria.util;

import java.util.List;

public final class ConversoesParaString {
	
	
	/**
	 * Retorna uma String contendo os valores da lista separados apenas por vírgulas e sem colchetes.
	 * 
	 * @param lista - Lista a ser convertida para String.
	 * @return O resultado da conversão. Se a lista for null o retorno será null.
	 */
	public static String listaSemColchetes(List<?> lista) {
		return lista == null ? null : lista.toString().replaceAll("[\\[\\]\\s]", "");
	}

}
