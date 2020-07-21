package br.com.pizzaria.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pizzaria.dao.PizzaDAO;
import br.com.pizzaria.model.Pizza;
import br.com.pizzaria.model.TipoSabor;

@Component
@Transactional
public class PizzaService {

	@Autowired
	private PizzaDAO pizzaDAO;

	public void cadastra(Pizza pizza) {
		pizzaDAO.cadastra(pizza);
		
	}

	public List<Pizza> buscaPizzas() {
		return pizzaDAO.buscaPizzas();
	}
	
	public List<Pizza> buscaPizzasPeloTipoSabor(TipoSabor tipoSabor) {
		return pizzaDAO.buscaPizzasPeloTipoSabor(tipoSabor);
	}
	
	
	public Pizza buscaPizza(Integer id) {
		return pizzaDAO.buscaPizza(id);
	}

	public void deletaPeloId(Integer id) {
		pizzaDAO.deletaPeloId(id);
		
	}

	public Pizza editaPizza(Pizza pizza) {
		return pizzaDAO.editaPizza(pizza);
	}

	public TipoSabor buscaTipoSaborPeloId(Integer idPizza) {
		return pizzaDAO.buscaTipoSaborPeloId(idPizza);
	}
	
}
