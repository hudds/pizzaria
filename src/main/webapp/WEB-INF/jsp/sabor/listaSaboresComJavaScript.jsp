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
	      		
			      <li id="dropdownTiposSabores" class="nav-item dropdown">
			        <a class="btn btn-danger dropdown-toggle dropdown-tipo-selecionado" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">        	
			          	<c:if test="${not empty tipoSelecionado}">Exibindo somente pizzas do tipo ${tipoSelecionado}</c:if>
			          	<c:if test="${empty tipoSelecionado}">Exibir somente pizzas do tipo:</c:if>
			        </a>
			        <div class="dropdown-menu dropdown-tipos" aria-labelledby="navbarDropdown">
			
			        	<button class="dropdown-item dropdown-item-tipo" type = "button" onclick="saborController.selectTipo('')">TODAS</button>
				        <c:forEach items="${tipos}" var="tipo">
				        	<button onclick="saborController.selectTipo('${tipo}')" class="dropdown-item dropdown-item-tipo" type="button" value="">${tipo }</button>
			        	</c:forEach>
			        </div>
			      </li>
			    </ul>
			    <form class="form-inline my-2 my-lg-0 form-busca" id="formBusca" onsubmit="saborController.fetchSabores(event)">
			      	<input id="inputSearchSabor" class="form-control mr-sm-2 campo-busca" name="busca" autocomplete="off" value="" type="search" placeholder="Insira o nome ou a descrição aqui" aria-label="Insira o nome ou a descrição aqui">
			      	<button class="btn btn-outline-success my-2 my-sm-0 btn-busca" type="button" onclick="saborController.fetchSabores()">Buscar</button>
			    </form>
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
		
		<div id="searchMessageContainer">
		</div>
		
		<div id="messageContainer">
		</div>
		
		<div class="container container-sabores text-center" id = "sabores">
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
	<div id="containerModalConfirmDelete"></div>
	<sec:authorize access="hasRole('ADMIN')" var="isAdmin"></sec:authorize>
	<script src="${pageContext.request.contextPath}/resources/js/shared/Ajax.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/Paths.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/helper/Binder.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/service/ProxyFactory.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/model/Message.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/view/View.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/view/MessageView.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/view/ModalConfirmDeleteView.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/sabor/DropdownTiposSaboresView.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/sabor/SaborView.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/sabor/SaborService.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/sabor/SaborController.js"></script>
	<script>var saborController = new SaborController("${pageContext.request.contextPath}", "${_csrf.token}", "${_csrf.headerName}", ${isAdmin})</script>

</tags:pageTemplate>
  