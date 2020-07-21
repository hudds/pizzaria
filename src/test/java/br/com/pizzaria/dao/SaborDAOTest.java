package br.com.pizzaria.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
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

import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.TipoSabor;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SaborDAO.class, Sabor.class })
@EntityScan(basePackageClasses = { Sabor.class })
@DataJpaTest
@AutoConfigurationPackage
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class SaborDAOTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private SaborDAO saborDAO;

	@Test
	public void testeGravaSabor() {
		Sabor sabor = criaSabor(null);
		saborDAO.gravaSabor(sabor);
		Sabor encontrado = entityManager.find(Sabor.class, sabor.getId());
		assertThat(encontrado).isEqualTo(sabor);
	}

	@Test
	public void testeBuscaSabor() {
		Sabor sabor = criaSabor(null);
		entityManager.persist(sabor);
		Sabor encontrado = saborDAO.buscaSabor(sabor.getId());
		assertThat(encontrado).isEqualTo(sabor);
	}

	@Test
	public void testeEdita() {
		Sabor sabor = criaSabor(null);
		entityManager.persist(sabor);
		Sabor editado = new Sabor("Pizza editada", "editada", TipoSabor.SALGADA);
		editado.setId(sabor.getId());
		saborDAO.edita(editado);
		assertThat(saboresSaoIguais(sabor, editado)).isTrue();
	}

	@Test
	public void testeBuscaSabores() {
		int qntDeSabores = 10;
		for (int i = 0; i < qntDeSabores; i++) {
			entityManager.persist(criaSabor(null));
		}
		assertThat(saborDAO.buscaSabores().size()).isEqualTo(qntDeSabores);
	}

	@Test
	public void testeRemove() {
		Sabor sabor = criaSabor(null);
		Integer id = (Integer) entityManager.persistAndGetId(sabor);
		int tamanhoAntes = saborDAO.buscaSabores().size();
		saborDAO.remove(id);
		int tamanhoDepois = saborDAO.buscaSabores().size();
		assertThat(tamanhoDepois).isLessThan(tamanhoAntes);
	}

	@Test
	public void testeBuscaSaboresPorTituloOuDescricao() {
		int qntSabores = 10;
		List<Sabor> sabores = new ArrayList<>();
		for (int i = 0; i < qntSabores; i++) {
			Sabor sabor = new Sabor("Titulo" + i, "descricao" + i, TipoSabor.SALGADA);
			sabores.add(sabor);
			entityManager.persist(sabor);
		}
		sabores.forEach(s -> System.out.println(s.getTitulo()));
		assertThat(saborDAO.buscaSaboresPorTituloOuDescricao("descricao").size()).isEqualTo(qntSabores);
		assertThat(saborDAO.buscaSaboresPorTituloOuDescricao("Descricao").size()).isEqualTo(qntSabores);
		assertThat(saborDAO.buscaSaboresPorTituloOuDescricao("Titulo").size()).isEqualTo(qntSabores);
		assertThat(saborDAO.buscaSaboresPorTituloOuDescricao("titulo").size()).isEqualTo(qntSabores);
		assertThat(saborDAO.buscaSaboresPorTituloOuDescricao("Titulo1").size()).isEqualTo(1);
		assertThat(saborDAO.buscaSaboresPorTituloOuDescricao("titulo1").size()).isEqualTo(1);
		assertThat(saborDAO.buscaSaboresPorTituloOuDescricao("descricao1").size()).isEqualTo(1);
		assertThat(saborDAO.buscaSaboresPorTituloOuDescricao("Descricao1").size()).isEqualTo(1);
	}
	
	@Test
	public void testeBuscaSaboresPorTipoTituloOuDescricao() {
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
		
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.SALGADA, "descricao").size()).isEqualTo(qntSalgadas);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.SALGADA, "Descricao").size()).isEqualTo(qntSalgadas);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.DOCE, "descricao").size()).isEqualTo(qntDoces);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.DOCE, "Descricao").size()).isEqualTo(qntDoces);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.SALGADA,"Titulo").size()).isEqualTo(qntSalgadas);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.SALGADA,"titulo").size()).isEqualTo(qntSalgadas);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.DOCE,"Titulo").size()).isEqualTo(qntDoces);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.DOCE,"titulo").size()).isEqualTo(qntDoces);
		
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.SALGADA, "Titulo"+ (qntSalgadas-1)).size()).isEqualTo(1);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.SALGADA, "titulo" + (qntSalgadas-1)).size()).isEqualTo(1);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.DOCE, "Titulo"+ (qntSalgadas-1)).size()).isEqualTo(0);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.DOCE, "titulo" + (qntSalgadas-1)).size()).isEqualTo(0);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.SALGADA, "Titulo"+ (qntSalgadas+1)).size()).isEqualTo(0);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.SALGADA, "titulo" + (qntSalgadas+1)).size()).isEqualTo(0);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.DOCE, "Titulo"+ (qntSalgadas+1)).size()).isEqualTo(1);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.DOCE, "titulo" + (qntSalgadas+1)).size()).isEqualTo(1);
		
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.SALGADA, "Descricao"+ (qntSalgadas-1)).size()).isEqualTo(1);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.SALGADA, "descricao" + (qntSalgadas-1)).size()).isEqualTo(1);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.DOCE, "Descricao"+ (qntSalgadas-1)).size()).isEqualTo(0);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.DOCE, "descricao" + (qntSalgadas-1)).size()).isEqualTo(0);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.SALGADA, "Descricao"+ (qntSalgadas+1)).size()).isEqualTo(0);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.SALGADA, "descricao" + (qntSalgadas+1)).size()).isEqualTo(0);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.DOCE, "Descricao"+ (qntSalgadas+1)).size()).isEqualTo(1);
		assertThat(saborDAO.buscaSaboresPorTipoETituloOuDescricao(TipoSabor.DOCE, "descricao" + (qntSalgadas+1)).size()).isEqualTo(1);
		
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
