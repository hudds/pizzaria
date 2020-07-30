<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:pageTemplate title="Cadastro Bebida" css="home.css">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<h1>Nova Bebida</h1>
		
		<form:form cssClass="form" modelAttribute="novaBebida" action="${ pageContext.request.contextPath}/bebida/cadastro" method="post">
			<div class="form-group">
				<label>Título: </label>
				<form:input type="text" cssClass="form-control" path="titulo"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="titulo"></form:errors>
			</div>
			<div class="form-group">
				<label>Preço: </label>
				<form:input type="number" step="0.01" min="0" cssClass="form-control" path="valor"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="valor"></form:errors>
			</div>
			<form:button type="submit" class="btn btn-danger">Cadastrar</form:button>
		</form:form>
	</div>

</tags:pageTemplate>
  