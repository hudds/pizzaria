<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<tags:pageTemplate title="Bebidas Cadastradas" css="home.css">
		<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
			<h1>Bebidas Cadastradas</h1>
			
			<sec:authorize access="hasRole('ADMIN')">
				<div class="m-3">
					<a href="${contextPath}/bebida/cadastro" class="btn btn-danger text-white">Cadastrar nova bebida</a>
				</div>
			</sec:authorize>
			
			<c:if test="${bebida_delete_status == 'success'}">
				<div class="alert alert-success" role="alert">
					Bebida foi deletada!
				</div>
			</c:if>
			
			<c:if test="${bebida_cadastro_status == 'success'}">
				<div class="alert alert-success" role="alert">
					Bebida foi cadastrada!
				</div>
			</c:if>
			<c:if test="${bebida_edit_status == 'success'}">
				<div class="alert alert-success" role="alert">
					Bebida foi editada!
				</div>
			</c:if>
			<div id="messageContainer"></div>
			<section id="bebidas"></section>
		</div>
	<div id="containerModalConfirmDelete">
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/Paths.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/Ajax.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/helper/Binder.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/service/ProxyFactory.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/model/Message.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/view/View.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/view/MessageView.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/shared/view/ModalConfirmDeleteView.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/sabor/listaSabores.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bebida/BebidaService.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bebida/BebidaView.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bebida/BebidaController.js"></script>
	<sec:authorize access="hasRole('ADMIN')" var="isAdmin"></sec:authorize>
	<script>
		var bebidaController = new BebidaController("${pageContext.request.contextPath}", "${_csrf.token}", "${_csrf.headerName}", ${isAdmin});
	</script>
</tags:pageTemplate>
  