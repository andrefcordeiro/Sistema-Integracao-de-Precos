<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 25/04/2022
  Time: 09:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/view/head.jsp" %>
    <title>Página inicial</title>
</head>
<body>
<div class="container d-flex flex-column justify-content-center align-items-center" style="height: 100vh">
    <%--    <h1 class="p-2 display-3">Página inicial </h1>--%>

    <div class="d-flex justify-content-around align-items-center">
        <a class="btn btn-outline-dark" style="margin: 10px"
           href="${pageContext.request.contextPath}/view/interface-publica/main.jsp">
            Interface pública</a>
        <a class="btn btn-outline-dark" style="margin: 10px"
           href="${pageContext.request.contextPath}/view/interface-privada/main.jsp">
            Interface privada</a>
    </div>
</div>
</body>
</html>