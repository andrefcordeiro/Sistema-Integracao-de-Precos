<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 30/04/2022
  Time: 18:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c-rt" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Dados do jogo</title>
    <%@include file="/view/head.jsp" %>

</head>
<body>
<jsp:include page="./header.jsp"/>
<div class="container d-flex flex-column align-items-center p-3">
    <h1 class="mt-5 mb-5 text-dark"></h1>
    <jsp:include page="jogo.jsp">
        <jsp:param name="texto_preco"
                   value="O último preço encontrado para este produto foi de "/>
        <jsp:param name="mostrar_dados_jogo"
                   value="true"/>
    </jsp:include>

    <c-rt:set var="pathSecao"
              value="${pageContext.request.getAttribute('javax.servlet.forward.request_uri')}?${pageContext.request.queryString}"
              scope="page"/>

    <%-- Links para seções --%>
    <div class="d-flex flex-row mt-5">
        <jsp:include page="linkSecaoDown.jsp">
            <jsp:param name="href"
                       value="${pageScope.pathSecao}#avaliacoes"/>
            <jsp:param name="nome" value="Avaliações"/>
        </jsp:include>
    </div>

    <%-- Estatísticas --%>
    <jsp:include page="estatisticas.jsp"/>

    <div class="d-flex flex-row mt-5">
        <jsp:include page="linkSecaoDown.jsp">
            <jsp:param name="href"
                       value="${pageScope.pathSecao}#perguntas"/>
            <jsp:param name="nome" value="Perguntas"/>
        </jsp:include>
    </div>

    <%-- Avaliações --%>
    <div id="avaliacoes" class="d-flex flex-column align-items-center m-5" style="width: 80%">
        <h2 class="text-dark m-5"> Avaliações</h2>

        <div class="d-flex flex-column">
            <c:forEach items="${jogo.avaliacoesClientes}" var="item">
                <c:set var="avaliacao" value="${item}" scope="request"/>
                <jsp:include page="avaliacao.jsp"/>
            </c:forEach>
        </div>
    </div>

    <%-- Perguntas --%>
    <div id="perguntas" class="d-flex flex-column align-items-center m-5" style="width: 80%">

        <jsp:include page="linkSecaoUp.jsp">
            <jsp:param name="href"
                       value="${pageScope.pathSecao}#avaliacoes"/>
            <jsp:param name="nome" value="Avaliações"/>
        </jsp:include>
        <h2 class="text-dark m-5"> Perguntas</h2>

        <c:forEach items="${jogo.perguntasClientes}" var="item">
            <c:set var="pergunta" value="${item}" scope="request"/>
            <jsp:include page="pergunta.jsp"/>
        </c:forEach>
    </div>
</div>
</body>
</html>
