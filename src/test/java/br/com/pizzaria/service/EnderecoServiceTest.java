package br.com.pizzaria.service;

import static org.assertj.core.api.Assertions.assertThat;

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

import br.com.caelum.stella.type.Estado;
import br.com.pizzaria.dao.EnderecoDAO;
import br.com.pizzaria.model.Endereco;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { EnderecoService.class, EnderecoDAO.class})
@EntityScan(basePackageClasses = { Endereco.class})
@DataJpaTest
@AutoConfigurationPackage
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class EnderecoServiceTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private EnderecoService enderecoService;

	@Test
	public void gravaEnderecoTest() {
		int n = 1;
		
		// persiste enderecos iguais, apenas o id eh diferente
		Endereco endereco = buildEndereco(n); 
		entityManager.persist(endereco);
		endereco = buildEndereco(n);
		assertThat(enderecoService.grava(endereco)).isFalse();
		
		// persiste enderecos diferentes
		endereco = buildEndereco(n);
		endereco.setNumero(n+1);
		assertThat(enderecoService.grava(endereco)).isTrue();
		assertThat(entityManager.find(Endereco.class, endereco.getId())).isNotNull();
	}

	private Endereco buildEndereco(int n) {
		Endereco endereco = new Endereco();
		endereco.setBairro("Bairro " + n);
		endereco.setCep(String.format("%08d", n));
		endereco.setCidade("Cidade " + n);
		endereco.setComplemento("Complemento " + n);
		endereco.setEstado(Estado.RJ);
		endereco.setLogradouro("Logradouro " + n);
		endereco.setNumero(n);
		return endereco;
	}
	
}
