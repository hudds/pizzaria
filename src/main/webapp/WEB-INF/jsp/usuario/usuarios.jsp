<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:pageTemplate title="Permissões" css="home.css">
	<div class="container p-3 bg-light h-100">
		<h1>Usuários e suas permissões.</h1>
		
		<table class="table">
			<thead>
				<tr>
					<th>Nome</th>
					<th>Nome de usuário</th>
					<th>Email</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${usuarios}" var="usuario">
				<tr>
					<td>
						${usuario.nome }
					</td>
					<td>
						${usuario.nomeDeUsuario }
					</td>
					<td>
						${usuario.email }
					</td>
					<sec:authorize access="hasRole('ADMIN')">
						<td>
							<a href="${pageContext.request.contextPath}/usuarios/roles/${usuario.id}">
								<button>Editar pertmissões de usuário</button>
							</a>
						</td>
					</sec:authorize>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		 
	</div>

</tags:pageTemplate>
  