<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="p-3 bg-light border rounded">
	<h1 class="mb-5">Informações do usuário</h1>
	<div class="container mt-4">
		<h2>Informações de login</h2>
		<table class = "table table-borderless m-3 mt-4 bg-white w-75">
			<tbody>
				<tr>
					<td class="align-middle">Nome de usuário: </td>
					<td class="align-middle">${usuario.nomeDeUsuario}</td>
				</tr>
				<tr>
					<td class="align-middle">E-mail: </td>
					<td class="align-middle">${usuario.email}</td>
				</tr>
				
			</tbody>
		</table>
		<a class="btn btn-link" href="${contextPath}/usuarios/info/email">Alterar e-mail</a>
		<a class="btn btn-link" href="${contextPath}/usuarios/info/senha">Alterar senha</a>
	</div>
	<div class="container mt-5">
		<h2>Endereço de entrega e contato</h2>
		<c:if test="${empty usuario.endereco}">
			<p>Você não cadastrou um endereço ainda.</p> 
			<a class="btn-link" href="${contextPath}/usuarios/info/endereco-telefone?redirectAfter=%2Fusuarios%2Fmenu%2Finfo&intention=CREATE">Clique aqui para cadastrar um endereço.</a>
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
				</tbody>
			</table>
			<a class="btn btn-link" href="${contextPath}/usuarios/info/endereco-telefone?redirectAfter=%2Fusuarios%2Fmenu%2Finfo&intention=EDIT">Editar informações</a>
		</c:if>
	</div>
</div>
<script src="${pathJs}vanilla-masker.js" type="text/javascript"></script>
<script>
	var cep = document.querySelector(".cep")
	var celular = document.querySelector(".celular")
	var telefone= document.querySelector(".telefone")
	cep.innerText = VMasker.toPattern(cep.innerText, "99999-999")
	if(celular != null){		
		celular.innerText = VMasker.toPattern(celular.innerText, "(99) 99999-9999")
	}
	if(telefone != null){		
		telefone.innerText = VMasker.toPattern(telefone.innerText, "(99) 9999-9999")
	}
</script>