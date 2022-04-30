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

    <form class="form"
          style="padding-top: 50px; width: 75%; "
          action="${pageContext.servletContext.contextPath}/jogo/buscarPorTitulo"
          method="GET">

        <div class="container d-flex flex-row align-items-center input-group mb-3"
             style="width: 100%">
            <input id="jogo-titulo"
                   class="form-control"
                   type="text" name="jogo-titulo"
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

        <c:forEach items="${jogos}" var="item">
            <c:set var="jogo" value="${item}" scope="request"/>

            <div class="container mb-5">
                <jsp:include page="jogo.jsp"/>

                    <%-- Ofertas do jogo --%>
                <div class="container ml-5">
                    <p class="text-dark "><strong> Lojas que ofertam este jogo: </strong></p>
                    <div class="container d-flex flex-row">
                        <c:forEach items="${jogo.ofertasJogo}" var="item">
                            <a id="oferta-jogo" class="btn text-white btn-dark mr-2"
                               type="submit">${item.nomeLoja}</a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>