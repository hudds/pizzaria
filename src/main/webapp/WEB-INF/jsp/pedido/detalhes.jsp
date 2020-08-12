<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${status_registro_pedido == 'success'}">
	<c:set value="Pedido concluido com sucesso" var = "title"/>
</c:if>
<c:if test="${empty status_registro_pedido}">
	<c:set value="Detalhes do pedido" var = "title"/>
</c:if>


<tags:pageTemplate title="${title }" css="home.css">
		<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<c:if test="${status_registro_pedido == 'success'}">
			<h1 class="text-success">O seu pedido foi registrado com sucesso!</h1>
			<h2>Agora é só aguardar! Abaixo estão as informações do pedido.</h2>
		</c:if>
		<c:if test="${empty status_registro_pedido}">
			<h1>Detalhes do pedido</h1>
		</c:if>
			<div class="border rounded mt-5 p-3">
			
				<div class="container">
					<fmt:parseDate value="${pedido.horaPedido}" pattern = "yyyy-MM-dd'T'HH:mm:ss" var = "dataPedido" type ="date"/>
					<h5>Pedido feito em <fmt:formatDate value="${dataPedido}" pattern="dd/MM/yyyy 'às' HH'h'mm"/></h5>
					<h5>Estado do pedido: ${pedido.estado.value.toLowerCase() }</h5>
					<table class="table border rounded bg-white">
						<thead>
							<tr>
								<th scope="col">Item</th>
								<th scope="col">Quantidade</th>
								<th scope="col">Valor</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pedido.pizzas}" var="item">
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
							<c:forEach items="${pedido.bebidas}" var="item">
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
				<div class="container d-flex justify-content-around  align-items-center">
					<div class="card w-100 mr-5">
						<div class="card-body">
							<h5 class="card-title">Endereço de entrega</h5>
							<p class="card-text">
								${pedido.endereco.logradouro}, ${pedido.endereco.numero} <br>
								Bairro: ${pedido.endereco.bairro} <br>
								CEP: ${pedido.endereco.cep} <br>
								<c:if test="${not pedido.endereco.complemento.trim().isEmpty()}">
									Complemento: ${pedido.endereco.complemento}
								</c:if>
							</p>
						</div>
					</div>
					<div class="card w-100">
						<div class="card-body ">
							<h5 class="card-title">Valor total: <fmt:formatNumber value="${pedido.pagamento.valor}" type="currency" currencyCode="BRL"/></h5>
							<h5 class="card-title">Forma de pagamento: ${pedido.pagamento.formaDePagamento.value}</h5>
							<c:if test="${pedido.pagamento.formaDePagamento == 'DINHEIRO' }">
								<h5 class="card-title">Valor para trocar: <fmt:formatNumber value="${pedido.pagamento.valorAReceber}" type="currency" currencyCode="BRL"/></h5>
								<h5 class="card-title">Troco: <fmt:formatNumber value="${pedido.pagamento.troco}" type="currency" currencyCode="BRL"/></h5>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
</tags:pageTemplate>
  