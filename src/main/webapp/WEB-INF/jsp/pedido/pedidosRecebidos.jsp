<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tags:pageTemplate title="Pedidos Recebidos" css="home.css">
	<form>
		<input id="change-state-to" type="hidden" value="PREPARANDO"/>
		<input id="estado-pedido" type="hidden" value="RECEBIDO"/>
	</form>
	<div class="container p-3 bg-light h-auto">
		<h2>Pedidos Recebidos</h2>
		<ul id="lista-pedidos-recebidos" class="list-group">
			
		</ul>
		<template id="template-pedido">
			<li class="list-group-item">
				<div class="border rounded p-3">
					<h5 class="card-title numero-pedido"></h5>
					<h6 class="container-data-hora-pedido">Pedido feito em <span class="data-pedido"></span> às <span class="hora-pedido"></span></h6>
					<table class="table">
						<thead>
							<tr>
								<th scope="col">Item</th>
								<th scope="col">Quantidade</th>
							</tr>
						</thead>
						<tbody class="tabela-itens">
						</tbody>
					</table>
					<div class="container">
						<div class="row">
							<div class="col-sm border p-2 rounded m-1">
								<h6>Informações do Pagamento</h6>
								<p>Forma de pagamento: <span class="forma-pagamento"></span></p>
								<p>Valor do pedido: <span class="valor-pedido"></span></p>
								<p class="d-none container-valor-receber">Valor a receber: <span class="valor-receber"></span></p>
								<p class="d-none container-troco">Valor de troco: <span class="valor-troco"></span></p>
							</div>
							<div class="col-sm border p-2 rounded m-1">
								<h6>Informações do cliente</h6>
								<p>Nome: <span class="nome-cliente"></span></p>
								<p>E-mail: <span class="email-cliente"></span></p>
								<p class="d-none container-telefone-cliente">Telefone: <span class="telefone-cliente"></span></p>
								<p class="d-none container-celular-cliente">Celular: <span class="celular-cliente"></span></p>
							</div>
							<div class="col-sm border p-2 rounded m-1">
								<h6>Endereço de entrega</h6>
								<p><span class="logradouro"></span>, <span class="numero-endereco"></span></p>
								<p class="container-complemento-endereco d-none">Complemento: <span class="complemento-endereco"></span></p>
								<p>Bairro: <span class="bairro"></span></p>
								<p>Cidade: <span class="cidade"></span>, <span class="estado-endereco"></span></p>
								<p>CEP: <span class="cep"></span></p>
							</div>
						</div>
					</div>
					<button class="btn btn-danger button-muda-estado">Mudar para preparando</button>
				</div>
			</li>
		</template>
		<template id="template-item-pedido">
			<tr>
				<td class="titulo"></td>
				<td class="quantidade"></td>
			</tr>
		</template>
		
	</div>
<script src="${pathJs}vanilla-masker.js" type="text/javascript"></script>
<script src="${pathJs}pedido/muda-estado.js" type="text/javascript"></script>
<script src="${pathJs}pedido/busca-pedidos.js" type="text/javascript"></script>
<script type="text/javascript">
	mantemAtualizado("REGISTRADO")
</script>
</tags:pageTemplate>
  