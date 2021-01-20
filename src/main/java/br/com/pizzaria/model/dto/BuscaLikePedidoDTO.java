package br.com.pizzaria.model.dto;

import java.time.LocalDateTime;

import br.com.pizzaria.dao.filter.Filter;
import br.com.pizzaria.dao.util.annotation.FilterParameter;
import br.com.pizzaria.dao.util.annotation.QueryFilter;
import br.com.pizzaria.model.EstadoPedido;

@QueryFilter
public class BuscaLikePedidoDTO implements Filter{
	@FilterParameter
	private Integer id;
	private LocalDateTime firstDate;
	private LocalDateTime secondDate;
	@FilterParameter
	private EstadoPedido estado;
	@FilterParameter
	private BuscaLikeEnderecoDTO endereco;
	@FilterParameter
	private BuscaLikeUsuarioDTO cliente;
	private boolean fetchPizzas;
	private boolean fetchBebidas;
	private String orderBy = "id";
	
	public LocalDateTime getFirstDate() {
		return firstDate;
	}
	public void setFirstDate(LocalDateTime firstDate) {
		if(firstDate != null) {
			this.firstDate = removeSecondsAndNanos(firstDate);
			return;
		}
		this.firstDate = firstDate;
	}
	public LocalDateTime getSecondDate() {
		return secondDate;
	}
	public void setSecondDate(LocalDateTime secondDate) {
		if(secondDate != null) {
			this.secondDate = removeSecondsAndNanos(secondDate);
			return;
		}
		this.secondDate = secondDate;
	}
	public EstadoPedido getEstado() {
		return estado;
	}
	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}
	public BuscaLikeEnderecoDTO getEndereco() {
		return endereco;
	}
	public void setEndereco(BuscaLikeEnderecoDTO endereco) {
		this.endereco = endereco;
	}
	public BuscaLikeUsuarioDTO getCliente() {
		return cliente;
	}
	public void setCliente(BuscaLikeUsuarioDTO cliente) {
		this.cliente = cliente;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	private LocalDateTime removeSecondsAndNanos(LocalDateTime localDateTime) {
		return localDateTime.minusSeconds(localDateTime.getSecond()).minusNanos(localDateTime.getNano());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + (fetchBebidas ? 1231 : 1237);
		result = prime * result + (fetchPizzas ? 1231 : 1237);
		result = prime * result + ((firstDate == null) ? 0 : firstDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
		result = prime * result + ((secondDate == null) ? 0 : secondDate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuscaLikePedidoDTO other = (BuscaLikePedidoDTO) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (estado != other.estado)
			return false;
		if (fetchBebidas != other.fetchBebidas)
			return false;
		if (fetchPizzas != other.fetchPizzas)
			return false;
		if (firstDate == null) {
			if (other.firstDate != null)
				return false;
		} else if (!firstDate.equals(other.firstDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orderBy == null) {
			if (other.orderBy != null)
				return false;
		} else if (!orderBy.equals(other.orderBy))
			return false;
		if (secondDate == null) {
			if (other.secondDate != null)
				return false;
		} else if (!secondDate.equals(other.secondDate))
			return false;
		return true;
	}
	public boolean getFetchPizzas() {
		return fetchPizzas;
	}
	public void setFetchPizzas(boolean fetchPizzas) {
		this.fetchPizzas = fetchPizzas;
	}
	public boolean isFetchBebidas() {
		return fetchBebidas;
	}
	public void setFetchBebidas(boolean fetchBebidas) {
		this.fetchBebidas = fetchBebidas;
	}
	
}
