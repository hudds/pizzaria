package br.com.pizzaria.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pizzaria.dao.EnderecoDAO;
import br.com.pizzaria.model.Endereco;

@Component
@Transactional
public class EnderecoService {

	@Autowired
	private EnderecoDAO enderecoDAO;
	
	public void grava(Endereco endereco) {		
		enderecoDAO.grava(endereco);

	}
	
	public void edita(Endereco endereco) {
		enderecoDAO.edita(endereco);
	}
	
}
