<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:pageTemplate title="Cadastro" css="home.css">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<h1>Novo usuário</h1>
		
		<form:form cssClass="form" modelAttribute="novoUsuario" action="${ pageContext.request.contextPath}/usuarios/cadastro" method="post">
			<div class="form-group">
				<label>Seu nome completo: </label>
				<form:input type="text" cssClass="form-control" path="nome"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="nome"></form:errors>
			</div>
			<div class="form-group">
				<label>Nome de usuário: </label>
				<form:input type="text" cssClass="form-control" path="nomeDeUsuario"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="nomeDeUsuario"></form:errors>
			</div>
			<div class="form-group">
				<label>Email: </label>
				<form:input type="email" cssClass="form-control" path="email"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="email"></form:errors>
			</div>
			<div class="form-group">
				<label>Senha: </label>
				<form:input type="password" cssClass="form-control" path="senha"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="senha"></form:errors>
			</div>
			<div class="form-group">
				<label>Confirme sua senha: </label>
				<form:input type="password" cssClass="form-control" path="confirmacaoSenha"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="confirmacaoSenha"></form:errors>
			</div>
			<form:button type="submit" class="btn btn-danger">Cadastrar</form:button>
		</form:form>
	</div>

</tags:pageTemplate>
  