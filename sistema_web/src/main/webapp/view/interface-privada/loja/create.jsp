<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 08/02/2022
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/view/head.jsp" %>
    <title>Cadastro de website</title>
</head>
<body>

<jsp:include page="../../header.jsp"/>


<div class="d-flex flex-column justify-content-center align-items-center">
    <h1 class="display-3"> Cadastrar website/loja </h1>
    <form class="form"
          action="${pageContext.servletContext.contextPath}/loja/create"
          method="POST">

        <div class="form-group">
            <label class="control-label" for="website-nome">Nome</label>
            <input id="website-nome" class="form-control" type="text" name="nome" required/>

            <p class="help-block"></p>
        </div>

        <div class="form-group">
            <label class="control-label">Nome da seção no website</label>
            <input class="form-control password-input"
                   type="text" name="nome_secao" required/>
        </div>

        <div class="text-center">
            <button class="btn btn-dark" type="submit">Cadastrar</button>
        </div>
    </form>
</div>

</body>
</html>
