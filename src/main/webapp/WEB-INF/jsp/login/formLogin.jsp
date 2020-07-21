<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
  
<tags:pageTemplate title="Login" css="home.css">
	<div class="container p-5 w-50 mt-5 mb-5 bg-light">
		<c:if test="${not empty usuario}">
			<div class="alert alert-success mt-1 mb-5">
				${usuario.nomeDeUsuario} foi cadastrado com sucesso!
			</div>
		</c:if>
		<form:form action="/login" method="post">
			<div class="form-group">
				<label>Nome de usuário ou Email</label>
				<input type="text" class="form-control" name="username"/>
			</div>
			<div class="form-group">
				<label>Senha</label>
				<input type="password" class="form-control" name="password"/>
			</div>
			<div>
				<button class="btn btn-primary">Login</button>
			</div>
		</form:form>
		<c:if test="${not empty status}">
			<div class="alert alert-danger mt-4 mb-1">
				Nome de usuário ou email ou senha estão incorretos.
			</div>
		</c:if>
	</div>
</tags:pageTemplate>