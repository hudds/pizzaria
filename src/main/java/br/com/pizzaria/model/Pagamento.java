package br.com.pizzaria.model;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.com.pizzaria.validation.constraint.ValorAReceberValido;


@Entity
@Table(name = "TB_PAGAMENTOS")
@ValorAReceberValido
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pagamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "FORMA_DE_PAGAMENTO")
	@Enumerated(EnumType.STRING)
	@NotNull
	private FormaDePagamento formaDePagamento;
	
	@Column(name = "VALOR")
	@NotNull 
	@Min(0)
	private BigDecimal valor = BigDecimal.ZERO;
	
	@Column(name="VALOR_A_RECEBER")
	@NotNull
	@Min(value = 0)
	private BigDecimal valorAReceber = BigDecimal.ZERO;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FormaDePagamento getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
		this.valorAReceber = this.formaDePagamento == FormaDePagamento.CARTAO ? valor : this.valorAReceber;
	}

	public BigDecimal getValorAReceber() {
		return valorAReceber;
	}

	public void setValorAReceber(BigDecimal valorParaTroco) {
		this.valorAReceber = valorParaTroco;
	}
	
	public BigDecimal getTroco() {
		if(formaDePagamento != FormaDePagamento.DINHEIRO) {
			return BigDecimal.ZERO;
		}
		return valorAReceber.subtract(valor);
	}
	
}
