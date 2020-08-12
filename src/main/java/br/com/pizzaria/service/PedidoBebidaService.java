package br.com.pizzaria.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pizzaria.dao.PedidoBebidaDAO;
import br.com.pizzaria.model.PedidoBebida;

@Service
@Transactional
public class PedidoBebidaService {
	
	@Autowired
	private PedidoBebidaDAO pedidoBebidaDAO;
	
	public void grava(PedidoBebida pedidoBebida) {
		pedidoBebidaDAO.grava(pedidoBebida);
	}
}
