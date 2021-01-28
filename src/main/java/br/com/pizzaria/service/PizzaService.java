package br.com.pizzaria.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.pizzaria.dao.PizzaDAO;
import br.com.pizzaria.model.Pizza;
import br.com.pizzaria.model.TipoSabor;
import br.com.pizzaria.security.util.RoleChecker;

@Component
@Transactional
public class PizzaService {

	@Autowired
	private PizzaDAO pizzaDAO;

	public Integer grava(Pizza pizza) {
		return pizzaDAO.cadastra(pizza);
		
	}

	public List<Pizza> buscaPizzas() {
		return pizzaDAO.buscaPizzas();
	}
	
	public List<Pizza> buscaPizzas(boolean visivel) {
		return pizzaDAO.buscaPizzas(visivel);
	}
	
	public List<Pizza> buscaPizzas(Authentication authentication){
		if(RoleChecker.currentUserIsAdmin(authentication)) {
			return this.buscaPizzas();
		}
		return pizzaDAO.buscaPizzas(true);
		
	}
	
	public List<Pizza> buscaPizzasOrdenadasPeloTipoSabor(TipoSabor primeiro){
		return buscaPizzasOrdenadasPeloTipoSabor(primeiro, null);
	}
	
	public List<Pizza> buscaPizzasOrdenadasPeloTipoSabor(TipoSabor primeiro, Boolean visivel){
		List<Pizza> pizzas = visivel == null ? buscaPizzas() : buscaPizzas(visivel);
		pizzas.sort((p1, p2) -> {
			Integer p1Value = p1.getTipoSabor() == primeiro ? 0 : 1;
			Integer p2Value = p2.getTipoSabor() == primeiro ? 0 : 1;
			return p1Value.compareTo(p2Value);
		});
		return pizzas;
	}
	
	public List<Pizza> buscaPizzasPeloTipoSabor(TipoSabor tipoSabor) {
		return pizzaDAO.buscaPizzasPeloTipoSabor(tipoSabor);
	}
	
	
	public Pizza busca(Integer id) {
		return pizzaDAO.buscaPizza(id);
	}

	public void deletaPeloId(Integer id) {
		pizzaDAO.deletaPeloId(id);
		
	}

	public Pizza edita(Pizza pizza) {
		return pizzaDAO.edita(pizza);
	}

	public TipoSabor buscaTipoSaborPeloId(Integer idPizza) {
		return pizzaDAO.buscaTipoSaborPeloId(idPizza);
	}

	public void setVisivel(Integer id, boolean visivel) {
		Pizza pizza = pizzaDAO.buscaPizza(id);
		if(pizza == null) {
			throw new EntityNotFoundException("Nenhuma pizza encontrada com o id: " + id);
		}
		pizza.setVisivel(visivel);
		this.edita(pizza);
	}
	
}
