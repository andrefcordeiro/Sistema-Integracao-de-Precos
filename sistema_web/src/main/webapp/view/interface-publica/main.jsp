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
    <%--          action="${pageContext.servletContext.contextPath}/loja/create"--%>
          method="POST">

        <div class="d-flex flex-row align-items-center input-group mb-3">
            <input id="jogo-nome" class="form-control" type="text" name="nome"
                   placeholder="Insira o nome do jogo" required/>

            <div class="pl-5 text-center">
                <button class="btn btn-dark " type="submit">Buscar</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>