<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="option"  required="true"%>

<tags:menuDoUsuarioNavbar></tags:menuDoUsuarioNavbar>
<jsp:include page="/WEB-INF/jsp/usuario/partial/${option}.jsp"/>


