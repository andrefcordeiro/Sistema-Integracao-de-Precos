<%--
  Created by IntelliJ IDEA.
  User: bruno
  Date: 12/02/2022
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dados do Jogo ${jogo.titulo}</title>
</head>
<body>
<div class="d-flex flex-row justify-content-center align-items-center">
    <p class="col-2" style="margin: 10px">Nome: ${jogo.titulo}</p>
    <p class="col-2" style="margin: 10px">Seção: ${jogo.desenvolvedora}</p>
    <p class="col-2" style="margin: 10px">Seção: ${jogo.anoLancamento}</p>
    <p class="col-2" style="margin: 10px">Nome: ${jogo.fabricante}</p>
    <p class="col-2" style="margin: 10px">Seção: ${jogo.marca}</p>
    <p class="col-2" style="margin: 10px">Nome: ${jogo.multijogador}</p>
    <p class="col-2" style="margin: 10px">Seção: ${jogo.genero}</p>
    
    
    <div class="column col-6" style="margin: 10px">
        <a href="${pageContext.servletContext.contextPath}/consultas/getHistorico?id=${jogo.num}&loja=${loja_nome}">
            <button class="col-5 btn btn-outline-dark" style="margin: 10px" >Consultar historico de precos</button>
        </a>
        <a href="${pageContext.servletContext.contextPath}/consultas/getAvaliacoes?id=${jogo.num}&loja=${loja_nome}">
            <button class="col-5 btn btn-outline-dark" style="margin: 10px" >Consultar Avaliações</button>
        </a>
        <a href="${pageContext.servletContext.contextPath}/consultas/getPerguntas?id=${jogo.num}&loja=${loja_nome}">
            <button class="col-5 btn btn-outline-dark" style="margin: 10px" >Consultar Perguntas</button>
        </a>
        
    </div>
</div>
            <img src="${jogo.urlCapa}">
            
            <h1>Descrição</h1>
            
            <p>${jogo.descricao}</p>
</body>
</html>

