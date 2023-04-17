package br.com.pizzaria.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

import br.com.pizzaria.dao.SaborDAO;
import br.com.pizzaria.dao.filter.BuscaLikeSabor;
import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.TipoSabor;

@SpringBootTest
@ContextConfiguration(classes = { SaborService.class, SaborDAO.class})
@EntityScan(basePackageClasses = { Sabor.class })
@DataJpaTest
@AutoConfigurationPackage
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SaborServiceTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private SaborService saborService;

	@Test
	public void testeGravaSabor() {
		Sabor sabor = criaSabor(null);
		saborService.grava(sabor);
		Sabor encontrado = entityManager.find(Sabor.class, sabor.getId());
		assertThat(encontrado).isEqualTo(sabor);
	}

	@Test
	public void testeBuscaSabor() {
		Sabor sabor = criaSabor(null);
		entityManager.persist(sabor);
		Sabor encontrado = saborService.buscaSabor(sabor.getId());
		assertThat(encontrado).isEqualTo(sabor);
	}

	@Test
	public void testeEdita() {
		Sabor sabor = criaSabor(null);
		entityManager.persist(sabor);
		Sabor editado = new Sabor("Pizza editada", "editada", TipoSabor.SALGADA);
		editado.setId(sabor.getId());
		saborService.edita(editado);
		assertThat(saboresSaoIguais(sabor, editado)).isTrue();
	}

	@Test
	public void testeBuscaSabores() {
		int qntDeSabores = 10;
		for (int i = 0; i < qntDeSabores; i++) {
			entityManager.persist(criaSabor(null));
		}
		assertThat(saborService.buscaSabores().size()).isEqualTo(qntDeSabores);
	}

	@Test
	public void testeGravaERemove() {
		Sabor sabor = criaSabor(null);
		Integer id = saborService.grava(sabor);
		int tamanhoAntes = saborService.buscaSabores().size();
		saborService.remove(id);
		int tamanhoDepois = saborService.buscaSabores().size();
		assertThat(tamanhoDepois).isLessThan(tamanhoAntes);
	}

	
	@Test
	public void testeBuscaSaboresPorTipoETituloOuDescricao() {
		int qntSabores = 10;
		List<Sabor> sabores = new ArrayList<>();
		int qntSalgadas = qntSabores/2;
		int qntDoces = qntSabores-qntSalgadas;
		for (int i = 0; i < qntSabores; i++) {
			TipoSabor tipo = i+1 <= qntSalgadas ? TipoSabor.SALGADA : TipoSabor.DOCE;
			Sabor sabor = new Sabor("Titulo" + i, "descricao" + i, tipo);
			sabores.add(sabor);
			entityManager.persist(sabor);
		}
		sabores.forEach(s -> System.out.println(s.getTitulo()));
		
		BuscaLikeSabor busca = new BuscaLikeSabor();
		
		busca.setTipo(TipoSabor.SALGADA);
		busca.setDescricao("descricao");
		assertThat(saborService.buscaSabores(busca)).hasSize(qntSalgadas);
		busca.setDescricao("Descricao");
		assertThat(saborService.buscaSabores(busca)).hasSize(qntSalgadas);
		
		busca.setTitulo("titulo");
		assertThat(saborService.buscaSabores(busca)).hasSize(qntSalgadas);
		
		busca.setTipo(TipoSabor.DOCE);
		busca.setDescricao("descricao");
		assertThat(saborService.buscaSabores(busca)).hasSize(qntDoces);
		busca.setDescricao("Descricao");
		assertThat(saborService.buscaSabores(busca)).hasSize(qntDoces);
		
		
		
	}
	
	@Test
	public void buscaSaboresPelosIds() {
		int qntSabores = 10;
		int qntSaboresParaBuscar = 5;
		Set<Integer> ids = new HashSet<Integer>();
		for (int i = 0; i < qntSabores; i++) {
			if(i < qntSaboresParaBuscar) {
				ids.add(entityManager.persistAndGetId(criaSabor(null), Integer.class));
			}
		}
		
		List<Sabor> sabores = saborService.buscaSabores(ids);
		assertThat(sabores.size()).isEqualTo(ids.size());
		sabores.forEach(s -> {
				assertThat(ids.contains(s.getId())).isTrue();
			}
		);
		
		
	}

	private Sabor criaSabor(Integer id) {
		Sabor sabor = new Sabor("Calabresa", "Molho de tomate, mussarela, calabresa, cebola e orégano",
				TipoSabor.SALGADA);
		sabor.setId(id);
		return sabor;
	}

	private boolean saboresSaoIguais(Sabor sabor1, Sabor sabor2) {
		return sabor1.getTipo().equals(sabor2.getTipo()) && sabor1.getDescricao().equals(sabor2.getDescricao())
				&& sabor1.getTitulo().equals(sabor2.getTitulo());
	}

}
