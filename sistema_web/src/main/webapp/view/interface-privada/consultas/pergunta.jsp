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
<table>
    <tr>
        <td>
            <h3>Pergunta</h3>

            <p>${pergunta.textoPergunta}</p>

            <h3>Resposta</h3>

            <p>${pergunta.textoResposta}</p>
        </td>
        <td>
            <p class="col-2" style="margin: 10px">Loja: ${pergunta.nomeLoja}</p>
            <p class="col-2" style="margin: 10px">Data da pergunta: ${pergunta.dataPergunta}</p>
            <p class="col-2" style="margin: 10px">Data da resposta: ${pergunta.dataResposta}</p>
            <p class="col-2" style="margin: 10px">Votos de utilidade da pergunta: ${pergunta.votosPergUtil}</p>





        </td>


    </tr>
</table>
</body>
</html>
