<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/view/head.jsp" %>

    <title>Interface pública</title>
</head>
<body>
<jsp:include page="./header.jsp"/>

<div class="container d-flex flex-column align-items-center">

    <%-- Input para pesquisa --%>
    <form class="form"
          style="padding-top: 50px; width: 75%; "
          action="${pageContext.servletContext.contextPath}/jogo/buscarPorTitulo"
          method="GET">

        <div class="container d-flex flex-row align-items-center input-group mb-3 mt-5"
             style="width: 100%">
            <input id="jogo_titulo"
                   class="form-control"
                   type="text" name="jogo_titulo"
                   placeholder="Insira o titulo do jogo"
                   required/>

            <div class="pl-5 text-center">
                <button id="buscar" class="btn btn-dark" type="submit">Buscar</button>
            </div>
        </div>
    </form>

    <div class="container d-flex flex-column justify-content-center" style="width: 90%; ">
        <%-- erro na consulta --%>
        <c:if test="session.getAttribute('error') != null">
            <p><strong><%=request.getAttribute("error")%>
            </strong></p>
        </c:if>

        <%--    Jogos retornados --%>
        <c:forEach items="${jogos}" var="item">
            <c:set var="jogo" value="${item}" scope="request"/>
            <c:set var="texto_preco" value="O menor preço encontrado para este produto é "
                   scope="request"/>
            <c:set var="mostrar_dados_jogo" value="false" scope="request"/>

            <div class="container mt-5 p-3">
                <jsp:include page="jogo.jsp"/>

                    <%-- Ofertas do jogo --%>
                <div class="container ml-5">
                    <p class="text-dark "><strong> Lojas que ofertam este jogo: </strong></p>
                    <div class="d-flex flex-row">
                        <c:forEach items="${jogo.ofertasJogo}" var="oferta">

                            <%-- Botão para acessar página com dados de um jogo em uma loja --%>
                            <form class="form"
                                  action="${pageContext.servletContext.contextPath}/jogo/buscarDadosJogoLoja"
                                  method="GET">

                                <input type="hidden" name="nome_loja" value="${oferta.nomeLoja}"/>
                                <input type="hidden" name="id_jogo" value="${item.idJogo}"/>

                                <button id="oferta-jogo" class="btn btn-dark mr-2" type="submit">
                                        ${oferta.nomeLoja}</button>
                            </form>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>