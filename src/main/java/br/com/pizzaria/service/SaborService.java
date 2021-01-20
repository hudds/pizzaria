package br.com.pizzaria.service;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.pizzaria.dao.SaborDAO;
import br.com.pizzaria.dao.filter.Filter;
import br.com.pizzaria.dao.filter.SaborFilter;
import br.com.pizzaria.model.Sabor;
import br.com.pizzaria.model.TipoSabor;
import br.com.pizzaria.security.util.RoleChecker;

@Component
@Transactional
public class SaborService {

	@Autowired
	private SaborDAO saborDAO;

	public List<Sabor> buscaSabores() {
		return saborDAO.buscaSabores();
	}

	public Integer grava(Sabor sabor) {
		return saborDAO.gravaSabor(sabor);
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

	public List<Sabor> buscaSabores(Collection<Integer> idsSabores) {
		return this.saborDAO.buscaSabores(idsSabores);
	}

	public void setVisivel(Integer id, Boolean visivel) {
		Sabor sabor = saborDAO.buscaSabor(id);
		if(sabor ==  null) {
			throw new EntityNotFoundException("Could not find entity with id: " + id);
		}
		sabor.setVisivel(visivel);
		this.edita(sabor);
	}
	
	public List<Sabor> buscaSabores(Filter busca){
		return saborDAO.buscaSabores(busca);
	}
	
	public List<Sabor> buscaSabores(SaborFilter busca, Authentication authentication){
		if(!RoleChecker.currentUserIsAdmin(authentication)) {
			busca.setVisivel(true);
		}
		return saborDAO.buscaSabores(busca);
	}

}
