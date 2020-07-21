<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<tags:pageTemplate title="Nossas pizzas" css="home.css">
		<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
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
			        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
			        	<button class="dropdown-item dropdown-item-tipo" type = "button" >TODAS</button>
				        <c:forEach items="${tipos}" var="tipo">
				        	<button class="dropdown-item dropdown-item-tipo" type="button">${tipo }</button>
			        	</c:forEach>
			        </div>
			      </li>
			    </ul>
			    <form:form class="form-inline my-2 my-lg-0 form-busca" >
			      <input class="form-control mr-sm-2 campo-busca" name="busca" autocomplete="off" value="${busca}" type="search" placeholder="Insira o nome ou a descrição aqui" aria-label="Insira o nome ou a descrição aqui">
			      <input type="hidden" value="${tipoSelecionado}" name="tipo"/>
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
							<a href="#" class="btn btn-danger mb-1 card-sabor-btn-fazer-pedido">Fazer pedido.</a>
							<sec:authorize access="hasRole('ADMIN')">
								<a href="" class="btn btn-danger mb-1 card-sabor-btn-editar">Editar Sabor</a>
								<a href=""  class="btn btn-danger mb-1 card-sabor-btn-deletar">Deletar Sabor</a>
							</sec:authorize>
						</div>
					</div>
			</template>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/sabor/listaSabores.js"></script>
</tags:pageTemplate>
  