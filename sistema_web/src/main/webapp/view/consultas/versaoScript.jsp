<%--
  Created by IntelliJ IDEA.
  User: bruno
  Date: 12/02/2022
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dados do Script ${versao.numVersao} versão ${versao.numScript}</title>
</head>
<body>
<div class="d-flex flex-row justify-content-center align-items-center">
    <p class="col-2" style="margin: 10px">Script: ${versao.numVersao}
    </p>
    <p class="col-2" style="margin: 10px">
        Versão ${versao.numScript}
    </p>
</div>


<h3>Algoritmo</h3>

<p>${versao.algoritmo}</p>
</body>
</html>
