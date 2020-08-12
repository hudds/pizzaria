package br.com.pizzaria.model.form;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import br.com.pizzaria.model.FormaDePagamento;
import br.com.pizzaria.model.Pagamento;

public class PagamentoForm {

	private Integer id;
	private FormaDePagamento formaDePagamento;
	private BigDecimal valor;
	private BigDecimal valorAReceber;

	public PagamentoForm() {

	}

	public PagamentoForm(Pagamento pagamento) {
		BeanUtils.copyProperties(pagamento, this);
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public FormaDePagamento getFormaDePagamento() {
		return this.formaDePagamento;
	}
	
	public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public BigDecimal getValorAReceber() {
		return valorAReceber;
	}
	
	public void setValorAReceber(BigDecimal valorAReceber) {
		this.valorAReceber = valorAReceber;
	}

	public Pagamento createPagamento() {
		Pagamento pagamento = new Pagamento();
		BeanUtils.copyProperties(this, pagamento);
		System.out.println(pagamento.getValorAReceber());
		return pagamento;
	}
	
}
