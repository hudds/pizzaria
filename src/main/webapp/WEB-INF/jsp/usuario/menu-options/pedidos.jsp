<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="p-3 bg-light border rounded">
	<c:if test="${empty pedidos}">
		<h1>Você não fez nenhum pedido ainda.</h1>
	</c:if>
	<c:if test="${not empty pedidos }">
		<h1>Seus pedidos</h1>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">Número do pedido</th>
					<th scope="col">Data do pedido</th>
					<th scope="col">Valor</th>
					<th scope="col">Estado do pedido</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pedidos}" var="pedido">
					<tr>
						<td>${pedido.id}</td>
						<fmt:parseDate value="${pedido.horaPedido}" pattern = "yyyy-MM-dd'T'HH:mm:ss" var = "dataPedido" type ="date"/>
						<td><fmt:formatDate value="${dataPedido}" pattern="dd/MM/yyyy 'às' HH'h'mm"/></td>
						<td><fmt:formatNumber value="${pedido.pagamento.valor}" type="currency" currencyCode="BRL"/></td>
						<td>${pedido.estado.value}</td>
						<td><a class="btn-link" href="${contextPath}/pedido/detalhes/${pedido.id}">Mais detalhes do pedido</a></td>
					</tr>				
				</c:forEach>	
			</tbody>
		</table>
	</c:if>
</div>
