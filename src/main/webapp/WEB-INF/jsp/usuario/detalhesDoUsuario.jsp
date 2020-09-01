<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:pageTemplate title="Detalhes do Usuário" css="home.css">
	<div class="container p-3 bg-light">
		<h1>Detalhes do Usuário</h1>
		
		<div class="p-3 bg-light border rounded">
			<div class="container mt-4">
				<h2 class="mt-2">${usuario.nome}</h2>
			</div>
			<div class="container mt-5">
				<h2>Endereço de entrega</h2>
				<c:if test="${empty usuario.endereco}">
					<p>Este usuário não cadastrou um endereço ainda.</p> 
				</c:if>
				<c:if test="${not empty usuario.endereco}">
					<table class = "table table-borderless m-3 mt-4 bg-white w-75">
						<tbody>
							<tr>
								<td class="align-middle">CEP: </td>
								<td class="align-middle cep">${usuario.endereco.cep}</td>
							</tr>
							<tr>
								<td class="align-middle">Estado: </td>
								<td class="align-middle">${usuario.endereco.estado}</td>
							</tr>
							<tr>
								<td class="align-middle">Cidade: </td>
								<td class="align-middle">${usuario.endereco.cidade}</td>
							</tr>
							<tr>
								<td class="align-middle">Bairro: </td>
								<td class="align-middle">${usuario.endereco.bairro}</td>
							</tr>
							<tr>
								<td class="align-middle">Logradouro: </td>
								<td class="align-middle">${usuario.endereco.logradouro}</td>
							</tr>
							<tr>
								<td class="align-middle">Número: </td>
								<td class="align-middle">${usuario.endereco.numero}</td>
							</tr>
							<c:if test="${not empty usuario.endereco.complemento}">
								<tr>
									<td class="align-middle">Complemento: </td>
									<td class="align-middle">${usuario.endereco.complemento}</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</c:if>
				<h2 class="mt-2">Contato</h2>
				<table class="table table-borderless m-3 mt-4 bg-white w-75">
					<tbody>
						<tr>
							<td>Email:</td>
							<td>${usuario.email}</td>
						</tr>
						<c:if test="${empty usuario.telefone and empty usuario.celular}">
							<tr>
								<td>Este usuário não cadastrou nenhum número de telefone ainda.</td>
							</tr> 
						</c:if>
						<c:if test="${not empty usuario.telefone or not empty usuario.celular}">
							<c:if test="${not empty usuario.telefone}">
								<tr>
									<td class="align-middle">Telefone fixo: </td>
									<td class="align-middle telefone">${usuario.telefone}</td>
								</tr>
							</c:if>
							<c:if test="${not empty usuario.celular}">
								<tr>
									<td class="align-middle">Telefone celular: </td>
									<td class="align-middle celular">${usuario.celular}</td>
								</tr>
							</c:if>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script src="${pathJs}vanilla-masker.js" type="text/javascript"></script>
	<script src="${pathJs}apply-masks.js" type="text/javascript"></script>
</tags:pageTemplate>
  