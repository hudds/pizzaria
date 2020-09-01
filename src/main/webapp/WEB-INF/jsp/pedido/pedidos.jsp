<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tags:pageTemplate title="Pedidos" css="home.css">
	<div class="container p-3 bg-light h-auto">
		<h1>Pedidos</h1>
		<div class="border m-3 p-3">
			<h2>Buscar pedido</h2>	
			<form:form modelAttribute="buscaPedido" cssClass="form" action="${contextPath}/pedido/lista" method="GET">
				<div class="form-group">
					<label>Estado do pedido:</label>
					<form:select path="estadoPedido" cssClass="form-control">
						<c:forEach items="${estadosPedido}" var="estadoPedido">
							<form:option value="${estadoPedido}" label="${estadoPedido.value}"/>
						</c:forEach>
					</form:select>
				</div>
				<div class="form-group">
					<label>Entre:</label>
					<c:if test="${not empty buscaPedido.firstDate }">
						<fmt:parseDate value="${buscaPedido.firstDate}" pattern = "yyyy-MM-dd'T'HH:mm" var = "firstDateParsed" type ="date"/>
						<fmt:formatDate value="${firstDateParsed}" pattern="dd/MM/yyyy HH'h':mm" var="firstDateFormatted"/>
					</c:if>
					<form:input path="firstDate" value="${firstDateFormatted }" type="text" class="form-control date-time-input"/>
					<label>e</label>
					<c:if test="${not empty buscaPedido.secondDate }">
						<fmt:parseDate value="${buscaPedido.secondDate}" pattern = "yyyy-MM-dd'T'HH:mm" var = "secondDateParsed" type ="date"/>
						<fmt:formatDate value="${secondDateParsed}" pattern="dd/MM/yyyy HH'h':mm" var="secondDateFormatted"/>
					</c:if>
					<form:input path="secondDate" value="${ secondDateFormatted}" type="text" class="form-control date-time-input"/>
				</div>
				
				<c:if test="${not empty buscaPedido.endereco.estado}">
					<input type="hidden" id="show-endereco-form" value = "true">
				</c:if>
				<c:if test="${empty buscaPedido.endereco.estado}">
					<input type="hidden" id="show-endereco-form" value = "false">
				</c:if>
				
				<div id = "form-endereco" style="display: none;">
					<div class="form-endereco masks-form w-100 p-3 mr-1">
						<h3>Endereço</h3>
						<div class="form-group">
							<label>CEP: </label>
							<form:input type="text" cssClass="form-control masked mask-cep" path="endereco.cep"/>
							<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.cep"></form:errors>
						</div>
						<div class="form-group">
							<label>Estado: </label>
							<form:select cssClass="form-control input-estado" path="endereco.estado">
								<form:options items="${tipos}"/>
							</form:select>
							<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.estado"></form:errors>
						</div>
						<div class="form-group">
							<label>Cidade: </label>
							<form:input type="text" cssClass="form-control input-cidade" path="endereco.cidade"/>
							<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.cidade"></form:errors>
						</div>
						<div class="form-group">
							<label>Bairro: </label>
							<form:input type="text" cssClass="form-control input-bairro" path="endereco.bairro"/>
							<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.bairro"></form:errors>
						</div>
						<div class="form-group">
							<label>Logradouro: </label>
							<form:input type="text" cssClass="form-control input-logradouro" path="endereco.logradouro"/>
							<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.logradouro"></form:errors>
						</div>
						<div class="form-group">
							<label>Número: </label>
							<form:input type="text" cssClass="form-control input-numero" path="endereco.numero"/>
							<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.numero"></form:errors>
						</div>
						<div class="form-group">
							<label>Complemento: </label>
							<form:input type="text" cssClass="form-control input-complemento" path="endereco.complemento"/>
							<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.complemento"></form:errors>
						</div>
					</div>
				</div>
				<div>
					<button type="button" class="btn btn-link toggle-endereco-form" onclick="toggleEnderecoForm()">Mostrar opções de busca por endereço</button>
				</div>
				<form:button class="btn btn-danger">Buscar</form:button>
			</form:form>
		</div>

		<table class="table">
			<thead>
				<tr>
					<th scope="col">Número do pedido</th>
					<th scope="col">Data do pedido</th>
					<th scope="col">Valor</th>
					<th scope="col">Estado do pedido</th>
					<th scope="col">Cliente</th>
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
						<td>
							<a class="btn-link" href="${contextPath}/usuarios/detalhes/${pedido.cliente.id}">
								<c:if test="${not empty pedido.cliente.nome}">${pedido.cliente.nome}</c:if>
								<c:if test="${empty pedido.cliente.nome}">${pedido.cliente.nomeDeUsuario}</c:if>
							</a>
						</td>
						<td><a class="btn-link" href="${contextPath}/pedido/detalhes/${pedido.id}">Mais detalhes</a></td>
					</tr>				
				</c:forEach>	
			</tbody>
		</table>
	</div>
<script src="${pathJs}vanilla-masker.js" type="text/javascript"></script>
<script src="${pathJs}moment.min.js" type="text/javascript"></script>
<script src="${pathJs}apply-masks.js" type="text/javascript"></script>
<script src="${pathJs}usuario/enderecoForm.js" type="text/javascript"></script>
<script src="${pathJs}usuario/unmask-form.js" type="text/javascript"></script>
<script src="${pathJs}pedido/pedidos.js" type="text/javascript"></script>
</tags:pageTemplate>
  