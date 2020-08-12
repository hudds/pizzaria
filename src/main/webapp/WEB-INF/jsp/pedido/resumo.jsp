<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<tags:pageTemplate title="Resumo do pedido" css="home.css">
		<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
			<c:if test="${carrinhoVazio }">
				<h1>Você não fez um pedido ainda.</h1>
			</c:if>
			<c:if test="${not carrinhoVazio }">
				<h1>Resumo do pedido</h1>
				
				<h3>Itens:</h3>
				<div class="container mt-5">
					<table class="table border rounded bg-white">
						<thead>
							<tr>
								<th scope="col">Item</th>
								<th scope="col">Quantidade</th>
								<th scope="col">Valor</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${itens}" var="item">
								<tr>
									<td>
										<p>${item.descricao}</p>
									</td>
									<td>
										${item.quantidade}
									</td>
									<td>
										<p><fmt:formatNumber value="${item.valor}" type="currency" currencyCode="BRL"/></p>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
			<div class="m-3">
				<a href="${contextPath}/pedido/carrinho" class="btn btn-danger text-light">Editar itens</a>
			</div>
			<div class="container d-flex justify-content-around  align-items-center">
				<div class="card w-100 mr-5">
					<div class="card-body">
						<h5 class="card-title">Endereço de entrega</h5>
						<p class="card-text">
							${endereco.logradouro}, ${endereco.numero} <br>
							Bairro: ${endereco.bairro} <br>
							CEP: ${endereco.cep} <br>
							<c:if test="${not endereco.complemento.trim().isEmpty()}">
								Complemento: ${endereco.complemento}
							</c:if>
						</p>
					</div>
				</div>
				<div class="card w-100">
					<div class="card-body ">
						<h5 class="card-title">Valor total: <fmt:formatNumber value="${pagamento.valor}" type="currency" currencyCode="BRL"/></h5>
						<h5 class="card-title">Forma de pagamento: ${pagamento.formaDePagamento.value}</h5>
						<c:if test="${pagamento.formaDePagamento == 'DINHEIRO' }">
							<h5 class="card-title">Valor para trocar: <fmt:formatNumber value="${pagamento.valorAReceber}" type="currency" currencyCode="BRL"/></h5>
							<h5 class="card-title">Troco: <fmt:formatNumber value="${pagamento.troco}" type="currency" currencyCode="BRL"/></h5>
						</c:if>
						<form action="${contextPath}/pedido/confirmacao" method="POST">
							<sec:csrfInput/>
							<button type="submit" class="btn btn-danger">Confirmar pedido</button>
						</form>
					</div>
				</div>
			</div>
		</div>
</tags:pageTemplate>
  