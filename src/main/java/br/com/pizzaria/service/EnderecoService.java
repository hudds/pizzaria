package br.com.pizzaria.service;

import java.util.List;

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
	
	public boolean grava(Endereco endereco) {
		List<Endereco> enderecosIguais = enderecoDAO.buscaEnderecosIguais(endereco);
		if(enderecosIguais.isEmpty()) {			
			enderecoDAO.grava(endereco);
			return true;
		} 
		endereco.setId(enderecosIguais.get(0).getId());
		return false;
	}
	
	public void edita(Endereco endereco) {
		enderecoDAO.edita(endereco);
	}

}
