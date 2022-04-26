<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 08/02/2022
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/view/head.jsp" %>
    <title>Consulta de website</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<div class="container d-flex flex-column align-items-center mt-5">
    <h1 class="display-3 pb-5"> Lojas cadastradas </h1>
    <jsp:useBean id="lojas" scope="request" type="java.util.List"/>
    <div class="container bg-dark rounded">
        <c:forEach items="${lojas}" var="item">
            <c:set var="loja" value="${item}" scope="request"/>
            <jsp:include page="loja.jsp"/>
        </c:forEach>
    </div>
</div>
</body>
</html>
