<%--
  Created by IntelliJ IDEA.
  User: bruno
  Date: 12/02/2022
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pergunta do Jogo ${jogo.titulo}</title>
</head>
<body>
<div class="d-flex flex-row justify-content-center align-items-center">
    <p class="col-2" style="margin: 10px">Loja: ${pergunta.nomeLoja}</p>
    <p class="col-2" style="margin: 10px">Data da pergunta: ${pergunta.dataPergunta}</p>
    <p class="col-2" style="margin: 10px">Data da resposta: ${pergunta.dataResposta}</p>
    <p class="col-2" style="margin: 10px">Votos de utilidade da pergunta: ${pergunta.votosPergUtil}</p>
    
    
    
    
    
</div>
            
            
            <h1>Pergunta</h1>
            
            <p>${pergunta.textoPergunta}</p>
            
            <h1>Resposta</h1>
            
            <p>${pergunta.textoResposta}</p>
</body>
</html>



