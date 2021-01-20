<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ attribute name="title" required="true"%>
<%@ attribute name="css" required="false" %>
<%@ attribute name="customCss" required="false" %>

<fmt:setLocale value="pt-BR" scope="application" />

<c:url value="${pageContext.request.contextPath}/resources/imagens/" var="pathImagens" scope="application"></c:url>
<c:url value="${pageContext.request.contextPath}/resources/css/" var="pathCss" scope="application"></c:url>
<c:url value="${pageContext.request.contextPath}/resources/js/" var="pathJs" scope="application"></c:url>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
		<title>${title} - Pizzaria</title>
		<link rel="icon" href="${pathImagens}favicon.png">
		<tags:linkBootstrap/>
		<link rel="stylesheet" href="${pathCss}main.css">
		<c:if test="${ not empty css }" >
			<link rel="stylesheet" href="${pathCss}${css}">
		</c:if>
		<c:if test="${ not empty customCss }" >
			<link rel="stylesheet" href="${pathCss}${customCss}">
		</c:if>
		<link rel="stylesheet" href="${pathCss}navbar.css">
		
	</head>
	<body >
		<input id="context-path" type="hidden" value="${pageContext.request.contextPath}">
		<input id="_csrf" type="hidden" value="${_csrf.token}"/>
		<input id="_csrf_header" type="hidden" value="${_csrf.headerName}"/>
		<jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
		<jsp:doBody/>
		<script src="${pathJs}jQuery/jquery-3.2.1.slim.min.js"></script>
		<script src="${pathJs}bootstrap/popper.min.js"></script>
		<script src="${pathJs}bootstrap/bootstrap.min.js"></script>
	</body>
</html>