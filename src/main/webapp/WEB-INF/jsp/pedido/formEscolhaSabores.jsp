<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<tags:pageTemplate title="Escolha os sabores" css="home.css">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<h1>Escolha até 4 sabores</h1>
		<div class="container mt-5 mb-3 border p-3 rounded bg-white">
			<h3>Pizza selecionada:</h3>
				<div class="card m-1" style="width: 18rem;">
					<div class="card-body text-center">
						<h5 class="card-title">${pizzaSelecionada.titulo}</h5>
						<h5 class="card-title">${pizzaSelecionada.tipoSabor}</h5>
						<h5 class="card-title"><fmt:formatNumber value="${pizzaSelecionada.preco}" type="currency" /></h5>
						<a class="btn btn-danger mb-1" href="${pageContext.request.contextPath}/pedido/fazerPedido">Escolher outra Pizza</a>
					</div>
				</div>
			<h3>Sabores selecionados:</h3>
			<ul id="lista-sabores-selecionados" class="list-group list-group-horizontal">
				<template id="template-sabor-selecionado">
					<li class="list-group-item sabor-selecionado">
						<p class="sabor-selecionado-titulo"></p>
						<button class="btn btn-remover-sabor-selecionado" type="button">Remover</button>
					</li>
				</template>
			</ul>
			<form:form class="form-confirmar-pedido-pizza" modelAttribute="novoPedidoPizza" method="POST" action="${pageContext.request.contextPath}/pedido/addPizza">
				<form:hidden path="idPizza" value="${pizzaSelecionada.id}"/>
				<form:hidden class="campo-sabores" path="idsSabores"/>
				<button id="btn-confirmar-pedido-pizza" type="submit" class="btn btn-danger">Confirmar</button>
			</form:form>
		</div>
		<div class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
		    	<ul class="navbar-nav mr-auto">
		      		
		    	</ul>
			    <form:form class="form-inline my-2 my-lg-0 form-busca" >
					<input class="form-control mr-sm-2 campo-busca" name="busca" autocomplete="off" value="${busca}" type="search" placeholder="Insira o nome ou a descrição aqui" aria-label="Insira o nome ou a descrição aqui">
					<input id="input-tipo-selecionado" type="hidden" value="${pizzaSelecionada.tipoSabor}" name="tipo"/>
					<button class="btn btn-outline-success my-2 my-sm-0 btn-busca" type="button" onclick="acaoBuscar()">Buscar</button>
			    </form:form>
		  	</div>
		</div>
		<c:if test="${not empty statusDelete}">
			<div class="alert alert-success mt-1 mb-5">
				Sabor foi deletado com sucesso!
			</div>
		</c:if>
		<div class="alert alert-secondary mt-1 mb-5 alert-busca" style="display:none;">
		</div>
		<div class="container text-center" id = "lista-sabores">
			<template id="template-card-sabor">
					<div class="card d-inline-block m-2" style="width: 18rem;">
						<div class="card-body" >
							<h5 class="card-title card-sabor-titulo"></h5>
							<p class="card-text card-sabor-descricao"></p>
							<button class="btn btn-danger mb-1 card-sabor-btn-selecionar">Selecionar</button>
						</div>
					</div>
			</template>
		</div>
	</div>
	<script src = "${pageContext.request.contextPath}/resources/js/pedido/formEscolhaSabores.js"></script>
</tags:pageTemplate>
  