<%--
  Created by IntelliJ IDEA.
  User: bruno
  Date: 12/02/2022
  Time: 18:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/view/head.jsp" %>
    <title>Versões do Script n ${num_script}</title>
</head>
<body>

<jsp:include page="../../header.jsp"/>

<div class="d-flex flex-column justify-content-center align-items-center">
    <h1 class="display-3"> Versões existentes </h1>
    <jsp:useBean id="versoes" scope="request" type="java.util.List"/>
    <c:forEach items="${versoes}" var="item">
        <c:set var="versao" value="${item}" scope="request"/>
        <jsp:include page="versaoScript.jsp"/>
    </c:forEach>
</div>
</body>
</html>
