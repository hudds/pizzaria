<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:pageTemplate title="Endereço de entrega" css="home.css">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<h1>Endereço de entrega</h1>
		
		<form:form cssClass="form" modelAttribute="enderecoPedido" action="${contextPath}/pedido/endereco" method="POST">
			<div class="form-group">
				<form:hidden path="id"/>
				<label>Estado: </label>
				<form:select cssClass="form-control" path="estado">
					<form:options items="${tipos}"/>
				</form:select>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="estado"></form:errors>
			</div>
			<div class="form-group">
				<label>Cidade: </label>
				<form:input type="text" cssClass="form-control" path="cidade"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="cidade"></form:errors>
			</div>
			<div class="form-group">
				<label>CEP: </label>
				<form:input type="text" cssClass="form-control" path="cep"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="cep"></form:errors>
			</div>
			<div class="form-group">
				<label>Bairro: </label>
				<form:input type="text" cssClass="form-control" path="bairro"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="bairro"></form:errors>
			</div>
			<div class="form-group">
				<label>Logradouro: </label>
				<form:input type="text" cssClass="form-control" path="logradouro"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="logradouro"></form:errors>
			</div>
			<div class="form-group">
				<label>Número: </label>
				<form:input type="number" step="1" min="1" cssClass="form-control" path="numero"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="numero"></form:errors>
			</div>
			<div class="form-group">
				<label>Complemento: </label>
				<form:input type="text" step="1" min="1" cssClass="form-control" path="complemento"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="complemento"></form:errors>
			</div>
			<form:button type="submit" class="btn btn-danger">Confirmar</form:button>
		</form:form>
	</div>

</tags:pageTemplate>
  