package br.com.pizzaria.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pizzaria.dao.PagamentoDAO;
import br.com.pizzaria.model.Pagamento;

@Service
@Transactional
public class PagamentoService {
	
	@Autowired
	private PagamentoDAO pagamentoDAO;
	
	public void grava(Pagamento pagamento) {
		pagamentoDAO.grava(pagamento);
	}
}
