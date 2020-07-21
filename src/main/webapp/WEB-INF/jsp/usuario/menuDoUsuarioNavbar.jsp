<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:pageTemplate title="Menu do Usuário" css="home.css">
	<div class="container p-3 bg-light h-100">
		<h1>Menu do Usuário</h1>
		
		<nav class = "navbar navbar-expand-lg navbar-light bg-danger" style="font-size: 1.5em;">
			<ul class = "navbar-nav">
				<li class= "nav-item">
					<a class = "nav-link text-light navbar-link" href='<c:url value="/"/>'>Informações de usuário</a>
				</li>
			</ul>
		 </nav>
	</div>
</tags:pageTemplate>
  