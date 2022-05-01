<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 30/04/2022
  Time: 18:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Dados do jogo</title>
    <%@include file="/view/head.jsp" %>

</head>
<body>
<jsp:include page="./header.jsp"/>
<div class="container d-flex flex-column align-items-center mt-5 p-3">
    <h1 class="mb-5">${jogo.nomeLoja}</h1>
    <c:set var="texto_preco" value="O último preço encontrado para este produto foi de "
           scope="request"/>
    <c:set var="mostrar_dados_jogo" value="true"
           scope="request"/>
    <jsp:include page="jogo.jsp"/>

    <div class="d-flex flex-column align-items-center m-5" style="width: 80%">
        <h2>Avaliações</h2>
        <c:forEach items="${jogo.avaliacoesClientes}" var="item">
            <c:set var="avaliacao" value="${item}" scope="request"/>
            <jsp:include page="avaliacao.jsp"/>
        </c:forEach>
    </div>
</div>
</body>
</html>
