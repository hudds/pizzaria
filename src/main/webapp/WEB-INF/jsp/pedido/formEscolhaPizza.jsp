<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tags:pageTemplate title="Nossas pizzas">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<h1>Escolha sua Pizza!</h1>	
		<div class="container text-center">
			<c:forEach items="${pizzas }" var="pizza">
					<div class="card d-inline-block m-2" style="width: 18rem;">
						<div class="card-body">
							<h5 class="card-title">${pizza.titulo}</h5>
							<h5 class="card-title">${pizza.tipoSabor}</h5>
							<p class="card-text">${sabor.descricao}</p>
							<h5 class="card-title"><fmt:formatNumber value="${pizza.preco}" type="currency" /></h5>
							<form:form action="/pedido/fazerPedido" method="GET">
								<input name="sabores" type="hidden" value="${idsSaboresSelecionados}">
								<input name="pizza" type="hidden" value="${pizza.id}">
								<button class="btn btn-danger mb-1" type="submit">Selecionar</button>
							</form:form>
						</div>
					</div>
			</c:forEach>
		</div>
	</div>
	<script src = "${pageContext.request.contextPath}/resources/js/pedido/formEscolhaPizza.js" ></script>
</tags:pageTemplate>
  