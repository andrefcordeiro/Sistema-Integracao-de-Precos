<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 09/02/2022
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Websites/Lojas cadastradas</title>
</head>
<body>
<div class="container">
    <div class="row">
        <p class="col-2">Nome: ${loja.nome}</p>
        <p class="col-2">Seção: ${loja.nomeSecao}</p>
        <div class="column col-6">
            <button class="col-5 btn btn-outline-dark">Consultar jogos </button>
            <button class="col-5 btn btn-outline-dark">Inserir novos dados</button>
        </div>
    </div>
</div>
</body>
</html>
