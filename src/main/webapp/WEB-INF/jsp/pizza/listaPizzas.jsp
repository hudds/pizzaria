<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<tags:pageTemplate title="Nossas pizzas" css="home.css">
	<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
		<h1>Nossas Pizzas</h1>
		<sec:authorize access="hasRole('ADMIN')">	
			<a href="/pizza/cadastro/" class="btn btn-danger m-2">Cadastrar nova pizza.</a>
		</sec:authorize>
		<c:if test="${not empty statusCadastroPizza}">
			<div class="alert alert-success mt-1 mb-5">
				Informações foram cadastradas com sucesso!
			</div>
		</c:if>
		<div class="container text-center">
			<c:forEach items="${pizzas}" var="pizza">
					<div class="card d-inline-block m-2" style="width: 18rem;">
						<div class="card-body">
							<h5 class="card-title">${pizza.titulo}</h5>
							<h5 class="card-title">${pizza.tipoSabor}</h5>
							<p class="card-text">${sabor.descricao}</p>
							<h5 class="card-title"><fmt:formatNumber value="${pizza.preco}" type="currency" /></h5>
							<a href="#" class="btn btn-danger mb-1">Fazer pedido.</a>
							<sec:authorize access="hasRole('ADMIN')">
								<div>
									<a href="/pizza/edit/${pizza.id}" class="btn btn-danger mb-1">Editar Pizza</a>
								</div>
								<div>
									<a href="/pizza/delete/${pizza.id}" class="btn btn-danger">Deletar Pizza</a>
								</div>
							</sec:authorize>
						</div>
					</div>
			</c:forEach>
		</div>
	</div>
</tags:pageTemplate>
  