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
    <div class="d-flex justify-content-around align-items-center">
        <a class="btn btn-outline-dark" style="margin: 10px"
           href="${pageContext.servletContext.contextPath}/loja/create">
            Cadastrar website/loja</a>
        <a class="btn btn-outline-dark" style="margin: 10px"
           href="${pageContext.servletContext.contextPath}/loja/getAll">
            Consultar dados de um website/loja</a>
    </div>
</div>
</body>
</html>