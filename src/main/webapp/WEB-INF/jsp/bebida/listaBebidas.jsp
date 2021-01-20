<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<tags:pageTemplate title="Bebidas Cadastradas" css="home.css">
		<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
			<h1>Bebidas Cadastradas</h1>
			
			<sec:authorize access="hasRole('ADMIN')">
				<div class="m-3">
					<a href="${contextPath}/bebida/cadastro" class="btn btn-danger text-white">Cadastrar nova bebida</a>
				</div>
			</sec:authorize>
			
			<c:if test="${bebida_delete_status == 'success'}">
				<div class="alert alert-success" role="alert">
					Bebida foi deletada!
				</div>
			</c:if>
			
			<c:if test="${bebida_cadastro_status == 'success'}">
				<div class="alert alert-success" role="alert">
					Bebida foi cadastrada!
				</div>
			</c:if>
			
			<table class="table">
				<thead>
					<tr>
						<th scope="col">Título</th>
						<th scope="col">Valor</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${bebidas}" var="bebida">
						<tr>
							<td>${bebida.titulo}</td>
							<td><fmt:formatNumber type="currency" value="${bebida.valor}" currencyCode="BRL" /></td>
							<sec:authorize access="hasRole('ADMIN')">
								<td>
									<a href="${contextPath}/bebida/edit/${bebida.id}" class="btn btn-danger text-white">Editar</a>
								</td>
								<td>
									<a href="${contextPath}/bebida/delete/${bebida.id}" class="btn btn-danger text-white">Deletar</a>
								</td>
							</sec:authorize>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
	<script src="${pageContext.request.contextPath}/resources/js/sabor/listaSabores.js"></script>
</tags:pageTemplate>
  