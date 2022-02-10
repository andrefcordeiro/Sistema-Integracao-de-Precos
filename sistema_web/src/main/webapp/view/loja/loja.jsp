<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 09/02/2022
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Websites/Lojas cadastradas</title>
</head>
<body>
<div class="container">
    <div class="row">
        <p class="col-2">Nome: ${loja.nome}</p>
        <p class="col-2">Seção: ${loja.nomeSecao}</p>
        <div class="column col-6">
            <button class="col-5 btn btn-outline-dark">Consultar jogos</button>
            <a class="col-5 btn btn-outline-dark"
               href="${pageContext.servletContext.contextPath}/jogo/create?nome_loja=${loja.nome}">
                Inserir novos dados</a>
        </div>
    </div>
</div>
</body>
</html>
