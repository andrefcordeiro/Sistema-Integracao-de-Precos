<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 09/02/2022
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/view/head.jsp" %>
    <title>Crawling</title>
</head>
<body>
<jsp:include page="../header.jsp"/>


<div class="d-flex flex-column justify-content-center align-items-center">
    <h1 class="display-3">Inserir dados - loja "${nome_loja}"</h1>
    <form class="form"
          action="${pageContext.servletContext.contextPath}/crawling/create?nome_loja=${nome_loja}"
          enctype="multipart/form-data"
          method="POST">

        <div class="form-group">
            <label for="output_crawling">Arquivo .json resultante do crawling: </label>
            <input type="file"
                   class="form-control" id="output_crawling"
                   name="output_crawling"
                   accept=".json"
                   required
            />
        </div>

        <div class="form-group">
            <label for="script_crawling">Arquivo .py usado no crawling: </label>
            <input type="file"
                   class="form-control" id="script_crawling"
                   name="script_crawling"
                   accept=".py"
                   required/>
        </div>

        <div class="form-group">
            <label class="control-label">Função do script: </label>
            <input class="form-control password-input"
                   type="text" name="funcao_script" required/>
        </div>

        <div class="text-center">
            <button class="btn btn-dark" type="submit">Executar</button>
        </div>
    </form>
</div>

</body>
</html>
