package br.com.pizzaria.dao.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import br.com.pizzaria.dao.util.annotation.FilterParameter;
import br.com.pizzaria.dao.util.annotation.Like;
import br.com.pizzaria.dao.util.annotation.PredicateOr;
import br.com.pizzaria.dao.util.annotation.QueryFilter;
import br.com.pizzaria.util.StringUtil;

public class PredicateGenerator {
	
	private CriteriaBuilder cb;
	private List<Predicate> predicates = new ArrayList<>();
	private List<Predicate> orPredicates = new ArrayList<>();

	public PredicateGenerator(CriteriaBuilder cb) {
		this.cb = cb;
	}

	public void generate(From<?, ?> from, Object object) {
		checkIfQueryFilter(object);
		Class<? extends Object> clazz = object.getClass();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(FilterParameter.class)) {
				String getterName = createGetterName(field);
				try {
					Method getter = clazz.getMethod(getterName);
					Object parameterValue = getter.invoke(object);
					
					if (parameterValue == null) {
						continue;
					}
					if (field.getType().isAnnotationPresent(QueryFilter.class)) {
						Join<Object, Object> join = from.join(field.getName());
						this.generate(join, parameterValue);
					} else {
						if (StringUtil.isEmptyString(parameterValue)) {
							continue;
						}
						List<Predicate> predicates = field.isAnnotationPresent(PredicateOr.class) ? this.orPredicates : this.predicates;
						if (StringUtil.isString(parameterValue) && field.isAnnotationPresent(Like.class)) {
							Predicate likePredicate = createLikePredicate(field, parameterValue, from);
							predicates.add(likePredicate);
							continue;
						}
						predicates.add(cb.equal(from.get(field.getName()), parameterValue));
					}
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	private String createGetterName(Field field) {
		return "get" + StringUtil.capitalizeFirstLetter(field.getName());
	}
	
	private Predicate createLikePredicate(Field field, Object parameterValue, From<?,?> from) {
		String s = (String) "%" + parameterValue + "%";
		Expression<String> expression = from.get(field.getName());
		if(field.getAnnotation(Like.class).caseSensitive()) {
			s = s.toLowerCase();
			expression = cb.lower(expression);
		}
		return cb.like(expression, s);
	}

	public void clear() {
		this.predicates.clear();
		this.orPredicates.clear();
	}

	private static void checkIfQueryFilter(Object object) {
		if (!object.getClass().isAnnotationPresent(QueryFilter.class)) {
			throw new IllegalArgumentException("Class must be annotated with " + QueryFilter.class.toString());
		}
	}
	
	/**
	 * Predicates that will be separated by "and" in the query
	 * @return List of predicates
	 */

	public List<Predicate> getPredicates() {
		return new ArrayList<>(predicates);
	}
	

	/**
	 * Predicates that will be separated by "or" in the query
	 * @return List of predicates
	 */
	public List<Predicate> getOrPredicates() {
		return new ArrayList<>(orPredicates);
	}

	
}
