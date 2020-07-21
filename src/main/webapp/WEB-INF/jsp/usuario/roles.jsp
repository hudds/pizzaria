<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:pageTemplate title="Permissões" css="home.css">
	<div class="container p-3 bg-light h-100">
		<h1>Editar permissões de ${usuario.nomeDeUsuario}</h1>
		
		<form:form modelAttribute="usuario" cssClass="form container text-center w-50" action='${pageContext.request.contextPath}/usuarios/roles' method="post">
			<div class="form-group">
				<form:checkboxes cssClass="form-check-input" element ="div class='form-control'" items="${roles}" path="roles"/>
				<form:input type="hidden" value="${usuario.id }" path="id"/>
			</div>
			<form:button>Confirmar</form:button>
		</form:form>
		 
	</div>

</tags:pageTemplate>
  