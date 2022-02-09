<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/view/head.jsp" %>
    <title>Página inicial</title>
</head>
<body>
<h1><%= "Página inicial" %>
</h1>
<br/>
<div style="display: flex; flex-direction: column; margin: 20px">
    <a href="${pageContext.servletContext.contextPath}/website/create">
        Cadastrar website/loja</a>
    <a href="${pageContext.servletContext.contextPath}/website/get">
        Consultar dados cadastrados de um website/loja</a>
</div>
</body>
</html>