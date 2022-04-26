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
<div class="d-flex flex-row justify-content-center align-items-center">
    <p class="col-2 text-light" style="margin: 10px">Nome: ${loja.nome}</p>
    <p class="col-2 text-light" style="margin: 10px">Seção: ${loja.nomeSecao}</p>
    <div class="column col-6" style="margin: 10px">
        <a href="${pageContext.servletContext.contextPath}/consultas/getJogos?nome_loja=${loja.nome}">
            <button class="col-5 btn btn-outline-light" style="margin: 10px" >Consultar jogos</button>
        </a>
        <a href="${pageContext.servletContext.contextPath}/consultas/getScripts?nome_loja=${loja.nome}">
            <button class="col-5 btn btn-outline-light" style="margin: 10px" >Consultar Scripts</button>
        </a>
        <a class="col-5 btn btn-outline-light" style="margin: 10px"
           href="${pageContext.servletContext.contextPath}/crawling/create?nome_loja=${loja.nome}">
            Inserir novos dados</a>
    </div>
</div>
</body>
</html>
