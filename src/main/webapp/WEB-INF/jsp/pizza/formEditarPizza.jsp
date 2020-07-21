<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:pageTemplate title="Nova Pizza" css="home.css">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<h1>Editar informações da pizza.</h1>
		
		<c:if test="${not empty statusEditPizza}">
			<div class="alert alert-success mt-1 mb-5">
				Informações foram alteradas com sucesso!
			</div>
		</c:if>
		
		<form:form cssClass="form" modelAttribute="pizzaEdit" action="${ pageContext.request.contextPath}/pizza/edit/" method="post">
			<div class="form-group">
				<label>Título: </label>
				<form:input type="text" cssClass="form-control" path="titulo" value="${pizza.titulo }"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="titulo"></form:errors>
			</div>
			<div class="form-group">
				<label>Descrição: </label>
				<form:textarea type="text" cssClass="form-control" path="descricao" value="${pizza.descricao}"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="descricao"></form:errors>
			</div>
			<div class="form-group">
				<label>Tipo: </label>
				<form:select cssClass="form-control" path="tipoSabor" >
					<form:options items="${tipos}"/>
				</form:select>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="tipoSabor"></form:errors>
			</div>
			<div class="form-group">
				<label>Preço: </label>
				<form:input type="number" step="0.01" min="0" cssClass="form-control" path="preco" value="${pizza.preco }"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="preco"></form:errors>
			</div>
			<form:hidden value="${pizza.id}" path="id"/>
			<form:button type="submit" class="btn btn-danger">Editar</form:button>
		</form:form>
		<a class="btn btn-danger mt-2" href="${ pageContext.request.contextPath}/pizza">Cancelar</a>
	</div>

</tags:pageTemplate>
  