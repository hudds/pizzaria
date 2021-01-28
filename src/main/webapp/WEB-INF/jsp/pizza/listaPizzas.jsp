<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<tags:pageTemplate title="Nossas pizzas" css="home.css">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<h1>Nossas Pizzas</h1>
		<sec:authorize access="hasRole('ADMIN')">	
			<a href="/pizza/cadastro/" class="btn btn-danger m-2">Cadastrar nova pizza.</a>
		</sec:authorize>
		<c:if test="${not empty statusCadastroPizza}">
			<div class="alert alert-success mt-1 mb-5">
				Informações foram cadastradas com sucesso!
			</div>
		</c:if>
		<div id="messageContainer">
		</div>
		<section class="container text-center">
			<div id="pizzas">
				
			</div>
		</section>
	</div>
	
	<div id="containerModalConfirmDelete">
	</div>
	
	<script src="${pageContext.request.contextPath}/resources/js/shared/Ajax.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/Paths.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/helper/Binder.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/service/ProxyFactory.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/model/Message.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/view/View.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/view/MessageView.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/view/ModalConfirmDeleteView.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/pizza/PizzaView.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/pizza/PizzaService.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/pizza/PizzaController.js"></script>
	<sec:authorize access="hasRole('ADMIN')" var="isAdmin"></sec:authorize>
	<script>var pizzaController = new PizzaController("${pageContext.request.contextPath}", "${_csrf.token}", "${_csrf.headerName}", ${isAdmin})</script>
</tags:pageTemplate>
  