package br.com.pizzaria.validation;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.pizzaria.model.PedidoPizza;
import br.com.pizzaria.model.Pizza;
import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.form.PedidoPizzaForm;
import br.com.pizzaria.service.PizzaService;
import br.com.pizzaria.service.SaborService;

public class PedidoPizzaValidation implements Validator{

	private PizzaService pizzaService;
	private SaborService saborService;

	public PedidoPizzaValidation(PizzaService pizzaService, SaborService saborService) {
		this.pizzaService = pizzaService;
		this.saborService = saborService;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PedidoPizzaForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "idPizza", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "idsSabores", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "quantidade", "field.required");
		
		if(errors.hasErrors()) {
			return;
		}
		
		PedidoPizzaForm pedido = (PedidoPizzaForm) target;
		
		if(pedido.getQuantidade() <= 0) {
			errors.rejectValue("quantidade", "pedidoPizza.quantidade.invalido");
			return;
		}
		
		if(pedido.getIdsSabores().size() > PedidoPizza.SABORES_QUANTIDADE_MAXIMA || pedido.getIdsSabores().size() < 1) {
			errors.rejectValue("idsSabores", "pedidoPizza.sabores.quantidade.invalido");
			return;
		}
		
		Pizza pizza = pizzaService.buscaPizza(pedido.getIdPizza());
		List<Sabor> sabores = saborService.buscaSabores(pedido.getIdsSabores());
		for(Sabor sabor : sabores) {
			if(!sabor.getTipo().equals(pizza.getTipoSabor())) {
				errors.rejectValue("idPizza", "pedidoPizza.tipoSabor.invalido");
				errors.rejectValue("idsSabores", "pedidoPizza.tipoSabor.invalido");
				break;
			}
		}
		
	}

}
