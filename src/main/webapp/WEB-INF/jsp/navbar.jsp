<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set value="${pageContext.request.contextPath}" var="contextPath" scope="application"/>
<c:url value="${contextPath}/resources/imagens/" var="linkImagens"></c:url>
<header>
	<nav class = "navbar navbar-expand-lg main-navbar" style="font-size: 1.5em;">
		<a class="navbar-brand mr-5" href="#">
			<img alt="pizza" src="${linkImagens }favicon.png" width="70" height="70">
		</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    		<span class="navbar-toggler-icon"></span>
  		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class = "navbar-nav">
				<li class= "nav-item">
					<a class = "nav-link text-light navbar-link" href='<c:url value="/"/>'>Home</a>
				</li>
				<li class= "nav-item">
					<a class = "nav-link text-light navbar-link" href='<c:url value="/sabor?tipo=SALGADA"/>'>Sabores</a>
				</li>
				<li class= "nav-item">
					<a class = "nav-link text-light navbar-link" href='<c:url value="/pizza"/>'>Pizzas</a>
				</li>
				<li class= "nav-item">
					<a class = "nav-link text-light navbar-link" href='<c:url value="/pedido/fazerPedido"/>'>Fazer pedido</a>
				</li>
				
				<sec:authorize access="hasRole('ADMIN')">
					<li class= "nav-item dropdown">
					 	<button style = "border: none;" class="btn-danger nav-link text-light navbar-link dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	    					Opções administrativas
	  					</button>
	  					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
	  						<a class="dropdown-item p-2"  href='<c:url value="/usuarios"/>'>Usuários</a>
	  						<a class="dropdown-item p-2" href='<c:url value="/bebida/lista"/>'>Bebidas</a>
					    	<a class="dropdown-item p-2" href='<c:url value="/pedido/lista"/>'>Banco de pedidos</a>
					    	<a class="dropdown-item p-2" href='<c:url value="/pedido/recebidos"/>'>Pedidos recebidos</a>
	    					<a class="dropdown-item p-2" href='<c:url value="/pedido/preparando"/>'>Pedidos em preparo</a>
	    					<a class="dropdown-item p-2" href='<c:url value="/pedido/enviados"/>'>Pedidos enviados</a>
	  					</div>
					
					</li>
				</sec:authorize>
			</ul>
			<ul class="navbar-nav ml-auto">
				<li class= "nav-item">
					<a class = "nav-link text-light navbar-link" href='<c:url value="/pedido/carrinho"/>'>Carrinho</a>
				</li>
				<sec:authorize access="!isAuthenticated()">		
					<li class= "nav-item">
						<a class = "nav-link text-light navbar-link" href='<c:url value="/login"/>'>Login</a>
					</li>
					<li class= "nav-item">
						<a class = "nav-link text-light navbar-link" href='<c:url value="/usuarios/cadastro"/>'>Criar conta</a>
					</li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class= "nav-item">
						<a class = "nav-link text-light navbar-link" href='${contextPath}/usuarios/menu/info'>
							Minha Conta
						</a>
					</li>		
					<li class= "nav-item">
						<a class = "nav-link text-light navbar-link" href='<c:url value="/logout"/>'>Logout</a>
					</li>
				</sec:authorize>
			</ul>
		</div>
	</nav>
</header>