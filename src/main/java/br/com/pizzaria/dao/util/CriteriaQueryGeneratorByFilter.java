package br.com.pizzaria.dao.util;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.pizzaria.dao.filter.Filter;

public class CriteriaQueryGeneratorByFilter<T> {

	private final Class<T> returnType;
	private final CriteriaBuilder criteriaBuilder;
	private PredicateGenerator predicateGenerator;
	private CriteriaQuery<T> cq;
	private Root<T> root;
	private boolean fieldsInitialized = false;

	public CriteriaQueryGeneratorByFilter(Class<T> returnType, CriteriaBuilder criteriaBuilder) {
		this.returnType = returnType;
		this.criteriaBuilder = criteriaBuilder;

	}
	
	private void initializeFields() {
		this.predicateGenerator = new PredicateGenerator(criteriaBuilder);
		this.cq = criteriaBuilder.createQuery(returnType);
		this.root = cq.from(returnType);
		fieldsInitialized = true;
	}

	public CriteriaQuery<T> generate(Filter filter, List<Predicate> customPredicates, List<Predicate> customOrPredicates) {
		if(!fieldsInitialized) {
			initializeFields();
		}
		predicateGenerator.generate(root, filter);
		
		List<Predicate> predicates = predicateGenerator.getPredicates();
		List<Predicate> orPredicates = predicateGenerator.getOrPredicates();
		
		if(customPredicates != null) {
			predicates.addAll(customPredicates);
		}
		if(customOrPredicates != null) {
			orPredicates.addAll(customOrPredicates);
		}
		
		System.out.println("Predicates: " + predicateGenerator.getPredicates());
		System.out.println("Or predicates " + predicateGenerator.getOrPredicates());
		
		Predicate andPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		Predicate orPredicate = criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
		
		if (!predicates.isEmpty() && !orPredicates.isEmpty()) {
			cq.select(root).where(criteriaBuilder.and(andPredicate, orPredicate));
		} else if (!predicates.isEmpty()) {
			cq.select(root).where(andPredicate);
		} else if (!orPredicates.isEmpty()) {
			cq.select(root).where(orPredicate);
		} else {
			cq.select(root);
		}

		cq.orderBy(criteriaBuilder.asc(root.get(filter.getOrderBy())));
		fieldsInitialized = false;
		return cq;
	}
	
	public CriteriaQuery<T> generate(Filter filter, List<Predicate> customPredicates) {
		return generate(filter, customPredicates, null);
	}
	
	public CriteriaQuery<T> generate(Filter filter) {
		return generate(filter, null, null);
	}
	
	public Root<T> getRoot() {
		if(!fieldsInitialized) {
			initializeFields();
		}
		return root;
	}

}
