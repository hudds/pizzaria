package br.com.pizzaria.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import br.com.pizzaria.dao.PizzaDAO;
import br.com.pizzaria.dao.SaborDAO;
import br.com.pizzaria.model.PedidoPizza;
import br.com.pizzaria.model.Pizza;
import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.TipoSabor;
import br.com.pizzaria.model.dto.PedidoPizzaFormDTO;
import br.com.pizzaria.service.PizzaService;
import br.com.pizzaria.service.SaborService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SaborService.class, SaborDAO.class, PizzaService.class, PizzaDAO.class})
@EntityScan(basePackageClasses = { Sabor.class})
@DataJpaTest
@AutoConfigurationPackage
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PedidoPizzaValidationTest {
	
	@Autowired
	private TestEntityManager em;
	@Autowired
	private PizzaService pizzaService;
	@Autowired
	private SaborService saborService;
	
	@Test
	public void testValidateTodosCamposPreenchidos() {
		Integer idPizza = em.persistAndGetId(criaPizza(TipoSabor.SALGADA), Integer.class);
		List<Integer> idsSabores = new ArrayList<Integer>();
		idsSabores.add(em.persistAndGetId(criaSabor(TipoSabor.SALGADA), Integer.class));
		PedidoPizzaFormDTO pedido = new PedidoPizzaFormDTO();
		pedido.setIdPizza(idPizza);
		pedido.setIdsSabores(idsSabores);
		pedido.setQuantidade(1);
		
		Errors errors = new BeanPropertyBindingResult(pedido, "novoPedidoPizza");
		PedidoPizzaValidation validation = new PedidoPizzaValidation(pizzaService, saborService);
		validation.validate(pedido, errors);
		
		assertThat(errors.hasErrors()).isFalse();
		
		
		
	}
	
	@Test
	public void testValidateCampoPizzaVazio() {
		List<Integer> idsSabores = new ArrayList<Integer>();
		idsSabores.add(em.persistAndGetId(criaSabor(TipoSabor.SALGADA), Integer.class));
		PedidoPizzaFormDTO pedido = new PedidoPizzaFormDTO();
		pedido.setIdsSabores(idsSabores);
		pedido.setQuantidade(1);
		
		Errors errors = new BeanPropertyBindingResult(pedido, "novoPedidoPizza");
		PedidoPizzaValidation validation = new PedidoPizzaValidation(pizzaService, saborService);
		validation.validate(pedido, errors);
		
		System.out.println(errors.getFieldErrors());
		
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("idPizza")).isNotNull();
		
	}
	
	@Test
	public void testValidateCampoSaboresVazio() {
		Integer idPizza = em.persistAndGetId(criaPizza(TipoSabor.SALGADA), Integer.class);
		PedidoPizzaFormDTO pedido = new PedidoPizzaFormDTO();
		pedido.setIdPizza(idPizza);
		pedido.setQuantidade(1);
		
		Errors errors = new BeanPropertyBindingResult(pedido, "novoPedidoPizza");
		PedidoPizzaValidation validation = new PedidoPizzaValidation(pizzaService, saborService);
		validation.validate(pedido, errors);
		
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("idsSabores")).isNotNull();
	}
	
	@Test
	public void testValidateCampoQuantidadeInvalido() {
		Integer idPizza = em.persistAndGetId(criaPizza(TipoSabor.SALGADA), Integer.class);
		List<Integer> idsSabores = new ArrayList<Integer>();
		idsSabores.add(em.persistAndGetId(criaSabor(TipoSabor.SALGADA), Integer.class));
		PedidoPizzaFormDTO pedido = new PedidoPizzaFormDTO();
		pedido.setIdPizza(idPizza);
		pedido.setIdsSabores(idsSabores);
		pedido.setQuantidade(0);
		
		for (int i  = 0; i > -10; i--) {
			pedido.setQuantidade(i);
			Errors errors = new BeanPropertyBindingResult(pedido, "novoPedidoPizza");
			PedidoPizzaValidation validation = new PedidoPizzaValidation(pizzaService, saborService);
			validation.validate(pedido, errors);
			
			assertThat(errors.hasErrors()).isTrue();
			assertThat(errors.getFieldError("quantidade")).isNotNull();
		}
		
	}
	
	@Test
	public void testValidateSaboresIncompativeis() {
		Integer idPizza = em.persistAndGetId(criaPizza(TipoSabor.SALGADA), Integer.class);
		List<Integer> idsSabores = new ArrayList<Integer>();
		idsSabores.add(em.persistAndGetId(criaSabor(TipoSabor.DOCE), Integer.class));
		PedidoPizzaFormDTO pedido = new PedidoPizzaFormDTO();
		pedido.setIdPizza(idPizza);
		pedido.setIdsSabores(idsSabores);
		pedido.setQuantidade(1);
		
		Errors errors = new BeanPropertyBindingResult(pedido, "novoPedidoPizza");
		PedidoPizzaValidation validation = new PedidoPizzaValidation(pizzaService, saborService);
		validation.validate(pedido, errors);
		
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("idPizza")).isNotNull();
		assertThat(errors.getFieldError("idsSabores")).isNotNull();
		
	}
	
	
	@Test
	public void testQuantidadeDeSaboresInvalida() {
		IntStream.range(0, 11).forEach(i -> {
			Integer idPizza = em.persistAndGetId(criaPizza(TipoSabor.SALGADA), Integer.class);
			List<Integer> idsSabores = new ArrayList<Integer>();
			IntStream.range(0, i).forEach(j -> {
				idsSabores.add(em.persistAndGetId(criaSabor(TipoSabor.SALGADA), Integer.class));			
			});
			PedidoPizzaFormDTO pedido = new PedidoPizzaFormDTO();
			pedido.setIdPizza(idPizza);
			pedido.setIdsSabores(idsSabores);
			pedido.setQuantidade(1);
			
			Errors errors = new BeanPropertyBindingResult(pedido, "novoPedidoPizza");
			PedidoPizzaValidation validation = new PedidoPizzaValidation(pizzaService, saborService);
			validation.validate(pedido, errors);
			System.out.println(pedido.getIdsSabores().size());
			if(pedido.getIdsSabores().size() >= 1 && pedido.getIdsSabores().size() <= PedidoPizza.SABORES_QUANTIDADE_MAXIMA ) {
				assertThat(errors.hasErrors()).isFalse();
			} else {
				assertThat(errors.hasErrors()).isTrue();
				assertThat(errors.getFieldError("idsSabores")).isNotNull();
			}
			
		});
		
		
		
	}
	
	private Sabor criaSabor(TipoSabor tipoSabor) {
		Sabor sabor = new Sabor();
		sabor.setTitulo("Sabor Teste");
		sabor.setDescricao("sabor teste");
		sabor.setTipo(tipoSabor);
		return sabor;
	}
	
	private Pizza criaPizza(TipoSabor tipoSabor) {
		Pizza pizza = new Pizza();
		pizza.setPreco(BigDecimal.TEN);
		pizza.setTitulo("Teste");
		pizza.setDescricao("teste");
		pizza.setTipoSabor(tipoSabor);
		return pizza;
	}
	
}
