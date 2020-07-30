<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tags:pageTemplate title="Apagar Bebida" css="home.css">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<h1>Tem certeza que deseja excluir a bebida abaixo?</h1>
		<div class="container text-center">
			<div class="card d-inline-block m-2" style="width: 18rem;">
				<div class="card-body">
					<h5 class="card-title">${bebida.titulo}</h5>
					<p class="card-text"><fmt:formatNumber value="${bebida.valor}" type="currency" currencyCode="BRL"/></p>					
				</div>
			</div>
			<form:form action="${ pageContext.request.contextPath}/bebida/delete/${bebida.id}" method="post">
				<div class="form-group">
					<button type="submit" class="btn btn-danger">Sim</button>
				</div>
			</form:form>
			<a class="btn btn-primary" href="${ pageContext.request.contextPath}/bebida/lista">Não</a>
		</div>
	</div>
</tags:pageTemplate>
  