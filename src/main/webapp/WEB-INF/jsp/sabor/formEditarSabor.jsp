<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:pageTemplate title="Editar Produto" css="home.css">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<h1>Editar Sabor</h1>
		
		<c:if test="${not empty status_cadastro}">
			<div class="alert alert-success mt-1 mb-5">
				Sabor foi alterado com sucesso!
			</div>
		</c:if>
		
		<form:form cssClass="form" modelAttribute="sabor" action="${ pageContext.request.contextPath}/sabor/edit" method="post">
			<div class="form-group">
				<label>Título: </label>
				<form:input type="text" cssClass="form-control" path="titulo" value="${sabor.titulo }"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="titulo"></form:errors>
			</div>
			<div class="form-group">
				<label>Descrição: </label>
				<form:textarea type="text" cssClass="form-control" path="descricao" value="${sabor.descricao }"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="descricao"></form:errors>
			</div>
			<div class="form-group">
				<label>Tipo: </label>
				<form:select cssClass="form-control" path="tipo">
					<form:options items="${tipos}"/>
				</form:select>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="tipo"></form:errors>
			</div>
			<form:hidden path="id" value="${sabor.id}"/>
			<form:button type="submit" class="btn btn-danger">Gravar Mudanças</form:button>
		</form:form>
	</div>

</tags:pageTemplate>
  