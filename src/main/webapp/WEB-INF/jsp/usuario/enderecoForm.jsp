<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${intention == 'CONFIRM'}">
	<c:set value="Confirme as informações" var="title"/>
</c:if>
<c:if test="${intention == 'EDIT'}">
	<c:set value="Editar informações" var="title"/>
</c:if>
<c:if test="${intention == 'CREATE'}">
	<c:set value="Cadastrar informações" var="title"/>
</c:if>
<tags:pageTemplate title="${title }" css="home.css">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		
		<c:if test="${intention == 'CONFIRM'}">
			<h1>Confirme as informações abaixo:</h1>
		</c:if>
		<c:if test="${intention == 'EDIT'}">
			<h1>Editar informações:</h1>
		</c:if>
		<c:if test="${intention == 'CREATE'}">
			<h1>Cadastrar informações:</h1>
		</c:if>
		
		
		<form:form cssClass="form masks-form" modelAttribute="enderecoEContato" action="${contextPath}/usuarios/info/endereco-telefone?redirectAfter=${redirectAfter}&intention=${intention}" method="POST">
			<form:hidden path="endereco.id"/>
			<div class="d-flex justify-content-around">
				<div class="form-endereco w-100 p-3 mr-1">
					<h2>Endereço de entrega</h2>
					<div class="form-group">
						<label>CEP: </label>
						<form:input type="text" cssClass="form-control masked mask-cep" path="endereco.cep"/>
						<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.cep"></form:errors>
					</div>
					<div class="form-group">
						<label>Estado: </label>
						<form:select cssClass="form-control input-estado" path="endereco.estado">
							<form:options items="${tipos}"/>
						</form:select>
						<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.estado"></form:errors>
					</div>
					<div class="form-group">
						<label>Cidade: </label>
						<form:input type="text" cssClass="form-control input-cidade" path="endereco.cidade"/>
						<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.cidade"></form:errors>
					</div>
					<div class="form-group">
						<label>Bairro: </label>
						<form:input type="text" cssClass="form-control input-bairro" path="endereco.bairro"/>
						<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.bairro"></form:errors>
					</div>
					<div class="form-group">
						<label>Logradouro: </label>
						<form:input type="text" cssClass="form-control input-logradouro" path="endereco.logradouro"/>
						<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.logradouro"></form:errors>
					</div>
					<div class="form-group">
						<label>Número: </label>
						<form:input type="text" cssClass="form-control input-numero" path="endereco.numero"/>
						<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.numero"></form:errors>
					</div>
					<div class="form-group">
						<label>Complemento: </label>
						<form:input type="text" cssClass="form-control input-complemento" path="endereco.complemento"/>
						<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="endereco.complemento"></form:errors>
					</div>
				</div>
				<div class="form-contato w-100 p-3 ml-1">
					<h2>Telefone para contato</h2>
					<div class="form-group">
						<label>Telefone fixo: </label>
						<form:input type="text" cssClass="form-control masked mask-tel" path="telefone"/>
						<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="telefone"></form:errors>
					</div>
					<div class="form-group">
						<label>Telefone celular: </label>
						<form:input type="text" cssClass="form-control masked mask-cel" path="celular"/>
						<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="celular"></form:errors>
					</div>
				</div>
			</div>
			<div class= "text-center">
				<form:button type="submit" class="btn btn-danger">Confirmar</form:button>
			</div>
		</form:form>
	</div>
	<script type="text/javascript" src="${pathJs}vanilla-masker.js"></script>
	<script type="text/javascript" src="${pathJs}apply-masks.js"></script>
	<script type="text/javascript" src="${pathJs}usuario/enderecoForm.js"></script>
	<script type="text/javascript" src="${pathJs}usuario/telefoneForm.js"></script>
	<script type="text/javascript" src="${pathJs}usuario/unmask-form.js"></script>
</tags:pageTemplate>
  