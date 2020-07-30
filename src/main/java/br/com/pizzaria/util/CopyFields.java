package br.com.pizzaria.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class CopyFields {
	
	/**
	 * Copy all the declared fields from one object to another using the getters and the setters. The fields must be the same type and must have public getters and setters.
	 * @param a - Object that is going to receive the fields
	 * @param b - Object that the fields are going to be copied from
	 */
	public static void copy(Object a, Object b) {
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("copy foi chamado");
		Field[] declaredFields = a.getClass().getDeclaredFields();
		for(Field field: declaredFields) {
			System.out.println("----------------------------------------\nloop copy");
			String fieldName = field.getName();
			fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			String setterName = "set" + fieldName;
			String getterName = "get" + fieldName;
			try {
				Method getter = b.getClass().getMethod(getterName);
				Method setter = a.getClass().getMethod(setterName, field.getType());
				
				setter.invoke(a, getter.invoke(b));
				
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
}
