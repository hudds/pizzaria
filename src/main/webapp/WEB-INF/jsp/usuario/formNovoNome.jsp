<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:pageTemplate title="Mudar nome" css="home.css">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<h1>Mudar nome</h1>
		
		<form class="form mt-3" action="${ pageContext.request.contextPath}/usuarios/info/nome" method="post">
			<sec:csrfInput/>
			<div class="form-group">
				<label>Novo nome: </label>
				<input type="text" class="form-control" name="novoNome"/>
				<form:errors cssClass="p-1 mt-2 rounded-lg alert-danger" element="div" path="novoNome"></form:errors>
			</div>
			<button type="submit" class="btn btn-danger">Confirmar</button>
		</form>
	</div>

</tags:pageTemplate>
  