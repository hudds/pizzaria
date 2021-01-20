<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<tags:pageTemplate title="Nossos sabores" css="home.css" customCss="sabores.css">
		<main class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
			<h1>Nossos sabores</h1>
			<div class="navbar navbar-expand-lg navbar-light bg-light">
			
			  <div class="collapse navbar-collapse" id="navbarSupportedContent">
			    <ul class="navbar-nav mr-auto">
			     <li class="nav-item">
			     	<sec:authorize access="hasRole('ADMIN')">	
						<a href="/sabor/cadastro/" class="btn btn-danger">Cadastrar novo sabor</a>
					</sec:authorize>
	      		</li>
			      <li class="nav-item dropdown">
			        <a class="btn btn-danger dropdown-toggle dropdown-tipo-selecionado" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">        	
			          	<c:if test="${not empty tipoSelecionado}">Exibindo somente pizzas do tipo ${tipoSelecionado}</c:if>
			          	<c:if test="${empty tipoSelecionado}">Exibir somente pizzas do tipo:</c:if>
			        </a>
			        <div class="dropdown-menu dropdown-tipos" aria-labelledby="navbarDropdown">
			
			        	<button class="dropdown-item dropdown-item-tipo" type = "button">TODAS</button>
				        <c:forEach items="${tipos}" var="tipo">
				        	<button class="dropdown-item dropdown-item-tipo" type="button">${tipo }</button>
			        	</c:forEach>
			        </div>
			      </li>
			    </ul>
			    <form:form class="form-inline my-2 my-lg-0 form-busca" >
			      <input class="form-control mr-sm-2 campo-busca" name="busca" autocomplete="off" value="" type="search" placeholder="Insira o nome ou a descrição aqui" aria-label="Insira o nome ou a descrição aqui">
			      <input type="hidden" value="${tipoSelecionado}" name="tipo"/>
			      <button class="btn btn-outline-success my-2 my-sm-0 btn-busca" type="button" onclick="acaoBuscar()">Buscar</button>
			    </form:form>
			  </div>
			</div>
			
		<c:if test="${statusDelete == 'success'}">
			<div class="alert alert-success mt-1 mb-5">
				Sabor foi deletado com sucesso!
			</div>
		</c:if>
		
		
		<c:if test="${errorType == 'dataIntegrity'}">
			<div class="alert alert-danger mt-1 mb-5">
				Não foi posível deletar este sabor porque já existe pedidos feitos com ele.
			</div>
		</c:if>
		
		<div class="alert alert-secondary mt-1 mb-5 alert-busca" style="display:none;">
				
		</div>
		
		<div class="container container-sabores text-center" id = "lista-sabores">
			<template id="template-card-sabor">
				<div class="card m-2 card-sabor">
					<div class="card-body" >
						<sec:authorize access="hasRole('ADMIN')">
							<button class="btn-ocultar-sabor" type="button"><img title="Tornar invisível" alt="Olho" src="${pathImagens}visivel.png"/></button>
							<button class="btn-mostrar-sabor" type="button"><img title="Tornar visível" alt="Olho com um risco" src="${pathImagens}oculto.png"/></button>
						</sec:authorize>
						<h5 class="card-title card-sabor-titulo"></h5>
						<p class="card-text card-sabor-descricao"></p>
						<a href="/pedido/fazerPedido" class="btn btn-danger mb-1 card-sabor-btn-fazer-pedido">Fazer pedido.</a>
						<sec:authorize access="hasRole('ADMIN')">
							<div>
								<a href="" class="btn btn-danger mb-1 card-sabor-btn-editar">
									Editar Sabor
								</a>
							</div>
							<div>
								<button type="button" class="btn btn-danger btn-delete-sabor" data-toggle="modal"
									data-target="#modalConfirmarDelete">
									Deletar sabor
								</button>
							</div>
						</sec:authorize>
					</div>
				</div>
			</template>
		</div>
	</main>
	<sec:authorize access="hasRole('ADMIN')">
		<div class="modal fade" id="modalConfirmarDelete" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<dialog class="modal-content">
					<header class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Tem certeza que deseja deletar o sabor <span class="modal-nome-sabor"></span>?</h5>
					    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					    	<span aria-hidden="true">&times;</span>
						</button>
					</header>
					<footer class="modal-footer">
						<form:form class="form-deletar-sabor" action="" method="POST">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Não</button>
							<button type="submit" class="btn btn-primary">Sim</button>
						</form:form>
					</footer>
				</dialog>
			</div>
		</div>
	</sec:authorize>
	<script src="${pageContext.request.contextPath}/resources/js/Ajax.js"></script>
	<script>var ajax = new Ajax("${pageContext.request.contextPath}", "${_csrf.token}", "${_csrf.headerName}")</script>
	<script src="${pageContext.request.contextPath}/resources/js/sabor/buscaSabores.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/sabor/listaSabores.js"></script>
</tags:pageTemplate>
  