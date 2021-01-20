package br.com.pizzaria.util;

public class StringUtil {

	/**
	 * Create a new String with the first letter in upper case.
	 * @param s - String to have its first letter transformed to upper case
	 * @return String with the first letter capitalized
	 */
	public static String capitalizeFirstLetter(String s) {
		return s.substring(0,1).toUpperCase() + s.substring(1);
	}
	
	/**
	 * Check if an object is an instance of String
	 * @param object - object to be checked
	 * @return true if object is a String, else returns false 
	 */
	public static boolean isString(Object object) {
		return object instanceof String;
	}

	/**
	 * Checks if an object is a String, if so, checks if the String is empty
	 * @param object - object to be checked
	 * @return true if object is an empty String, false if object is not a String or is not an empty String 
	 */
	public static boolean isEmptyString(Object object) {
		if (isString(object)) {
			String s = (String) (object);
			return s.isEmpty();
		}
		return false;
	}
}
