<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:pageTemplate title="Menu do usuario" css="home.css">
	<div class="p-3 d-flex justify-content-between bg-transparent" style="height: 100%; width: 100%;">
		<nav class = "nav flex-column navbar-dark p-3 transparent-dark " style="width: 18%;">
	 		<a class="navbar-brand" href="${contextPath}/usuarios/menu/info">Menu do usuário</a>
			<ul class = "navbar-nav">
				<li class= "nav-item">
					<a class = "nav-link navbar-link" href="${contextPath}/usuarios/menu/info">Informações de usuário</a>
				</li>
			</ul>
		</nav>
		<div class="container">
			<jsp:include page="/WEB-INF/jsp/usuario/partial/${option}.jsp"/>
		</div>
	</div>
</tags:pageTemplate>
  