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
<nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="position:fixed; width: 100%;">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <div class="container d-flex flex-row mr-5">
                    <li class="nav-item">
                        <a class="navbar-brand"
                           href="${pageContext.request.contextPath}/index.jsp">
                            Início</a>
                    </li>
                    <li class="nav-item">
                        <form class="form"
                              action="${pageContext.servletContext.contextPath}/estatisticasGerais"
                              method="GET">
                            <button id="buscar" class="btn navbar-brand" type="submit">
                                Estatísticas gerais
                            </button>
                        </form>

                    </li>
                </div>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
