<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ attribute name="title" required="true"%>
<%@ attribute name="css" required="false" %>

<fmt:setLocale value="pt-BR" scope="application" />

<c:url value="${pageContext.request.contextPath}/resources/imagens/" var="pathImagens" scope="application"></c:url>
<c:url value="${pageContext.request.contextPath}/resources/css/" var="pathCss" scope="application"></c:url>
<c:url value="${pageContext.request.contextPath}/resources/js/" var="pathJs" scope="application"></c:url>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
		<tags:linkBootstrap/>
		<title>${title} - Pizzaria</title>
		<link rel="icon" href="${pathImagens}favicon.png">
		<link rel="stylesheet" href="${pathCss}main.css">
		<c:if test="${ not empty css }" >
			<link rel="stylesheet" href="${pathCss}${css}">
		</c:if>
		<link rel="stylesheet" href="${pathCss}navbar.css">
		
	</head>
	<body >
		<input id="context-path" type="hidden" value="${pageContext.request.contextPath}">
		<jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
		<jsp:doBody/>
		<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
		<script src="${pathJs}bootstrap/bootstrap.min.js"></script>
	</body>
</html>