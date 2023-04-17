package br.com.pizzaria.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

//@RunWith(SpringRunner.class)
@EntityScan(basePackageClasses = { Sabor.class })
@DataJpaTest
@AutoConfigurationPackage
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PagamentoTest {

	@Autowired
	private TestEntityManager em;

	@Test
	public void testPersistFormaDePagamento() {
		em.persist(buildPagamento());
	}

	@Test
	public void testPersistNullFormaDePagamento() {
		Pagamento pagamento = buildPagamento();
		pagamento.setFormaDePagamento(null);
		assertThrows(ConstraintViolationException.class, () -> em.persist(pagamento));

	}

	@Test
	public void testPersistNullValor() {
		Pagamento pagamento = buildPagamento();
		pagamento.setValor(null);
		assertThrows(ValidationException.class, () -> em.persist(pagamento));

	}

	@Test
	public void testPersistValorMenorQueZero() {
		Pagamento pagamento = buildPagamento();
		pagamento.setValor(new BigDecimal("-1"));
		assertThrows(ConstraintViolationException.class, () -> em.persist(pagamento));

	}

	@Test
	public void testPersistValorParaValorAReceberMenorQueZero() {
		Pagamento pagamento = buildPagamento();
		pagamento.setValorAReceber(new BigDecimal("-1"));
		assertThrows(ConstraintViolationException.class, () -> em.persist(pagamento));
	}

	@Test
	public void testValorAReceberMenorQueValor() {
		Pagamento pagamento = buildPagamento();
		pagamento.setValor(BigDecimal.TEN);
		pagamento.setValorAReceber(BigDecimal.ZERO);
		assertThrows(ConstraintViolationException.class, () -> em.persist(pagamento));
	}

	@Test
	public void testSetValor() {
		Pagamento pagamento = new Pagamento();
		pagamento.setFormaDePagamento(FormaDePagamento.CARTAO);
		pagamento.setValor(BigDecimal.TEN);
		assertThat(pagamento.getValorAReceber()).isEqualTo(pagamento.getValor());

		pagamento = new Pagamento();
		pagamento.setFormaDePagamento(FormaDePagamento.DINHEIRO);
		pagamento.setValor(BigDecimal.TEN);
		assertThat(pagamento.getValorAReceber()).isNotEqualTo(pagamento.getValor());
	}

	private Pagamento buildPagamento() {
		Pagamento pagamento = new Pagamento();
		pagamento.setFormaDePagamento(FormaDePagamento.DINHEIRO);
		pagamento.setValor(BigDecimal.TEN);
		pagamento.setValorAReceber(BigDecimal.TEN);
		return pagamento;
	}
}
