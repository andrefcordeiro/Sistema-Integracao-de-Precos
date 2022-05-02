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
    <div class="d-flex flex-row">
        <p><strong>"${pergunta.textoPergunta}"</strong></p>
        <c:if test="${pergunta.dataPergunta != null}">
            <p class="ml-5 mr-5"> Data:${pergunta.dataPergunta}</p>
        </c:if>
        <div class="d-flex flex-row ml-auto">
            <p class="ml-5 mr-2">${pergunta.votosPergUtil}</p>
            <img src="https://img.icons8.com/external-kmg-design-glyph-kmg-design/32/000000/external-like-feedback-kmg-design-glyph-kmg-design.png"
                 height="20px"/>
        </div>
    </div>

    <div class="d-flex flex-row">
        <c:if test="${pergunta.textoResposta != ''}">
            <p>R: "${pergunta.textoResposta}"</p>
            <c:if test="${pergunta.dataPergunta != null}">
                <p class="ml-5 mr-5"> Data: ${pergunta.dataPergunta}</p>
            </c:if>
        </c:if>
    </div>

</div>
</body>
</html>
