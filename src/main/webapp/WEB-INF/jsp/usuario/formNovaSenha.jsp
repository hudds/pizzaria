<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:pageTemplate title="Mudar senha" css="home.css">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<h1>Mudar senha</h1>
		
		<form:form modelAttribute="novaSenha" cssClass="form mt-3" action="${ pageContext.request.contextPath}/usuarios/info/senha" method="post">
			<div class="form-group">
				<label>Senha atual: </label>
				<form:input type="password" class="form-control" path="senhaAntiga"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="senhaAntiga"></form:errors>
			</div>
			<div class="form-group">
				<label>Nova senha: </label>
				<form:input type="password" class="form-control" path="senhaNova"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="senhaNova"></form:errors>
			</div>
			<div class="form-group">
				<label>Confirmar nova senha: </label>
				<form:input type="password" class="form-control" path="senhaNovaConfirmacao"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="senhaNovaConfirmacao"></form:errors>
			</div>
			<button type="submit" class="btn btn-danger">Confirmar</button>
		</form:form>
	</div>

</tags:pageTemplate>
  