<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 01/05/2022
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Avaliacao</title>
</head>
<body>
<div class="container d-flex flex-column border rounded p-4 m-2">
    <p>"${avaliacao.titulo}"</p>
    <p>"${avaliacao.texto}"</p>
    <div class="d-flex flex-row ">
        <strong>Autor: "${avaliacao.nomeAvaliador}"</strong>
        <div class="d-flex flex-row ml-5">
            <c:forEach begin="1" end="${avaliacao.estrelas}" varStatus="loop">
                <img src="https://img.icons8.com/color/48/000000/filled-star--v1.png"
                     height="30px"/>
            </c:forEach>
        </div>
        <p class="ml-5 mr-5">${avaliacao.dataRealizacao}</p>
        <div class="d-flex flex-row ml-auto">
            <p class="ml-5 mr-2">${avaliacao.votosAvalUtil}</p>
            <img src="https://img.icons8.com/external-kmg-design-glyph-kmg-design/32/000000/external-like-feedback-kmg-design-glyph-kmg-design.png"
                 height="20px"/>
        </div>
    </div>

</div>
</body>
</html>
