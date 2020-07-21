package br.com.pizzaria.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pizzaria.dao.SaborDAO;
import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.TipoSabor;

@Component
@Transactional
public class SaborService {

	@Autowired
	private SaborDAO saborDAO;

	public List<Sabor> buscaSaboresPorTipoETituloOuDescricao(TipoSabor tipo, String tituloOuDescricao) {
		if (tipo != null && tituloOuDescricao != null) {
			return saborDAO.buscaSaboresPorTipoETituloOuDescricao(tipo, tituloOuDescricao);
		}

		if (tituloOuDescricao != null) {
			return saborDAO.buscaSaboresPorTituloOuDescricao(tituloOuDescricao);
		}

		if (tipo != null) {
			return buscaSaboresPorTipo(tipo);
		}

		return buscaSabores();

	}

	public List<Sabor> buscaSaboresPorTipo(TipoSabor tipo) {
		return saborDAO.buscaSaboresPorTipo(tipo);
	}

	public List<Sabor> buscaSabores() {
		return saborDAO.buscaSabores();
	}

	public void gravaSabor(Sabor sabor) {
		saborDAO.gravaSabor(sabor);
	}

	public Sabor buscaSabor(Integer id) {
		return saborDAO.buscaSabor(id);
	}

	public void edita(Sabor sabor) {
		saborDAO.edita(sabor);
	}

	public void remove(Integer id) {
		saborDAO.remove(id);
	}
	
	public TipoSabor buscaTipoSaborPeloId(Integer id) {
		return saborDAO.buscaTipoSaborPeloId(id);
	}

}
