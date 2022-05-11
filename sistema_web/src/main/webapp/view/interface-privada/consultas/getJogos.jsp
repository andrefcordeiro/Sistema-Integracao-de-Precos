<%--
  Created by IntelliJ IDEA.
  User: bruno
  Date: 12/02/2022
  Time: 18:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/view/head.jsp" %>
    <title>Jogos da loja - ${nome_loja}</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<div class="d-flex flex-column justify-content-center align-items-center">
    <h1 class="display-3"> Jogos cadastrados - ${nome_loja} </h1>
    <jsp:useBean id="jogos" scope="request" type="java.util.List"/>
    <c:forEach items="${jogos}" var="item">
        <c:set var="jogo" value="${item}" scope="request"/>
        <jsp:include page="jogo.jsp">
            <jsp:param name="loja" value="${nome_loja}" />
        </jsp:include>
    </c:forEach>
</div>
</body>
</html>
