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
    <title>Avaliação do Jogo ${jogo.titulo}</title>
</head>
<body>
<div class="d-flex flex-row justify-content-center align-items-center">
    <p class="col-2" style="margin: 10px">Jogo: ${jogo.titulo}</p>
    <p class="col-2" style="margin: 10px">Data: ${avaliacao.dataRealizacao}</p>
    <p class="col-2" style="margin: 10px">Nota: ${avaliacao.estrelas}</p>
    <p class="col-2" style="margin: 10px">Avaliador: ${avaliacao.nomeAvaliador}</p>
    <p class="col-2" style="margin: 10px">País: ${avaliacao.paisAvaliador}</p>
    <p class="col-2" style="margin: 10px">Votos: ${avaliacao.votosAvalUtil}</p>
    
    
    
    
</div>
            
            
            <h1>${avaliacao.titulo}</h1>
            
            <p>${avaliacao.texto}</p>
</body>
</html>


