package br.com.pizzaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pizzaria.model.Endereco;

@Repository
public class EnderecoDAO {
	
	@Autowired
	private EntityManager em;
	
	public void grava(Endereco endereco) {
		em.persist(endereco);
	}
	
	public void edita(Endereco endereco) {
		em.merge(endereco);
	}

	public List<Endereco> buscaEnderecosIguais(Endereco endereco) {
		
		TypedQuery<Endereco> query = em.createQuery(EnderecoQueries.BUSCA_ENDERECO_IGUAL, Endereco.class);
		query.setParameter("pEstado", endereco.getEstado());
		query.setParameter("pCidade", endereco.getCidade());
		query.setParameter("pCep", endereco.getCep());
		query.setParameter("pBairro", endereco.getBairro());
		query.setParameter("pLogradouro", endereco.getLogradouro());
		query.setParameter("pNumero", endereco.getNumero());
		query.setParameter("pComplemento", endereco.getComplemento());
		
		
		return query.getResultList();
	}
	
}

final class EnderecoQueries {
	protected static final String BUSCA_ENDERECO_IGUAL = "select e from Endereco e where "
			+ " e.estado = :pEstado"
			+ " and e.cidade = :pCidade"
			+ " and e.cep = :pCep"
			+ " and e.bairro = :pBairro"
			+ " and e.logradouro = :pLogradouro"
			+ " and e.numero = :pNumero"
			+ " and e.complemento = :pComplemento";
}
