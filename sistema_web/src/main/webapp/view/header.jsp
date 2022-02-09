<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 09/02/2022
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Header</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
<%--        <a class="navbar-brand" href="../index.jsp">Início</a>--%>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="navbar-brand"
                       href="${pageContext.servletContext.contextPath}/loja/create">
                        Cadastrar loja</a>
                </li>
                <li class="nav-item">
                    <a class="navbar-brand"
                       href="${pageContext.servletContext.contextPath}/loja/getAll">
                        Consultar loja</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
