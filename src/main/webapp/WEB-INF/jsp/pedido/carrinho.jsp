<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<tags:pageTemplate title="Seu pedido" css="home.css">
		<div class="container p-3 bg-light mt-3 mb-3" style="height:fit-content">
			<h1>Seu pedido</h1>
			<div class="container mt-5">
				<table class="table border rounded bg-white">
					<thead>
						<tr>
							<th scope="col">Item</th>
							<th scope="col">Quantidade</th>
							<th scope="col">Valor</th>
							<th scope="col"></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ids}" var="id">
							<c:set value="${itens.get(id)}" var="item" scope="page"/>
							<tr>
								<td>
									<h5>${item.titulo}</h5>
									<p>${item.descricao}</p>
								</td>
								<td>
									<form action="${contextPath}/pedido/item/quantidade" method="POST" enctype="multipart/form-data">
										<sec:csrfInput/>
										<input name="id" type="hidden" value="${id}"/>
										<div class="form-group">
											<input class="form-control" name="quantidade" type="number" min="0" step="1" value="${item.quantidade}"/>
										</div>
										<button class="btn btn-danger" type="submit">Atualizar Carrinho</button>
									</form>
								</td>
								<td>
									<p><fmt:formatNumber value="${item.valor}" type="currency" currencyCode="BRL"/></p>
								</td>
								<td>
									<form action="${contextPath}/pedido/item/remove" method="POST" enctype="multipart/form-data">
										<sec:csrfInput/>
										<input name="id" type="hidden" value="${id}"/>
										<button class="btn btn-danger" type="submit">Remover item</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="m-3">
				<form class="mb-3 border rounded  p-2" action="${contextPath}/pedido/addBebida" method="POST" enctype="multipart/form-data">
					<sec:csrfInput/>
					<div class="form-row">
						<div class="col">
							<select name="id" class="form-control mb-2">
								<c:forEach items="${bebidas}" var="bebida">
									<option value="${bebida.id}">${bebida.titulo}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col">
							<button class="btn btn-danger" type="submit">Adicionar bebida</button>
						</div>
					</div>
				</form>
				<a href="${contextPath}/pedido/fazerPedido" class="btn btn-danger text-light">Adicionar pizza</a>
			</div>
			
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">Valor total: <fmt:formatNumber value="${valorTotal}" type="currency" currencyCode="BRL"/></h5>

					<a href="${contexPath}/pedido/endereco" class="btn btn-danger">Confirmar pedido</a>

				</div>
			</div>
		</div>
</tags:pageTemplate>
  