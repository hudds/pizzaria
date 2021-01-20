package br.com.pizzaria.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pizzaria.dao.BebidaDAO;
import br.com.pizzaria.model.Bebida;

@Component
@Transactional
public class BebidaService {
	
	@Autowired
	private BebidaDAO bebidaDAO;

	public Integer grava(Bebida bebida) {
		return bebidaDAO.cadastra(bebida);
		
	}

	public List<Bebida> buscaBebidas() {
		return bebidaDAO.buscaBebidas();
	}

	public Bebida buscaBebida(Integer bId) {
		return bebidaDAO.buscaBebida(bId);
	}

	public void edita(Bebida bebida) {
		bebidaDAO.edita(bebida);
	}

	public int deletaPeloId(Integer id) {
		return bebidaDAO.deletaPeloId(id);
		
	}
	
	public void setVisivel(Integer id, boolean visivel) {
		Bebida bebida = this.bebidaDAO.buscaBebida(id);
		if(bebida == null) {
			throw new EntityNotFoundException("Nenhuma bebida encontrada com o id: " + id);
		}
		bebida.setVisivel(visivel);
		this.edita(bebida);
	}
	
}
