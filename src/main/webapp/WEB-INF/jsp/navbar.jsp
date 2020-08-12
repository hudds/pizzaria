<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set value="${pageContext.request.contextPath}" var="contextPath" scope="application"/>
<c:url value="${contextPath}/resources/imagens/" var="linkImagens"></c:url>
<header>
	<nav class = "navbar navbar-expand-lg navbar-light bg-danger main-navbar" style="font-size: 1.5em;">
	<a class="navbar-brand mr-5" href="#">
		<img alt="pizza" src="${linkImagens }favicon.png" width="70" height="70">
	</a>
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
				<li class= "nav-item">
					<a class = "nav-link text-light navbar-link" href='<c:url value="/usuarios"/>'>Usuários</a>
				</li>
				<li class= "nav-item">
					<a class = "nav-link text-light navbar-link" href='<c:url value="/bebida/lista"/>'>Bebidas</a>
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
	</nav>
</header>