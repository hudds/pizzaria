<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tags:pageTemplate title="Detalhes do pagamento" css="home.css">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<h1>Detalhes do pagamento</h1>
		
		<form:form cssClass="form form-pagamento mb-3" modelAttribute="pagamento" action="${contextPath}/pedido/pagamento" method="POST">
			<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path=""></form:errors>
			<form:hidden path="valor"/>
			<div class="form-group">
				<label>Forma de pagamento: </label>
				<form:select cssClass="select-forma-pagamento form-control" path="formaDePagamento">
				<c:forEach items="${formasDePagamento}" var="formaDePagamento">
					<form:option value="${formaDePagamento}" label = "${formaDePagamento.value}" />
				</c:forEach>
				</form:select>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="formaDePagamento"></form:errors>
			</div>
			<div class="form-group valor-troco-form-group">
				<label>Caso o pagamento seja em dinheiro, insira o valor para o troco: </label>
				<fmt:formatNumber value="${pagamento.valorAReceber}" var="valorAReceberFormatado" pattern="#.00"/>
				<form:input value="${valorAReceberFormatado}" type="text" min = "${pagamento.valorAReceber}" step="" cssClass="form-control brl-input" path="valorAReceber"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="valorAReceber"></form:errors>
			</div>
			<form:button type="submit" class="btn btn-confirmar-pagamento btn-danger">Confirmar</form:button>
		</form:form>
		<div class="card">
			<div class="card-body">
				<h5 class="card-title">Valor total da compra: <fmt:formatNumber value="${valorTotal}" type="currency" currencyCode="BRL"/></h5>
			</div>
		</div>
	</div>
	<script src="${pathJs}vanilla-masker.js"></script>
	<script src="${pathJs}pedido/form-pagamento.js"></script>
</tags:pageTemplate>
  