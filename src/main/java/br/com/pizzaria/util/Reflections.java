package br.com.pizzaria.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Reflections {

	public static List<Field> getNonNullDeclaredFields(Object object){
		Class<? extends Object> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		List<Field> nonNullFields = new ArrayList<>();
		for(Field field : fields) {
			try {
				if(field.get(object) != null) {
					nonNullFields.add(field);
				}
			} catch(IllegalAccessException e) {
				try {
					Method getter = getterFrom(clazz, field);
					if(getter.invoke(object) != null) {
						nonNullFields.add(field);
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
					// intentional empty catch block
				}
			} 
		}
		return nonNullFields;
	}

	public static Method getterFrom(Class<? extends Object> clazz, Field field)
			throws NoSuchMethodException, SecurityException {
		String getterName = getGetterName(field);

		return clazz.getDeclaredMethod(getterName);
	}

	public static boolean hasPublicGetter(Class<? extends Object> clazz, Field field) {
		try {
			clazz.getDeclaredField(getGetterName(field));
			return true;
		} catch (NoSuchFieldException | SecurityException e) {
			return false;
		}
	}

	private static String getGetterName(Field field) {
		return "get" + StringUtil.capitalizeFirstLetter(field.getName());
	}
	
}
