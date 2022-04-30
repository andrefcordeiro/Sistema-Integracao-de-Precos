<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 26/04/2022
  Time: 08:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Jogo</title>
</head>
<body>
<a>
    <div class="d-flex flex-row m-5 mb-0">

        <img src=${jogo.urlCapa} alt="urlCapaJogo" style="height: 250px;">

        <div class="d-flex flex-column ml-5 p-3 border rounded" style="width: 100%;">
            <h3 class="text-dark m-3">${jogo.titulo}</h3>
            <p class="text-dark m-2 ml-3"><strong>Data de lançamento:</strong> ${jogo.dataLancamento}
            </p>
            <c:if test="${jogo.desenvolvedora} != null">
                <p class="text-dark m-2 ml-3"><strong>Desenvolvedora:</strong> ${jogo.descricao}</p>
            </c:if>
            <c:if test="${jogo.genero} != null">
                <p class="text-dark m-2 ml-3"><strong>Gênero:</strong> ${jogo.genero}</p>
            </c:if>
            <c:if test="${jogo.multijogador} != null">
                <p class="text-dark m-2 ml-3"><strong>Multijogador:</strong> ${jogo.multijogador}</p>
            </c:if>
            <c:if test="${jogo.fabricante} != null">
                <p class="text-dark m-2 ml-3"><strong>Fabricante:</strong> ${jogo.fabricante}</p>
            </c:if>
            <c:if test="${jogo.marca} != null">
                <p class="text-dark m-2 ml-3"><strong>Marca:</strong> ${jogo.marca}</p>
            </c:if>
            <p class="text-dark m-2 ml-3"><strong>Descrição:</strong> ${jogo.descricao}</p>
        </div>
    </div>

</a>
</body>
</html>
